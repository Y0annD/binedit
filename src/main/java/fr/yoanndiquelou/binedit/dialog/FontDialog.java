package fr.yoanndiquelou.binedit.dialog;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.yoanndiquelou.binedit.Settings;

/**
 * Allow user to choose font in table.
 * 
 * @author yoann
 *
 */
public class FontDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6231593685824914577L;

	private static ResourceBundle mBundle = ResourceBundle
			.getBundle("fr.yoanndiquelou.binedit.dialog.resources.FontDialogBundle");

	private JList<String> mFontList;

	private JList<String> mStyleList;

	private JLabel mDemoLabel;

	public FontDialog() {
		super();
		setTitle(mBundle.getString("title"));
		setLocationRelativeTo(null);
		getContentPane().setLayout(new GridBagLayout());
		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		List<Font> monoFonts1 = new ArrayList<>();

		FontRenderContext frc = new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT,
				RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT);
		String allowedChars = "0123456789ABCDEF";
		for (Font font : fonts) {
			double width = font.getStringBounds("0", frc).getWidth();
			boolean equals = true;
			for (int i = 1; i < allowedChars.length() && equals; i++) {
				double w = font.getStringBounds(allowedChars.substring(i, i + 1), frc).getWidth();
				if (w != width) {
					equals = false;
				}
			}
			if (equals) {
				monoFonts1.add(font);
			}
		}
		final Map<String, List<Font>> availableFonts = monoFonts1.stream().collect(Collectors.groupingBy(Font::getFamily));
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.BOTH;
		constraint.anchor = GridBagConstraints.WEST;
		constraint.gridx = 0;
		constraint.gridy = 0;
		constraint.weightx = 1;
		constraint.weighty = 0;
		getContentPane().add(new JLabel(mBundle.getString("fontname")), constraint);
		constraint.gridx++;
		getContentPane().add(new JLabel(mBundle.getString("fontstyle")), constraint);
		constraint.gridx++;
		getContentPane().add(new JLabel(mBundle.getString("fontsize")), constraint);
		constraint.gridy++;
		constraint.gridx = 0;
		constraint.weighty = 1;

		mFontList = new JList<>(availableFonts.keySet().toArray(new String[0]));
		mFontList.setName("FontList");
		DefaultListModel<String> styleModel = new DefaultListModel<String>();
		mFontList.addListSelectionListener(e -> {
			styleModel.clear();
			for (Font f : availableFonts.get((String) mFontList.getSelectedValue())) {
				styleModel.addElement(f.getName());
			}
			mStyleList.addSelectionInterval(0, 0);
		});
		getContentPane().add(new JScrollPane(mFontList), constraint);
		constraint.gridx++;
		mStyleList = new JList<>(styleModel);
		mStyleList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				String fontName = (String) mFontList.getSelectedValue();
				if (mStyleList.getSelectedIndex() != -1) {
					Font font = availableFonts.get(fontName).get(mStyleList.getSelectedIndex());
					font = font.deriveFont(12f);

					mDemoLabel.setFont(font);
					mDemoLabel.invalidate();
				}

			}
		});
		mStyleList.setName("FontStyle");
		getContentPane().add(new JScrollPane(mStyleList), constraint);
		constraint.gridx++;
		constraint.weighty=0;
		JSpinner sizeSpinner = new JSpinner();
		JSpinner.NumberEditor spinnerEditor = new JSpinner.NumberEditor(sizeSpinner);
		sizeSpinner.setEditor(spinnerEditor);

		spinnerEditor.getModel().setMinimum(0);
		spinnerEditor.getModel().setMaximum(100);
		spinnerEditor.getModel().setStepSize(1);
		spinnerEditor.getModel().setValue(Settings.getFontSize());

		spinnerEditor.getFormat().applyPattern("###,##0");
		getContentPane().add(sizeSpinner, constraint);

		constraint.gridx = 0;
		constraint.gridy++;
		mDemoLabel = new JLabel("0123456789ABCDEF");
		getContentPane().add(mDemoLabel, constraint);
		constraint.gridy++;

		constraint.gridx = 1;
		JButton okButton = new JButton(mBundle.getString("ok"));
		okButton.setName("ok");
		okButton.addActionListener(a -> {
			Settings.setFont(availableFonts.get(mFontList.getSelectedValue()).get(mStyleList.getSelectedIndex()));
			Settings.setFontSize((int) sizeSpinner.getModel().getValue());
			dispose();
		});
		JButton cancelButton = new JButton(mBundle.getString("cancel"));
		cancelButton.setName("cancel");
		cancelButton.addActionListener(a -> dispose());
		getContentPane().add(okButton, constraint);
		constraint.gridx++;
		getContentPane().add(cancelButton, constraint);
		pack();
	}

}
