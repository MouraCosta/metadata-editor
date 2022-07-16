package com.moura.app;

import java.util.logging.Logger;
import javax.swing.UIManager;

import com.moura.Application;

/**
 * App entry.
 *
 */
public class App {

    static final Logger LOGGER = Logger.getLogger("MetadataEditor");

	public static Application app;

	static {
		try {
			LOGGER.info("Setting System Look and Feel.");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception err) {
			err.printStackTrace();
		}
		app = new Application();
	}

    public static void main( String[] args ) {
        app.start();
		LOGGER.info("Application Started");
    }
}
