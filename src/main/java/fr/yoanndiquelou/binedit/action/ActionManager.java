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
	/** Copy content to clipBoard. */
	private Action mCopyAction;

	public ActionManager() {
		mUndoAction = new UndoAction();
		mRedoAction = new RedoAction();
		mUndoAction.setEnabled(false);
		mRedoAction.setEnabled(false);
		mOpenAction = new OpenAction();
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
		mCopyAction = new CopyBinaryAction();
		mCopyAction.setEnabled(false);
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
	 * Action to copy content.
	 * 
	 * @return copy action
	 */
	public Action getCopyBinaryAction() {
		return mCopyAction;
	}

	public static ActionManager getInstance() {
		return INSTANCE;
	}
}
