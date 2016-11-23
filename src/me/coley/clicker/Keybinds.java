package me.coley.clicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Maps;

import me.coley.clicker.util.Saveable;
import me.coley.clicker.value.ValueUser;

/**
 * Lazy keybind system. Could be more abstract/extensible but it does what it
 * needs to do.
 * 
 * @author Matt
 */
public class Keybinds implements Saveable {
	// Constants - Keybind ID's
	public static int BIND_TOGGLE_RECORDING = 100;
	public static int BIND_TOGGLE_CLICKER = 101;
	public static int BIND_TOGGLE_GUI = 102;
	// File for saving/loading
	private static final File fileBinds = new File("keybinds.txt");
	// Key to key name map
	private static final Map<Integer, String> conv = Maps.newHashMap();
	// Values
	private Map<Integer, Integer> keybinds = Maps.newHashMap();
	// Values - Names's
	private Map<Integer, String> names = Maps.newHashMap();
	// Values - Attached components
	private Map<Integer, ValueUser> registered = Maps.newHashMap();

	/**
	 * Add a value of the given ID to a given value and assign it a name.
	 * 
	 * @param keybindID
	 *            Setting identifier <i>(constant)</i>
	 * @param name
	 *            Settting name
	 * @param value
	 *            Setting intiial value
	 */
	public void addKeyValue(int keybindID, String name, int value) {
		keybinds.put(keybindID, value);
		names.put(keybindID, name);
	}

	/**
	 * Associate a ValueUser and a keybind by it's ID.
	 * 
	 * @param keybindID
	 * @param updateable
	 */
	public void register(int keybindID, ValueUser updateable) {
		registered.put(keybindID, updateable);
	}

	/**
	 * Update the keybind linked to the ID with a given value.
	 * 
	 * @param keybindID
	 * @param value
	 */
	public void updateKeybind(int keybindID, int value) {
		if (keybinds.containsKey(keybindID)) {
			keybinds.put(keybindID, value);
			if (registered.containsKey(keybindID)) {
				registered.get(keybindID).onValueUpdated();
			}
		}
	}

	/**
	 * Retrieve the keybind given the keybind's ID.
	 * 
	 * @param keybindID
	 * @return
	 */
	public int getKey(int keybindID) {
		return keybinds.get(keybindID);
	}

	/**
	 * Retrieve the map of keybinds.
	 * 
	 * @return
	 */
	public Map<Integer, Integer> getKeybinds() {
		return keybinds;
	}

	/**
	 * Retrieve a key's name from its vkCode.
	 * 
	 * @param vkCode
	 * @return
	 */
	public static String getKeyName(int vkCode) {
		if (!conv.containsKey(vkCode)) {
			return " ??? ";
		}
		return conv.get(vkCode);
	}

	/**
	 * Retrieve the keybind's name given the keybind's ID.
	 * 
	 * @param keybindID
	 * @return
	 */
	public String getName(int settingID) {
		return names.get(settingID);
	}

	@Override
	public void load() {
		try {
			if (fileBinds.exists()) {
				List<String> linesN = FileUtils.readLines(fileBinds);
				for (String line : linesN) {
					if (line.startsWith("/"))
						continue;
					String[] data = line.split(":");
					// NAME : ID : VALUE
					int key = Integer.parseInt(data[1]);
					int value = Integer.parseInt(data[2]);
					updateKeybind(key, value);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save() {
		try {
			if (!fileBinds.exists()) {
				fileBinds.createNewFile();
			}
			List<String> linesN = new ArrayList<String>(keybinds.size());
			for (int i : keybinds.keySet()) {
				// NAME : ID : VALUE
				linesN.add(names.get(i) + ":" + i + ":" + keybinds.get(i).intValue());
			}
			FileUtils.writeLines(fileBinds, linesN);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static {
		conv.put(65, "A");
		conv.put(66, "B");
		conv.put(67, "C");
		conv.put(68, "D");
		conv.put(69, "E");
		conv.put(70, "F");
		conv.put(71, "G");
		conv.put(72, "H");
		conv.put(73, "I");
		conv.put(74, "J");
		conv.put(75, "K");
		conv.put(76, "L");
		conv.put(77, "M");
		conv.put(78, "N");
		conv.put(79, "O");
		conv.put(80, "P");
		conv.put(81, "Q");
		conv.put(82, "R");
		conv.put(83, "S");
		conv.put(84, "T");
		conv.put(85, "U");
		conv.put(86, "V");
		conv.put(87, "W");
		conv.put(88, "X");
		conv.put(89, "Y");
		conv.put(90, "Z");
		conv.put(49, "1");
		conv.put(50, "2");
		conv.put(51, "3");
		conv.put(52, "4");
		conv.put(53, "5");
		conv.put(54, "6");
		conv.put(55, "7");
		conv.put(56, "8");
		conv.put(57, "9");
		conv.put(112, "F1");
		conv.put(113, "F2");
		conv.put(114, "F3");
		conv.put(115, "F4");
		conv.put(116, "F5");
		conv.put(117, "F6");
		conv.put(118, "F7");
		conv.put(119, "F8");
		conv.put(120, "F9");
		conv.put(121, "F10");
		conv.put(122, "F11");
		conv.put(123, "F12");
		conv.put(189, "-");
		conv.put(187, "=");
		conv.put(219, "[");
		conv.put(221, "]");
		conv.put(186, ";");
		conv.put(222, "'");
		conv.put(191, "/");
		conv.put(220, "\\");
		conv.put(190, ".");
		conv.put(27, "ESCAPE");
		conv.put(45, "INSERT");
		conv.put(13, "RETURN");
		conv.put(96, "NUM 0");
		conv.put(97, "NUM 1");
		conv.put(98, "NUM 2");
		conv.put(99, "NUM 3");
		conv.put(100, "NUM 4");
		conv.put(101, "NUM 5");
		conv.put(102, "NUM 6");
		conv.put(103, "NUM 7");
		conv.put(104, "NUM 8");
		conv.put(105, "NUM 9");
		conv.put(106, "NUM *");
		conv.put(107, "NUM +");
		conv.put(109, "NUM -");
		conv.put(110, "NUM .");
		conv.put(111, "NUM /");
		conv.put(20, "CAPS LOCK");
		conv.put(8, "BACKSPACE");
		conv.put(32, "SPACE");
		conv.put(162, "L-CTRL");
		conv.put(163, "R-CTRL");
		conv.put(160, "L-SHIFT");
		conv.put(161, "R-SHIFT");
		conv.put(46, "DELETE");
		conv.put(36, "HOME");
		conv.put(164, "L-ALT");
		conv.put(165, "R-ALT");
		conv.put(9, "TAB");
		conv.put(188, ",");
		conv.put(192, "`");
	}
}
