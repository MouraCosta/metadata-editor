package com.moura.metadataeditor.controllers;

import java.io.File;
import java.util.Map;

import com.moura.metadataeditor.MetadataEditor;
import com.moura.metadataeditor.components.MetadataFields;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
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

    private File workingFile;

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
        if (workingFile != null) {
            if (!MetadataEditor.setMetadata(workingFile, metadataFields.getMetadata())) {
                Alert errorDialog = new Alert(AlertType.ERROR);
                errorDialog.setHeaderText("Something went wrong!");
                errorDialog.setContentText("Looks like something went wrong with "
                    + "ExifTool. Either the given arguments are wrong or the operation with this "
                    + "file type is unsupported.");
                errorDialog.show();
            }
        } else {
            Alert alertDialog = new Alert(AlertType.INFORMATION);
            alertDialog.setContentText("First select a file before trying to save "
                + "anything.");
            alertDialog.show();
        }
    }

    /**
     * Reads the file metadata and sets up the main view widgets.
     * 
     * @param file is from which the metadata will be got from.
     */
    public void setup(File file) {
        Map<String, String> metadata;
        metadata = MetadataEditor.getMetadata(file);

        if (metadata.isEmpty()) {
            Alert alertDialog = new Alert(AlertType.ERROR);
            alertDialog.setContentText("You don't have exiftool installed in your "
                + "system.");
            alertDialog.show();
        } else {
            String creationDateString = metadata.get("Creation Date");
            String fileType = metadata.get("File Type");

            creationDateString = creationDateString == null ? "Unknown" : creationDateString;
            fileType = fileType == null ? "Unknown" : fileType;

            String labelOutput = String.format("Filename: %s\nCreation Date: %s\nFile Type: %s",
                    file.getName(), creationDateString, fileType);
            fileLabel.setText(labelOutput);
            
            metadataFields.setupFields(MetadataEditor.getNeededMetadata(file));
            workingFile = file;
        }
    }
}
