package com.moura.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.moura.app.App;
/**
 * JDialog child that contains an already setting for the components and how it
 * should work.
 * 
 * @author de Moura
 */
public class NewFieldDialog extends JDialog {

	private App appView;
	private Dimension dialogDimension = new Dimension(424, 112);
	private String initialFieldMessage = "Put your field here";
	private String initialValueMessage = "Put your value here";

	final Pattern KEY_PATTERN = Pattern.compile("(([A-Z][a-z]+) ?)+");

	JLabel fieldLabel, valueLabel;
	JTextField fieldTextField, valueTextField;
	JButton addFieldButton;

	/**
	 * The default constructor for this class.
	 * @param appView is the instance of the JFrame child, Application.
	 */
	public NewFieldDialog(App appView) {
		super(appView);
		this.appView = appView;
		setTitle("Add a new field");
		setSize(dialogDimension);
		setResizable(false);
		setLayout(new GridBagLayout());
		setupComponents();
	}

	private void setupComponents() {
		GridBagConstraints c = new GridBagConstraints();

		fieldLabel = new JLabel("Field");
		fieldTextField = createValidatedTextField(initialFieldMessage);
		valueLabel = new JLabel("Value");
		valueTextField = new JTextField(initialValueMessage);
		addFieldButton = createActionButton();

		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		add(createFieldInput(fieldLabel, fieldTextField), c);

		c.gridx = 0;
		c.gridy = 1;
		add(createFieldInput(valueLabel, valueTextField), c);

		c.gridy = 2;
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		add(addFieldButton, c);
	}

	private JPanel createFieldInput(JLabel label, JTextField textField) {
		JPanel inputPanel = new JPanel(new GridLayout(1, 2));
		inputPanel.add(label);
		inputPanel.add(textField);
		return inputPanel;
	}

	private JTextField createValidatedTextField(String initialString) {
		JTextField textField = new JTextField(initialString);
		textField.addFocusListener(new FocusListener() {
			final Color ORIGINAL_COLOUR = textField.getForeground();
			final Color ON_VALID_COLOUR = Color.GREEN;
			final Color ON_INVALID_COLOUR = Color.RED;

			public void focusGained(FocusEvent event) {
				textField.setForeground(ORIGINAL_COLOUR);
			}

			public void focusLost(FocusEvent event) {
				String text = textField.getText();
				Matcher matcher = KEY_PATTERN.matcher(text);
				if (matcher.matches()) {
					textField.setForeground(ON_VALID_COLOUR);
				} else {
					textField.setForeground(ON_INVALID_COLOUR);
				}
			}
		});
		return textField;
	}

	private JButton createActionButton() {
		JDialog thisClass = this;
		JButton button = new JButton("Add field");
		button.addActionListener(new ActionListener() {
			private void validateAction(String field, String value) {
				if (! (field.isEmpty() && value.isEmpty())) {
					if (field.equals(initialFieldMessage) || value.equals(initialValueMessage)) {
						JOptionPane.showMessageDialog(thisClass, "Do not use the initial values.");
					} else {
						Matcher matcher = KEY_PATTERN.matcher(fieldTextField.getText());
						if (matcher.matches()) {
							boolean success = appView.metadataFields.addField(field, value);
							JOptionPane.showMessageDialog(
								thisClass,
								success? "Metadata Field added sucessfully": "Field already exists"
							);
						} else {
							JOptionPane.showMessageDialog(thisClass,
								"The field key should be Titlelised.");
						}
					}
				} else {
					JOptionPane.showMessageDialog(thisClass, "You must insert data to proceed.");
				}
			}

			public void actionPerformed(ActionEvent event) {
				String field = fieldTextField.getText();
				String value = valueTextField.getText();
				validateAction(field, value);
			}
		});
		return button;
	}
}