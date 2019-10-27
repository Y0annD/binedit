package fr.yoanndiquelou.binedit.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;

public class SettingsFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4659234961010726451L;
	/** Number of word per line. */
	private JSpinner mNumberOfWordTextField;

	public SettingsFrame() {
		setTitle("Préférences");
		setName("Preferences");
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;

		JLabel label = new JLabel("Nombre de mots par ligne:");
		JSpinner tf = new JSpinner();
		tf.setValue(1);
		add(label, c);
		c.gridx++;
		add(tf, c);
		JButton validate = new JButton("Validate");
		validate.addActionListener(l -> {
			dispose();
		});
		c.gridx = 0;
		c.gridy++;
		add(validate, c);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(l -> {
			dispose();
		});
		c.gridx++;
		add(cancel, c);
		setResizable(false);
		pack();
	}
}
