package fr.yoanndiquelou.binedit.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import fr.yoanndiquelou.binedit.Settings;
import fr.yoanndiquelou.binedit.frame.ViewerSettingsFrame;
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
	private BinEditTableModel mModel;
	/** Table contenant le fichier. */
	private JTable mTable;
	/** Panel d'information. */
	private InfoPanel mInfoPanel;
	/** Preferences. */
	private Preferences mPrefs;

	public BinaryViewer(File file) {
		addJMenuBar();
		mPrefs = Preferences.userRoot().node(this.getClass().getName());
		mFile = file;
		mSettings = new ViewerSettings(mPrefs);
		setName(String.format("BinaryViewer_%s/%s", mFile.getParentFile().getName(), mFile.getName()));
		setTitle(file.getName());
		setClosable(true);
		setResizable(true);
		setIconifiable(true);

		BasicFileAttributes attr;
		long size;
		try {
			attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
			size = attr.size();
		} catch (IOException e) {
			size = 0;
		}
		try {
			mRaf = new RandomAccessFile(mFile, "r");
//			mContent = Files.readAllBytes(mFile.toPath());

			mModel = new BinEditTableModel(mRaf, size, mSettings);
			TableCellRenderer renderer = new BinEditTableCellRenderer();
			mTable = new JTable(mModel);
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
			JScrollPane scroll = new JScrollPane(mTable);
			scroll.getViewport().addChangeListener(new DelayedChangeHandler(scroll));
			scroll.getViewport().addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					int rowHeight = mTable.getRowHeight();
					Point pos = scroll.getViewport().getViewPosition();
					int row = (int) (pos.getY() / rowHeight);
					mModel.updateViewPort(row);
				}
			});

			getContentPane().add(scroll, BorderLayout.CENTER);
			mInfoPanel = new InfoPanel(size);
			getContentPane().add(mInfoPanel, BorderLayout.SOUTH);

			setVisible(true);
			pack();

			mSettings.addPropertyChangeListener(l -> {
				mTable.getSelectionModel().removeListSelectionListener(BinaryViewer.this);
				mTable.getColumnModel().getSelectionModel().removeListSelectionListener(BinaryViewer.this);
				mModel.fireTableStructureChanged();
				updateTableConstraints();

				updateSelection();
				mInfoPanel.setShift(mSettings.getShift());
				mTable.repaint();
				mTable.getSelectionModel().addListSelectionListener(BinaryViewer.this);
				mTable.getColumnModel().getSelectionModel().addListSelectionListener(BinaryViewer.this);
			});
			mModel.addTableModelListener(l -> {
				updateTableConstraints();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateSelection() {
		long minRow = mModel.getMinSelectionAddr() / mSettings.getNbWordPerLine();
		long maxRow = mModel.getMaxSelectionAddr() / mSettings.getNbWordPerLine();
		long minColumn = mModel.getMinSelectionAddr() - minRow * mSettings.getNbWordPerLine() + 1;
		long maxColumn = mModel.getMaxSelectionAddr() - maxRow * mSettings.getNbWordPerLine() + 1;
		mTable.getSelectionModel().addSelectionInterval((int) minRow, (int) maxRow);
		mTable.getColumnModel().getSelectionModel().addSelectionInterval((int) minColumn, (int) maxColumn);
	}

	public void updateTableConstraints() {
		TableColumn column = null;
		int maxWidth = 0;
		int width;
		Font font = new Font("Courier", Font.PLAIN, 13);
		JLabel c = new JLabel();
		c.setFont(font);
		for (int i = 0; i < mTable.getColumnModel().getColumnCount(); i++) {
			column = mTable.getColumnModel().getColumn(i);
			if (i == 0) {
				width = SwingUtilities.computeStringWidth(c.getFontMetrics(font), mModel.mMaxAddrStr);
			} else if (i > mSettings.getNbWordPerLine()) {
				width = 17;
			} else {
				width = 24;
			}
			column.setMaxWidth(width);
			column.setMinWidth(width);
			column.setPreferredWidth(width);
			column.setWidth(width);
			maxWidth += width;
		}
		maxWidth += (mSettings.getNbWordPerLine() + 1) * mTable.getIntercellSpacing().width;
		setMaximumSize(new Dimension(maxWidth + 30, 900));
	}

	public ViewerSettings getSettings() {
		return mSettings;
	}

	private void addJMenuBar() {
		JMenuBar menu = new JMenuBar();
		JMenuItem preferencesItem = new JMenuItem(mBundle.getString("menu.display.preferences"));
		preferencesItem.addActionListener(l -> {
			ViewerSettingsFrame frame = new ViewerSettingsFrame(mSettings);
			frame.setVisible(true);
		});
		menu.add(preferencesItem);
		setJMenuBar(menu);
	}

	/**
	 * Repaint du tabulaire pour le changement de selection de la colone.
	 */
	public void valueChanged(ListSelectionEvent le) {
		if (null != mTable) {
			long minSelectionAddr;
			long maxSelectionAddr;
			long anchorRow = mTable.getSelectionModel().getAnchorSelectionIndex();
			long anchorColumn = mTable.getColumnModel().getSelectionModel().getAnchorSelectionIndex() - 1;
			if (anchorColumn > mSettings.getNbWordPerLine()) {
				anchorColumn -= mSettings.getNbWordPerLine();
			}
			long minRow;
			long maxRow;
			long minColumn;
			long maxColumn;

			minRow = mTable.getSelectionModel().getMinSelectionIndex();
			maxRow = mTable.getSelectionModel().getMaxSelectionIndex();
			minColumn = mTable.getColumnModel().getSelectionModel().getMinSelectionIndex() - 1;
			maxColumn = mTable.getColumnModel().getSelectionModel().getMaxSelectionIndex() - 1;
			if (minColumn > mSettings.getNbWordPerLine()) {
				minColumn -= mSettings.getNbWordPerLine();
			}
			if (maxColumn > mSettings.getNbWordPerLine()) {
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
	}

	@Override
	public void dispose() {
		Settings.removePropertyChangeListener(mModel);
		try {
			mRaf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.dispose();
	}

	public class DelayedChangeHandler implements ChangeListener {

		private Timer timer;
		private ChangeEvent last;
		private JScrollPane mScroll;

		public DelayedChangeHandler(JScrollPane scroll) {
			mScroll = scroll;
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
			int row = (int) (pos.getY() / rowHeight);
			mModel.updateViewPort(row);
		}

	}
}
