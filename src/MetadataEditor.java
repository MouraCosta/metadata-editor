import java.util.logging.Logger;
import javax.swing.UIManager;

import com.moura.Application;

/**
 * Main.
 * @author de Moura
 */
public class MetadataEditor {

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

	public static void main(String[] args) {
		app.start();
		LOGGER.info("Application Started");
		System.getProperties().forEach((x, y) -> System.out.println(x+":"+y));
	}
}