package com.musicplayer.ui;

import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import com.musicplayer.player.HigherPlayer;
import com.musicplayer.ui.tool.SongListPanel;

/*
 * 播放列表面板
 * @date 2015-7-11
 */

public class PlayListPanel extends JPanel {

	private SongListPanel songListPanel;
	private SongListPanel cloudsPanel;
	private SongListPanel downloadPanel;
	private JButton[] buttons;
	private JFrame parent;

	private CardLayout card;

	public PlayListPanel(JButton[] buttons, JFrame parent) {

		setOpaque(false);

		card = new CardLayout();
		setLayout(card);

		this.buttons = buttons;
		this.parent = parent;

		initPanel();
	}

	private void initPanel() {
		songListPanel = new SongListPanel("默认列表");
		songListPanel.addPopupMenuToTree();

		cloudsPanel = new SongListPanel("默认列表", "我喜欢");
		cloudsPanel.addPopupMenuToTree();

		downloadPanel = new SongListPanel("下载中", "已下载");

		add(songListPanel, "0");
		add(cloudsPanel, "1");
		add(downloadPanel, "2");

		setAction();
	}

	private void setAction() {

		for (int i = 0; i < buttons.length; i++) {
			int k = i;
			buttons[i].addActionListener(event -> {
				card.show(this, "" + k);
			});
		}

	}

	public void deliverHigherPlayer(HigherPlayer HigherPlayer) {
		songListPanel.setPlayer(HigherPlayer);
		cloudsPanel.setPlayer(HigherPlayer);
		downloadPanel.setPlayer(HigherPlayer);
	}

	public JTree[] deliverTree() {
		return new JTree[] { songListPanel.getTree(), cloudsPanel.getTree(),
				downloadPanel.getTree() };
	}
}
