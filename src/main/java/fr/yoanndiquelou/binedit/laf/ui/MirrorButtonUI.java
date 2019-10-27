package fr.yoanndiquelou.binedit.laf.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

public class MirrorButtonUI extends BasicButtonUI {

	@Override
	public void paint(Graphics g, JComponent c) {
		Graphics2D g2d = (Graphics2D) g.create();
		int w = c.getWidth();
		int h = c.getHeight();
		g2d.translate(w / 2, h / 2);
		g2d.scale(-1, -1);
		g2d.translate(-w / 2, -h / 2);
		super.paint(g2d, c);
		g2d.dispose();
	}
}
