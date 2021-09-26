package com.moura.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FileDialog;
import java.io.File;
import java.lang.reflect.Field;

import javax.swing.JFileChooser;

import com.moura.Application;
import com.moura.MetadataEditor;

/**
 * Class responsible for representing the action of selecting a file.
 * 
 * @author de Moura
 */
public class SelectFileAction implements ActionListener {

	Application app;
	MetadataEditor metadataEditor;
	FileDialog fileChooser;

	/**
	 * Default constructor of this component.
	 * 
	 * @param app An Application object that holds the whole app view.
	 * @param metadataEditor A MetadataEditor object responsible for doing the
	 * whole app processing.
	 */
	public SelectFileAction(Application app, MetadataEditor metadataEditor) {
		this.app = app;
		this.metadataEditor = metadataEditor;
		this.fileChooser = new FileDialog(app, "Open File");
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// First, a window will popup asking what file to select.
		fileChooser.setVisible(true);
		String filename = fileChooser.getFile();
		System.out.println(filename);
		if (filename != null) {
			if (app.fileSelected) {
				// There's a file on working. Clean it.
				app.metadataFields.clean();
			}
			File selectedFile = fileChooser.getFiles()[0];
			app.onChange = selectedFile;
			app.fileSelected = true;
			app.metadataFields.setupFields(metadataEditor.getMetadata(selectedFile));
			app.filenameIconLabel.setThumbnail(selectedFile);
		}
	}
}