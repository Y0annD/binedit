package fr.yoanndiquelou.binedit.laf.ui;

import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicEditorPaneUI;

public class BEEditorPaneUI extends BasicEditorPaneUI{

	@Override
	public void installUI(JComponent c) {
		c.setFont(new Font("Courier", Font.PLAIN, 13));
		super.installUI(c);		
	}
}
