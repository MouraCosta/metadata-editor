package com.moura.metadataeditor.components;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.moura.metadataeditor.MetadataEditor;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * MetadataFields is a class that works as widget, grouping several
 * MetadataField objects.
 * 
 * @author De Moura
 */
public class MetadataFields extends ScrollPane {

	public List<MetadataField> fields = new ArrayList<>();

	private VBox fieldsPanel = new VBox(10);
	private VBox rootBox = new VBox();
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
	public void setupFields(File file) {
		clear();
		Map<String, String> metadata = MetadataEditor.getNeededMetadata(file);
		metadata.forEach(this::addField);
		addButton.setVisible(true);
		System.gc();
	}

	/**
	 * Removes all MetadataField instances.
	 */
	public void clear() {
		fields.forEach(fieldsPanel.getChildren()::remove);
		fields.clear();
		System.gc();
	}

	/**
	 * Adds a new MetadataField instance into the component.
	 * 
	 * @param key   The field name
	 * @param value The value of the field
	 */
	public void addField(String key, String value) {
		MetadataField mField = new MetadataField(key, value, this);
		mField.setPadding(new Insets(0, 0, 0, 3));
		fieldsPanel.getChildren().add(mField);
		fields.add(mField);

		FadeTransition ft = new FadeTransition(Duration.millis(1000), mField);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();
	}

	public void removeField(MetadataField mField) {
		TranslateTransition tt = new TranslateTransition(Duration.millis(300), mField);

		tt.setByX(this.getWidth());
		tt.setOnFinished((x) -> {
			fields.remove(mField);
			fieldsPanel.getChildren().remove(mField);
			System.gc();
		});
		tt.play();
	}

	public List<Node> getFields() {
		return fieldsPanel.getChildren();
	}

	/**
	 * Updates the metadata fields.
	 */
	public void update(File file) {
		Map<String, String> newMetadata = MetadataEditor.getNeededMetadata(file);
		Map<String, String> metadata = getMetadata();

		metadata.putAll(metadata);
		int index = 0;
		for (Entry<String, String> entry : newMetadata.entrySet()) {
			if (metadata.containsKey(entry.getKey())) {
				((MetadataField) getFields().get(index)).setFieldName(entry.getKey());
				((MetadataField) getFields().get(index)).setFieldInfo(entry.getValue());
				index++;
			}
		}
		System.gc();
	}

	/**
	 * Returns the metadata inserted into the MetadataFields widget into a Map.
	 * 
	 * @return The metadata
	 */
	public Map<String, String> getMetadata() {
		Map<String, String> metadata = new HashMap<>();
		fields.forEach((metadataField) -> {
			metadata.put(metadataField.getFieldName(), metadataField.getFieldInfo());
		});
		return metadata;
	}

	/**
	 * Checks if all the MetadataField instances within this class are valid.
	 * The condition to be valid is all instances must be valid.
	 * 
	 * @return true when all the MetadataField instances are valid, otherwise
	 *         false
	 */
	public boolean isValid() {
		for (Node node : getFields()) {
			if (!((MetadataField) node).isValid()) {
				return false;
			}
		}
		return true;
	}
}