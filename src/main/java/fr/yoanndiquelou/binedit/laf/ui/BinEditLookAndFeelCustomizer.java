package fr.yoanndiquelou.binedit.laf.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import fr.yoanndiquelou.binedit.Settings;

public final class BinEditLookAndFeelCustomizer {

	private static Preferences mUIPreferences = Preferences.userNodeForPackage(Settings.class);

	public static final void customize() {
		try {
			UIManager.setLookAndFeel(/* NimbusLookAndFeel.class.getName() */UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UIManager.put("BinaryViewer.Font", new Font(mUIPreferences.get("BinaryViewer.Font", "Courrier"), Font.BOLD,
				mUIPreferences.getInt("BinaryViewer.font.size", 9)));
		UIManager.put("BinaryViewer.alternateRowColor", Color.lightGray);
		UIManager.put("Table.focusCellForeground", Color.black);
		UIManager.put("Table.focusCellBackground", Color.white);
		UIManager.put("Table.selectionForeground", Color.black);
		UIManager.put("Selection.background", new Color(191, 98, 4));
		UIManager.put("Selection.foreground", Color.black);
		customizeImages();
	}

	/**
	 * Customize icons.
	 */
	public static final void customizeImages() {
		UIManager.put("save.icon", getIcon("resources/save.png"));
		UIManager.put("saveAs.icon", getIcon("resources/saveAs.png"));
		UIManager.put("clean.icon", getIcon("resources/clean.png"));
		UIManager.put("cut.icon", getIcon("resources/cut.png"));
		UIManager.put("undo.icon", getIcon("resources/undo.png"));
		UIManager.put("redo.icon", getIcon("resources/redo.png"));
		UIManager.put("copy.icon", getIcon("resources/copy.png"));
		UIManager.put("paste.icon", getIcon("resources/paste.png"));
	}

	/**
	 * Get a resized icon.
	 * 
	 * @param url url of icon
	 * @return scaled icon
	 */
	private static ImageIcon getIcon(String url) {
		ImageIcon icon = new ImageIcon(url);
		return new ImageIcon(icon.getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT));
	}

}
