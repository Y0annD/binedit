package fr.yoanndiquelou.binedit.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.panel.SettingsFrame;

/**
 * Main frame menu.
 * 
 * @author Y0annD
 *
 */
public class FrameMenu extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5569892852996274252L;

	private JFileChooser mFileChooser;

	/** Resources bundle for i18n. */
	private ResourceBundle mBundle = ResourceBundle.getBundle("MenuBundle");

	public FrameMenu() {
		mFileChooser = new JFileChooser();
		JMenu fileMenu = new JMenu(mBundle.getString("menu.file"));
		JMenu displayMenu = new JMenu(mBundle.getString("menu.display"));
		JMenu aboutMenu = new JMenu(mBundle.getString("menu.about"));

		// ------ File menu
		JMenuItem exitItem = new JMenuItem(mBundle.getString("menu.file.exit"));
		// on event exit the app
		exitItem.addActionListener(e -> System.exit(0));
		JMenuItem openItem = new JMenuItem(mBundle.getString("menu.file.open"));

		openItem.addActionListener(l -> {
			int result = mFileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				AppController.getInstance().openFile(mFileChooser.getSelectedFile());
			}
		});
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.META_MASK));
		fileMenu.add(openItem);
		fileMenu.add(exitItem);

		// ------ Display menu
		JMenuItem preferencesItem = new JMenuItem(mBundle.getString("menu.display.preferences"));
//		preferencesItem.setEnabled(false);
		preferencesItem.addActionListener(l->{
			new SettingsFrame().setVisible(true);
		});
		preferencesItem.setEnabled(!AppController.getInstance().getMDIPanel().getViewers().isEmpty());
		displayMenu.add(preferencesItem);
		displayMenu.add(new JSeparator());

		// ------ About menu
		JMenuItem aboutItem = new JMenuItem(mBundle.getString("menu.about.about"));
		aboutMenu.add(aboutItem);

		add(fileMenu);
		add(displayMenu);
		add(aboutMenu);
	}
}
