package me.coley.clicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Maps;

import me.coley.clicker.util.AbsoluteFile;
import me.coley.clicker.util.Saveable;
import me.coley.clicker.value.BooleanValue;
import me.coley.clicker.value.NumericValue;
import me.coley.clicker.value.ValueUser;

/**
 * Lazy value system. Could be more abstract/extensible but it does what it
 * needs to do.
 * 
 * @author Matt
 */
public class Values implements Saveable {
	// Constants - Value ID's
	public static int SET_DEV_DELAY = 3;
	public static int SET_AVG_DELAY = 2;
	public static int SET_MAX_DELAY = 1;
	public static int SET_MIN_DELAY = 0;
	public static int SET_WINDOW_TARGET = 10;
	// Files for saving/loading
	private static final File fileNumData = new AbsoluteFile("settings_num.txt");
	private static final File fileBoolData = new AbsoluteFile("settings_bool.txt");
	// Values
	private Map<Integer, NumericValue> intMap = Maps.newLinkedHashMap();
	private Map<Integer, BooleanValue> boolMap = Maps.newLinkedHashMap();
	// Values - Names's
	private Map<Integer, String> names = Maps.newHashMap();
	// Values - Attached components
	private Map<Integer, ValueUser> registered = Maps.newHashMap();

	/**
	 * Add a value of the given ID to a given value and assign it a name.
	 * 
	 * @param settingID
	 *            Setting identifier <i>(constant)</i>
	 * @param name
	 *            Settting name
	 * @param value
	 *            Setting intiial value
	 */
	public void addNumericValue(int settingID, String name, NumericValue value) {
		intMap.put(settingID, value);
		names.put(settingID, name);
	}

	/**
	 * Add a value of the given ID to a given value and assign it a name.
	 * 
	 * @param settingID
	 *            Setting identifier <i>(constant)</i>
	 * @param name
	 *            Settting name
	 * @param value
	 *            Setting intiial value
	 */
	public void addBooleanValue(int settingID, String name, BooleanValue value) {
		boolMap.put(settingID, value);
		names.put(settingID, name);
	}

	/**
	 * Associate a ValueUser and a setting by it's ID.
	 * 
	 * @param settingID
	 * @param updateable
	 */
	public void registerUser(int settingID, ValueUser updateable) {
		registered.put(settingID, updateable);
	}

	/**
	 * Update the setting linked to the ID with a given value.
	 * 
	 * @param settingID
	 * @param value
	 */
	public void updateNumeric(int settingID, int value) {
		NumericValue nvalue = intMap.get(settingID);
		nvalue.setValue(value);
		if (registered.containsKey(settingID)) {
			registered.get(settingID).onValueUpdated();
		}
	}

	/**
	 * Update the setting linked to the ID with a given value.
	 * 
	 * @param settingID
	 * @param value
	 */
	public void updateBoolean(int settingID, boolean value) {
		BooleanValue bvalue = boolMap.get(settingID);
		bvalue.setValue(value);
		if (registered.containsKey(settingID)) {
			registered.get(settingID).onValueUpdated();
		}
	}

	/**
	 * Retrieve the value of a setting given its ID.
	 * 
	 * @param settingID
	 * @return
	 */
	public NumericValue getNumericSetting(int settingID) {
		return intMap.get(settingID);
	}

	/**
	 * Retrieve the value of a setting given its ID.
	 * 
	 * @param settingID
	 * @return
	 */
	public BooleanValue getBooleanSetting(int settingID) {
		return boolMap.get(settingID);
	}

	/**
	 * Retrieve the map of numeric values.
	 * 
	 * @return
	 */
	public Map<Integer, NumericValue> getNumericValues() {
		return intMap;
	}

	/**
	 * Retrieve the name of the setting given its ID.
	 * 
	 * @param settingID
	 * @return
	 */
	public String getName(int settingID) {
		return names.get(settingID);
	}

	@Override
	public void load() {
		try {
			if (fileNumData.exists()) {
				List<String> linesN = FileUtils.readLines(fileNumData);
				for (String line : linesN) {
					if (line.startsWith("/"))
						continue;
					String[] data = line.split(":");
					// NAME : ID : VALUE
					int key = Integer.parseInt(data[1]);
					int value = Integer.parseInt(data[2]);
					intMap.get(key).setValue(value);
				}
			}
			if (fileNumData.exists()) {
				List<String> linesB = FileUtils.readLines(fileBoolData);
				for (String line : linesB) {
					if (line.startsWith("/"))
						continue;
					String[] data = line.split(":");
					// NAME : ID : VALUE
					int key = Integer.parseInt(data[1]);
					boolean value = Boolean.parseBoolean(data[2]);
					boolMap.get(key).setValue(value);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save() {
		try {
			if (!fileNumData.exists()) {
				fileNumData.createNewFile();
			}
			if (!fileBoolData.exists()) {
				fileBoolData.createNewFile();
			}

			List<String> linesN = new ArrayList<String>(intMap.size());
			for (int i : intMap.keySet()) {
				// NAME : ID : VALUE
				linesN.add(names.get(i) + ":" + i + ":" + intMap.get(i).getCurrent().intValue());
			}
			List<String> linesB = new ArrayList<String>(boolMap.size());
			for (int i : boolMap.keySet()) {
				// NAME : ID : VALUE
				linesB.add(names.get(i) + ":" + i + ":" + boolMap.get(i).getCurrent().booleanValue());
			}
			FileUtils.writeLines(fileNumData, linesN);
			FileUtils.writeLines(fileBoolData, linesB);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
