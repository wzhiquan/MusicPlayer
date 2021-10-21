package com.musicplayer.player;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jvnet.substance.SubstanceLookAndFeel;
import com.musicplayer.ui.Frame;

/*
 * ��дһ��ʵ�ֲ��š����������Ĳ�����
 */

public class Main {
	public static void main(String[] args) {

		try {
			// ���ù۸�
			UIManager
					.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel");
			// ����ˮӡ
			SubstanceLookAndFeel
					.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceMosaicWatermark");
			// ���ý�����Ⱦ
			SubstanceLookAndFeel
					.setCurrentGradientPainter("org.jvnet.substance.painter.WaveGradientPainter");

			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(() -> {
			new Frame();
		});

	}

}
