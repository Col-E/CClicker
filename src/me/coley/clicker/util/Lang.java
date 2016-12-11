package me.coley.clicker.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Maps;

/**
 * Simple i18n
 * 
 * @author Matt
 */
public class Lang {
	private static final Map<Integer, String> lang = Maps.newHashMap();
	//@formatter:off
	public static final int 
	// Home tab
	HOME_TITLE = 0,
	HOME_TREE_HOME = 1,
	HOME_TREE_VERSIONHIST = 2,
	HOME_TREE_USAGEGUIDE = 3,
	HOME_TREE_USAGEGUIDE_MSG = 4,
	HOME_WELCOME_TITLE = 31,
	HOME_WELCOME_MSG = 32,
	// Controls tab
	CONTROLS_TITLE = 100,
	CONTROLS_KEY_ID = 101,
	CONTROLS_TOGGLE_STATS = 111,
	CONTROLS_TOGGLE_CLICK = 112,
	CONTROLS_TOGGLE_GUI = 113,
	// Settings tab
	SETTINGS_TITLE = 200,
	SETTINGS_ACTIVE = 201,
	SETTINGS_DELAY_MIN = 211,
	SETTINGS_DELAY_MAX = 212,
	SETTINGS_DELAY_AVG = 213,
	SETTINGS_DELAY_DEV = 214,
	SETTINGS_WINDOW_TARGET = 215,
	SETTINGS_WINDOW_CHOOSE = 216,
	// Statistics tab
	STATISTICS_TITLE = 300,
	STATISTICS_RECORD_SOME_DATA = 301,
	// Agent tab
	AGENT_TITLE = 500,
	AGENT_ATTATCH_VM = 501,
	// Changelog
	CHANGELOG_1_0 = 401,
	CHANGELOG_1_1 = 402,
	CHANGELOG_1_2 = 403;
	//@formatter:on
	static {
		File langFile = new AbsoluteFile("Lang.txt");
		List<String> lines = null;
		try {
			lines = FileUtils.readLines(langFile);
			for (String line : lines) {
				if (line.startsWith("/"))
					continue;
				line = line.replace("\\n", "\n");
				line = line.replace("\\t", "        ");
				String[] data = line.split(":");
				int key = Integer.parseInt(data[0]);
				String msg = data[1];
				lang.put(key, msg);
			}
		} catch (IOException e) {
			// TODO: Warn about file IO error with language file
			e.printStackTrace();
		}
	}

	/**
	 * Retreive a translated string given an identifier 'i'.
	 * 
	 * @param i
	 *            String ID
	 * @return
	 */
	public static String get(int i) {
		return lang.get(i);
	}
}
