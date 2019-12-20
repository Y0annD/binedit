package fr.yoanndiquelou.binedit.frame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
			.getBundle("fr.yoanndiquelou.binedit.frame.resources.ViewerSettings");

	/**
	 * Frame for Viewer settings.
	 * 
	 * @param viewerSettings viewer settings model
	 */
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
		JLabel shiftLabel = new JLabel(mBundle.getString("SHIFT"));
		JLabel fixedColumnLabel = new JLabel(mBundle.getString("FIXED_COLUMNS"));
		c.anchor = GridBagConstraints.WEST;
		JSpinner tf = new JSpinner();
		tf.setValue(viewerSettings.getNbWordPerLine());
		((JSpinner.DefaultEditor) tf.getEditor()).getTextField().setColumns(4);
		add(label, c);
		c.gridx++;
		add(tf, c);

		JSpinner shiftSpinner = new JSpinner();
		shiftSpinner.setValue(viewerSettings.getShift());
		((JSpinner.DefaultEditor) shiftSpinner.getEditor()).getTextField().setColumns(4);
		c.gridy++;
		c.gridx = 0;
		add(shiftLabel, c);
		c.gridx++;
		add(shiftSpinner, c);
		c.gridy++;
		c.gridx = 0;

		add(fixedColumnLabel, c);
		c.gridx++;
		JCheckBox fixedColumnCheckBox = new JCheckBox();
		fixedColumnCheckBox.setName("FIXED_COLUMNS");
		fixedColumnCheckBox.setSelected(viewerSettings.getFixNumberOfColumn());
		add(fixedColumnCheckBox, c);
		JButton validate = new JButton(mBundle.getString("CONFIRM"));
		validate.addActionListener(l -> {
			viewerSettings.setNbWordPerline(Integer.valueOf(tf.getValue().toString()));
			viewerSettings.setShift(Integer.valueOf(shiftSpinner.getValue().toString()));
			viewerSettings.setFixNumberOfColumn(fixedColumnCheckBox.isSelected());
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
