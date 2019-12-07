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
		setLayout(new BorderLayout());
		mAddrLabel = new JLabel(String.valueOf(0));
		mAddrLabel.setName("AddrLabel");
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		JPanel sizePanel = new JPanel();
		sizePanel.add(new JLabel(mBundle.getString("FILE_SIZE")));
		sizePanel.add(new JLabel(String.valueOf(size)));
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
		String minAddr = AddressUtils.getHexString(newMinAddr*Settings.getDisplayMode().getBytes());
		String maxAddr = AddressUtils.getHexString(newMaxAddr*Settings.getDisplayMode().getBytes());
		if (minAddr.equals(maxAddr)) {
			mAddrLabel.setText(minAddr);
		} else {
			mAddrLabel.setText(String.format("[%s;%s]", minAddr, maxAddr));
		}

	}

}
