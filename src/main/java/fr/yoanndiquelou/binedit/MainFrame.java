package fr.yoanndiquelou.binedit;

import java.awt.BorderLayout;
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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import fr.yoanndiquelou.binedit.menu.FrameMenu;
import fr.yoanndiquelou.binedit.model.DisplayMode;
import fr.yoanndiquelou.binedit.panel.ExplorerPanel;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 892164120910579373L;
	/** Preferences. */
	private Preferences mPrefs;
	/** App controller. */
	private AppController mController;

	public MainFrame() {
		mPrefs = Preferences.userRoot().node(getClass().getName());
		setTitle("BinEdit");
		setExtendedState(MAXIMIZED_BOTH);
		mController = AppController.getInstance();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setJMenuBar(new FrameMenu());

		JPanel explorerPanel = new ExplorerPanel();

		JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, explorerPanel, mController.getMDIPanel());
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
		mController.getMDIPanel().setMinimumSize(minimumSize);
		mController.getMDIPanel().setDropTarget(new DropTarget() {
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
							mController.getInstance().openFile(file);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		add(buildToolbar(), BorderLayout.NORTH);
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

	private JToolBar buildToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setName("Toolbar");
		JButton openButton = new JButton(UIManager.getIcon("FileView.directoryIcon"));
		openButton.addActionListener(a -> {
			JFileChooser fc = new JFileChooser();
			int result = fc.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				mController.getInstance().openFile(fc.getSelectedFile());
			}
		});
		toolbar.add(openButton);
		toolbar.add(new JToolBar.Separator());
		ButtonGroup buttonGroup = new ButtonGroup();
		JToggleButton displayModes[] = new JToggleButton[DisplayMode.values().length];
		int index = 0;
		DisplayMode mode = Settings.getDisplayMode();
		for (DisplayMode m : DisplayMode.values()) {
			JToggleButton btn = new JToggleButton(m.getText());
			buttonGroup.add(btn);
			displayModes[index++] = btn;
			btn.setName("ToggleBtn_" + m.toString());
			btn.setSelected(m.equals(mode));
			btn.addItemListener(l -> {
				if (btn.isSelected()) {
					Settings.setDisplayMode(m);

				}
			});

			toolbar.add(btn);
		}
		return toolbar;
	}

}
