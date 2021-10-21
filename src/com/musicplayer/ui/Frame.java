package com.musicplayer.ui;

import com.musicplayer.player.HigherPlayer;
import com.musicplayer.ui.tool.ButtonToolBar;
import com.musicplayer.ui.tool.IconButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/*
 * @date 2015-7-11
 */

public class Frame extends JFrame {

	private final int InitialWidth = 365;
	private final int InitialHeight = 670;
	private final Point InitialPoint;
	private ShowPanel showPanel;
	private PlayPanel playPanel;
	private PlayListPanel playListPanel;
	private ButtonToolBar toolBar;
	private final static String savaPath = "D:/";

	public Frame() {

		setTitle("���ֲ�����");
		setSize(InitialWidth, InitialHeight);

		Dimension dime = Toolkit.getDefaultToolkit().getScreenSize();
		InitialPoint = new Point((dime.width - InitialWidth) / 2,
				(dime.height - InitialHeight) / 2);
		setLocation(InitialPoint);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		readSongLibrary();

		buildPanel();

		setVisible(true);

		requestFocus();
	}

	private void buildPanel() {
		playPanel = new PlayPanel();

		// ToolBar:the left of the Frame
		toolBar = new ButtonToolBar(JToolBar.VERTICAL, 4);

		JButton[] toolBarButtons = new JButton[] {
				new IconButton("�����б�", "icon/note.png"),
				new IconButton("�����ղ�", "icon/clouds.png"),
				new IconButton("�ҵ�����", "icon/download.png") };

		toolBar.addButtons(toolBarButtons);

		playListPanel = new PlayListPanel(toolBar.getButtons(), this);

		// ���ݸ�����������
		showPanel = new ShowPanel();
		JTree[] listTree = playListPanel.deliverTree();
		HigherPlayer player = playPanel.getHigherPlayer();

		// ��ͨ�������������б����
		playPanel.setTrees(listTree);
		player.setJTree(listTree[0]);
		playListPanel.deliverHigherPlayer(player);

		// ��ͨ��������������
		playPanel.setLrcPanelTextArea(showPanel.getTextArea());

		// ��ͨ�ֿ�����벥�����
		playPanel.setParentFrame(this);
		playPanel.setPreferredSize(new Dimension(650, 115));
		playListPanel.setPreferredSize(new Dimension(900, 520));
		toolBar.setPreferredSize(new Dimension(0, 520));
		buildLayout();

		setAction();
	}

	private void setAction() {

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				saveSongLibrary();
				System.exit(0);
			}
		});
	}

	private void buildLayout() {
		Box topBox = Box.createHorizontalBox();

		topBox.add(playPanel);
		Box bottomBox = Box.createHorizontalBox();
		bottomBox.add(toolBar);
		bottomBox.add(playListPanel);
		Container mainPanel = getContentPane();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(topBox);
		mainPanel.add(bottomBox);
		mainPanel.add(Box.createVerticalStrut(15));
	}

	@SuppressWarnings({ "unchecked" })
	private void readSongLibrary() {

		File library = new File("D:/SongLibrary.dat");
	}
			
	private void saveSongLibrary() {
			try {
				ObjectOutputStream outputStream = new ObjectOutputStream(
						new FileOutputStream(new File("D:/SongLibrary.dat")));
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

