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

        Map<String, String> metadata = MetadataEditor.getMetadata(file);

        String creationDateString = metadata.get("Creation Date");
        String fileType = metadata.get("File Type");

        creationDateString = creationDateString == null? "Unknown":creationDateString;
        fileType = fileType == null? "Unknown": fileType;

        String labelOutput = String.format("Filename: %s\nCreation Date: %s\nFile Type: %s",
            file.getName(), creationDateString, fileType);
        fileLabel.setText(labelOutput);

        if (!metadata.isEmpty()) {
            metadataFields.setupFields(MetadataEditor.getNeededMetadata(file));
        } else {
            // Shows a dialog informing that it's not possible to acquire data without exiftool.
        }
    }

    /**
     * Save any modification made to the selected file's metadata.
     */
    public void saveButtonClick() {
        System.out.println("I'm amazing");
    }
}
