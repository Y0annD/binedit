package fr.yoanndiquelou.binedit.action;

import java.util.ResourceBundle;

import javax.swing.Action;

import fr.yoanndiquelou.binedit.Settings;

public class ActionManager {

	private static ActionManager INSTANCE = new ActionManager();

	private ResourceBundle mBundle = ResourceBundle.getBundle("fr.yoanndiquelou.binedit.action.resources.ActionBundle");
	/** Undo action. */
	private Action mUndoAction;
	/** Redo action. */
	private Action mRedoAction;
	/** Open file action. */
	private Action mOpenAction;
	/** Save file action. */
	private Action mSaveAction;
	/** Save as file action. */
	private Action mSaveAsAction;
	/** Exit action. */
	private Action mExitAction;
	/** Display toolbar action. */
	private Action mDisplayToolbarAction;
	/** Display displayBar action. */
	private Action mDisplayDisplayBarAction;
	/** Display statusBar action. */
	private Action mDisplayStatusBarAction;
	/** Display address action. */
	private Action mDisplayAddressAction;
	/** Address in hexa action. */
	private Action mDisplayAddressInHexaAction;
	/** Info in hexa action. */
	private Action mDisplayInfoInHexaAction;
	/** Goto action. */
	private Action mGotoAction;
	/** Number of info by line action. */
	private Action mInfoByLineAction;
	/** choose font action. */
	private Action mFontAction;
	/** Copy binary content to clipBoard. */
	private Action mCopyBinaryAction;
	/** Copy text content to clipBoard. */
	private Action mCopyTextAction;
	/** Paste action. */
	private Action mPasteAction;
	/** Cut action. */
	private Action mCutAction;
	/** Clean action. */
	private Action mCleanAction;

	public ActionManager() {
		mUndoAction = new UndoAction();
		mRedoAction = new RedoAction();
		mUndoAction.setEnabled(false);
		mRedoAction.setEnabled(false);
		mOpenAction = new OpenAction();
		mSaveAction = new SaveAction();
		mSaveAction.setEnabled(false);
		mSaveAsAction = new SaveAsAction();
		mSaveAsAction.setEnabled(false);
		mExitAction = new ExitAction();
		mDisplayToolbarAction = new DisplayToolbarAction();
		mDisplayDisplayBarAction = new VisibilityAction(Settings.DISPLAY_DISPLAYBAR,
				mBundle.getString("display.displaybar.short"), mBundle.getString("display.displaybar.long"));
		mDisplayStatusBarAction = new VisibilityAction(Settings.DISPLAY_STATUSBAR,
				mBundle.getString("display.statusbar.short"), mBundle.getString("display.statusbar.long"));
		mDisplayAddressAction = new VisibilityAction(Settings.DISPLAY_ADDRESSES,
				mBundle.getString("display.addresses.short"), mBundle.getString("display.addresses.long"));
		mDisplayAddressInHexaAction = new VisibilityAction(Settings.ADDRESSES_HEXA,
				mBundle.getString("display.addressesInHexa.short"), mBundle.getString("display.addressesInHexa.long"));
		mDisplayInfoInHexaAction = new VisibilityAction(Settings.INFO_HEXA,
				mBundle.getString("display.infoInHexa.short"), mBundle.getString("display.infoInHexa.long"));
		mGotoAction = new GotoAction();
		mGotoAction.setEnabled(false);
		mInfoByLineAction = new InfoByLineAction();
		mFontAction = new FontAction();
		mCopyBinaryAction = new CopyBinaryAction();
		mCopyBinaryAction.setEnabled(false);
		mCopyTextAction = new CopyTextAction();
		mCopyTextAction.setEnabled(false);
		mPasteAction = new PasteAction();
		mPasteAction.setEnabled(false);
		mCutAction = new CutAction();
		mCutAction.setEnabled(false);
		mCleanAction = new CleanAction();
		mCleanAction.setEnabled(false);
	}

	/**
	 * Get undo action.
	 * 
	 * @return undo action
	 */
	public Action getUndoAction() {
		return mUndoAction;
	}

	/**
	 * Get Redo action.
	 * 
	 * @return redo action
	 */
	public Action getRedoAction() {
		return mRedoAction;
	}

	/**
	 * Get open file action.
	 * 
	 * @return open file action
	 */
	public Action getOpenAction() {
		return mOpenAction;
	}

	/**
	 * Get save file action.
	 * 
	 * @return save file action
	 */
	public Action getSaveAction() {
		return mSaveAction;
	}

	/**
	 * Get saveAs file action.
	 * 
	 * @return save as file action
	 */
	public Action getSaveAsAction() {
		return mSaveAsAction;
	}

	/**
	 * Get exit action.
	 * 
	 * @return exit action
	 */
	public Action getExitAction() {
		return mExitAction;
	}

	/**
	 * Get display toolbar action.
	 * 
	 * @return display toolbar action
	 */
	public Action getDisplayToolbarAction() {
		return mDisplayToolbarAction;
	}

	/**
	 * Get Display display bar action.
	 * 
	 * @return display display bar action
	 */
	public Action getDisplayDisplayBarAction() {
		return mDisplayDisplayBarAction;
	}

	/**
	 * Get Display status bar action.
	 * 
	 * @return display status bar action
	 */
	public Action getDisplayStatusBarAction() {
		return mDisplayStatusBarAction;
	}

	/**
	 * Return display addresses action.
	 * 
	 * @return display addresses action
	 */
	public Action getDisplayAddressesAction() {
		return mDisplayAddressAction;
	}

	/**
	 * Return display addresses in hexa action.
	 * 
	 * @return display addresses in hexa action
	 */
	public Action getAddressesInHexaAction() {
		return mDisplayAddressInHexaAction;
	}

	/**
	 * Return display info in hexa action.
	 * 
	 * @return display info in hexa action
	 */
	public Action getInfoInHexaAction() {
		return mDisplayInfoInHexaAction;
	}

	/**
	 * Return goto action.
	 * 
	 * @return goto action
	 */
	public Action getGoToAction() {
		return mGotoAction;
	}

	/**
	 * Return number of info by line action.
	 * 
	 * @return number of info by line action
	 */
	public Action getInfoByLineAction() {
		return mInfoByLineAction;
	}

	/**
	 * Get action to change font.
	 * 
	 * @return font action
	 */
	public Action getFontAction() {
		return mFontAction;
	}

	/**
	 * Return the cut action.
	 * 
	 * @return cut action
	 */
	public Action getCutAction() {
		return mCutAction;
	}

	/**
	 * Action to copy binary content.
	 * 
	 * @return copy binary action
	 */
	public Action getCopyBinaryAction() {
		return mCopyBinaryAction;
	}

	/**
	 * Action to copy text content.
	 * 
	 * @return copy text action
	 */
	public Action getCopyTextAction() {
		return mCopyTextAction;
	}

	/**
	 * Action to paste clipboard content to file.
	 * 
	 * @return paste content action
	 */
	public Action getPasteAction() {
		return mPasteAction;
	}

	/**
	 * Action to clean selection.
	 * 
	 * @return clean action
	 */
	public Action getCleanAction() {
		return mCleanAction;
	}

	public static ActionManager getInstance() {
		return INSTANCE;
	}
}
