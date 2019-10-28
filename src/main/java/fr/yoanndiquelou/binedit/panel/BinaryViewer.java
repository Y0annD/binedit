package fr.yoanndiquelou.binedit.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ResourceBundle;

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
	/** Table contenant le fichier. */
	private JTable mTable;
	/** Panel d'information. */
	private InfoPanel mInfoPanel;

	public BinaryViewer(File file) {
		mFile = file;
		mSettings = new ViewerSettings();
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

		BinEditTableModel model = new BinEditTableModel(mContent, mSettings);
		TableCellRenderer renderer = new BinEditTableCellRenderer();
		mTable = new JTable(model);
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
		TableColumn column = null;
		int maxWidth = 0;
		int width;
		Font font = new Font("Courier", Font.PLAIN, 12);
		JLabel c = new JLabel();
		c.setFont(font);
		for (int i = 0; i < model.getColumnCount(); i++) {
			column = mTable.getColumnModel().getColumn(i);
			if (i == 0) {
				width = SwingUtilities.computeStringWidth(c.getFontMetrics(font), model.mMaxAddrStr) * 2;
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
		maxWidth += (mSettings.getNbWordPerLine() + 1) * mTable.getIntercellSpacing().width;
		setMaximumSize(new Dimension(maxWidth + 30, 900));

//		addComponentListener(new ComponentAdapter() {
//
//			@Override
//			public void componentResized(ComponentEvent e) {
//				super.componentResized(e);
//				if (null != mTable) {
//					mTable.repaint();
//				}
//			}
//		});
	}

	public JMenuBar getJMenuBar() {
		JMenuBar menu = new JMenuBar();
		JMenuItem preferencesItem = new JMenuItem(mBundle.getString("menu.display.preferences"));
//		preferencesItem.setEnabled(false);
		preferencesItem.addActionListener(l -> {
			new SettingsFrame().setVisible(true);
		});
		menu.add(preferencesItem);
		return menu;
	}

	/**
	 * Repaint du tabulaire pour le changement de selection de la colone.
	 */
	public void valueChanged(ListSelectionEvent le) {
		if (null != mTable) {
			mTable.repaint();
			long addr = mTable.getSelectedRow()
					* ((BinEditTableModel) mTable.getModel()).getSettings().getNbWordPerLine();
			addr += mTable.getSelectedColumn();
			if (mTable.getSelectedColumn() > ((BinEditTableModel) mTable.getModel()).getSettings().getNbWordPerLine()) {
				addr -= ((BinEditTableModel) mTable.getModel()).getSettings().getNbWordPerLine();
			}
			mInfoPanel.setAddr(addr);
		}
	}

}
