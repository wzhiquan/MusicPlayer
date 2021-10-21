package com.musicplayer.ui;

import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
/*
 * 展示面板
 * @date 2015-7-11
 */

public class ShowPanel extends JPanel {
	// 歌词面板相关
	private JTextArea textArea;
	public ShowPanel() {
		setLayout(new CardLayout());

		init();
	}

	private void init() {
		// 歌词面板处理
		textArea = new JTextArea();
	}

	public JTextArea getTextArea() {
		return textArea;
	}
}
