package com.moura.metadataeditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application class
 * 
 * @author de Moura
 */
public class MetadataEditorApp extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("/views/main.fxml"));
		Scene scene = new Scene(root, 600, 400);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Metadata Editor");
		primaryStage.setMinWidth(640);
		primaryStage.setMinHeight(480);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}