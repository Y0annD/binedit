package fr.yoanndiquelou.binedit;

import java.io.File;
import java.util.Stack;

import fr.yoanndiquelou.binedit.action.ActionManager;
import fr.yoanndiquelou.binedit.command.ICommand;
import fr.yoanndiquelou.binedit.command.IUndoableCommand;
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
	/** Undo stack. */
	private Stack<IUndoableCommand> mUndoStack;
	/** Redo stack. */
	private Stack<IUndoableCommand> mRedoStack;

	/**
	 * App controller constructor.
	 */
	private AppController() {
		super();
		mUndoStack = new Stack<>();
		mRedoStack = new Stack<>();
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
	public BinaryViewer openFile(File file) {
		return mMdiPanel.open(file);
	}

	/**
	 * Defined focused editor.
	 * 
	 * @param focusedEditor focused editor
	 */
	public void setFocusedEditor(BinaryViewer focusedEditor) {
		mFocusedEditor = focusedEditor;
		ActionManager.getInstance().getCopyBinaryAction().setEnabled(true);
	}

	/**
	 * Remove focused editor if same as saved.
	 * 
	 * @param focusedEditor focusedEditor to remove
	 */
	public void removeFocusedEditor(BinaryViewer focusedEditor) {
		if (mFocusedEditor.equals(focusedEditor)) {
			mFocusedEditor = null;
			ActionManager.getInstance().getCopyBinaryAction().setEnabled(false);
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

	/**
	 * Add an action to action stack.
	 * 
	 * @param command action to add
	 */
	public void executeCommand(ICommand command) {
		command.execute();
	}

	/**
	 * Execute an undoable command.
	 * 
	 * @param command undoable command
	 */
	public void executeCommand(IUndoableCommand command) {
		command.execute();
		if (mUndoStack.isEmpty()) {
			ActionManager.getInstance().getUndoAction().setEnabled(true);
		}
		mUndoStack.add(command);
		if (!mRedoStack.isEmpty()) {
			ActionManager.getInstance().getRedoAction().setEnabled(false);
		}
		mRedoStack.clear();
	}

	/**
	 * Undo action.
	 */
	public void undo() {
		IUndoableCommand cmd = mUndoStack.pop();
		if (mRedoStack.isEmpty()) {
			ActionManager.getInstance().getUndoAction().setEnabled(false);
		}
		cmd.undo();
		mRedoStack.add(cmd);
		ActionManager.getInstance().getRedoAction().setEnabled(true);
	}

	/**
	 * Undo action.
	 */
	public void redo() {
		IUndoableCommand cmd = mRedoStack.pop();
		cmd.execute();
		if (mRedoStack.isEmpty()) {
			ActionManager.getInstance().getRedoAction().setEnabled(false);
		}
		ActionManager.getInstance().getUndoAction().setEnabled(true);
		mUndoStack.add(cmd);
	}

	/**
	 * Get number of action in stack.
	 * 
	 * @return number of action in stack
	 */
	public int getUndoStackSize() {
		return mUndoStack.size();
	}

	/**
	 * Get number of commands in redo in stack.
	 * 
	 * @return number of action in stack
	 */
	public int getRedoStackSize() {
		return mRedoStack.size();
	}

	/**
	 * Reset stacks.
	 */
	public void resetStacks() {
		mRedoStack.clear();
		ActionManager.getInstance().getRedoAction().setEnabled(false);
		mUndoStack.clear();
		ActionManager.getInstance().getUndoAction().setEnabled(false);
	}
}
