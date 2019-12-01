package fr.yoanndiquelou.binedit.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.command.GotoCommand;
import fr.yoanndiquelou.binedit.menu.action.DisplayToolbarAction;

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
	private transient ResourceBundle mBundle = ResourceBundle.getBundle("MenuBundle");

	public FrameMenu() {
		mFileChooser = new JFileChooser();
		JMenu fileMenu = new JMenu(mBundle.getString("menu.file"));

		JMenu aboutMenu = new JMenu(mBundle.getString("menu.about"));

		// ------ File menu
		JMenuItem exitItem = new JMenuItem(mBundle.getString("menu.file.exit"));
		// on event exit the app
		exitItem.addActionListener(e -> {
			System.exit(0);
		});
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
		JMenuItem gotoMenuItem = new JMenuItem(mBundle.getString("menu.display.goto"));
		gotoMenuItem.addActionListener(new GotoCommand());
		JMenuItem findMenuItem = new JMenuItem(mBundle.getString("menu.display.find"));
		JMenuItem replaceMenuItem = new JMenuItem(mBundle.getString("menu.display.replace"));
		JMenuItem preferencesItem = new JMenuItem(mBundle.getString("menu.display.preferences"));
//		preferencesItem.setEnabled(false);
//		preferencesItem.addActionListener(l->{
//			new ViewerSettingsFrame().setVisible(true);
//		});
		preferencesItem.setEnabled(!AppController.getInstance().getMDIPanel().getViewers().isEmpty());
//		displayMenu.add(gotoMenuItem);
//		displayMenu.add(findMenuItem);
//		displayMenu.add(replaceMenuItem);
//		displayMenu.add(preferencesItem);
//		displayMenu.add(new JSeparator());

		// ------ About menu
		JMenuItem aboutItem = new JMenuItem(mBundle.getString("menu.about.about"));
		aboutMenu.add(aboutItem);

		add(fileMenu);
		add(getDisplayMenu());
		add(aboutMenu);
	}
	
	public JMenu getDisplayMenu() {
		JMenu displayMenu = new JMenu(mBundle.getString("menu.display"));
		JCheckBoxMenuItem displayToolbarItem = new JCheckBoxMenuItem(new DisplayToolbarAction());
		displayToolbarItem.setText(mBundle.getString("menu.display.toolbar"));
		displayMenu.add(displayToolbarItem);
		JCheckBoxMenuItem displayDisplaybarItem = new JCheckBoxMenuItem(mBundle.getString("menu.display.displaybar"));
		displayMenu.add(displayDisplaybarItem);
		JCheckBoxMenuItem displayStatusBar = new JCheckBoxMenuItem(mBundle.getString("menu.display.statusbar"));
		displayMenu.add(displayStatusBar);
		displayMenu.add(new JSeparator());
		return displayMenu;
	}
}
