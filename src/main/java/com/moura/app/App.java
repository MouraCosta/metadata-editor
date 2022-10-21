package com.moura.app;

import java.io.File;

import com.moura.MetadataEditor;
import com.moura.components.MetadataFields;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


/**
 * Application class
 * 
 * @author de Moura
 */
public class App extends Application {
	public MetadataFields metadataFields = new MetadataFields();
	public boolean fileSelected;
	public File onChange;
	private MetadataEditor metadataEditor = new MetadataEditor();

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("ui.fxml"));
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