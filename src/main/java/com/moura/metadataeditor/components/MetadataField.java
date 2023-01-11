package com.moura.metadataeditor.components;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * MetadataField is a class that represents a single entry. It's heavily
 * associated with the MetadataFields class, as this latter class groups several
 * MetadataField objects into a scrollable pane.
 * 
 * @author De Moura
 */
public class MetadataField extends AnchorPane {

	private static int fieldNameInstances = 0;

	@FXML
	private TextField fieldname;
	@FXML
	private TextField fieldinfo;
	@FXML
	private Button deleteButton = new Button();
	private boolean valid = false;

	final private List<Node> NODES_IN_PARENT;

	final Pattern FIELD_NAME_PATTERN = Pattern.compile("^(?:[A-Z][a-z]*)+(?:[\\s-][A-Z][a-z]*)*$");

	/**
	 * Creates a metadata field with an empty text box.
	 * 
	 * @param fieldName   The field name
	 * @param parentNodes Reference to where this Node belongs.
	 */
	public MetadataField(String fieldName, List<Node> parentNodes) {
		super();
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource("/views/metadata_field_view.fxml"));
		loader.setController(this);
		try {
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		NODES_IN_PARENT = parentNodes;

		fieldname.setText(fieldName);

		// Add code to validate field name titles
		fieldname.focusedProperty().addListener((obs, oldVal, newVal) -> {
			this.validateFocused(newVal);
		});
	}

	/**
	 * Creates a metadata field with a set field name and value.
	 * 
	 * @param fieldName The field name
	 * @param value     The value
	 */
	public MetadataField(String fieldName, String value, List<Node> parentNodes) {
		this(fieldName, parentNodes);
		fieldinfo.setText(value);
	}

	/**
	 * Obtains the inserted value in this metadata field.
	 * 
	 * @return The value in this metadata field
	 */
	public String getText() {
		return fieldinfo.getText();
	}

	public String getFieldName() {
		return fieldname.getText();
	}

	/**
	 * Deletes this node from the parent.
	 */
	public void delete() {
		NODES_IN_PARENT.remove(this);
	}

	public void validateFocused(boolean newVal) {
		if (newVal) {
			// TextField is focused now. Reset to its' original state.
			fieldname.setStyle("-fx-text-fill: white");
		} else {
			// TextField has lost focus. Check for errors.
			fieldNameInstances = 0;
			NODES_IN_PARENT.forEach((node) -> {
				MetadataField current = (MetadataField) node;
				if (current.getFieldName().equals(this.getFieldName())) {
					fieldNameInstances++;
				}
			});
			if (fieldNameInstances > 1) {
				fieldname.setStyle("-fx-text-fill: red");
				valid = false;
			}else if (!FIELD_NAME_PATTERN.matcher(getFieldName()).matches()) {
				fieldname.setStyle("-fx-text-fill: red");
				valid = false;
			} else {
				valid = true;
			}
		}
	}

	public boolean isValid() {
		return valid;
	}
}