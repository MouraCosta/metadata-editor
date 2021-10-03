package com.moura;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.moura.actions.AddNewFieldAction;
import com.moura.actions.DeleteFieldAction;
import com.moura.actions.SaveChangesAction;
import com.moura.actions.SelectFileAction;
import com.moura.components.FilenameIconLabel;
import com.moura.components.MetadataFields;

/**
 * Application class
 * @author de Moura
 */
public class Application extends JFrame {

	public FilenameIconLabel filenameIconLabel = new FilenameIconLabel(this);
	public MetadataFields metadataFields = new MetadataFields(this);
	public JButton saveButton = new JButton("Save"), selectFileButton = new JButton("Select File"),
		addFieldButton = new JButton("+"), deleteFieldButton = new JButton("-");

	public boolean fileSelected;
	public File onChange;
	private MetadataEditor metadataEditor = new MetadataEditor();

	/**
	 * Default class constructor.
	 */
	public Application() {
		super("Metadata Editor");
		setIconImage(new ImageIcon("data/icon.png").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());

		setupComponents();
		setSize(600, 400);
		setMinimumSize(getSize());
	}

	/**
	 * It starts the application. In fact, this method just makes the window
	 * visible, everything is already setup.
	 */
	public void start() {
		setVisible(true);
	}

	private void setupButtons() {
		// Add functions to the buttons.
		selectFileButton.addActionListener(new SelectFileAction(this, metadataEditor));
		saveButton.addActionListener(new SaveChangesAction(this, metadataEditor));
		addFieldButton.addActionListener(new AddNewFieldAction(this));
		deleteFieldButton.addActionListener(new DeleteFieldAction(this));

		JPanel buttonsPanel = new JPanel(new GridBagLayout());

		// Setups the addField button in the application view.
		GridBagConstraints addFieldButtonConstraints = new GridBagConstraints();
		addFieldButtonConstraints.gridx = 1;
		addFieldButtonConstraints.gridy = 1;
		addFieldButtonConstraints.weightx = 1;
		addFieldButtonConstraints.anchor = GridBagConstraints.WEST;
		add(addFieldButton, addFieldButtonConstraints);

		// Setups the delete field button in the application view.
		GridBagConstraints deleteButtonConstraints = new GridBagConstraints();
		deleteButtonConstraints.gridx = 1;
		deleteButtonConstraints.gridy = 1;
		deleteButtonConstraints.weightx = 1;
		deleteButtonConstraints.anchor = GridBagConstraints.WEST;
		deleteButtonConstraints.insets = new Insets(0, 45, 0, 0);
		add(deleteFieldButton, deleteButtonConstraints);

		// Setups the selectFile button in the application view.
		GridBagConstraints selectFileButtonConstraints = new GridBagConstraints();
		selectFileButtonConstraints.weightx = 1;
		selectFileButtonConstraints.weighty = 1;
		selectFileButtonConstraints.anchor = GridBagConstraints.NORTHWEST;
		selectFileButtonConstraints.gridx = 0;
		selectFileButtonConstraints.gridy = 0;
		buttonsPanel.add(selectFileButton, selectFileButtonConstraints);

		// Setups the save button in the application view.
		GridBagConstraints saveButtonConstraints = new GridBagConstraints();
		saveButtonConstraints.weightx = 1;
		saveButtonConstraints.weighty = 1;
		saveButtonConstraints.gridx = 1;
		saveButtonConstraints.gridy = 0;
		saveButtonConstraints.anchor = GridBagConstraints.NORTHEAST;
		buttonsPanel.add(saveButton, saveButtonConstraints);

		// Setups the panel that hold the save and selectFile buttons.
		GridBagConstraints panelConstraints = new GridBagConstraints();
		panelConstraints.gridx = 1;
		panelConstraints.gridy = 2;
		panelConstraints.weightx = 1;
		panelConstraints.anchor = GridBagConstraints.WEST;
		panelConstraints.insets = new Insets(30, 0, 30, 0);
		add(buttonsPanel, panelConstraints);
	}

	private void setupComponents() {
		filenameIconLabel.add();
		metadataFields.add();
		setupButtons();
	}
}