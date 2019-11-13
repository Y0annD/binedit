package fr.yoanndiquelou.binedit;

import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import fr.yoanndiquelou.binedit.menu.FrameMenu;
import fr.yoanndiquelou.binedit.panel.ExplorerPanel;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 892164120910579373L;
	/** Preferences. */
	private Preferences mPrefs;

	public MainFrame() {
		mPrefs = Preferences.userRoot().node(getClass().getName());
		setTitle("BinEdit");
		setExtendedState(MAXIMIZED_BOTH);
		AppController controller = AppController.getInstance();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setJMenuBar(new FrameMenu());
		JPanel explorerPanel = new ExplorerPanel();

		JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, explorerPanel, controller.getMDIPanel());
		pane.setOneTouchExpandable(true);
		pane.setContinuousLayout(true);

		HierarchyListener hierarchyListener = new HierarchyListener() {

			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				long flag = e.getChangeFlags();
				if ((flag & HierarchyEvent.SHOWING_CHANGED) == HierarchyEvent.SHOWING_CHANGED) {
					mPrefs.putDouble("splitPane", pane.getResizeWeight());
				}

			}
		};
		// Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(150, 50);
		explorerPanel.setMinimumSize(minimumSize);
		controller.getMDIPanel().setMinimumSize(minimumSize);
		controller.getMDIPanel().setDropTarget(new DropTarget() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6418118605479053389L;

			@SuppressWarnings("unchecked")
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					List<File> droppedFiles = (List) evt.getTransferable()
							.getTransferData(DataFlavor.javaFileListFlavor);
					for (File file : droppedFiles) {
						if (file.isFile()) {
							controller.getInstance().openFile(file);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		add(pane);
		setVisible(true);
		pack();

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				pane.setDividerLocation(mPrefs.getInt("splitPane", 1));
				pane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent pce) {
						if (!pce.getOldValue().equals(pce.getNewValue())) {
							mPrefs.putInt("splitPane", pane.getDividerLocation());
						}
					}
				});
			}
		});
	}

}
