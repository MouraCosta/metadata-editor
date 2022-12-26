package com.moura.metadataeditor.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * MetadataFields is a class that works as widget, grouping several
 * MetadataField objects.
 * 
 * @author De Moura
 */
public class MetadataFields extends ScrollPane {

	// form: <metadata-field, [Label, TextField]>
	private Map<String, MetadataField> fields = new HashMap<>();

	private VBox fieldsPanel = new VBox();

	/**
	 * Creates a MetadataFields object.
	 */
	public MetadataFields() {
		super();
		setHbarPolicy(ScrollBarPolicy.NEVER);
		setFitToWidth(true);
		fieldsPanel.getStyleClass().add("fields-panel");
		this.setContent(fieldsPanel);
	}

	/**
	 * Generates MetadataField objects and adds them to this class instance.
	 * 
	 * This method reads a Map<String, String> object, that is, the metadata,
	 * and generates MetadataField objects with its' keys as the fields names
	 * and its' values as the values of the fields.
	 * 
	 * Too assure that no substantial data remains in this widget from previous
	 * setups, all the MetadataField instances are removed.
	 * 
	 * @param metadata Map of strings containing all the metadata fields and
	 *                 its' values
	 */
	public void setupFields(Map<String, String> metadata) {
		clear();
		for (Map.Entry<String, String> keyValue : metadata.entrySet()) {
			MetadataField mField = new MetadataField(keyValue.getKey(), keyValue.getValue());
			fieldsPanel.getChildren().add(mField);
			fields.put(keyValue.getKey(), mField);
		}
	}

	/**
	 * Removes all MetadataField instances.
	 */
	public void clear() {
		fields.forEach((x, y) -> {
			fieldsPanel.getChildren().remove(y);
		});
		fields.clear();
	}

	/**
	 * Adds a new MetadataField instance into the component.
	 * 
	 * @param key   The field name
	 * @param value The value of the field
	 * 
	 * @return True for success. False when a field with the same attributes
	 *         already exists
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
	 * Removes only the specified MetadataField instances.
	 * 
	 * @param metadataFieldNames List of names of the fields to be deleted.
	 */
	public void remove(List<String> metadataFieldNames) {
		metadataFieldNames.forEach((key) -> {
			MetadataField mField = fields.get(key);
			fieldsPanel.getChildren().remove(mField);
			fields.remove(key);
		});
	}

	/**
	 * Returns the metadata inserted into the MetadataFields widget into a Map.
	 * 
	 * @return The metadata
	 */
	public Map<String, String> getMetadata() {
		Map<String, String> metadata = new HashMap<>();
		fields.forEach((k, v) -> {
			metadata.put(k, v.getText());
		});
		return metadata;
	}
}