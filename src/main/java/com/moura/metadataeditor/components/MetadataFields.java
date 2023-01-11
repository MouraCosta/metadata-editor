package com.moura.metadataeditor.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * MetadataFields is a class that works as widget, grouping several
 * MetadataField objects.
 * 
 * @author De Moura
 */
public class MetadataFields extends ScrollPane {

	private List<MetadataField> fields = new ArrayList<>();
	private VBox rootBox = new VBox();
	private VBox fieldsPanel = new VBox();
	private Button addButton = new Button("+");

	/**
	 * Creates a MetadataFields object.
	 */
	public MetadataFields() {
		super();
		setHbarPolicy(ScrollBarPolicy.NEVER);
		setFitToWidth(true);
		fieldsPanel.getStyleClass().add("fields-panel");

		addButton.setOnAction((event) -> {
			addField("None", "None");
		});
		addButton.setVisible(false);

		rootBox.setId("rootbox");
		rootBox.getChildren().add(fieldsPanel);
		rootBox.getChildren().add(addButton);

		this.setContent(rootBox);
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
		metadata.forEach(this::addField);
		addButton.setVisible(true);
	}

	/**
	 * Removes all MetadataField instances.
	 */
	public void clear() {
		fields.forEach(fieldsPanel.getChildren()::remove);
		fields.clear();
	}

	/**
	 * Adds a new MetadataField instance into the component.
	 * 
	 * @param key   The field name
	 * @param value The value of the field
	 */
	public void addField(String key, String value) {
		MetadataField mField = new MetadataField(key, value, fieldsPanel.getChildren());
		mField.setPadding(new Insets(0, 0, 10, 3));
		fieldsPanel.getChildren().add(mField);
		fields.add(mField);
	}

	/**
	 * Returns the metadata inserted into the MetadataFields widget into a Map.
	 * 
	 * @return The metadata
	 */
	public Map<String, String> getMetadata() {
		Map<String, String> metadata = new HashMap<>();
		fields.forEach((metadataField) -> {
			metadata.put(metadataField.getFieldName(), metadataField.getText());
		});
		return metadata;
	}

	public boolean isValid() {
		for (Node node: fieldsPanel.getChildren()) {
			MetadataField current = (MetadataField) node;
			if (!current.isValid()) {
				return false;
			}
		}
		return true;
	}
}