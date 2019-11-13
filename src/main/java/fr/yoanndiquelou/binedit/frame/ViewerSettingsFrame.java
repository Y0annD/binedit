package fr.yoanndiquelou.binedit.frame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;

import fr.yoanndiquelou.binedit.model.ViewerSettings;

public class ViewerSettingsFrame extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4659234961010726451L;
	/** resources bundle. */
	private ResourceBundle mBundle = ResourceBundle
			.getBundle("fr.yoanndiquelou.binedit.frame.resources.ViewerSettings");;

	public ViewerSettingsFrame(ViewerSettings viewerSettings) {
		setLocationRelativeTo(null);
		setModal(true);
		setTitle("Préférences");
		setName("Preferences");
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;

		JLabel label = new JLabel(mBundle.getString("WORDS_PER_LINE"));
		JSpinner tf = new JSpinner();
		tf.setValue(viewerSettings.getNbWordPerLine());
		((JSpinner.DefaultEditor)tf.getEditor()).getTextField().setColumns(4);
		add(label, c);
		c.gridx++;
		add(tf, c);
		JLabel shiftLabel = new JLabel(mBundle.getString("SHIFT"));
		JSpinner shiftSpinner = new JSpinner();
		shiftSpinner.setValue(viewerSettings.getShift());
		((JSpinner.DefaultEditor) shiftSpinner.getEditor()).getTextField().setColumns(4);
		c.gridy++;
		c.gridx = 0;
		add(shiftLabel, c);
		c.gridx++;
		add(shiftSpinner, c);
		JButton validate = new JButton(mBundle.getString("CONFIRM"));
		validate.addActionListener(l -> {
			viewerSettings.setNbWordPerline(Integer.valueOf(tf.getValue().toString()));
			viewerSettings.setShift(Integer.valueOf(shiftSpinner.getValue().toString()));
			dispose();
		});
		c.gridx = 0;
		c.gridy++;
		add(validate, c);
		JButton cancel = new JButton(mBundle.getString("CANCEL"));
		cancel.addActionListener(l -> {
			dispose();
		});
		c.gridx++;
		add(cancel, c);
		setResizable(false);
		pack();
	}
}
