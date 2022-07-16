package com.moura.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.moura.app.App;
import com.moura.components.NewFieldDialog;

public class AddNewFieldAction implements ActionListener {

	App appView;
	NewFieldDialog dialog;

	public AddNewFieldAction(App appView) {
		this.appView = appView;
		dialog = new NewFieldDialog(appView);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (appView.fileSelected) {
			dialog.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(appView, "Select a File to add metadata fields.");
		}
	}
}