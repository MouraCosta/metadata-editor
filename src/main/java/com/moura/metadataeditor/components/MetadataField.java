package com.moura.metadataeditor.components;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
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

	@FXML
	private Label fieldname;
	@FXML
	private TextField fieldinfo = new TextField();

	/**
	 * Creates a metadata field with an empty text box.
	 * 
	 * @param fieldName The field name
	 */
	public MetadataField(String fieldName) {
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

		fieldname.setText(fieldName);
	}

	/**
	 * Creates a metadata field with a set field name and value.
	 * 
	 * @param fieldName The field name
	 * @param value     The value
	 */
	public MetadataField(String fieldName, String value) {
		this(fieldName);
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
}