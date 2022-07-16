package com.moura.components;

import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.moura.app.App;

/**
 * A class responsible for showing the user all the fields that a file contains.
 */
public class MetadataFields {

	private Map<String, JComponent[]> fields = new HashMap<>();
	private JPanel fieldsPanel = new JPanel();
	private JScrollPane mainPane = new JScrollPane(fieldsPanel);
	private App app;

	/**
	 * The default class constructor.
	 * 
	 * @param app The Application object representing the main view.
	 */
	public MetadataFields(App app) {
		fieldsPanel.setLayout(new GridLayout(0, 1));
		this.app = app;
	}

	/**
	 * Sets all the metadata fields in the component through a map.
	 * 
	 * @param metadata Map of strings containing all the fields and its values.
	 */
	public void setupFields(Map<String, String> metadata) {
		for (Map.Entry<String, String> keyValue : metadata.entrySet()) {
			JLabel currentLabel = new JLabel(keyValue.getKey());
			JTextField currentTextField = new JTextField(keyValue.getValue());
			fieldsPanel.add(currentLabel);
			fieldsPanel.add(currentTextField);
			fields.put(keyValue.getKey(), new JComponent[] { currentLabel, currentTextField });
		}
		fieldsPanel.revalidate();
	}

	/**
	 * Remove all the fields and their values.
	 */
	public void clean() {
		fields.forEach((x, y) -> {
			fieldsPanel.remove(y[0]);
			fieldsPanel.remove(y[1]);
		});
		fields.clear();
	}

	/**
	 * Adds a new field to the component.
	 * 
	 * @param key   A String representing the field.
	 * @param value A String representing the value of the field.
	 * 
	 * @return True when it was possible to add this new field. False when this
	 *         field already exists.
	 */
	public boolean addField(String key, String value) {
		if (fields.get(key) == null) {
			JLabel label = new JLabel(key);
			JTextField textField = new JTextField(value);
			fieldsPanel.add(label);
			fieldsPanel.add(textField);
			fieldsPanel.revalidate();
			fields.put(key, new JComponent[] { label, textField });
			return true;
		}
		return false;
	}

	/**
	 * Adds the component to the frame.
	 */
	public void add() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 0, 0, 10);
		app.add(mainPane, c);
	}

	/**
	 * Removes all the fields according to the respective keys on a
	 * List<String> object.
	 * 
	 * @param metadataKeys A List<String> object that contains all the
	 *                     keys to their respective fields.
	 */
	public void remove(List<String> metadataKeys) {
		metadataKeys.forEach((key) -> {
			JComponent[] components = fields.get(key);
			fieldsPanel.remove(components[0]);
			fieldsPanel.remove(components[1]);

			fields.remove(key);
		});
		fieldsPanel.revalidate();
	}

	/**
	 * Gets all the metadata in the MetadataFields component and parse all
	 * these to a Map<String, String> object.
	 * 
	 * @return A Map<String, String> object containing the metadata fields.
	 */
	public Map<String, String> getMetadata() {
		Map<String, String> metadata = new HashMap<>();
		fields.forEach((k, v) -> {
			metadata.put(k, ((JTextField) v[1]).getText());
		});
		return metadata;
	}
}