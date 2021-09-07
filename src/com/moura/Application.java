package com.moura;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * Application class
 * @author de Moura
 */
public class Application extends JFrame {

	private GridBagConstraints c = new GridBagConstraints();

	/**
	 * Default class constructor.
	 */
	public Application() {
		super("Metadata Editor");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
	}

	/**
	 * It starts the application. In fact, this method just makes the window
	 * visible, everything is already setup.
	 */
	public void start() {
		setVisible(true);
	}
}