package com.moura.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;

import com.moura.Application;
import com.moura.MetadataEditor;

public class SelectFileAction implements ActionListener {

	Application app;
	MetadataEditor metadataEditor;
	JFileChooser fileChooser = new JFileChooser();

	public SelectFileAction(Application app, MetadataEditor metadataEditor) {
		this.app = app;
		this.metadataEditor = metadataEditor;
		fileChooser.setSize(600, 400);
	}

	public void actionPerformed(ActionEvent event) {
		// First, a window will popup asking what file to select.
		int exitCode = fileChooser.showOpenDialog(app);
		if (exitCode == 0) {
			// The user selected a file for sure. Change the environment.
			if (app.fileSelected) {
				// There is a file selected. Clean the fields.
				app.metadataFields.clean();
			}
			File selectedFile = fileChooser.getSelectedFile();
			app.onChange = selectedFile;
			app.metadataFields.setupFields(metadataEditor.getMetadata(selectedFile));
			app.fileSelected = true;
			app.filenameIconLabel.setThumbnail(selectedFile);
		}
	}
}