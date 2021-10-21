package com.musicplayer.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JFrame;

import com.musicplayer.player.HigherPlayer;
import com.musicplayer.song.SongNode;
import com.musicplayer.ui.tool.IconButton;
import com.musicplayer.ui.tool.TimeProgressBar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ������� ʹ��NetBean Matisse����.��������3����ǩ��7����ť��1����������1����Ͽ��1��������
 * @date 2015-7-11
 */

public class PlayPanel extends JPanel {

	/*
	 * Creates new form HubPlayPanel
	 */
	public PlayPanel() {
		initComponents();
		initPlayer();
	}

	private void initComponents() {

		songName = new JLabel("Music");

		download = new IconButton("����", "icon/download.png");
		mark = new IconButton("��ϲ��", "icon/heart.png");
		share = new IconButton("����", "icon/share.png");

		timerProgressBar = new TimeProgressBar();
		mode = new JComboBox<>();

		backPlay = new IconButton("��һ��", "icon/back.png");
		play = new IconButton("����", "icon/play.png");
		play.setSelectedIcon(new ImageIcon("icon/pause.png"));
		play.setDisabledSelectedIcon(new ImageIcon("icon/play.png"));
		play.setMnemonic(KeyEvent.VK_ENTER);

		frontPlay = new IconButton("��һ��", "icon/front.png");
		voiceControl = new IconButton("����", "icon/voice.png");
		voiceAdjust = new JSlider(minVoice, maxVoice);
		voiceAdjust.setValue(suitableVoice);
		audioTotalTimeLabel = new JLabel("4:00");
		currentTimeCountLabel = new JLabel("0:00");

		setOpaque(false);
		setPreferredSize(new Dimension(360, 110));

		download.setMaximumSize(new Dimension(3030, 3030));
		download.setMinimumSize(new Dimension(30, 30));
		download.setPreferredSize(new Dimension(30, 30));

		mark.setPreferredSize(new Dimension(30, 30));

		share.setPreferredSize(new Dimension(30, 30));

		timerProgressBar.setPreferredSize(new Dimension(340, 7));

		mode.setModel(new DefaultComboBoxModel(new String[] { "��������", "����ѭ��",
				"˳�򲥷�", "�б�ѭ��", "�������" }));

		backPlay.setPreferredSize(new Dimension(30, 30));

		play.setPreferredSize(new Dimension(30, 30));

		frontPlay.setPreferredSize(new Dimension(30, 30));

		voiceControl.setPreferredSize(new Dimension(30, 30));

		voiceAdjust.setPreferredSize(new Dimension(50, 20));

		audioTotalTimeLabel.setText("0:00");

		currentTimeCountLabel.setText("0:00");

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(12, 12,
																		12)
																.addComponent(
																		songName,
																		GroupLayout.PREFERRED_SIZE,
																		230,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		download,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		mark,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		share,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(10, 10,
																		10)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createParallelGroup(
																								GroupLayout.Alignment.TRAILING,
																								false)
																								.addGroup(
																										layout.createSequentialGroup()
																												.addComponent(
																														currentTimeCountLabel)
																												.addPreferredGap(
																														LayoutStyle.ComponentPlacement.RELATED,
																														GroupLayout.DEFAULT_SIZE,
																														Short.MAX_VALUE)
																												.addComponent(
																														audioTotalTimeLabel))
																								.addComponent(
																										timerProgressBar,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										mode,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.RELATED,
																										33,
																										Short.MAX_VALUE)
																								.addComponent(
																										backPlay,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										play,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										frontPlay,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE)
																								.addGap(28,
																										28,
																										28)
																								.addComponent(
																										voiceControl,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										LayoutStyle.ComponentPlacement.RELATED)
																								.addComponent(
																										voiceAdjust,
																										GroupLayout.PREFERRED_SIZE,
																										81,
																										GroupLayout.PREFERRED_SIZE)
																								.addGap(1,
																										1,
																										1)))))
								.addGap(0, 7, Short.MAX_VALUE)));

		layout.linkSize(SwingConstants.HORIZONTAL, new Component[] { download,
				mark, share });

		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGroup(
																										layout.createParallelGroup(
																												GroupLayout.Alignment.LEADING)
																												.addGroup(
																														layout.createSequentialGroup()
																																.addGap(10,
																																		10,
																																		10)
																																.addGroup(
																																		layout.createParallelGroup(
																																				GroupLayout.Alignment.BASELINE)
																																				.addComponent(
																																						download,
																																						GroupLayout.PREFERRED_SIZE,
																																						GroupLayout.DEFAULT_SIZE,
																																						GroupLayout.PREFERRED_SIZE)
																																				.addComponent(
																																						mark,
																																						GroupLayout.PREFERRED_SIZE,
																																						20,
																																						GroupLayout.PREFERRED_SIZE)
																																				.addComponent(
																																						share,
																																						GroupLayout.PREFERRED_SIZE,
																																						20,
																																						GroupLayout.PREFERRED_SIZE))
																																.addGap(6,
																																		6,
																																		6))
																												.addGroup(
																														GroupLayout.Alignment.TRAILING,
																														layout.createSequentialGroup()
																																.addContainerGap()
																																.addComponent(
																																		songName,
																																		GroupLayout.PREFERRED_SIZE,
																																		20,
																																		GroupLayout.PREFERRED_SIZE)
																																.addPreferredGap(
																																		LayoutStyle.ComponentPlacement.UNRELATED)))
																								.addComponent(
																										timerProgressBar,
																										GroupLayout.PREFERRED_SIZE,
																										GroupLayout.DEFAULT_SIZE,
																										GroupLayout.PREFERRED_SIZE))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(55,
																										55,
																										55)
																								.addGroup(
																										layout.createParallelGroup(
																												GroupLayout.Alignment.CENTER)
																												.addComponent(
																														currentTimeCountLabel)
																												.addComponent(
																														audioTotalTimeLabel))))
																.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.CENTER)
																				.addComponent(
																						play,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						backPlay,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						frontPlay,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(76, 76,
																		76)
																.addComponent(
																		voiceControl,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
												.addGroup(
														GroupLayout.Alignment.CENTER,
														layout.createSequentialGroup()
																.addGap(80, 80,
																		80)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING)
																				.addComponent(
																						voiceAdjust,
																						GroupLayout.Alignment.CENTER,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						mode,
																						GroupLayout.Alignment.CENTER,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE))))
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		layout.linkSize(SwingConstants.VERTICAL, new Component[] { download,
				mark, share });

	}

	private void initPlayer() {
		player = new HigherPlayer();

		player.setPlayButton(play);
		player.setSongNameLabel(songName);
		player.setAudioTotalTimeLabel(audioTotalTimeLabel);
		player.setCurrentTimeCountLabel(currentTimeCountLabel);
		player.setTimerProgressBar(timerProgressBar);

		timerProgressBar.setTimerControl(player.IsPause);
		timerProgressBar.setCurrentTimeCountLabel(currentTimeCountLabel);

		setButtonsAction();
		setOtherAction();
	}

	public HigherPlayer getHigherPlayer() {
		return player;
	}

	public void setButtonsAction() {

		// play button
		play.addActionListener(event -> {

			if (player.getLoadSongName() == null) {
				return;
			}

			// �ڵ�һ�β���ʱ Ϊ�˲���������һ��if�ж�
			// ���CurrentSong==nullʱ������룬��ֹ��ǰ�������ţ����ǵ�ǰ�����̻߳�û��ʵ�� ���쳣
			if (player.getPlayingSongName() == null) {
				player.setPlayingSongName(player.getLoadSongName());
			}

			// �ڲ��Ź����л�����
			if (!player.getLoadSongName().equals(player.getPlayingSongName())
					&& !player.IsComplete) {

				// ��ֹ��ǰ��������
				player.end();
				player.setPlayingSongName(player.getLoadSongName());
			}

			// ����ͣ �򲥷�
			if (player.IsPause) {

				if (player.NeedContinue) {
					// ��������
					player.resume();
					// �������Ŀ���
					timerProgressBar.setTimerControl(false);
					timerProgressBar.resumeTimer();
					play.setIcon(play.getSelectedIcon());
					play.setToolTipText("��ͣ");
					return;
				}
				// �������Ŀ���
				timerProgressBar.setTimerControl(false);
				// ���Ÿ���
				player.open();
				// ��������
				voiceAdjust.setValue(suitableVoice);
				songName.setText(player.getPlayingSongName());
				play.setIcon(play.getSelectedIcon());
				play.setToolTipText("��ͣ");
				player.IsPause = false;

			} else {
				// �ǲ��� ����ͣ
				player.IsPause = true;

				timerProgressBar.setTimerControl(true);

				play.setIcon(play.getDisabledSelectedIcon());
				play.setToolTipText("����");
			}

			songName.updateUI();
			play.updateUI();
			parentFrame.setVisible(true);
		});

		// backPlay button
		backPlay.addActionListener(event -> {

			if (player.getLoadSongName() == null) {
				return;
			}

			player.IsPlayNextSong = false;
			player.next();
			play.doClick();

		});

		// frontPlay button
		frontPlay.addActionListener(event -> {

			if (player.getLoadSongName() == null) {
				return;
			}

			player.IsPlayNextSong = true;
			player.next();
			play.doClick();

		});

		// voiceControl button
		voiceControl.addActionListener(event -> {

			if (!player.IsPause) {
				if (voiceAdjust.getValue() != minVoice) {
					player.setVoiceValue(minVoice);
					voiceAdjust.setValue(minVoice);
				} else {
					player.setVoiceValue(suitableVoice);
					voiceAdjust.setValue(suitableVoice);
				}
			}

		});

		// mark button
		// ������ڴ��� ��list��ȡ����� ����Ӹ��� ����
		mark.addActionListener(event -> {

			if (player.getLoadSongName() == null) {
				return;
			}

			SongNode playedSong = (SongNode) player.getPlayingSong();

			// �������б�
		addSongNodeToTreeList(trees[1], 1, playedSong);

			JOptionPane.showMessageDialog(null, "�ѳɹ���ӵ��ղ��б�", null,
					JOptionPane.PLAIN_MESSAGE, null);
		});

	}

	public void setOtherAction() {
		timerProgressBar.addChangeListener(event -> {
		});

		voiceAdjust.addChangeListener(event -> {
			if (!player.IsPause) {
				player.setVoiceValue(voiceAdjust.getValue());
			}
		});

		mode.addActionListener(event -> {
			player.mode = mode.getSelectedIndex();

		});

	}
	public void addSongNodeToTreeList(JTree tree, int index, SongNode songNode) {

		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel()
				.getRoot();
		DefaultMutableTreeNode list = (DefaultMutableTreeNode) root
				.getChildAt(index);

		list.add(songNode);

		// �б�������
		String listName = (String) list.getUserObject();
		listName = listName.substring(0, listName.lastIndexOf("[")) + "["
				+ list.getChildCount() + "]";
		list.setUserObject(listName);

		// ������ﲻ�������Ļ� �᲻��ȷ��ʾ
		tree.updateUI();

	}

	public void removeSongNodeInTreeList(JTree tree, int index,
			SongNode songNode) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel()
				.getRoot();
		DefaultMutableTreeNode list = (DefaultMutableTreeNode) root
				.getChildAt(index);

		list.remove(songNode);

		// �б�������
		String listName = (String) list.getUserObject();
		listName = listName.substring(0, listName.lastIndexOf("[")) + "["
				+ list.getChildCount() + "]";
		list.setUserObject(listName);

		// ������ﲻ�������Ļ� �᲻��ȷ��ʾ
		tree.updateUI();

	}

	public void setTrees(JTree[] trees) {
		this.trees = trees;
	}

	public void setLrcPanelTextArea(JTextArea textArea) {
		timerProgressBar.setLrcPanelTextArea(textArea);
	}

	public void setParentFrame(JFrame frame) {
		this.parentFrame = frame;
	}

	// Variables declaration - do not modify
	private JLabel songName;
	private JButton download;
	private JButton mark;
	private JButton share;

	private TimeProgressBar timerProgressBar;
	private JLabel currentTimeCountLabel;
	private JLabel audioTotalTimeLabel;

	private JComboBox<String> mode;
	private JButton backPlay;
	private JButton play;
	private JButton frontPlay;
	private JSlider voiceAdjust;
	private JButton voiceControl;

	private JTree[] trees;

	private JFrame parentFrame;

	private HigherPlayer player;
	// ��FloatControl.Type.MASTER_GAIN�õ�������
	private final int minVoice = -80;
	private final int maxVoice = 6;
	private final int suitableVoice = -20;
	// End of variables declaration

}
