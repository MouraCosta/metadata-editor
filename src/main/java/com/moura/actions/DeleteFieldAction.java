package com.moura.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import com.moura.app.App;
import com.moura.components.DeleteFieldDialog;

/**
 * A class responsible for deleting fields from the MetadataFields component.
 * 
 * @author de Moura
 */
public class DeleteFieldAction implements ActionListener {

    App app;
    DeleteFieldDialog dialog;

    /**
     * Default constructor for this class.
     * 
     * @param app An Application object representing the app view.
     */
    public DeleteFieldAction(App app) {
        this.app = app;
        dialog = new DeleteFieldDialog(app);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (app.fileSelected) {
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(app, "You must select a file before removing a field.");
        }
    }
}