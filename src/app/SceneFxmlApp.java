package app;

import app.model.SceneName;
import app.util.FxmlInfo;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class SceneFxmlApp extends Application {

	private static final String SCENE_STEP_FXML = "/fxml/scene-step.fxml";
	private static final String SCENE_INTERACTIVE_FXML = "/fxml/scene-interactive.fxml";

	/** Holds the information for various scenes to switch between */
	private static Map<SceneName, FxmlInfo> scenes = new HashMap<>();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		
		//scenes.put(SceneName.MAIN, new FxmlInfo(MAIN_FXML, SceneName.MAIN, stage));
		scenes.put(SceneName.SCENE_STEP, new FxmlInfo(SCENE_STEP_FXML, SceneName.SCENE_STEP, stage));
		scenes.put(SceneName.SCENE_INTERACTIVE, new FxmlInfo(SCENE_INTERACTIVE_FXML,
				SceneName.SCENE_INTERACTIVE, stage));
		
		// getScene() will load the FXML file the first time
		stage.setScene(scenes.get(SceneName.SCENE_STEP).getScene());
		stage.setTitle("Multi-Scene Demo");
		stage.show();
	}

	public static Map<SceneName, FxmlInfo> getScenes() {
		return scenes;
	}

	public static void updateScenes(SceneName name, FxmlInfo info) {
		scenes.put(name, info);
	}

}
