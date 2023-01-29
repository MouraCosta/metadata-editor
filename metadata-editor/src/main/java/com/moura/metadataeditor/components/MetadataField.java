package com.moura.metadataeditor.components;

import java.io.IOException;
import java.util.regex.Pattern;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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

	final private MetadataFields ROOT;
	final Pattern FIELD_NAME_PATTERN = Pattern.compile("^(?:[A-Z][a-zA-Z]*)+(?:[\\s-][A-Z][a-zA-Z]*)*$");

	/**
	 * Creates a metadata field with an empty text box.
	 * 
	 * @param fieldName   The field name
	 * @param parentNodes Reference to where this Node belongs.
	 */
	public MetadataField(String fieldName, MetadataFields root) {
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

		ROOT = root;

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
	public MetadataField(String fieldName, String value, MetadataFields root) {
		this(fieldName, root);
		fieldinfo.setText(value);
	}

	/**
	 * Obtains the inserted value in this metadata field.
	 * 
	 * @return The value in this metadata field
	 */
	public String getFieldInfo() {
		return fieldinfo.getText();
	}

	public String getFieldName() {
		return fieldname.getText();
	}

	public void setFieldInfo(String newText) {
		fieldinfo.setText(newText);
	}

	public void setFieldName(String newFieldName) {
		fieldname.setText(newFieldName);
	}

	/**
	 * Deletes this node from the parent.
	 */
	public void delete() {
		TranslateTransition tt = new TranslateTransition(Duration.millis(500), this);
		ScaleTransition st = new ScaleTransition(Duration.millis(300), this);

		tt.setByX(this.getWidth());
		tt.setOnFinished((x) -> {
			ROOT.removeField(this);
		});

		st.setFromX(1);
		st.setFromY(1);
		st.setToX(0);
		st.setToY(0);

		st.play();
		tt.play();
	}

	public void validateFocused(boolean focused) {
		if (focused) {
			// TextField is focused now. Reset to its' original state.
			fieldname.setStyle("-fx-text-fill: white");
		} else {
			// TextField has lost focus. Check for errors.
			isValid();
		}
	}

	public boolean isValid() {
		fieldNameInstances = 0;
		ROOT.getFields().forEach((node) -> {
			MetadataField current = (MetadataField) node;
			if (current.getFieldName().equals(this.getFieldName())) {
				fieldNameInstances++;
			}
		});
		if (fieldNameInstances > 1) {
			fieldname.setStyle("-fx-text-fill: red");
			return false;
		} else if (!FIELD_NAME_PATTERN.matcher(getFieldName()).matches()) {
			fieldname.setStyle("-fx-text-fill: red");
			return false;
		}
		return true;
	}
}