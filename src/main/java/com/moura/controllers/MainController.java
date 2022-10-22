package com.moura.controllers;

import java.io.File;
import java.util.Map;

import com.moura.MetadataEditor;
import com.moura.components.MetadataFields;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

/**
 * Class responsible for responding to user actions in the main view of the
 * application.
 * 
 * @author De Moura
 */
public class MainController {
    
    @FXML Label fileLabel = new Label();
    @FXML Button openButton = new Button();
    @FXML Button saveButton = new Button();
    @FXML MetadataFields metadataFields = new MetadataFields();

    MetadataEditor metadataEditor = new MetadataEditor();

    /**
     * Opens a file chooser and reads and writes the selected file's metadata
     * into the metadataFields widget. 
     */
    public void openButtonClick() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(null);

        if (file == null) {
            return;
        }

        Map<String, String> metadata = null;
        try {
            metadata = metadataEditor.getMetadata(file);
        } catch (Exception err) {
            err.printStackTrace();
        }

        String creationDateString = metadata.get("Creation Date");
        String fileType = metadata.get("File Type");
        String labelOutput = String.format("Filename: %s\nCreation Date: %s\nFile Type: %s",
            file.getName(), creationDateString, fileType);
        try {
            metadataFields.setupFields(metadataEditor.getNeededMetadata(file));
            fileLabel.setText(labelOutput);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    /**
     * Save any modification made to the selected file's metadata.
     */
    public void saveButtonClick() {
        System.out.println("I'm amazing");
    }
}
