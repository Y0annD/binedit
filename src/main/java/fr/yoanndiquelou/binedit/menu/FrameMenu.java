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
import fr.yoanndiquelou.binedit.Settings;
import fr.yoanndiquelou.binedit.command.GotoCommand;
import fr.yoanndiquelou.binedit.frame.ViewerSettingsFrame;
import fr.yoanndiquelou.binedit.menu.action.DisplayToolbarAction;
import fr.yoanndiquelou.binedit.menu.action.VisibilityAction;

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
		setName("MENU_BAR");
		mFileChooser = new JFileChooser();
		JMenu fileMenu = new JMenu(mBundle.getString("menu.file"));
		fileMenu.setName("menu.file");
		JMenu aboutMenu = new JMenu(mBundle.getString("menu.about"));
		aboutMenu.setName("menu.about");
		// ------ File menu
		JMenuItem exitItem = new JMenuItem(mBundle.getString("menu.file.exit"));
		exitItem.setName("menu.file.exit");
		// on event exit the app
		exitItem.addActionListener(e -> {
			System.exit(0);
		});
		JMenuItem openItem = new JMenuItem(mBundle.getString("menu.file.open"));
		openItem.setName("menu.file.open");

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
		displayMenu.setName("menu.display");
		JCheckBoxMenuItem displayToolbarItem = new JCheckBoxMenuItem(new DisplayToolbarAction());
		displayToolbarItem.setText(mBundle.getString("menu.display.toolbar"));
		displayToolbarItem.setName("menu.display.toolbar");
		displayToolbarItem.setSelected(Settings.getDisplayToolbar());
		displayMenu.add(displayToolbarItem);
		JCheckBoxMenuItem displayDisplaybarItem = new JCheckBoxMenuItem(
				new VisibilityAction(Settings.DISPLAY_DISPLAYBAR));
		displayDisplaybarItem.setText(mBundle.getString("menu.display.displaybar"));
		displayDisplaybarItem.setName("menu.display.displaybar");
		displayDisplaybarItem.setSelected(Settings.getVisibility(Settings.DISPLAY_DISPLAYBAR));
		displayDisplaybarItem.setEnabled(false);
		displayMenu.add(displayDisplaybarItem);
		JCheckBoxMenuItem displayStatusBar = new JCheckBoxMenuItem(new VisibilityAction(Settings.DISPLAY_STATUSBAR));
		displayStatusBar.setText(mBundle.getString("menu.display.statusbar"));
		displayStatusBar.setName("menu.display.statusbar");
		displayStatusBar.setEnabled(false);
		displayMenu.add(displayStatusBar);
		displayMenu.add(new JSeparator());
		JMenuItem fontItem = new JMenuItem(mBundle.getString("menu.display.font"));
		fontItem.setName("menu.display.font");
		fontItem.setEnabled(false);
		displayMenu.add(fontItem);
		JMenuItem colorItem = new JMenuItem(mBundle.getString("menu.display.color"));
		colorItem.setName("menu.display.color");
		colorItem.setEnabled(false);
		displayMenu.add(colorItem);
		displayMenu.add(new JSeparator());
		JCheckBoxMenuItem addressesVisibilityItem = new JCheckBoxMenuItem(
				new VisibilityAction(Settings.DISPLAY_ADDRESSES));
		addressesVisibilityItem.setText(mBundle.getString("menu.display.address"));
		addressesVisibilityItem.setName("menu.display.address");
		displayMenu.add(addressesVisibilityItem);
		displayMenu.add(new JSeparator());
		JCheckBoxMenuItem addressesInHexa = new JCheckBoxMenuItem(new VisibilityAction(Settings.ADDRESSES_HEXA));
		addressesInHexa.setText(mBundle.getString("menu.display.address.hexa"));
		addressesInHexa.setName("menu.display.address.hexa");
		displayMenu.add(addressesInHexa);
		JCheckBoxMenuItem informationInHexa = new JCheckBoxMenuItem(new VisibilityAction(Settings.INFO_HEXA));
		informationInHexa.setText(mBundle.getString("menu.display.info.hexa"));
		informationInHexa.setName("menu.display.info.hexa");
		displayMenu.add(informationInHexa);
		displayMenu.add(new JSeparator());
		JMenuItem infoByLine = new JMenuItem(mBundle.getString("menu.display.info.line"));
		infoByLine.setName("menu.display.info.line");
		displayMenu.add(infoByLine);
		infoByLine.addActionListener((e) -> {
			if (null != AppController.getInstance().getFocusedEditor()) {
				ViewerSettingsFrame frame = new ViewerSettingsFrame(
						AppController.getInstance().getFocusedEditor().getSettings());
				frame.setVisible(true);
			}
		});
		return displayMenu;
	}
}
