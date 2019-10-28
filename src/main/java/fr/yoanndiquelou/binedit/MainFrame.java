package fr.yoanndiquelou.binedit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import fr.yoanndiquelou.binedit.laf.ui.BEEditorPaneUI;
import fr.yoanndiquelou.binedit.menu.FrameMenu;
import fr.yoanndiquelou.binedit.panel.ExplorerPanel;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 892164120910579373L;

	public MainFrame() {
		setTitle("BinEdit");
		setExtendedState(MAXIMIZED_BOTH);
		AppController controller = AppController.getInstance();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setJMenuBar(new FrameMenu());
		JPanel explorerPanel = new ExplorerPanel();

		JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, explorerPanel, controller.getMDIPanel());
		pane.setOneTouchExpandable(true);
		pane.setDividerLocation(200);
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
		pack();
		setVisible(true);
	}

	
}
