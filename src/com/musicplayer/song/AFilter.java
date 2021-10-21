package com.musicplayer.song;

import java.io.File;
import java.io.FileFilter;
import java.util.StringTokenizer;

/*
 * AFilter ������  �������� MP3 WAV MID ������� LRC
 * @date 2015-7-11
 */

public class AFilter implements FileFilter {

	private String description;

	public AFilter(String description) {
		this.description = description;
	}

	@Override
	public boolean accept(File f) {

		String name = f.getName();
		StringTokenizer token = new StringTokenizer(description, ",;");

		while (token.hasMoreTokens()) {
			if (name.toLowerCase().endsWith(token.nextToken().toLowerCase())) {
				return true;
			}
		}

		return false;
	}

	public String getDescription() {

		return description;
	}

}
