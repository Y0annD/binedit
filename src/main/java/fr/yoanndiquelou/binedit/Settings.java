package fr.yoanndiquelou.binedit;

import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import fr.yoanndiquelou.binedit.model.DisplayMode;

public class Settings {
	/** Display mode change event notifier. */
	public static final String DISPLAY_MODE_EVENT = "DisplayMode";
	/** Number of bytes per line event notifier. */
	public static final String WORD_PER_LINE = "WORD_PER_LINE";
	public static final String DISPLAY_CHAR = "DISPLAY_CHAR";
	public static final String NB_BYTES = "NB_BYTES";
	/** Display toolbar. */
	public static final String DISPLAY_TOOLBAR = "DISPLAY_TOOLBAR";
	/** Display display bar. */
	public static final String DISPLAY_DISPLAYBAR = "DISPLAY_DISPLAYBAR";
	/** Display status bar. */
	public static final String DISPLAY_STATUSBAR = "DISPLAY_STATUSBAR";
	/** Display addresses. */
	public static final String DISPLAY_ADDRESSES = "DISPLAY_ADDRESSES";
	/** Display addresses in hexa. */
	public static final String ADDRESSES_HEXA = "ADDRESSES_HEXA";
	/** Display addresses in hexa. */
	public static final String INFO_HEXA = "INFO_HEXA";
	/** Font size. */
	public static final String FONT_SIZE = "BinaryViewer.font.size";
	/** Font. */
	public static final String FONT = "BinaryBiewer.font";

	private static final Preferences mPrefs = Preferences.userRoot().node(Settings.class.getName());

	/** Number of bytes per line. */
	private static int mNbBytesPerLine = mPrefs.getInt(WORD_PER_LINE, 24);
	/** Display mode. */
	private static DisplayMode mDisplayMode = DisplayMode.fromValue(mPrefs.getBoolean(DISPLAY_CHAR, true),
			mPrefs.getInt(NB_BYTES, 1));

	/** Settings listeners. */
	private static List<PropertyChangeListener> mListeners = new ArrayList<>();

	/**
	 * Constructor.
	 */
	private Settings() {
		super();
	}

	/**
	 * Get number of bytes per line.
	 * 
	 * @return number of bytes per line
	 */
	public static int getBytesPerLine() {
		return mNbBytesPerLine;
	}

	/**
	 * set number of bytes per line.
	 * 
	 * @param number of bytes per line
	 */
	public static void setBytesPerLine(int nbBytesPerLine) {
		int oldValue = mNbBytesPerLine;
		if (oldValue != nbBytesPerLine) {
			mNbBytesPerLine = nbBytesPerLine;
			mPrefs.putInt(WORD_PER_LINE, mNbBytesPerLine);
			for (PropertyChangeListener listener : mListeners) {
				if (null != listener) {
					listener.propertyChange(new PropertyChangeEvent(null, WORD_PER_LINE, oldValue, mNbBytesPerLine));
				}
			}
		}
	}

	/**
	 * Get display mode.
	 * 
	 * @return display mode
	 */
	public static DisplayMode getDisplayMode() {
		return mDisplayMode;
	}

	/**
	 * Set display mode.
	 * 
	 * @param mode display mode
	 */
	public static void setDisplayMode(DisplayMode mode) {
		DisplayMode oldValue = mDisplayMode;
		if (mDisplayMode != mode) {
			mDisplayMode = mode;
			mPrefs.putBoolean(DISPLAY_CHAR, mode.displayChar());
			mPrefs.putInt(NB_BYTES, mode.getBytes());
			for (PropertyChangeListener listener : mListeners) {
				if (null != listener) {
					listener.propertyChange(new PropertyChangeEvent(mode, DISPLAY_MODE_EVENT, oldValue, mDisplayMode));
				}
			}
		}
	}

	public static void setDisplayToolbar(boolean display) {
		boolean oldValue = getDisplayToolbar();
		if (oldValue != display) {
			mPrefs.putBoolean(DISPLAY_TOOLBAR, display);
			for (PropertyChangeListener listener : mListeners) {
				listener.propertyChange(new PropertyChangeEvent(display, DISPLAY_TOOLBAR, oldValue, display));
			}
		}

	}

	public static boolean getDisplayToolbar() {
		return getVisibility(DISPLAY_TOOLBAR);
	}

	/**
	 * Get property visibility.
	 * 
	 * @param property property to get
	 * @return visibility
	 */
	public static boolean getVisibility(String property) {
		return mPrefs.getBoolean(property, true);
	}

	/**
	 * Set property visibility.
	 * 
	 * @param property property to change
	 * @param value    value to put
	 */
	public static void setVisibility(String property, boolean value) {
		boolean oldValue = getVisibility(property);
		if (oldValue != value) {
			mPrefs.putBoolean(property, value);
			for (PropertyChangeListener listener : mListeners) {
				listener.propertyChange(new PropertyChangeEvent(property, property, oldValue, value));
			}
		}
	}

	/**
	 * Set font size.
	 * 
	 * @param size font size
	 */
	public static void setFontSize(int size) {
		int oldValue = getFontSize();
		if (oldValue != size) {
			mPrefs.putInt(FONT_SIZE, size);
			for (PropertyChangeListener listener : mListeners) {
				listener.propertyChange(new PropertyChangeEvent(FONT_SIZE, FONT_SIZE, oldValue, size));
			}
		}
	}

	/**
	 * Get font size.
	 * 
	 * @return font size
	 */
	public static int getFontSize() {
		return mPrefs.getInt(FONT_SIZE, 12);
	}

	/**
	 * Set font.
	 * 
	 * @param font new font
	 */
	public static void setFont(Font font) {
		Font oldValue = getFont();
		if (!oldValue.equals(font)) {
			mPrefs.put(FONT, font.getFontName());
			for (PropertyChangeListener listener : mListeners) {
				listener.propertyChange(new PropertyChangeEvent(FONT, FONT, oldValue, font));
			}
		}
	}

	/**
	 * Get font.
	 * 
	 * @return font
	 */
	public static Font getFont() {
		return new Font(mPrefs.get(FONT, "Courrier"), Font.PLAIN, getFontSize());
	}

	/**
	 * Add listener if not already declared.
	 * 
	 * @param listener listener to add
	 */
	public static void addSettingsChangeListener(PropertyChangeListener listener) {
		mListeners.add(listener);
	}

	/**
	 * Remove listener if already declared.
	 * 
	 * @param listener listener to remove
	 */
	public static void removePropertyChangeListener(PropertyChangeListener listener) {
		mListeners.remove(listener);
	}
}
