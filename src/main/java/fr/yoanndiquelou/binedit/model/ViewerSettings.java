package fr.yoanndiquelou.binedit.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.prefs.Preferences;

public class ViewerSettings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1059145955991260906L;

	/** Number of word per line property key. */
	public static final String NB_WORD_PER_LINE = "nbWordPerLine";
	/** Fix number of column key. */
	public static final String FIX_NB_OF_COLUMN = "fixNumberOfColumn";
	/** Shift key. */
	public static final String SHIFT = "shift";

	/** Used to notify settings change. */
	private PropertyChangeSupport changeSupport;

	/** Number of words per line. */
	private int mNbWordPerLine;
	/** Shift number. */
	private int mShift;
	/** Fix number of column. */
	private boolean mFixNumberOfColumn;
	/** Preferences. */
	private Preferences mPrefs;

	/**
	 * 
	 */
	public ViewerSettings(Preferences prefs) {
		changeSupport = new PropertyChangeSupport(this);
		mPrefs = prefs;
		mNbWordPerLine = prefs.getInt(NB_WORD_PER_LINE, 16);
		mFixNumberOfColumn = prefs.getBoolean(FIX_NB_OF_COLUMN, true);
		mShift = 0;
	}

	/**
	 * Retourne le nombre de mots par ligne.
	 * 
	 * @return nombre de mots par ligne
	 */
	public int getNbWordPerLine() {
		return mNbWordPerLine;
	}

	/**
	 * Shift number.
	 * 
	 * @return shift number
	 */
	public int getShift() {
		return mShift;
	}

	/**
	 * Change number of words per line.
	 * 
	 * @param newNbWord new number of words per line
	 */
	public void setNbWordPerline(int newNbWord) {
		if (newNbWord != mNbWordPerLine) {
			int oldValue = mNbWordPerLine;
			mNbWordPerLine = newNbWord;
			mPrefs.putInt(NB_WORD_PER_LINE, newNbWord);
			changeSupport.firePropertyChange(NB_WORD_PER_LINE, oldValue, mNbWordPerLine);
		}
	}

	/**
	 * Set new shift value.
	 * 
	 * @param shift shift value
	 */
	public void setShift(int shift) {
		if (shift != mNbWordPerLine) {
			int oldValue = mShift;
			mShift = shift;
			changeSupport.firePropertyChange(SHIFT, oldValue, mShift);
		}
	}

	/**
	 * Does the number of columns are fix or depends of frame size.
	 * 
	 * @param fix fix number of column or not
	 */
	public void setFixNumberOfColumn(boolean fix) {
		if (mFixNumberOfColumn != fix) {
			mFixNumberOfColumn = fix;
			changeSupport.firePropertyChange(FIX_NB_OF_COLUMN, !fix, mFixNumberOfColumn);
		}
	}

	/**
	 * Is fixed number of column.
	 * 
	 * @return fix number of column
	 */
	public boolean getFixNumberOfColumn() {
		return mFixNumberOfColumn;
	}

	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

}
