package com.moura;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.moura.actions.AddNewFieldAction;
import com.moura.components.FilenameIconLabel;
import com.moura.components.MetadataFields;

/**
 * Application class
 * @author de Moura
 */
public class Application extends JFrame {

	private boolean fileSelected = false;

	public FilenameIconLabel filenameIconLabel = new FilenameIconLabel(this);
	public MetadataFields metadataFields = new MetadataFields(this);
	public JButton saveButton = new JButton("Save"), selectFileButton = new JButton("Select File"),
		addFieldButton = new JButton("+");

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
		show();
	}

	// TODO: Review this code. It sounds pretty crazy.
	private void setupButtons() {
		GridBagConstraints addFConstraints = new GridBagConstraints();
		addFConstraints.gridx = 1;
		addFConstraints.gridy = 1;
		addFConstraints.weightx = 1;
		addFConstraints.anchor = GridBagConstraints.WEST;
		addFieldButton.addActionListener(new AddNewFieldAction(this));
		add(addFieldButton, addFConstraints);
		JPanel buttonsPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.weightx = 1;
		c.weighty = 1;
		c.ipadx = 8;

		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridx = 0;
		c.gridy = 0;
		buttonsPanel.add(selectFileButton, c);
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTHEAST;
		buttonsPanel.add(saveButton, c);

		GridBagConstraints panelConstraints = new GridBagConstraints();
		panelConstraints.gridx = 1;
		panelConstraints.gridy = 2;
		panelConstraints.weightx = 1;
		panelConstraints.anchor = GridBagConstraints.WEST;
		panelConstraints.insets = new Insets(30, 0, 30, 0);
		add(buttonsPanel, panelConstraints);
	}

	// TODO: Go organise all the components in the place they should be
	private void setupComponents() {
		filenameIconLabel.add();
		metadataFields.add();
		setupButtons();
	}
}