package fr.yoanndiquelou.binedit.panel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class ViewerSettings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1059145955991260906L;

	public static final String NB_WORD_PER_LINE = "nbWordPerLine";

	private PropertyChangeSupport changeSupport;

	private int mNbWordPerLine;

	public ViewerSettings() {
		changeSupport = new PropertyChangeSupport(this);
		mNbWordPerLine = 16;
	}


	/**
	 * Retourne le nombre de mots par ligne.
	 * 
	 * @return nombre de mots par ligne
	 */
	public int getNbWordPerLine() {
		return mNbWordPerLine;
	}

	public void setNbWordPerline(int newNbWord) {
		int oldValue = mNbWordPerLine;
		mNbWordPerLine = newNbWord;
		changeSupport.firePropertyChange(NB_WORD_PER_LINE, oldValue, mNbWordPerLine);
	}

	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

}
