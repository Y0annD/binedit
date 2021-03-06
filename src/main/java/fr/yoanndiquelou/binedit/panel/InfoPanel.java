package fr.yoanndiquelou.binedit.panel;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import fr.yoanndiquelou.binedit.Settings;
import fr.yoanndiquelou.binedit.utils.AddressUtils;

/**
 * Panel d'information sur le fichier ouvert.
 * 
 * @author yoann
 *
 */
public class InfoPanel extends JPanel {
	/** Nom du panel. */
	public static final String INFO_PANEL_NAME = "INFO_PANEL";
	private ResourceBundle mBundle = ResourceBundle
			.getBundle("fr.yoanndiquelou.binedit.panel.resources.InformationBundle");
	/**
	 * 
	 */
	private static final long serialVersionUID = 4023387544245476943L;

	/** Address label. */
	private JLabel mAddrLabel;
	/** Shift panel. */
	private JPanel mShiftPanel;
	/** Shift label. */
	private JLabel mShiftLabel;

	public InfoPanel(long size) {
		setName(INFO_PANEL_NAME);
		setLayout(new BorderLayout());
		mAddrLabel = new JLabel(String.valueOf(0));
		mAddrLabel.setName("AddrLabel");
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		JPanel sizePanel = new JPanel();
		sizePanel.add(new JLabel(mBundle.getString("FILE_SIZE")));
		JLabel sizeLabel = new JLabel(String.valueOf(size));
		sizeLabel.setName("FILE_SIZE");
		sizePanel.add(sizeLabel);
		mShiftPanel = new JPanel();
		mShiftPanel.add(new JLabel(mBundle.getString("SHIFT")));
		mShiftLabel = new JLabel();
		mShiftLabel.setName("ShiftLabel");
		mShiftPanel.add(new JLabel());
		mShiftPanel.setVisible(false);
		JPanel addrPanel = new JPanel();
		addrPanel.add(new JLabel(mBundle.getString("ADDRESS")));
		addrPanel.add(mAddrLabel);

		add(sizePanel, BorderLayout.WEST);
		add(mShiftLabel, BorderLayout.CENTER);
		add(addrPanel, BorderLayout.EAST);
	}

	/**
	 * Set new shift value.
	 * 
	 * @param newShift shift value
	 */
	public void setShift(int newShift) {
		mShiftLabel.setText(AddressUtils.getHexString(newShift, true));
		mShiftPanel.setVisible(newShift != 0);
	}

	/**
	 * Set new selected addr.
	 * 
	 * @param newAddr new address
	 */
	public void setAddr(long newMinAddr, long newMaxAddr) {
		String minAddr;
		String maxAddr;
		if (Settings.getVisibility(Settings.ADDRESSES_HEXA)) {
			minAddr = AddressUtils.getHexString(newMinAddr * Settings.getDisplayMode().getBytes());
			maxAddr = AddressUtils.getHexString(newMaxAddr * Settings.getDisplayMode().getBytes());
		} else {
			minAddr = String.valueOf(newMinAddr * Settings.getDisplayMode().getBytes());
			maxAddr = String.valueOf(newMaxAddr * Settings.getDisplayMode().getBytes());
		}
		if (minAddr.equals(maxAddr)) {
			mAddrLabel.setText(minAddr);
		} else {
			mAddrLabel.setText(String.format("[%s;%s]", minAddr, maxAddr));
		}

	}

}
