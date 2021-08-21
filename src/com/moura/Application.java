package com.moura;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JFrame;

public class Application extends JFrame {

	public Application() {
		super("Metadata Editor");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
	}

	public void start() {
		setVisible(true);
	}
}