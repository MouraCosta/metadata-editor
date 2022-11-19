package com.moura.metadataeditor.controllers;

import java.io.File;
import java.util.Map;

import com.moura.metadataeditor.MetadataEditor;
import com.moura.metadataeditor.components.MetadataFields;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

/**
 * Class responsible for responding to user actions in the main view of the
 * application.
 * 
 * @author De Moura
 */
public class MainController {

    @FXML
    Label fileLabel = new Label();
    @FXML
    Button openButton = new Button();
    @FXML
    Button saveButton = new Button();
    @FXML
    MetadataFields metadataFields = new MetadataFields();

    /**
     * It checks if the data over the node is a file. If yes, then it'll allow
     * the data to be read by the node.
     * 
     * @param event is the default parameter for implementing custom Event
     *              objects handling.
     */
    public void onDragOver(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    /**
     * Gets the file dropped and sets up its' contents into the main view.
     * 
     * @param event is the default parameter for implementing custom Event
     *              objects handling.
     */
    public void onDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            // Basic functionality
            setup(db.getFiles().get(0));
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

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

        setup(file);
    }

    /**
     * Save any modification made to the selected file's metadata.
     */
    public void saveButtonClick() {
        System.out.println("I'm amazing");
    }

    /**
     * Reads the file metadata and sets up the main view widgets.
     * 
     * @param file is from which the metadata will be got from.
     */
    public void setup(File file) {
        Map<String, String> metadata = MetadataEditor.getMetadata(file);

        String creationDateString = metadata.get("Creation Date");
        String fileType = metadata.get("File Type");

        creationDateString = creationDateString == null ? "Unknown" : creationDateString;
        fileType = fileType == null ? "Unknown" : fileType;

        String labelOutput = String.format("Filename: %s\nCreation Date: %s\nFile Type: %s",
                file.getName(), creationDateString, fileType);
        fileLabel.setText(labelOutput);

        if (!metadata.isEmpty()) {
            metadataFields.setupFields(MetadataEditor.getNeededMetadata(file));
        } else {
            // Shows a dialog informing that it's not possible to acquire data without
            // exiftool.
        }
    }
}
