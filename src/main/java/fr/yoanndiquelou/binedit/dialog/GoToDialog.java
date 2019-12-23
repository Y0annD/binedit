package fr.yoanndiquelou.binedit.dialog;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import fr.yoanndiquelou.binedit.panel.BinaryViewer;

public class GoToDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7741240142437612950L;
	
	private ResourceBundle mBundle = ResourceBundle
			.getBundle("fr.yoanndiquelou.binedit.dialog.resources.GoToDialogBundle");

	public GoToDialog(BinaryViewer viewer) {
		setName("GoTo");
		setTitle(mBundle.getString("TITLE"));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		add(new JLabel(mBundle.getString("GOTO")), c);
		c.gridx++;
		c.weightx = 2;
		HexTextField tf = new HexTextField(false);
		tf.setName("ADDRESS");
		tf.setColumns(8);
		add(tf, c);
		c.gridy++;
		c.weightx = 1;
		c.gridwidth = 2;
		c.gridx = 0;
		JButton goToButton = new JButton(mBundle.getString("GOTO_BUTTON"));
		goToButton.setName("GOTO_BUTTON");
		goToButton.addActionListener((a) -> {
			String value = tf.getText();
			int address;
			if(value.toUpperCase().startsWith("0X")) {
				address = Integer.parseInt(value.substring(2), 16);
			}else {
				address=Integer.valueOf(tf.getText());
			}
			viewer.goTo(address);
			dispose();
		});
		add(goToButton, c);
		pack();
	}

}
