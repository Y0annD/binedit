package fr.yoanndiquelou.binedit.table;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.table.AbstractTableModel;

import fr.yoanndiquelou.binedit.Settings;
import fr.yoanndiquelou.binedit.model.DisplayMode;
import fr.yoanndiquelou.binedit.model.ViewerSettings;
import fr.yoanndiquelou.binedit.utils.AddressUtils;

public class BinEditTableModel extends AbstractTableModel implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6407633384182119219L;
	/** Contenu. */
	public final byte[] mContent;
	/** Nombre de byte par ligne. */
	public final ViewerSettings mSettings;
	/** adresse maximum sous forme de chaine de caractere. */
	public final String mMaxAddrStr;
	/** minAddrSelection. */
	private long mMinAddr;
	/** maxAddrSelection. */
	private long mMaxAddr;
	/** Mode d'affichage. */
	private DisplayMode mDisplayMode;

	public BinEditTableModel(byte[] content, ViewerSettings settings) {
		super();
		mDisplayMode = Settings.getDisplayMode();
		mContent = content;
		mSettings = settings;
		mMaxAddrStr = String.format("%02x", content.length - 1);
		mMinAddr = mMaxAddr = 0;
		Settings.addSettingsChangeListener(this);
	}

	@Override
	public int getRowCount() {
		return (int) Math.ceil(mContent.length / (double) mSettings.getNbWordPerLine());
	}

	@Override
	public int getColumnCount() {
		return (mDisplayMode.displayChar() ? 2 : 1) * mSettings.getNbWordPerLine() + 1;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return false;
		} else {
			return super.isCellEditable(rowIndex, columnIndex);
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String result;
		if (isValidAddress(rowIndex, columnIndex)) {
			if (columnIndex > mSettings.getNbWordPerLine()) {
				byte[] content = new byte[1];
				content[0] = mContent[rowIndex * mSettings.getNbWordPerLine() + columnIndex
						- mSettings.getNbWordPerLine() - 1 + mSettings.getShift()];
				result = new String(content).replace("\n", ".").replace(" ", ".");
			} else if (columnIndex > 0) {
				result = String.format("%02x",
						mContent[rowIndex * mSettings.getNbWordPerLine() + columnIndex - 1 + mSettings.getShift()])
						.toUpperCase();
			} else {
				result = getAddress(rowIndex, columnIndex);
			}
		} else {
			if (columnIndex > mSettings.getNbWordPerLine()) {
				result = new String(new byte[] { 0 }).replace("\n", ".").replace(" ", ".");
			} else {
				result = String.format("%02x", 0).toUpperCase();
			}
		}
		return result;
	}

	/**
	 * Get address with 00 added before when needed.
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @return
	 */
	private String getAddress(int rowIndex, int columnIndex) {
		int maxChar = mMaxAddrStr.length();
		String result;
		long addr = rowIndex * mSettings.getNbWordPerLine() + columnIndex + mSettings.getShift();
		result = AddressUtils.getHexString(addr);

		while ((addr >= 0 ? result.length() : result.length() - 1) < maxChar) {
			if (addr < 0) {
				result = "-0".concat(result.substring(1));
			} else {
				result = "0".concat(result);
			}
		}
		return result;
	}

	/**
	 * The address at the specified coordinates is valid?
	 * 
	 * @param rowIndex    row index in table
	 * @param columnIndex column index in table
	 * @return true if valid.
	 */
	private boolean isValidAddress(int rowIndex, int columnIndex) {
		try {
			if (columnIndex == 0)
				return true;
			if (columnIndex > mSettings.getNbWordPerLine()) {
				columnIndex -= mSettings.getNbWordPerLine();
			}
			byte a = mContent[rowIndex * mSettings.getNbWordPerLine() + columnIndex - 1 + mSettings.getShift()];
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}

	}

	/**
	 * Viewer settings.
	 * 
	 * @return
	 */
	public ViewerSettings getSettings() {
		return mSettings;
	}

	/**
	 * Update selection addresses.
	 * 
	 * @param minAddr min selection address
	 * @param maxAddr max selection address
	 */
	public void updateSelection(long minAddr, long maxAddr) {
		mMinAddr = minAddr;
		mMaxAddr = maxAddr;
	}

	/**
	 * Get the minimum selection address.
	 * 
	 * @return min addr selection
	 */
	public long getMinSelectionAddr() {
		return mMinAddr;
	}

	/**
	 * Get the maximum selection address.
	 * 
	 * @return max addr selection
	 */
	public long getMaxSelectionAddr() {
		return mMaxAddr;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (Settings.DISPLAY_MODE_EVENT.equals(evt.getPropertyName())) {
			mDisplayMode = (DisplayMode) evt.getNewValue();
			if (mDisplayMode.displayChar() != ((DisplayMode) evt.getOldValue()).displayChar()) {
				fireTableStructureChanged();
			}
		} else if (Settings.WORD_PER_LINE.equals(evt.getPropertyName())) {
			mSettings.setNbWordPerline((int) evt.getNewValue());
		}

	}
}
