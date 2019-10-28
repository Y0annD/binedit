package fr.yoanndiquelou.binedit.panel;

import java.awt.BorderLayout;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * Panel d'information sur le fichier ouvert.
 * 
 * @author yoann
 *
 */
public class InfoPanel extends JPanel {

	private ResourceBundle mBundle = ResourceBundle
			.getBundle("fr.yoanndiquelou.binedit.panel.resources.InformationBundle");
	/**
	 * 
	 */
	private static final long serialVersionUID = 4023387544245476943L;

	/** Address label. */
	private JLabel mAddrLabel;

	public InfoPanel(long size) {
		setLayout(new BorderLayout());
		mAddrLabel = new JLabel(String.valueOf(0));
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		JPanel sizePanel = new JPanel();
		sizePanel.add(new JLabel(mBundle.getString("FILE_SIZE")));
		sizePanel.add(new JLabel(String.valueOf(size)));
		JPanel addrPanel = new JPanel();
		addrPanel.add(new JLabel(mBundle.getString("ADDRESS")));
		addrPanel.add(mAddrLabel);
		add(sizePanel, BorderLayout.WEST);
		add(addrPanel, BorderLayout.EAST);
	}

	/**
	 * Set new selected addr.
	 * 
	 * @param newAddr new address
	 */
	public void setAddr(long newAddr) {
		mAddrLabel.setText(String.format("%02x", newAddr).toUpperCase(Locale.getDefault()));
	}

}
