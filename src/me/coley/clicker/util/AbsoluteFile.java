package me.coley.clicker.util;

import java.io.File;

/**
 * 
 * @author Matt
 */
@SuppressWarnings("serial")
public class AbsoluteFile extends File {
	public AbsoluteFile(String pathname) {
		super(ensurePath(pathname));
	}

	private static String ensurePath(String pathname) {
		String dir = System.getProperty("settings.dir");
		if (dir == null) {
			dir = System.getProperty("user.dir");
			System.setProperty("settings.dir", dir);
		}
		if (pathname.contains(dir))
			return pathname;
		return dir + File.separator + pathname;
	}

}
