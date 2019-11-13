package fr.yoanndiquelou.binedit;

import java.io.File;

import fr.yoanndiquelou.binedit.panel.MDIPanel;

/**
 * Application controller.
 * 
 * @author yoann
 *
 */
public class AppController {
	/** This instance. */
	private static AppController INSTANCE = new AppController();

	/** MDI zone. */
	private MDIPanel mMdiPanel = new MDIPanel();

	/**
	 * App controller constructor.
	 */
	private AppController() {
		super();
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
