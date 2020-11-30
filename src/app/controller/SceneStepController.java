package app.controller;

import app.SMO.Event;
import app.SMO.Handle;
import app.SMO.Request;
import app.SMO.SMO;
import app.SceneFxmlApp;
import app.entity.StepModBufferRow;
import app.entity.StepModEventRow;
import app.entity.StepModHandleRow;
import app.model.SceneName;
import app.model.Stageable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class SceneStepController implements Stageable {

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
	Label typeMessageLabel;

	@FXML
	Label deniedRequest;

	@FXML
	TableView handlesTable;
	@FXML
	private TableColumn<StepModHandleRow, Integer> handleNumberColumn;
	@FXML
	private TableColumn<StepModHandleRow, Integer> requestNumberColumn;
	@FXML
	private TableColumn<StepModHandleRow, Double> releaseTimeColumn;

	@FXML
	TableView bufferTable;
	@FXML
	private TableColumn<StepModBufferRow, Integer> bufferIndexNumberColumn;
	@FXML
	private TableColumn<StepModBufferRow, Integer> requestNumberBufferColumn;
	@FXML
	private TableColumn<StepModBufferRow, Double> BufferGenTimeColumn;

	@FXML
	TableView eventTable;
	@FXML
	private TableColumn<StepModEventRow, String> eventTypeColumn;
	@FXML
	private TableColumn<StepModEventRow, Integer> requestNumberEventColumn;
	@FXML
	private TableColumn<StepModEventRow, Double> eventTimeColumn;

	@FXML
	private void initialize() {
		bufferSizeTextField.setText(Integer.toString(3));
		lambdaTextField.setText(Integer.toString(6));
		alphaTextField.setText(Double.toString(0.3));
		betaTextField.setText(Double.toString(0.6));
		sourceCountTextField.setText(Integer.toString(3));
		handleCountTextField.setText(Integer.toString(3));

		handleNumberColumn.setCellValueFactory(new PropertyValueFactory<StepModHandleRow, Integer>("handleNumber"));
		requestNumberColumn.setCellValueFactory(new PropertyValueFactory<StepModHandleRow, Integer>("requestNumber"));
		releaseTimeColumn.setCellValueFactory(new PropertyValueFactory<StepModHandleRow, Double>("releaseTime"));

		bufferIndexNumberColumn.setCellValueFactory(new PropertyValueFactory<StepModBufferRow, Integer>("index"));
		requestNumberBufferColumn.setCellValueFactory(new PropertyValueFactory<StepModBufferRow, Integer>("requestNumber"));
		BufferGenTimeColumn.setCellValueFactory(new PropertyValueFactory<StepModBufferRow, Double>("genTime"));

		eventTypeColumn.setCellValueFactory(new PropertyValueFactory<StepModEventRow, String>("type"));
		requestNumberEventColumn.setCellValueFactory(new PropertyValueFactory<StepModEventRow, Integer>("requestNumber"));
		eventTimeColumn.setCellValueFactory(new PropertyValueFactory<StepModEventRow, Double>("eventTime"));
	}

	@FXML
	private void handleOnActionClose(ActionEvent event) {
		stage.close();
	}

	@FXML
	private void handleOnActionInteractiveButton(ActionEvent event) {
		stage.setScene(SceneFxmlApp.getScenes().get(SceneName.SCENE_INTERACTIVE).getScene());
	}

	@FXML
	private void handleOnActionNextStepButton(ActionEvent event) {
		smo.doStep();
		showResults();
	}


	@FXML
	private void handleOnActionStartButton(ActionEvent event) {
		initSmo();
		smo.doStep();
		showResults();
	}

	private void showResults() {
		typeMessageLabel.setText(smo.getTypeMessage());


		Handle[] handles = smo.getHandles();
		Request[] bufferRequests = smo.getBuffer().getRequestArray();
		List<Event> events = smo.getEventList();

		ObservableList<StepModHandleRow> handlesTableItems = handlesTable.getItems();
		handlesTable.getItems().clear();

		for (int i = 0; i < handles.length; i++) {
			handlesTableItems.add(new StepModHandleRow((i + 1),
					handles[i].getRequestNumber(),
					handles[i].getEndTime()
			));
		}

		ObservableList<StepModEventRow> eventTableItems = eventTable.getItems();
		eventTable.getItems().clear();

		for (Event event : events) {
			eventTableItems.add(new StepModEventRow(event.getType().toString(),
					event.getNumber(),
					event.getEndTime()
			));
		}

		ObservableList<StepModBufferRow> bufferTableItems = bufferTable.getItems();
		bufferTable.getItems().clear();

		for (int i = 0; i < bufferRequests.length; i++) {
			if (bufferRequests[i] == null){
				bufferTableItems.add(new StepModBufferRow((i + 1),
						0,
						0
				));
			} else {
				bufferTableItems.add(new StepModBufferRow((i + 1),
						bufferRequests[i].getNumber(),
						bufferRequests[i].getEndTime()
				));
			}
		}
		Request request = smo.getDenyRequest();
		if (request == null) {
			deniedRequest.setText("No denied request");
		} else {
			deniedRequest.setText("Denied request: " + request.getNumber() + " generationTime: " + request.getEndTime());
		}
	}

	private void initSmo() {
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

		smo = new SMO(Integer.parseInt(sourceCountTextField.getText()),
				Integer.parseInt(handleCountTextField.getText()),
				Integer.parseInt(bufferSizeTextField.getText()),
				Double.parseDouble(lambdaTextField.getText()),
				Double.parseDouble(betaTextField.getText()),
				Double.parseDouble(alphaTextField.getText())
		);
	}

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
