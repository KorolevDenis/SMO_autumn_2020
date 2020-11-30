package app.controller;

import app.SMO.Handle;
import app.SMO.SMO;
import app.SMO.Source;
import app.SceneFxmlApp;
import app.entity.HandleResultsRow;
import app.entity.SourceResultsRow;
import app.model.SceneName;
import app.model.Stageable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SceneInteractiveController implements Stageable {
	private Stage stage;
	private SMO smo;

	@FXML
	TextField bufferSizeTextField;
	@FXML
	TextField lambdaTextField;
	@FXML
	TextField alphaTextField;
	@FXML
	TextField betaTextField;
	@FXML
	TextField handleCountTextField;
	@FXML
	TextField sourceCountTextField;
	@FXML
	TextField requestCountTextField;

	@FXML
	TableView<SourceResultsRow> sourcesResultsTable;
	@FXML
	private TableColumn<SourceResultsRow, Integer> sourceNumberColumn;
	@FXML
	private TableColumn<SourceResultsRow, Integer> sourceCountColumn;
	@FXML
	private TableColumn<SourceResultsRow, Double> denyProbabilityColumn;
	@FXML
	private TableColumn<SourceResultsRow, Double> systemTimeColumn;
	@FXML
	private TableColumn<SourceResultsRow, Double> processTimeColumn;
	@FXML
	private TableColumn<SourceResultsRow, Double> bufferTimeColumn;
	@FXML
	private TableColumn<SourceResultsRow, Double> dispProcessTimeColumn;
	@FXML
	private TableColumn<SourceResultsRow, Double> dispBufferTTimeColumn;


	@FXML
	TableView<HandleResultsRow> handlesResultsTable;
	@FXML
	private TableColumn<HandleResultsRow, Integer> handleNumberColumn;
	@FXML
	private TableColumn<HandleResultsRow, Double> coefficientColumn;

	@FXML
	private void initialize() {
		bufferSizeTextField.setText(Integer.toString(6));
		lambdaTextField.setText(Integer.toString(1));
		alphaTextField.setText(Double.toString(0.3));
		betaTextField.setText(Double.toString(0.8));
		sourceCountTextField.setText(Integer.toString(6));
		handleCountTextField.setText(Integer.toString(4));
		requestCountTextField.setText(Integer.toString(20000));

		sourceNumberColumn.setCellValueFactory(new PropertyValueFactory<SourceResultsRow, Integer>("sourceNumber"));
		sourceCountColumn.setCellValueFactory(new PropertyValueFactory<SourceResultsRow, Integer>("sourceCount"));
		denyProbabilityColumn.setCellValueFactory(new PropertyValueFactory<SourceResultsRow, Double>("denyProbability"));
		systemTimeColumn.setCellValueFactory(new PropertyValueFactory<SourceResultsRow, Double>("systemTime"));
		processTimeColumn.setCellValueFactory(new PropertyValueFactory<SourceResultsRow, Double>("processTime"));
		bufferTimeColumn.setCellValueFactory(new PropertyValueFactory<SourceResultsRow, Double>("bufferTime"));
		dispProcessTimeColumn.setCellValueFactory(new PropertyValueFactory<SourceResultsRow, Double>("dispProcessTime"));
		dispBufferTTimeColumn.setCellValueFactory(new PropertyValueFactory<SourceResultsRow, Double>("dispBufferTTime"));

		handleNumberColumn.setCellValueFactory(new PropertyValueFactory<HandleResultsRow, Integer>("handleNumber"));
		coefficientColumn.setCellValueFactory(new PropertyValueFactory<HandleResultsRow, Double>("coefficient"));
	}

	@FXML private void handleOnActionClose(ActionEvent event) {
		stage.close();
	}

	@FXML private void handleOnStartButton(ActionEvent event) {
		if (bufferSizeTextField.getText() == null || bufferSizeTextField.getText().trim().isEmpty()) {
			return;
		}

		if (lambdaTextField.getText() == null || lambdaTextField.getText().trim().isEmpty()) {
			return;
		}

		if (alphaTextField.getText() == null || alphaTextField.getText().trim().isEmpty()) {
			return;
		}

		if (betaTextField.getText() == null || betaTextField.getText().trim().isEmpty()) {
			return;
		}

		if (handleCountTextField.getText() == null || handleCountTextField.getText().trim().isEmpty()) {
			return;
		}

		if (sourceCountTextField.getText() == null || sourceCountTextField.getText().trim().isEmpty()) {
			return;
		}

		if (requestCountTextField.getText() == null || requestCountTextField.getText().trim().isEmpty()) {
			return;
		}

		smo = new SMO(Integer.parseInt(sourceCountTextField.getText()),
				Integer.parseInt(handleCountTextField.getText()),
				Integer.parseInt(bufferSizeTextField.getText()),
				Double.parseDouble(lambdaTextField.getText()),
				Double.parseDouble(betaTextField.getText()),
				Double.parseDouble(alphaTextField.getText())
		);

		smo.init(Integer.parseInt(requestCountTextField.getText()));
		showResults();
	}

	@FXML private void handleOnNextButton(ActionEvent event) {
		if (requestCountTextField.getText() == null || requestCountTextField.getText().trim().isEmpty()) {
			return;
		}

		smo.init(Integer.parseInt(requestCountTextField.getText()));
		showResults();
	}

	@FXML private void handleOnActionStepModButton(ActionEvent event) {
		stage.setScene(SceneFxmlApp.getScenes().get(SceneName.SCENE_STEP).getScene());
	}

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void showResults() {
		Source[] sources = smo.getSources();

		double sumProcessM = 0;
		double sumSqrProcessM = 0;
		double sumBufferM = 0;
		double sumSqrBufferM = 0;
		for (int i = 0; i < sources.length; i++) {
			sumProcessM += sources[i].getAverageProcessTime()*sources[i].getDenyProbability();
			sumSqrProcessM += (sources[i].getAverageProcessTime()*sources[i].getAverageProcessTime())*sources[i].getDenyProbability();
			sumBufferM += sources[i].getAverageBufferTime()*sources[i].getDenyProbability();
			sumSqrBufferM += (sources[i].getAverageBufferTime()*sources[i].getAverageBufferTime())*sources[i].getDenyProbability();
		}

		Double dispProcessTime = sumSqrProcessM - sumProcessM*sumProcessM;
		Double dispBufferTime = sumSqrBufferM - sumBufferM*sumProcessM;

		ObservableList<SourceResultsRow> sourceResultsRows = sourcesResultsTable.getItems();
		sourcesResultsTable.getItems().clear();

		for (int i = 0; i < sources.length; i++) {
			sourceResultsRows.add(new SourceResultsRow((i + 1),
					sources[i].getSourceCount(),
					sources[i].getDenyProbability(),
					sources[i].getAverageSystemTime(),
					sources[i].getAverageProcessTime(),
					sources[i].getAverageBufferTime(),
					dispProcessTime,
					dispBufferTime
			));
		}

		Handle[] handles = smo.getHandles();

		ObservableList<HandleResultsRow> handleResultsRows = handlesResultsTable.getItems();
		handlesResultsTable.getItems().clear();

		for (int i = 0; i < handles.length; i++) {
			handleResultsRows.add(new HandleResultsRow((i + 1),
					handles[i].getCoefficient()
			));
		}
	}
}
