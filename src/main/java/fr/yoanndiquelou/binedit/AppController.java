package fr.yoanndiquelou.binedit;

import java.io.File;

import fr.yoanndiquelou.binedit.panel.BinaryViewer;
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
	/** Focused editor. */
	private BinaryViewer mFocusedEditor;
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

	/**
	 * Get MDI panel.
	 * 
	 * @return MDI panel
	 */
	public MDIPanel getMDIPanel() {
		return mMdiPanel;
	}

	/**
	 * Open a new file.
	 * 
	 * @param file file to open
	 */
	public void openFile(File file) {
		mMdiPanel.open(file);
	}

	/**
	 * Defined focused editor.
	 * 
	 * @param focusedEditor focused editor
	 */
	public void setFocusedEditor(BinaryViewer focusedEditor) {
		mFocusedEditor = focusedEditor;
	}

	/**
	 * Remove focused editor if same as saved.
	 * 
	 * @param focusedEditor focusedEditor to remove
	 */
	public void removeFocusedEditor(BinaryViewer focusedEditor) {
		if (mFocusedEditor.equals(focusedEditor)) {
			mFocusedEditor = null;
		}
	}

	/**
	 * Get focused editor
	 * 
	 * @return focused editor
	 */
	public BinaryViewer getFocusedEditor() {
		return mFocusedEditor;
	}

}
