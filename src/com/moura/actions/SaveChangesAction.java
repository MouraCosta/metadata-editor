package com.moura.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JOptionPane;

import com.moura.Application;
import com.moura.MetadataEditor;

/**
 * Class responsible for saving the changes the user does in the metadata fields.
 * @author de Moura*/
public class SaveChangesAction implements ActionListener {
	Application app;
	MetadataEditor metadataEditor;

	/**
	 * Default constructor for this component.
	 * 
	 * @param app An Application object that holds the view of the whole app.
	 * @param metadataEditor A MetadataEditor object that is responsible for
	 * actually doing what the programme should do
	 */
	public SaveChangesAction(Application app, MetadataEditor metadataEditor) {
		this.app = app;
		this.metadataEditor = metadataEditor;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (app.fileSelected) {
			int confirmationCode = JOptionPane.showConfirmDialog(app,
				"Do you really want to save your changes?"
			);
			if (confirmationCode == 0) {
				// The user really wants to save his changes.
				Map<String, String> newMetadata = app.metadataFields.getMetadata();
				try {
					if (metadataEditor.setMetadata(app.onChange, newMetadata)) {
						JOptionPane.showMessageDialog(app, "Metadata changed successfully.");
					} else {
						JOptionPane.showMessageDialog(app, "An error occurred.");
					}
				} catch (Exception err) {
					JOptionPane.showMessageDialog(app, "It's not possible to set metadata without"
					 +"exiftool.");
				}
			}
		} else {
			JOptionPane.showMessageDialog(app, "You must select a file to edit anything.");
		}
	}
}