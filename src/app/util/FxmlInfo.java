package app.util;

import app.model.SceneName;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class FxmlInfo {
	
	private String resourceName;
	private SceneName sceneName;
	private Stage stage;
	private Scene scene;

	public FxmlInfo(String resourceName, SceneName sceneName, Stage stage) {
		this.resourceName = resourceName;
		this.sceneName = sceneName;
		this.stage = stage;
	}
	
	/** @return the resource name for this FXML file */
	public String getResourceName() {
		return resourceName;
	}
	
	/** @return the {@link SceneName} for this FXML file */
	public SceneName getSceneName() {
		return sceneName;
	}
	
	/** @param scene the scene to set, loaded from this FxmlInfo */
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Scene getScene() {
		if (scene == null) {
			scene = new FxmlLoad().load(this);
		}
		return scene;
	}
	
	/** @return {@code true} if the scene has been built, otherwise {@code false} */
	public boolean hasScene() {
		return scene != null;
	}
	
	/** @return the primary stage for this Scene */
	public Stage getStage() {
		return stage;
	}
	
}
