package com.moura.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JOptionPane;

import com.moura.Application;
import com.moura.MetadataEditor;

public class SaveChangesAction implements ActionListener {
	Application app;
	MetadataEditor metadataEditor;

	public SaveChangesAction(Application app, MetadataEditor metadataEditor) {
		this.app = app;
		this.metadataEditor = metadataEditor;
	}

	public void actionPerformed(ActionEvent event) {
		if (app.fileSelected) {
			Map<String, String> newMetadata = app.metadataFields.getMetadata();
			if (metadataEditor.setMetadata(app.onChange, newMetadata)) {
				JOptionPane.showMessageDialog(app, "Metadata changed successfully.");
			} else {
				JOptionPane.showMessageDialog(app, "An error occurred.");
			}
		} else {
			JOptionPane.showMessageDialog(app, "You must select a file to edit anything.");
		}
	}
}