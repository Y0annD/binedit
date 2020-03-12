package fr.yoanndiquelou.binedit;

import javax.swing.SwingUtilities;

import fr.yoanndiquelou.binedit.laf.ui.BinEditLookAndFeelCustomizer;

/**
 * BinEdit app launcher.
 *
 */
public class App {

	/**
	 * Launcher.
	 */
	public void launch() {
		SwingUtilities.invokeLater(() -> {
			BinEditLookAndFeelCustomizer.customize();
			MainFrame frame = new MainFrame();
		});
	}

	public static void main(String[] args) {
		App app = new App();
		app.launch();

	}
}
