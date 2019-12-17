package fr.yoanndiquelou.binedit.dialog;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import fr.yoanndiquelou.binedit.panel.BinaryViewer;

public class GoToDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7741240142437612950L;

	public GoToDialog(BinaryViewer viewer) {
		super();
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;
		add(new JLabel("Go to:"),c);
		c.gridx++;
		c.weightx=1;
		add(new JTextField(5),c);
		c.gridy++;
		c.gridx=0;
		add(new JButton("Aller A"));
		pack();
	}

}
