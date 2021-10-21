package com.musicplayer.song;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

/*
 * SongNode 显示歌曲名字 不显示歌曲路径
 * @date 2015-7-11
 */

public class SongNode extends DefaultMutableTreeNode {

	private File song;
	private String dataUrl;
	// 播放时长
	private int totalTime;
	// 文件长度
	private int dataSize;
	// 解析的歌曲信息
	private LrcInfos lrcInfo;

	// 歌曲的上级路径
	private String parentPath;
	// 无扩展名的歌曲名
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
