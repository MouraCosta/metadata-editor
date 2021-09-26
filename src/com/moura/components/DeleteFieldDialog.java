package com.moura.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.moura.Application;

/**
 * Class responsible for showing the user a interface to delete the
 * fields in a MetadataFields object.
 * 
 * @author de Moura
 */
public class DeleteFieldDialog extends JDialog {

    Application app;
    Map<String, JCheckBox> fields = new HashMap<>();

    // Dialog Components
    JPanel fieldsPanel = new JPanel(new GridLayout(0, 1));
    JButton deleteButton = new JButton("Delete");
    JScrollPane scrollPane = new JScrollPane(fieldsPanel);

    public DeleteFieldDialog(Application app) {
        super(app);
        this.app = app;
        setSize(600, 400);
        setTitle("Check the fields to be deleted");
        setLayout(new GridBagLayout());
        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                if (removeSelected()) {
                    JOptionPane.showMessageDialog(app, "Fields deleted successfully");
                } else {
                    JOptionPane.showMessageDialog(app, "You didn't select any field to delete.");
                }
            }
        });
        setupComponents();
    }

    @Override
    public void setVisible(boolean state) {
        super.setVisible(state);
        setupFields(app.metadataFields.getMetadata());
    }

    private void setupFields(Map<String, String> availableMetadata) {
        availableMetadata.forEach((x, y) -> {
            JCheckBox currentCheckBox = new JCheckBox(x);
            fieldsPanel.add(currentCheckBox);
            fields.put(x, currentCheckBox);
        });
        scrollPane.revalidate();
        fieldsPanel.revalidate();
    }

    private void setupComponents() {
        GridBagConstraints fieldsConstraints = new GridBagConstraints();
        fieldsConstraints.gridx = 0;
        fieldsConstraints.gridy = 0;
        fieldsConstraints.weightx = 1;
        fieldsConstraints.weighty = 1;
        fieldsConstraints.fill = GridBagConstraints.BOTH;
        add(scrollPane, fieldsConstraints);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 0;
        buttonConstraints.weightx = 1;
        buttonConstraints.weighty = 1;
        add(deleteButton, buttonConstraints);
    }

    private List<String> getSelected() {
        List<String> selectedKeys = new ArrayList<>();
        fields.forEach((key, value) -> {
            if (value.isSelected()) {
                selectedKeys.add(key);
            }
        });
        return selectedKeys;
    }

    private boolean removeSelected() {
        List<String> selectedKeys = getSelected();
        if (selectedKeys.isEmpty()) {
            return false;
        }
        app.metadataFields.remove(selectedKeys);

        // Update the view of this dialog.
        selectedKeys.forEach((key) -> {
            fieldsPanel.remove(fields.get(key));
            fields.remove(key);
        });
        fieldsPanel.revalidate();
        return true;
    }
}