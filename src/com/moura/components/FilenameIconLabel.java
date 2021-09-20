package com.moura.components;

import java.awt.Image;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;


public class FilenameIconLabel extends JPanel {

	private JLabel filename, fileThumbnail;
	private JFrame frame;

	final ImageIcon NO_FILE = new ImageIcon("data/no_file.png");
	final String MESSAGE_FORMAT = "<html><p>%s</p></html>";

	/**
	 * The default constructor for this class.
	 * @param frame The window where this component will appear
	 */
	public FilenameIconLabel(JFrame frame) {
		this.frame = frame;
		setLayout(new GridBagLayout());
		filename = new JLabel("No file selected yet");
		filename.setFont(new Font(null, Font.BOLD, 14));
		fileThumbnail = new JLabel(NO_FILE);

		GridBagConstraints thumbnailConstraints = new GridBagConstraints();
		thumbnailConstraints.gridx = 0;
		thumbnailConstraints.gridy = 0;
		thumbnailConstraints.gridheight = 1;
		thumbnailConstraints.gridwidth = 1;
		thumbnailConstraints.weightx = 1;
		thumbnailConstraints.weighty = 1;
		thumbnailConstraints.anchor = GridBagConstraints.SOUTHWEST;

		GridBagConstraints filenameConstraints = new GridBagConstraints();
		filenameConstraints.gridx = 0;
		filenameConstraints.gridy = 1;
		filenameConstraints.gridheight = 1;
		filenameConstraints.gridwidth = 1;
		filenameConstraints.weighty = 1;
		filenameConstraints.anchor = GridBagConstraints.NORTHWEST;

		add(fileThumbnail, thumbnailConstraints);
		add(filename, filenameConstraints);
	}
	// TODO: Put this component in the right position
	/**
	 * Adds this component to the main frame.
	 */
	public void add() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		int padding = 20;
		c.insets = new Insets(padding, padding, 0, 0);
		frame.add(this, c);
	}

	public void setThumbnail(File file) {
		Icon fileIcon = FileSystemView.getFileSystemView().getSystemIcon(file);
		fileThumbnail.setIcon(fileIcon);
		filename.setText(String.format(MESSAGE_FORMAT, file.getName()));
	}
}