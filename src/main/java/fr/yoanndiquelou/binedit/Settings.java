package fr.yoanndiquelou.binedit;

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

	/**
	 * Add listener if not already declared.
	 * 
	 * @param listener listener to add
	 */
	public static void addSettingsChangeListener(PropertyChangeListener listener) {
		if (!mListeners.contains(listener)) {
			mListeners.add(listener);
		}
	}

	/**
	 * Remove listener if already declared.
	 * 
	 * @param listener listener to remove
	 */
	public static void removePropertyChangeListener(PropertyChangeListener listener) {
		if (mListeners.contains(listener)) {
			mListeners.remove(listener);
		}
	}
}
