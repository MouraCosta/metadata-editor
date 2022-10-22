package com.moura.app;

import com.moura.components.MetadataFields;
import com.moura.ui.UILoader;

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
public class App extends Application {
	public MetadataFields metadataFields = new MetadataFields();

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(UILoader.load("main.fxml"));
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