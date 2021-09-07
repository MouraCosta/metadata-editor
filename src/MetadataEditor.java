import javax.swing.UIManager;

import com.moura.Application;

/**
 * Main.
 * @author de Moura
 */
public class MetadataEditor {

	public static Application app;

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception err) {
			err.printStackTrace();
		}
		app = new Application();
	}

	public static void main(String[] args) {
		app.start();
	}
}