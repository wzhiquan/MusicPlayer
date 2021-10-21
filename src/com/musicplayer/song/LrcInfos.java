package com.musicplayer.song;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * �����Ϣ:����/����/ר��/ʱ��-����ı���
 * @date 2015-7-11
 */

public class LrcInfos {

	private String title;
	private String singer;
	private String album;
	private int totalTime;

	private List<Integer> timeList;
	private Map<Integer, String> infos;

	private String regex;
	private Pattern pattern;

	public LrcInfos() {
		infos = new HashMap<Integer, String>();
		timeList = new ArrayList<Integer>();
		regex = "\\[\\d{2}:\\d{2}\\.\\d{2}\\]";
		pattern = Pattern.compile(regex);
	}

	// ���뱾�ظ���ļ�
	public void read(File file) {

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String line = "";

			while ((line = reader.readLine()) != null) {
				match(line);
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ƥ��һ���ַ�
	private void match(String line) {

		// �жϵ�ǰ���Ƿ�Ϊ���֡����⡢ר�� ���� ����
		if (!line.endsWith("]")) {

			// ƥ�䵱ǰ��ʱ���ʽ
			Matcher matcher = pattern.matcher(line);

			int endIndex = 0;
			// ������
			while (matcher.find()) {
				// ��ȡ����
				String group = matcher.group();

				group = group.substring(1, group.length() - 1);

				timeList.add(toCurrentTime(group));
				endIndex = matcher.end();
			}

			String strInfos = line.substring(endIndex, line.length());
			timeList.forEach(each -> infos.put(each, strInfos));
			timeList.clear();

		} else if (line.startsWith("[ti:")) {
			title = line.substring(4, line.length() - 1);
		} else if (line.startsWith("[ar:")) {
			singer = line.substring(4, line.length() - 1);
		} else if (line.startsWith("[al:")) {
			album = line.substring(4, line.length() - 1);
		}
	}

	// ������ǰ��Ϣʱ�� ����
	private int toCurrentTime(String time) {
		int currentTime = 0;

		String[] spliter = time.split(":");
		currentTime += Integer.parseInt(spliter[0]) * 60;
		spliter = spliter[1].split("\\.");
		currentTime += Integer.parseInt(spliter[0]);
		if (Integer.parseInt(spliter[1]) >= 50) {
			currentTime++;
		}

		if (totalTime < currentTime) {
			totalTime = currentTime;
		}

		return currentTime;
	}

	public String getTitle() {
		return title;
	}

	public String getSinger() {
		return singer;
	}

	public String getAlbum() {
		return album;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public Map<Integer, String> getInfos() {
		return infos;
	}
}
