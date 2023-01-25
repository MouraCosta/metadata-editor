package com.moura.metadataeditor.controllers;

import java.io.File;
import java.util.Map;

import com.moura.metadataeditor.MetadataEditor;
import com.moura.metadataeditor.ThumbnailLoader;
import com.moura.metadataeditor.components.MetadataFields;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

/**
 * Class that sends responses to each user request in the main view.
 * 
 * @author De Moura
 */
public class MainController {

    @FXML
    Label fileLabel = new Label();
    @FXML
    ImageView fileThumbnail = new ImageView();
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
     * @param event Default parameter for implementing custom Event
     *              objects handling
     */
    public void onDragOver(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    /**
     * Gets and read the file dropped and sets up its' contents into the main
     * view.
     * 
     * Since multiple files can be selected, it's designed to always get the
     * first selected file to be read.
     * 
     * @param event Default parameter for implementing custom Event
     *              objects handling
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
     * It opens a FileChooser dialog to the user choose which file to be opened.
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
     * It saves any modification made to the selected file's metadata.
     */
    public void saveButtonClick() {
        if (workingFile != null) {
            if (!metadataFields.isValid()) {
                showDialog("Misconfiguration Detected",
                        "Looks like you either have repeated fields or the fields names "
                                + "don't match the pattern of proper field names.",
                        AlertType.ERROR);
            } else if (!MetadataEditor.setMetadata(workingFile, metadataFields.getMetadata())) {
                showDialog("Something went wrong", "Looks like something went wrong with Exiftool. Either the given "
                        + "arguments are wrong or the operation with this file types is unsupported.",
                        AlertType.ERROR);
            } else {
                // This block is reached when the metadata was successfully set. Update the
                // fields.
                metadataFields.update(workingFile);
            }
        } else {
            showDialog(null, "First select a file before trying to save anything.", AlertType.WARNING);
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

            metadataFields.setupFields(file);
            Image thumbnailImage = ThumbnailLoader.generateThumbnail(file);
            if (thumbnailImage != null) {
                fileThumbnail.setImage(thumbnailImage);
            } else {
                fileThumbnail.setImage(new Image(this.getClass().getResourceAsStream("/images/no_file.png")));
            }
            workingFile = file;
        }
    }

    private void showDialog(String headerText, String contentText, Alert.AlertType alertType) {
        Alert errorDialog = new Alert(alertType);
        errorDialog.getDialogPane().getStylesheets()
                .add(getClass().getResource("/stylesheets/dialog.css").toExternalForm());
        errorDialog.setHeaderText(headerText);
        errorDialog.setContentText(contentText);
        errorDialog.show();
    }
}
