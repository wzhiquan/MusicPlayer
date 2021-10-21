package com.musicplayer.ui.tool;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/*
 * Í¼Æ¬°´Å¥
 * @date 2015-7-11
 */

public class IconButton extends JButton {

	public IconButton(String tip) {
		setToolTipText(tip);
		setBorderPainted(false);
		setOpaque(false);
		setContentAreaFilled(false);

	}

	public IconButton(String tip, String imgUrl) {
		this(tip);
		setIcon(new ImageIcon(imgUrl));
	}

	@Override
	public String toString() {
		return getToolTipText();
	}
}
