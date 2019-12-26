package fr.yoanndiquelou.binedit.command;

public interface IUndoableCommand extends ICommand{
	
	/**
	 * Undo command.
	 */
	public void undo();
	
}
