package fr.yoanndiquelou.binedit.command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.yoanndiquelou.binedit.dialog.GoToDialog;

public class GotoCommand implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		new GoToDialog().setVisible(true);
	}

}
