package com.musicplayer.song;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

/*
 * SongNode ��ʾ�������� ����ʾ����·��
 * @date 2015-7-11
 */

public class SongNode extends DefaultMutableTreeNode {

	private File song;
	private String dataUrl;
	// ����ʱ��
	private int totalTime;
	// �ļ�����
	private int dataSize;
	// �����ĸ�����Ϣ
	private LrcInfos lrcInfo;

	// �������ϼ�·��
	private String parentPath;
	// ����չ���ĸ�����
	private String songName;

	public SongNode(File song) {
		super(song, false);
		this.song = song;

		parentPath = song.getParent();
		songName = song.getName();
		songName = songName.substring(0, songName.lastIndexOf("."));

		File f = new File(parentPath + "\\" + songName + ".lrc");
		lrcInfo = new LrcInfos();
	}

	public int getTotalTime() {
		return totalTime;
	}

	public LrcInfos getLrcInfo() {
		return lrcInfo;
	}

	public String getSongName() {
		return songName;
	}

	public File getSong() {
		return song;
	}

	public String getDataURL() {
		return dataUrl;
	}

	public int getDataSize() {
		return dataSize;
	}

	@Override
	public String toString() {
		if (songName.endsWith(".mp3")) {
			return songName;
		}
		return songName + ".mp3";
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}

		if (song == null) {
			return false;
		}

		SongNode objectNode = (SongNode) object;

		return song.equals(objectNode.getSong());
	}
}
