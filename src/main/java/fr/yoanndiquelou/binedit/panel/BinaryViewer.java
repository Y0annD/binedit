package fr.yoanndiquelou.binedit.panel;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import fr.yoanndiquelou.binedit.AppController;
import fr.yoanndiquelou.binedit.Settings;
import fr.yoanndiquelou.binedit.model.ViewerSettings;
import fr.yoanndiquelou.binedit.table.BinEditTableCellRenderer;
import fr.yoanndiquelou.binedit.table.BinEditTableModel;

public class BinaryViewer extends JInternalFrame implements ListSelectionListener {

	/**
	 * serial id.
	 */
	private static final long serialVersionUID = -5743598459949152992L;

	/** Resources bundle for i18n. */
	private ResourceBundle mBundle = ResourceBundle.getBundle("MenuBundle");

	/** Viewed file. */
	private File mFile;
	/** RandomAccessFile. */
	private RandomAccessFile mRaf;
	/** Byte per line. */
	private ViewerSettings mSettings;
	/** Table model. */
	private BinEditTableModel mModel;
	/** Table contenant le fichier. */
	private JTable mTable;
	/** Panel d'information. */
	private InfoPanel mInfoPanel;
	/** Preferences. */
	private Preferences mPrefs;
	/** Scroll pane. */
	private JScrollPane mScroll;
	/** Property change listener. */
	private PropertyChangeListener mSettingListener;
	/** Editor specific setting listener. */
	private PropertyChangeListener mLocalSettingListener;
	/** File size. */
	private long mSize;

	/**
	 * Binary file viewer.
	 * 
	 * @param file file to visualize
	 */
	public BinaryViewer(File file) {
		mPrefs = Preferences.userRoot().node(this.getClass().getName());
		mFile = file;
		mSettings = new ViewerSettings(mPrefs);
		setName(String.format("BinaryViewer_%s/%s", mFile.getParentFile().getName(), mFile.getName()));
		setTitle(file.getName());
		setClosable(true);
		setResizable(true);
		setIconifiable(true);

		BasicFileAttributes attr;
		try {
			attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
			mSize = attr.size();
		} catch (IOException e) {
			mSize = 0;
		}
		try {
			mRaf = new RandomAccessFile(mFile, "r");

			mModel = new BinEditTableModel(mRaf, mSize, mSettings);
			TableCellRenderer renderer = new BinEditTableCellRenderer();
			mTable = new JTable(mModel);
			mTable.setName("ContentTable");
			mTable.setFillsViewportHeight(true);
			mTable.setRowSelectionAllowed(true);
			mTable.setColumnSelectionAllowed(true);
			mTable.setCellSelectionEnabled(true);
			mTable.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			mTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			mTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			mTable.setTableHeader(null);
			mTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			mTable.setDefaultRenderer(Object.class, renderer);
			mTable.getColumnModel().getSelectionModel().addListSelectionListener(this);
			mTable.getSelectionModel().addListSelectionListener(this);
			mTable.getColumnModel().addColumnModelListener(new TableColumnModelListener() {

				@Override
				public void columnSelectionChanged(ListSelectionEvent e) {
					updateTableConstraints();
				}

				@Override
				public void columnRemoved(TableColumnModelEvent e) {
					updateTableConstraints();
				}

				@Override
				public void columnMoved(TableColumnModelEvent e) {
					updateTableConstraints();
				}

				@Override
				public void columnMarginChanged(ChangeEvent e) {
					updateTableConstraints();
				}

				@Override
				public void columnAdded(TableColumnModelEvent e) {
					updateTableConstraints();
				}
			});

			updateTableConstraints();
			mScroll = new JScrollPane(mTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			mScroll.getVerticalScrollBar().setName("ContentScroll_Vertical");
			mScroll.getViewport().addChangeListener(new DelayedChangeHandler());
			mScroll.getViewport().addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					int rowHeight = mTable.getRowHeight();
					Point pos = mScroll.getViewport().getViewPosition();
					int row = (int) (pos.getY() / rowHeight);
					mModel.updateViewPort(row);
				}
			});

			getContentPane().add(mScroll, BorderLayout.CENTER);
			mInfoPanel = new InfoPanel(mSize);
			getContentPane().add(mInfoPanel, BorderLayout.SOUTH);

			AppController.getInstance().setFocusedEditor(BinaryViewer.this);
			mSettingListener = new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (Settings.ADDRESSES_HEXA.equals(evt.getPropertyName())) {
						mInfoPanel.setAddr(mModel.getMinSelectionAddr(), mModel.getMaxSelectionAddr());
					} else if (Settings.DISPLAY_STATUSBAR.equals(evt.getPropertyName())) {
						mInfoPanel.setVisible((boolean) evt.getNewValue());
					} else if (Settings.FONT.equals(evt.getPropertyName()) ||Settings.FONT_SIZE.equals(evt.getPropertyName())) {
						mTable.repaint();
					}
				}
			};
			Settings.addSettingsChangeListener(mSettingListener);

			mLocalSettingListener = new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					mTable.getSelectionModel().removeListSelectionListener(BinaryViewer.this);
					mTable.getColumnModel().getSelectionModel().removeListSelectionListener(BinaryViewer.this);
					mModel.fireTableStructureChanged();
					computeMaxColumnNumber();
					updateTableConstraints();

					updateSelection();
					mInfoPanel.setShift(mSettings.getShift());
					mTable.repaint();
					mTable.getSelectionModel().addListSelectionListener(BinaryViewer.this);
					mTable.getColumnModel().getSelectionModel().addListSelectionListener(BinaryViewer.this);

				}
			};

			mSettings.addPropertyChangeListener(mLocalSettingListener);

			mModel.addTableModelListener(l -> {
				updateTableConstraints();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				AppController.getInstance().setFocusedEditor(BinaryViewer.this);
			}
		});

		addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {
				computeMaxColumnNumber();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});

		pack();
		setVisible(true);

	}

	/**
	 * Update selection.
	 */
	public void updateSelection() {
		long minRow = mModel.getMinSelectionAddr() / mSettings.getNbWordPerLine();
		long maxRow = mModel.getMaxSelectionAddr() / mSettings.getNbWordPerLine();
		long minColumn = mModel.getMinSelectionAddr() - minRow * mSettings.getNbWordPerLine();
		long maxColumn = mModel.getMaxSelectionAddr() - maxRow * mSettings.getNbWordPerLine();
		if (Settings.getVisibility(Settings.DISPLAY_ADDRESSES)) {
			minColumn += 1;
			maxColumn += 1;
		}
		mTable.getSelectionModel().addSelectionInterval((int) minRow, (int) maxRow);
		mTable.getColumnModel().getSelectionModel().addSelectionInterval((int) minColumn, (int) maxColumn);
	}

	/**
	 * Update column size.
	 */
	public void updateTableConstraints() {
		TableColumn column;
		int width;
		JLabel label = new JLabel();

		int start = 0;
		if (Settings.getVisibility(Settings.DISPLAY_ADDRESSES)) {
			start = 1;
		}
		for (int i = start; i < mTable.getColumnModel().getColumnCount(); i++) {
			column = mTable.getColumnModel().getColumn(i);
			column.sizeWidthToFit();
			if (i > mSettings.getNbWordPerLine()) {
				width = 17;
			} else {
				width = 24;
			}
			column.setMaxWidth(width);
			column.setMinWidth(width);
			column.setPreferredWidth(width);
			column.setWidth(width);
		}
	}

	/**
	 * Update number of column when "Fix number of column" is not checked.
	 */
	public void computeMaxColumnNumber() {
		if (!mSettings.getFixNumberOfColumn()) {
			int maxWidth = getWidth() - mScroll.getVerticalScrollBar().getWidth();
			int size = 0;
			int nbColumn = 0;
			if (Settings.getVisibility(Settings.DISPLAY_ADDRESSES)) {
				size += mTable.getColumnModel().getColumn(0).getWidth();
			}

			while (size < maxWidth) {
				size += 24;
				size += mTable.getColumnModel().getColumnMargin();
				if (Settings.getVisibility(Settings.DISPLAY_CHAR)) {
					size += 17;
					size += mTable.getColumnModel().getColumnMargin();
				}
				if (size < maxWidth) {
					nbColumn++;
				}
			}
			mSettings.setNbWordPerline(nbColumn);
		}
	}

	public ViewerSettings getSettings() {
		return mSettings;
	}

	/**
	 * Repaint du tabulaire pour le changement de selection de la colone.
	 */
	public void valueChanged(ListSelectionEvent le) {
		long minSelectionAddr;
		long maxSelectionAddr;
		long anchorRow = mTable.getSelectionModel().getAnchorSelectionIndex();
		long anchorColumn = mTable.getColumnModel().getSelectionModel().getAnchorSelectionIndex();

		long minRow;
		long maxRow;
		long minColumn;
		long maxColumn;

		minRow = mTable.getSelectionModel().getMinSelectionIndex();
		maxRow = mTable.getSelectionModel().getMaxSelectionIndex();
		if (minRow < 0 && maxRow < 0) {
			return;
		}
		minColumn = mTable.getColumnModel().getSelectionModel().getMinSelectionIndex();
		maxColumn = mTable.getColumnModel().getSelectionModel().getMaxSelectionIndex();
		int showAddresses = 0;
		if (Settings.getVisibility(Settings.DISPLAY_ADDRESSES)) {
			showAddresses = 1;
			if (minColumn > 0) {
				minColumn -= 1;
			}
			if (maxColumn > 0) {
				maxColumn -= 1;
			}
			if (anchorColumn > 0) {
				anchorColumn -= 1;
			}
		}
		if (anchorColumn > mSettings.getNbWordPerLine() - showAddresses) {
			anchorColumn -= mSettings.getNbWordPerLine();
		}
		if (minColumn > mSettings.getNbWordPerLine() - showAddresses) {
			minColumn -= mSettings.getNbWordPerLine();
		}
		if (maxColumn > mSettings.getNbWordPerLine() - showAddresses) {
			maxColumn -= mSettings.getNbWordPerLine();
		}
		minSelectionAddr = minRow * mSettings.getNbWordPerLine();
		maxSelectionAddr = maxRow * mSettings.getNbWordPerLine();

		if (minRow == maxRow) {
			if (minColumn == anchorColumn) {
				maxSelectionAddr += maxColumn;
				minSelectionAddr += minColumn;
			} else {
				maxSelectionAddr += minColumn;
				minSelectionAddr += maxColumn;
			}

		} else if (minColumn == anchorColumn) {
			if (minRow >= anchorRow) {
				maxSelectionAddr += maxColumn;
				minSelectionAddr += minColumn;
			} else {
				maxSelectionAddr += minColumn;
				minSelectionAddr += maxColumn;
			}
		} else {
			if (minRow >= anchorRow) {
				minSelectionAddr += maxColumn;
				maxSelectionAddr += minColumn;
			} else {
				minSelectionAddr += minColumn;
				maxSelectionAddr += maxColumn;
			}
		}
		if (minSelectionAddr > maxSelectionAddr) {
			long tmp = minSelectionAddr;
			minSelectionAddr = maxSelectionAddr;
			maxSelectionAddr = tmp;
		}
		mModel.updateSelection(minSelectionAddr, maxSelectionAddr);
		mInfoPanel.setAddr(minSelectionAddr + mSettings.getShift(), maxSelectionAddr + mSettings.getShift());

		mTable.repaint();
	}

	/**
	 * Go to address.
	 * <p>
	 * Move scrollBar to show specified address.
	 * </p>
	 * 
	 * @param address address to go to
	 */
	public void goTo(int address) {
		int scrollTick = mScroll.getVerticalScrollBar().getMaximum() / mTable.getRowCount();

		int goToRow = address / mSettings.getNbWordPerLine();
		mScroll.getVerticalScrollBar().setValue(goToRow * scrollTick);
	}

	/**
	 * Return first address on first visible row.
	 * 
	 * @return first address on first visible row
	 */
	public int getFirstVisibleAddress() {
		int rowHeight = mTable.getRowHeight();
		Point pos = mScroll.getViewport().getViewPosition();
		int startRow = (int) (pos.getY() / rowHeight);
		return startRow * mSettings.getNbWordPerLine();
	}

	@Override
	public void dispose() {
		mSettings.removePropertyChangeListener(mLocalSettingListener);
		Settings.removePropertyChangeListener(mSettingListener);
		Settings.removePropertyChangeListener(mModel);
		AppController.getInstance().removeFocusedEditor(this);
		try {
			mRaf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.dispose();
	}

	/**
	 * Get File size.
	 * 
	 * @return file size
	 */
	public long getFileSize() {
		return mSize;
	}

	public class DelayedChangeHandler implements ChangeListener {

		private Timer timer;
		private ChangeEvent last;

		public DelayedChangeHandler() {
			timer = new Timer(250, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					stableStateChanged();
				}
			});
			timer.setRepeats(false);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			last = e;
			timer.restart();
		}

		protected void stableStateChanged() {
			int rowHeight = mTable.getRowHeight();
			Point pos = mScroll.getViewport().getViewPosition();
			int startRow = (int) (pos.getY() / rowHeight);
			mModel.updateViewPort(startRow);
			updateTableConstraints();
		}
	}
}
