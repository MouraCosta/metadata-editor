package com.moura.metadataeditor.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * MetadataField is a class that represents a single entry. It's heavily
 * associated with the MetadataFields class, as this latter class groups several
 * MetadataField objects into a scrollable pane.
 */
class MetadataField extends GridPane {

	private Label fieldLabel;
	private TextField fTextField = new TextField();

	public MetadataField(String fieldName) {
		super();
		fieldLabel = new Label(fieldName);
		setHgap(10);

		add(fieldLabel, 0, 0);
		add(fTextField, 0, 1);
	}

	public MetadataField(String fieldName, String value) {
		this(fieldName);
		fTextField.setText(value);
	}

	public String getText() {
		return fTextField.getText();
	}
}

/**
 * A class responsible for showing the user all the fields that a file contains.
 */
public class MetadataFields extends ScrollPane {

	// form: <metadata-field, [Label, TextField]>
	private Map<String, MetadataField> fields = new HashMap<>();

	private VBox fieldsPanel = new VBox();

	public MetadataFields() {
		super();
		this.setContent(fieldsPanel);
	}

	/**
	 * Sets all the metadata fields in the component through a map. Whenever
	 * this function is called, it cleans old metadata within this pane.
	 * 
	 * @param metadata Map of strings containing all the fields and its values.
	 */
	public void setupFields(Map<String, String> metadata) {
		clean();
		for (Map.Entry<String, String> keyValue : metadata.entrySet()) {
			MetadataField mField = new MetadataField(keyValue.getKey(), keyValue.getValue());
			fieldsPanel.getChildren().add(mField);
			fields.put(keyValue.getKey(), mField);
		}
	}

	/**
	 * Remove all the fields and their values.
	 */
	public void clean() {
		fields.forEach((x, y) -> {
			fieldsPanel.getChildren().remove(y);
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
			MetadataField mField = new MetadataField(key, value);
			fieldsPanel.getChildren().add(mField);
			fields.put(key, mField);
			return true;
		}
		return false;
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
			MetadataField mField = fields.get(key);
			fieldsPanel.getChildren().remove(mField);
			fields.remove(key);
		});
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
			metadata.put(k, v.getText());
		});
		return metadata;
	}
}