package fr.yoanndiquelou.binedit.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

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
	/** file content. */
	private byte[] mContent;
	/** Byte per line. */
	private ViewerSettings mSettings;
	private BinEditTableModel mModel;
	/** Table contenant le fichier. */
	private JTable mTable;
	/** Panel d'information. */
	private InfoPanel mInfoPanel;
	/** Addresse de depart de la selection. */
	private long mMinSelectionAddr;
	/** Addresse de fin de la selection. */
	private long mMaxSelectionAddr;
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
		try {
			mContent = Files.readAllBytes(mFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			mContent = new byte[0];
		}

		mModel = new BinEditTableModel(mContent, mSettings);
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
		updateTableConstraints();
		JScrollPane scroll = new JScrollPane(mTable);
		BasicFileAttributes attr;
		long size;
		try {
			attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
			size = attr.size();
		} catch (IOException e) {
			size = 0;
		}
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
	}

	public void updateSelection() {
		long minRow = mMinSelectionAddr / mSettings.getNbWordPerLine();
		long maxRow = mMaxSelectionAddr / mSettings.getNbWordPerLine();
		long minColumn = mMinSelectionAddr - minRow * mSettings.getNbWordPerLine() + 1;
		long maxColumn = mMaxSelectionAddr - maxRow * mSettings.getNbWordPerLine() + 1;
		mTable.getSelectionModel().addSelectionInterval((int) minRow, (int) maxRow);
		mTable.getColumnModel().getSelectionModel().addSelectionInterval((int) minColumn, (int) maxColumn);

	}

	public void updateTableConstraints() {
		TableColumn column = null;
		int maxWidth = 0;
		int width;
		Font font = new Font("Courier", Font.PLAIN, 12);
		JLabel c = new JLabel();
		c.setFont(font);
		for (int i = 0; i < mTable.getModel().getColumnCount(); i++) {
			column = mTable.getColumnModel().getColumn(i);
			if (i == 0) {
				width = SwingUtilities.computeStringWidth(c.getFontMetrics(font), mModel.mMaxAddrStr) * 2;
				column.setPreferredWidth(width);
			} else if (i > mSettings.getNbWordPerLine()) {
				width = 17;
				column.setPreferredWidth(width);
			} else {
				width = 24;
				column.setPreferredWidth(width);
			}
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
//		preferencesItem.setEnabled(false);
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
			mMinSelectionAddr = mTable.getSelectionModel().getMinSelectionIndex() * mSettings.getNbWordPerLine();
			mMaxSelectionAddr = mTable.getSelectionModel().getMaxSelectionIndex() * mSettings.getNbWordPerLine();
			long minColumn = mTable.getColumnModel().getSelectionModel().getMinSelectionIndex() - 1;
			long maxColumn = mTable.getColumnModel().getSelectionModel().getMaxSelectionIndex() - 1;

			mMinSelectionAddr += minColumn;
			mMaxSelectionAddr += maxColumn;
			if (minColumn > mSettings.getNbWordPerLine()) {
				mMinSelectionAddr -= mSettings.getNbWordPerLine();
			}
			if (maxColumn > mSettings.getNbWordPerLine()) {
				mMaxSelectionAddr -= mSettings.getNbWordPerLine();
			}
			mTable.repaint();
//			long addr = mTable.getSelectedRow()
//					* ((BinEditTableModel) mTable.getModel()).getSettings().getNbWordPerLine();
//			addr += mTable.getSelectedColumn() - 1;
//			if (mTable.getSelectedColumn() > ((BinEditTableModel) mTable.getModel()).getSettings().getNbWordPerLine()) {
//				addr -= ((BinEditTableModel) mTable.getModel()).getSettings().getNbWordPerLine();
//			}
			mInfoPanel.setAddr(mMinSelectionAddr + mSettings.getShift(), mMaxSelectionAddr + mSettings.getShift());
		}
	}

}
