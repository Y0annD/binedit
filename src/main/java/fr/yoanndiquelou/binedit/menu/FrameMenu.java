package fr.yoanndiquelou.binedit.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.Settings;
import fr.yoanndiquelou.binedit.frame.ViewerSettingsFrame;
import fr.yoanndiquelou.binedit.menu.action.DisplayToolbarAction;
import fr.yoanndiquelou.binedit.menu.action.GotoAction;
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
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		fileMenu.add(openItem);
		fileMenu.add(exitItem);

		// ------ Display menu
		JMenuItem preferencesItem = new JMenuItem(mBundle.getString("menu.display.preferences"));
//		preferencesItem.setEnabled(false);
//		preferencesItem.addActionListener(l->{
//			new ViewerSettingsFrame().setVisible(true);
//		});
		preferencesItem.setEnabled(!AppController.getInstance().getMDIPanel().getViewers().isEmpty());

		// ------ About menu
		JMenuItem aboutItem = new JMenuItem(mBundle.getString("menu.about.about"));
		aboutMenu.add(aboutItem);

		add(fileMenu);
		add(getEditMenu());
		add(getDisplayMenu());
		add(aboutMenu);
	}

	/**
	 * Return edit menu.
	 * 
	 * @return edit menu
	 */
	public JMenu getEditMenu() {
		JMenu editMenu = new JMenu(mBundle.getString("menu.edit"));
		editMenu.setName("menu.edit");
		JMenuItem cancelItem = new JMenuItem(mBundle.getString("menu.edit.undo"));
		cancelItem.setName("menu.edit.undo");
		cancelItem.setEnabled(false);
		editMenu.add(cancelItem);
		JMenuItem redoItem = new JMenuItem(mBundle.getString("menu.edit.redo"));
		redoItem.setName("menu.edit.redo");
		redoItem.setEnabled(false);
		editMenu.add(redoItem);
		editMenu.add(new JSeparator());
		JMenuItem cutItem = new JMenuItem(mBundle.getString("menu.edit.cut"));
		cutItem.setName("menu.edit.cut");
		cutItem.setEnabled(false);
		editMenu.add(cutItem);
		JMenuItem copyBinaryItem = new JMenuItem(mBundle.getString("menu.edit.copy.binary"));
		copyBinaryItem.setName("menu.edit.copy.binary");
		copyBinaryItem.setEnabled(false);
		editMenu.add(copyBinaryItem);
		JMenuItem copyTextItem = new JMenuItem(mBundle.getString("menu.edit.copy.text"));
		copyTextItem.setName("menu.edit.copy.text");
		copyTextItem.setEnabled(false);
		editMenu.add(copyTextItem);
		JMenuItem pasteItem = new JMenuItem(mBundle.getString("menu.edit.paste"));
		pasteItem.setName("menu.edit.paste");
		pasteItem.setEnabled(false);
		editMenu.add(pasteItem);
		JMenuItem deleteSelectionItem = new JMenuItem(mBundle.getString("menu.edit.deleteSelection"));
		deleteSelectionItem.setName("menu.edit.deleteSelection");
		deleteSelectionItem.setEnabled(false);
		editMenu.add(deleteSelectionItem);
		JMenuItem selectAllItem = new JMenuItem(mBundle.getString("menu.edit.selectAll"));
		selectAllItem.setName("menu.edit.selectAll");
		selectAllItem.setEnabled(false);
		editMenu.add(selectAllItem);
		JMenuItem selectItem = new JMenuItem(mBundle.getString("menu.edit.select"));
		selectItem.setName("menu.edit.select");
		selectItem.setEnabled(false);
		editMenu.add(selectItem);
		editMenu.add(new JSeparator());
		JMenuItem searchItem = new JMenuItem(mBundle.getString("menu.edit.search"));
		searchItem.setName("menu.edit.search");
		searchItem.setEnabled(false);
		editMenu.add(searchItem);
		JMenuItem nextItem = new JMenuItem(mBundle.getString("menu.edit.next"));
		nextItem.setName("menu.edit.next");
		nextItem.setEnabled(false);
		editMenu.add(nextItem);
		JMenuItem previousItem = new JMenuItem(mBundle.getString("menu.edit.previous"));
		previousItem.setName("menu.edit.previous");
		previousItem.setEnabled(false);
		editMenu.add(previousItem);
		JMenuItem replaceItem = new JMenuItem(mBundle.getString("menu.edit.replace"));
		replaceItem.setName("menu.edit.replace");
		replaceItem.setEnabled(false);
		editMenu.add(replaceItem);
		JMenuItem fillItem = new JMenuItem(mBundle.getString("menu.edit.fill"));
		fillItem.setName("menu.edit.fill");
		fillItem.setEnabled(false);
		editMenu.add(fillItem);
		editMenu.add(new JSeparator());
		JMenuItem insertItem = new JMenuItem(mBundle.getString("menu.edit.insert"));
		insertItem.setName("menu.edit.insert");
		insertItem.setEnabled(false);
		editMenu.add(insertItem);
		JMenuItem deleteItem = new JMenuItem(mBundle.getString("menu.edit.delete"));
		deleteItem.setName("menu.edit.delete");
		deleteItem.setEnabled(false);
		editMenu.add(deleteItem);
		editMenu.add(new JSeparator());
		JMenuItem goToItem = new JMenuItem(new GotoAction());
		goToItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		goToItem.setText(mBundle.getString("menu.edit.goto"));
		goToItem.setName("menu.edit.goto");
		goToItem.setEnabled(false);
		editMenu.add(goToItem);
		editMenu.add(new JSeparator());
		JMenuItem externalEditorItem = new JMenuItem(mBundle.getString("menu.edit.externalEditor"));
		externalEditorItem.setName("menu.edit.externalEditor");
		externalEditorItem.setEnabled(false);
		editMenu.add(externalEditorItem);

		editMenu.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				goToItem.setEnabled(null != AppController.getInstance().getFocusedEditor());
			}
		});
		return editMenu;
	}

	/**
	 * Return displayMenu.
	 * 
	 * @return display menu
	 */
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
		displayStatusBar.setSelected(Settings.getVisibility(Settings.DISPLAY_STATUSBAR));
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
