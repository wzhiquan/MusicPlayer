package com.musicplayer.ui;

import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
/*
 * չʾ���
 * @date 2015-7-11
 */

public class ShowPanel extends JPanel {
	// ���������
	private JTextArea textArea;
	public ShowPanel() {
		setLayout(new CardLayout());

		init();
	}

	private void init() {
		// �����崦��
		textArea = new JTextArea();
	}

	public JTextArea getTextArea() {
		return textArea;
	}
}
