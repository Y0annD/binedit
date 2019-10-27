package fr.yoanndiquelou.binedit;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import fr.yoanndiquelou.binedit.menu.FrameMenu;
import fr.yoanndiquelou.binedit.panel.ExplorerPanel;
import fr.yoanndiquelou.binedit.panel.MDIPanel;

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
		pane.setDividerLocation(150);
		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(100, 50);
		explorerPanel.setMinimumSize(minimumSize);
		controller.getMDIPanel().setMinimumSize(minimumSize);
		add(pane);
//		pack();
		setVisible(true);
	}

}
