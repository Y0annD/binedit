package fr.yoanndiquelou.binedit.command.impl;

import fr.yoanndiquelou.binedit.command.IUndoableCommand;
import fr.yoanndiquelou.binedit.panel.BinaryViewer;

public class GoToCommand implements IUndoableCommand {
	/** Editor. */
	private BinaryViewer mViewer;
	/** Address before goto. */
	private int mOldAddress;
	/** Address after goto. */
	private int mNewAddress;

	/**
	 * GoTo command.
	 * 
	 * @param viewer  viewer
	 * @param address new address
	 */
	public GoToCommand(BinaryViewer viewer, int address) {
		mViewer = viewer;
		mNewAddress = address;
		mOldAddress = mViewer.getFirstVisibleAddress();
	}

	@Override
	public void execute() {
		if (null != mViewer) {
			mViewer.goTo(mNewAddress);
		}
	}

	@Override
	public void undo() {
		if (null != mViewer) {
			mViewer.goTo(mOldAddress);
		}
	}

}
