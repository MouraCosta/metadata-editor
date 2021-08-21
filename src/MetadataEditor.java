import javax.swing.UIManager;

import com.moura.Application;

public class MetadataEditor {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception err) {
			err.printStackTrace();
		}
		Application app = new Application();
		app.start();
	}
}