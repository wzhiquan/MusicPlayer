package com.musicplayer.ui.tool;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import com.musicplayer.song.LrcInfos;

/*
 * �������Ľ���������һ����ʱ�� ��ʱ��ӡ���
 * @date 2015-7-11
 */

public class TimeProgressBar extends JProgressBar {

	private boolean timerPause;
	private int audioTotalTime;
	private int counter;

	private Timer timer;
	private Task task;

	private JLabel currentTimeCountLabel;

	private LrcInfos lrcInfos;
	private Map<Integer, String> lrcInfosMap;
	private int nextLrcTime;

	// ������
	private JTextArea textArea;

	public TimeProgressBar() {
		counter = 0;
		setMinimum(0);
		setStringPainted(false);

	}

	// ������ʱ��
	public void startTimer() {

		timer = new Timer(true);
	    task = new Task();
		timer.schedule(task, 500, 1000);

	}

	// ������ʱ��
	public void resumeTimer() {
		synchronized (timer) {
			timer.notify();
		}
	}

	// ���ü�ʱ��
	public void cleanTimer() {
		counter = 0;

		if (timer != null) {
			timer.cancel();
			timer.purge();
		}

		currentTimeCountLabel.setText("0:00");
		this.setValue(0);

		textArea.setText("\n");

	}

	public void setAudioTotalTime(int n) {
		audioTotalTime = n;
		super.setMaximum(n);
	}

	public void setTimerControl(boolean IsPause) {
		this.timerPause = IsPause;
	}

	public void setCurrentTimeCountLabel(JLabel currentTimeCountLabel) {
		this.currentTimeCountLabel = currentTimeCountLabel;
	}

	public void setCurrentPlayedSongLrcInfo(LrcInfos lrcInfos) {
		this.lrcInfos = lrcInfos;
		lrcInfosMap = lrcInfos.getInfos();
	}

	public void setLrcPanelTextArea(JTextArea textArea) {
		this.textArea = textArea;

	}

	/*
	 * ��ӡtimeʱ����line����� audioTotalTime��ǰ���Ÿ�����ʱ�� nextLrcTime��Ѱ��һ����ʵĿ�ʼʱ��
	 */

	private void printNextLrcInTheTime(int time, int line) {

		for (int i = 0; i < line && time <= audioTotalTime; time++) {
			String content = lrcInfos.getInfos().get(time);
			if (content != null) {
				textArea.append(content + "\n");
				nextLrcTime = time + 1;
				i++;
			}
		}
	}

	class Task extends TimerTask {

		@Override
		public void run() {
			synchronized (timer) {
				if (counter == audioTotalTime) {
					// ��ֹ������ ����ǰ���񻹻ᱻִ��
					cleanTimer();
					return;
				}

				if (timerPause) {
					try {
						timer.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				try {

					if (lrcInfosMap.size() != 0) {
						String content = lrcInfosMap.get(counter);
						if (content != null) {

							// ��ȥ��һ�и�ʣ�ͬʱ��������
							textArea.select(textArea.getLineStartOffset(1),
									textArea.getLineEndOffset(textArea
											.getLineCount() - 1));
							textArea.setText(textArea.getSelectedText());

							// ��ʾ���һ����ʵ���һ��
							printNextLrcInTheTime(nextLrcTime, 1);
						}
					}
				} catch (BadLocationException e) {
					e.printStackTrace();
				}

				counter += 1;
				TimeProgressBar.this.setValue(counter);
				TimeProgressBar.this.currentTimeCountLabel
						.setText(getCurrentTime(counter));
			}
		}

		// ת��ʱ��
		public String getCurrentTime(int sec) {
			String time = "0:00";

			if (sec <= 0) {
				return time;
			}

			int minute = sec / 60;
			int second = sec % 60;
			int hour = minute / 60;

			if (second < 10) {
				time = minute + ":0" + second;
			} else {
				time = minute + ":" + second;
			}

			if (hour != 0) {
				time = hour + ":" + time;
			}

			return time;
		}
	}
}

