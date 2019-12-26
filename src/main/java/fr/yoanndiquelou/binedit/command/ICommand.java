package fr.yoanndiquelou.binedit.command;

@FunctionalInterface
public interface ICommand {

	/**
	 * Execute command.
	 */
	public void execute();
}
