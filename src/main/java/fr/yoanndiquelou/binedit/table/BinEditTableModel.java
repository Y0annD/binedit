package fr.yoanndiquelou.binedit.table;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import fr.yoanndiquelou.binedit.Settings;
import fr.yoanndiquelou.binedit.model.DisplayMode;
import fr.yoanndiquelou.binedit.model.ViewerSettings;

public class BinEditTableModel extends AbstractTableModel implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6407633384182119219L;
	/** Chunk size. */
	public static final int DEFAULT_CHUNK_SIZE = 1024 * 1024;
	/** File. */
	private final RandomAccessFile mFile;

	/** Nombre de byte par ligne. */
	public final ViewerSettings mSettings;
	/** adresse maximum sous forme de chaine de caractere. */
	public final String mMaxAddrStr;
	/** Byte buffer. */
	private final ByteBuffer mBuffer;
	/** Content. */
	private byte[] mContent;
	/** minAddrSelection. */
	private long mMinAddr;
	/** maxAddrSelection. */
	private long mMaxAddr;
	/** Mode d'affichage. */
	private DisplayMode mDisplayMode;
	/** content start address. */
	private int mContentStartAddress;
	/** File size. */
	private long mFileSize;
	/** Chunk size. */
	private int mChunkSize;

	public BinEditTableModel(RandomAccessFile file, long size, ViewerSettings settings) {
		super();

		mFile = file;
		mDisplayMode = Settings.getDisplayMode();
		mSettings = settings;
		mFileSize = size;
		mChunkSize = (int) Math.min(DEFAULT_CHUNK_SIZE, mFileSize);
		mBuffer = ByteBuffer.allocate(mChunkSize);
		try {
			mFile.getChannel().read(mBuffer);
			mBuffer.flip();
			mContent = mBuffer.array();
		} catch (IOException e) {
			mContent = new byte[(int) size];
			Arrays.fill(mContent, (byte) 0);
		}
		mMaxAddrStr = String.format("0X%02x", mFileSize - 1).toUpperCase(Locale.getDefault());
		mMinAddr = mMaxAddr = mContentStartAddress = 0;
		Settings.addSettingsChangeListener(this);
	}

	@Override
	public int getRowCount() {
		return (int) Math.ceil(mFileSize / (double) mSettings.getNbWordPerLine());
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
		int result;
		if (isValidAddress(rowIndex, columnIndex)) {
			if (columnIndex > mSettings.getNbWordPerLine()) {
				result = mContent[(rowIndex * mSettings.getNbWordPerLine() - mContentStartAddress) + columnIndex
						- mSettings.getNbWordPerLine() - 1 + mSettings.getShift()];
			} else if (columnIndex > 0) {
				result = mContent[(rowIndex * mSettings.getNbWordPerLine() - mContentStartAddress) + columnIndex - 1
						+ mSettings.getShift()];
			} else {
				result = getAddress(rowIndex, columnIndex) + 1;
			}
		} else {
			result = 0;
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
	public int getAddress(int rowIndex, int columnIndex) {
		return rowIndex * mSettings.getNbWordPerLine() + (columnIndex < mSettings.getNbWordPerLine() ? columnIndex
				: columnIndex - mSettings.getNbWordPerLine()) - 1 + mSettings.getShift();
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
			byte a = mContent[(rowIndex * mSettings.getNbWordPerLine() -mContentStartAddress)+ columnIndex - 1 + mSettings.getShift()];
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

	public void updateViewPort(int firstRow) {
		long firstAddress = firstRow * mSettings.getNbWordPerLine();
		if (firstAddress < mContentStartAddress
				|| (firstAddress > mContentStartAddress + 0.75 * mChunkSize&& mContentStartAddress+mChunkSize<mFileSize)) {
			long startAddress = -1;
			if (firstAddress >= mContentStartAddress + 0.75 * mChunkSize) {
				startAddress = Math.min((long) (mContentStartAddress + 0.5 * mChunkSize), mFileSize);
			} else {
				startAddress = Math.max(0, (long) (mContentStartAddress - 0.5 * mChunkSize));
			}
			try {
				mBuffer.clear();
				Arrays.fill(mContent, (byte)0);
				mFile.getChannel().position(startAddress).read(mBuffer);
				mBuffer.flip();
				mContentStartAddress = (int) startAddress;
				mContent = mBuffer.array();
				fireTableDataChanged();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
