package com.musicplayer.ui.tool;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import com.musicplayer.player.HigherPlayer;
import com.musicplayer.song.AFilter;
import com.musicplayer.song.SongNode;

/*
 * �����б����:�����б�����б������б�
 * @date 2015-7-11
 */

public class SongListPanel extends JScrollPane {
	// �˵������
	private JMenuItem newList;
	private JMenuItem removeList;
	private JMenuItem cleanList;

	private JMenuItem addSongFile;
	private JMenuItem addSongFolder;

	private JMenuItem removeSong;

	private JMenuItem addLrcFile;
	private JMenuItem addLrcFloder;

	private JPopupMenu popupMenu;

	// ����������б����
	private JTree tree;
	private DefaultMutableTreeNode topNode;
	private int defaultNodes;
	private List<SongNode> songlist;

	// �ļ��Ի������
	private JFileChooser fileChooser;
	private FileNameExtensionFilter songFilter;
	private FileNameExtensionFilter lrcFilter;

	// ������
	private HigherPlayer higherPlayer;

	public SongListPanel(String... defaultNodes) {
		this.defaultNodes = defaultNodes.length;

		initComponent(defaultNodes);
		createPopupmenu();
		createAction();

	}

	private void initComponent(String... defaultNodes) {

		// �����
		topNode = new DefaultMutableTreeNode();

		// node��Ĭ�ϵ��б� [0]��ʾ�б��еĸ�����
		for (int i = 0; i < defaultNodes.length; i++) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(
					defaultNodes[i] + "[0]");
			topNode.add(node);
		}

		tree = new JTree(topNode);
		// tree.setPreferredSize(new Dimension(290, 400));
		tree.startEditingAtPath(new TreePath(new Object[] { topNode,
				topNode.getFirstChild() }));

		// ���ظ��ڵ�
		tree.setRootVisible(false);

		getViewport().add(tree);

		// �ļ�ѡ��������
		fileChooser = new JFileChooser();
		songFilter = new FileNameExtensionFilter("��Ƶ�ļ�(*.mid;*.mp3;*.wav)",
				"mid", "MID", "mp3", "MP3", "wav", "WAV");
		lrcFilter = new FileNameExtensionFilter("����ļ�(*.lrc)", "lrc", "LRC");

		songlist = new Vector<SongNode>();
	}

	private void createPopupmenu() {
		popupMenu = new JPopupMenu();

		newList = new JMenuItem("�½��б�");
		removeList = new JMenuItem("�Ƴ��б�");
		cleanList = new JMenuItem("����б�");

		addSongFile = new JMenuItem("��ӱ��ظ���");
		addSongFolder = new JMenuItem("��ӱ��ظ����ļ���");

		JMenu addSong = new JMenu("��Ӹ���");
		addSong.add(addSongFile);
		addSong.add(addSongFolder);

		addLrcFile = new JMenuItem("��ӱ��ظ��");
		addLrcFloder = new JMenuItem("��ӱ��ظ���ļ���");

		addLrcFile.setEnabled(false);
		addLrcFloder.setEnabled(false);

		JMenu addLrc = new JMenu("��Ӹ��");
		addLrc.add(addLrcFile);
		addLrc.add(addLrcFloder);

		removeSong = new JMenuItem("ɾ������");

		popupMenu.add(newList);
		popupMenu.add(removeList);
		popupMenu.add(cleanList);

		popupMenu.addSeparator();
		popupMenu.add(addSong);

		popupMenu.addSeparator();
		popupMenu.add(removeSong);

	}

	private void createAction() {

		// �½��б�
		newList.addActionListener(event -> {
			String listName = JOptionPane.showInputDialog(this, "�������½��б������",
					null, JOptionPane.DEFAULT_OPTION);

			if (listName == null) {
				return;
			}

		addList(listName);

		});

		// �Ƴ��б�
		removeList
				.addActionListener(event -> {

					TreePath path = tree.getSelectionPath();

					if (path == null) {
						return;
					}

					DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
							.getLastPathComponent();

					// �ж��Ƿ��ڸ����ϴ����¼� �Ƿ��ش˸�����Ŀ¼
					if (path.getPathCount() == 3) {
						node = (DefaultMutableTreeNode) node.getParent();
					}

					// ��Ŀ¼�Ƿ�ΪĬ��Ŀ¼
					int nodeIndex = topNode.getIndex(node);
					if (nodeIndex < defaultNodes && nodeIndex != -1) {
						return;
					}

					// ��Ŀ¼�Ƿ񺬸��� �����򷵻�true
					if (node.isLeaf()) {
						node.removeFromParent();
					}

					// ��ģ¼���и��� ѯ���Ƿ��Ƴ�
					else if (JOptionPane
							.showConfirmDialog(this, "�б��ڰ�������,�Ƿ�ɾ��?", null,
									JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						;
					}

					// ��ֹ��ǰ��������
					TreeNode playedSong = higherPlayer.getPlayingSong();
					if (playedSong != null && node.getIndex(playedSong) != -1
							&& playedSong != null) {

						higherPlayer.end();

						// �����²��Ű�ť ʹ�����ڴ�����״̬
						higherPlayer.IsPause = false;
						higherPlayer.getPlayButton().doClick();
						higherPlayer.getSongNameLabel().setText("");
					}

					// ��ռ����еĸ���
					Enumeration<SongNode> e = node.children();
					while (e.hasMoreElements()) {
						songlist.remove(e.nextElement());
					}

					if (songlist.isEmpty()) {
						addLrcFile.setEnabled(false);
						addLrcFloder.setEnabled(false);
					}

					node.removeFromParent();
					tree.updateUI();
				});

		// ����б�
		cleanList.addActionListener(event -> {
			// ����ѡ�нڵ��·��
				TreePath path = tree.getSelectionPath();

				if (path == null) {
					return;
				}

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
						.getLastPathComponent();

				if (node.getChildCount() == 0) {
					node = (DefaultMutableTreeNode) node.getParent();
				}

				if (node == topNode) {
					return;
				}

				// ��ֹ��ǰ��������
				TreeNode playedSong = higherPlayer.getPlayingSong();

				if (playedSong != null && node.getIndex(playedSong) != -1
						&& playedSong != null) {

					higherPlayer.end();
					higherPlayer.IsPause = false;
					higherPlayer.getPlayButton().doClick();
					higherPlayer.getSongNameLabel().setText("");
				}

				Enumeration<SongNode> e = node.children();
				while (e.hasMoreElements()) {
					songlist.remove(e.nextElement());
				}

				if (songlist.isEmpty()) {
					addLrcFile.setEnabled(false);
					addLrcFloder.setEnabled(false);
				}

				node.removeAllChildren();

				// ������б�ĸ�����Ϊ0
				updateSongNumInList(node);

				tree.updateUI();
			});

		// ɾ������
		removeSong.addActionListener(event -> {
			TreePath path = tree.getSelectionPath();

			if (path == null) {
				return;
			}

			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
					.getLastPathComponent();

			// ��ѡ�нڵ��Ǹ���Ŀ¼
				if (path.getPathCount() == 2) {
					return;
				}

				// ��ȡ��ǰ�������б� ���ڸ����б��и�����
				DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node
						.getParent();

				// ��ֹ��ǰ��������
				if (node == higherPlayer.getPlayingSong()
						&& higherPlayer.playThread != null) {

					higherPlayer.end();
					higherPlayer.IsPause = false;
					higherPlayer.getPlayButton().doClick();
					higherPlayer.getSongNameLabel().setText("");
				}

				songlist.remove(node);
				if (songlist.isEmpty()) {
					addLrcFile.setEnabled(false);
					addLrcFloder.setEnabled(false);
				}

				node.removeFromParent();
				updateSongNumInList(parent);
				tree.updateUI();
			});

		// ���ӱ��ظ���
		addSongFile.addActionListener(event -> {
			TreePath path = tree.getSelectionPath();

			if (path == null) {
				return;
			}

			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
					.getLastPathComponent();

			// ѡ�нڵ��ǵ�3��
				if (path.getPathCount() == 3) {
					node = (DefaultMutableTreeNode) node.getParent();
				}

				// ����JFileChooser�ɶ�ѡ��Ƶ�ļ�
				fileChooser.setMultiSelectionEnabled(true);
				fileChooser.setFileFilter(songFilter);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
					return;
				}

				File[] files = fileChooser.getSelectedFiles();

				addSongs(node, files);

			});

		// ���ӱ��ظ����ļ���
		addSongFolder.addActionListener(event -> {

			// JTree�Ĵ����"���ӱ��ظ���"һ��
				TreePath path = tree.getSelectionPath();

				if (path == null) {
					return;
				}

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
						.getLastPathComponent();

				// ѡ�нڵ��ǵ�3��
				if (path.getPathCount() == 3) {
					node = (DefaultMutableTreeNode) node.getParent();
				}

				// ����JFileChooserֻѡ�ļ���
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setMultiSelectionEnabled(false);

				if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
					return;
				}

				// ��ȡѡ�е��ļ���
				File directory = fileChooser.getSelectedFile();

				// ��ȡ�ļ����е��ļ�,��SongFilter����
				File[] files = directory
						.listFiles(new AFilter(".mid;.mp3;.wav"));

				if (files.length == 0) {
					return;
				}

				addSongs(node, files);

			});


	
		tree.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				// �һ�ѡ�и���
				if (e.getButton() == MouseEvent.BUTTON3) {

					// ���һ����ӽ�����Ľڵ�·��
					TreePath path = tree.getPathForLocation(e.getX(), e.getY());

					if (path != null) {
						tree.setSelectionPath(path);
					}

				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				// ���������� ���Ÿ���
				if (e.getButton() == MouseEvent.BUTTON1) {

					if (e.getClickCount() == 2) {
						TreePath path = tree.getSelectionPath();

						// ��ѡ�нڵ��Ƿ�Ϊ����
						if (path != null && path.getPathCount() == 3) {

							SongNode songNode = (SongNode) path
									.getLastPathComponent();

							if (higherPlayer.getJTree() == null
									|| !tree.equals(higherPlayer.getJTree())) {
								higherPlayer.setJTree(tree);
							}

								higherPlayer.load(songNode);

							higherPlayer.getPlayButton().doClick();
						}
					}
				}
			}
		});

		tree.addTreeSelectionListener(event -> {

			TreePath path = tree.getSelectionPath();

			// ѡ�еĽڵ����б�
			if (path.getPathCount() == 2) {
				// �ӵ�ǰѡ�е��б�ʼ����

				higherPlayer.setCurrentListPath(path);
				tree.startEditingAtPath(path);

			}
		});

		tree.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if (KeyEvent.VK_SPACE == e.getKeyCode()) {
					higherPlayer.getPlayButton().doClick();
				}
			}
		});

	}

	public DefaultMutableTreeNode addList(String listName) {
		listName = listName + "[0]";
		DefaultMutableTreeNode songList = new DefaultMutableTreeNode(listName);
		topNode.add(songList);

		tree.updateUI();

		return songList;
	}

	public void addSongs(DefaultMutableTreeNode parent, File... files) {

		for (File f : files) {

			if (!f.exists()) {
				continue;
			}

			SongNode node = new SongNode(f);

			// �����ǰ�б���ڴ��׸�,count������0,�򲻼��뵱ǰ�б�
			long count = songlist.stream()
					.filter(each -> parent.equals(each.getParent()))
					.filter(each -> each.equals(node)).count();
			// �����ж� if(parent.isNodeChild(node))
			// ��Ϊǰ��new��һ���ڵ�,ͬʱTreeNodeû�������ӽڵ��equals�ж�
			if (count != 0) {
				continue;
			}

			parent.add(node);
			// �������ϼ����ļ�
			songlist.add(node);
		}

		if (!songlist.isEmpty()) {
			addLrcFile.setEnabled(true);
			addLrcFloder.setEnabled(true);
		}

		updateSongNumInList(parent);

		tree.updateUI();
	}

	public void addLrcs(File... files) {

		for (File f : files) {

			if (!f.exists()) {
				continue;
			}
			String name = f.getName();
			String lrcName = name.substring(0, name.lastIndexOf("."));

		}

	}


	// �����б��еĸ�����
	private void updateSongNumInList(DefaultMutableTreeNode node) {
		String listName = (String) node.getUserObject();
		listName = listName.substring(0, listName.lastIndexOf("[")) + "["
				+ node.getChildCount() + "]";
		node.setUserObject(listName);
	}

	public void addPopupMenuToTree() {
		tree.setComponentPopupMenu(popupMenu);
		tree.updateUI();
	}

	public void setPlayer(HigherPlayer player) {
		this.higherPlayer = player;
	}

	public JTree getTree() {
		return tree;
	}
}
