package app.util;

import app.SceneFxmlApp;
import app.model.Stageable;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class FxmlLoad {

	public Scene load(FxmlInfo fxmlInfo) {
		
		if (fxmlInfo.hasScene()) {
			return fxmlInfo.getScene();
		}

		URL url = getClass().getResource(fxmlInfo.getResourceName());

		if (url == null) {
			Platform.exit();
			return null;
		}

		FXMLLoader loader = new FXMLLoader(url);
		Scene scene;

		try {
			scene = new Scene(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
			Platform.exit();
			return null;
		}
		
		// Write back the updated FxmlInfo to the scenes Map in Main
		fxmlInfo.setScene(scene);
		SceneFxmlApp.updateScenes(fxmlInfo.getSceneName(), fxmlInfo);
		
		Stageable controller = loader.getController();
		if (controller != null) {
			controller.setStage(fxmlInfo.getStage());
		}
		
		return scene;
	}

	// This method isn't required, but it can help figure out why an FXML file isn't loading
	private void debugInfo(String resourceName) {
		try {
			Enumeration<URL> urls = ClassLoader.getSystemClassLoader().getResources(resourceName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
