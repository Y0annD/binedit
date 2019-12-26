package fr.yoanndiquelou.binedit.command.impl;

import fr.yoanndiquelou.binedit.Settings;
import fr.yoanndiquelou.binedit.command.IUndoableCommand;

/**
 * Change visibility.
 * 
 * @author yoann
 *
 */
public class VisibilityCommand implements IUndoableCommand {
	/** Property to change. */
	private String mProperty;
	/** New value to set. */
	private boolean mNewValue;
	/** Old value. */
	private boolean mOldValue;

	/**
	 * Command to change visibility.
	 * 
	 * @param property property to change
	 * @param newValue value to set
	 */
	public VisibilityCommand(String property, boolean newValue) {
		mProperty = property;
		mNewValue = newValue;
		mOldValue = Settings.getVisibility(property);
	}

	@Override
	public void execute() {
		Settings.setVisibility(mProperty, mNewValue);
	}

	@Override
	public void undo() {
		Settings.setVisibility(mProperty, mOldValue);
	}

}
