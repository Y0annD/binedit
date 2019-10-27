package fr.yoanndiquelou.binedit;

import java.io.File;

import javax.swing.JLabel;

import fr.yoanndiquelou.binedit.panel.MDIPanel;

public class AppController {

	private static AppController INSTANCE = new AppController();

	private MDIPanel mMdiPanel = new MDIPanel();

	private AppController() {

	}

	/**
	 * Get instance of app controller.
	 * 
	 * @return app controller instance
	 */
	public static AppController getInstance() {
		return INSTANCE;
	}

	public MDIPanel getMDIPanel() {
		return mMdiPanel;
	}

	public void openFile(File file) {
		mMdiPanel.open(file);
	}
	
}
