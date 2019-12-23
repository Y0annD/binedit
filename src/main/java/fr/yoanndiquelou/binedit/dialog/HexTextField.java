package fr.yoanndiquelou.binedit.dialog;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.bind.DatatypeConverter;

/**
 * Hex Text Field.
 * 
 * @author yoann
 *
 */
public class HexTextField extends JTextField implements DocumentListener {
	/** Serial ID. */
	private static final long serialVersionUID = -6694532039668603522L;
	/** Filtering or not. */
	private boolean filtering;
	/** Add space or not. */
	private boolean addSpace;

	/**
	 * HexTextField.
	 */
	public HexTextField() {
		this(true);
	}

	/**
	 * Hex text field.
	 * 
	 * @param space space between bytes
	 */
	public HexTextField(boolean space) {
		super();
		filtering = false;
		addSpace = space;
		getDocument().addDocumentListener(this);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		filterText();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		filterText();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		filterText();
	}

	/**
	 * Filter typed text.
	 */
	private void filterText() {
		if (filtering)
			return;
		filtering = true;

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				String input = getText();
				boolean isHexa = input.toUpperCase().startsWith("0X");
				StringBuilder filtered = new StringBuilder();
				int index = 0;

				// filter
				for (int i = 0; i < input.length(); i++) {
					char c = input.charAt(i);
					if ("Xx".indexOf(c) >= 0 && i == 1 && '0' == input.charAt(0)) {
						filtered.append(c);
					} else if (isHexa && "0123456789ABCDEFabcdef".indexOf(c) >= 0
							|| !isHexa && "0123456789".indexOf(c) >= 0) // hex only
					{
						filtered.append(c);
						if (index++ % 2 == 1 && i != input.length() - 1 && addSpace) {
							filtered.append(" "); // whitespace after each byte
						}
					}
				}

				// limit size
				int maxBytes = 256;
				String filteredText = filtered.toString();
				if (filteredText.length() > 3 * maxBytes) {
					filteredText = filteredText.substring(0, 3 * maxBytes);
					Toolkit.getDefaultToolkit().beep();
				}

				setText(filteredText);
				filtering = false;
			}
		});
	}
}