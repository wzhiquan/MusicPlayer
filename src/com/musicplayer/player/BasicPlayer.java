package com.musicplayer.player;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import com.musicplayer.ui.tool.TimeProgressBar;

/*
 * �ײ㲥��������Ҫ���Ʋ���
 * @date 2015-7-11
 */

public class BasicPlayer {

	public SourceDataLine sourceDataLine;
	private AudioInputStream audioInputStream;
	public URL audio;
	public boolean HTTPFlag;

	public Thread playThread;

	public boolean IsPause = true;// �Ƿ�ֹͣ����״̬
	public boolean NeedContinue;// ������ͬһ�׸��� �Ƿ��������״̬
	public boolean IsComplete;
	public boolean IsEnd;

	// ����������Ƿ�����
	private boolean IsChoke;

	private Timer checkConnection;

	// ��������
	private FloatControl floatVoiceControl;
	public TimeProgressBar timerProgressBar;

	public synchronized void play() {

		try {

			
				// ��ȡ������Ƶ������
				audioInputStream = AudioSystem.getAudioInputStream(audio);

			// ��ȡ��Ƶ�����ʽ
			AudioFormat audioFormat = audioInputStream.getFormat();
			// MPEG1L3תPCM_SIGNED
			if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
				audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
						audioFormat.getSampleRate(), 16,
						audioFormat.getChannels(),
						audioFormat.getChannels() * 2,
						audioFormat.getSampleRate(), false);
				audioInputStream = AudioSystem.getAudioInputStream(audioFormat,
						audioInputStream);
			}

			// �����������Ƶ��ʽ��ȡ����豸��Ϣ
			DataLine.Info info = new Info(SourceDataLine.class, audioFormat);
			// ��ȡ����豸����
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);

			// ������ܵ�
			sourceDataLine.open();
			// ����˹ܵ�ִ������ I/O
			sourceDataLine.start();

			// ��ȡ�������Ŀؼ�
			floatVoiceControl = (FloatControl) sourceDataLine
					.getControl(FloatControl.Type.MASTER_GAIN);

			// ����minValue -80 maxValue 6
			// ����ʵĳ�ʼ����
			floatVoiceControl.setValue(-20);

			byte[] buf = new byte[1024];
			int onceReadDataSize = 0;

			while ((onceReadDataSize = audioInputStream
					.read(buf, 0, buf.length)) != -1) {
				// ������û������
				IsChoke = false;

				if (IsEnd) {
					return;
				}

				// �Ƿ���ͣ
				if (IsPause) {
					pause();
				}

				// ������д���Ƶ���� ������˿�д��ǰ����
				sourceDataLine.write(buf, 0, onceReadDataSize);

				// Ԥ������������
				IsChoke = true;
			}

			IsChoke = false;
			// ��ˢ����������
			sourceDataLine.drain();

			sourceDataLine.close();
			audioInputStream.close();

			if (checkConnection != null) {
				checkConnection.cancel();
				checkConnection.purge();
				checkConnection = null;
				// System.out.println("EndTimeOutControl");
			}

		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException | InterruptedException e) {

			e.printStackTrace();

		}
	}

	public void load(URL url) {
		this.audio = url;
	}

	public void checkConnectionSchedule() {

		checkConnection = new Timer(true);

		checkConnection.schedule(new TimerTask() {

			// ��������
			int times = 0;

			@Override
			public void run() {

				if (IsChoke) {
					times++;

					// �����⵽����������20��
					if (times == 20) {
						try {

							// ����������
							timerProgressBar.cleanTimer();

							// ʹplayThread��Ȼִ����
							IsEnd = false;

							// �������ر�
							audioInputStream.close();

							JOptionPane.showMessageDialog(null, "�����쳣�ж�", "",
									JOptionPane.PLAIN_MESSAGE);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} else {
					times = 0;
				}
			}

		}, 2000, 500);
	}

	public synchronized void resume() {

		IsPause = false;
		NeedContinue = false;
		this.notify();
	}

	private synchronized void pause() throws InterruptedException {
		NeedContinue = true;
		this.wait();
	}

	public void end() {
		try {
			if (playThread == null) {
				return;
			}
			IsPause = true;
			NeedContinue = false;
			IsComplete = false;
			IsEnd = true;

			// �رյ�ǰ��������ܵ�
			sourceDataLine.close();
			audioInputStream.close();

			playThread = null;

		} catch (Exception e) {
			System.out.println("�жϲ��ŵ�ǰ����");
			IsPause = true;
			NeedContinue = false;
			IsComplete = false;
			IsEnd = true;
		}

	}

	// ��ȡ��Ƶ�ļ��ĳ��� ����
	public int getAudioTrackLength(URL url) {
		try {

			// ֻ�ܻ�ñ��ظ����ļ�����Ϣ
			AudioFile file = AudioFileIO.read(new File(url.toURI()));

			// ��ȡ��Ƶ�ļ���ͷ��Ϣ
			AudioHeader audioHeader = file.getAudioHeader();
			// �ļ����� ת����ʱ��
			return audioHeader.getTrackLength();
		} catch (CannotReadException | IOException | TagException
				| ReadOnlyFileException | InvalidAudioFrameException
				| URISyntaxException e) {
			e.printStackTrace();
			return -1;
		}

	}

	public String getAudioTotalTime(int sec) {
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

	public SourceDataLine getSourceDataLine() {
		return sourceDataLine;
	}

	public FloatControl getFloatControl() {
		return floatVoiceControl;
	}

}
