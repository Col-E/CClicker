package me.coley.clicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Maps;

import me.coley.clicker.util.Saveable;
import me.coley.clicker.value.BooleanValue;
import me.coley.clicker.value.NumericValue;
import me.coley.clicker.value.Updatable;
import me.coley.clicker.value.Value;

public class Values implements Saveable {
    private static final File fileNumData = new File("settings_num.txt");
    private static final File fileBoolData = new File("settings_bool.txt");
    private Map<Integer, NumericValue> valueMap = Maps.newLinkedHashMap();
    private Map<Integer, BooleanValue> boolMap = Maps.newLinkedHashMap();
    private Map<Integer, String> names = Maps.newHashMap();
    private Map<Integer, Updatable> registered = Maps.newHashMap();
    public static int SET_DEV_DELAY = 3;
    public static int SET_AVG_DELAY = 2;
    public static int SET_MAX_DELAY = 1;
    public static int SET_MIN_DELAY = 0;
    public static int SET_WINDOW_TARGET = 10;

    public void setNumericValue(int settingID, String name, NumericValue value) {
        valueMap.put(settingID, value);
        names.put(settingID, name);
    }

    public void setBooleanValue(int settingID, String name, BooleanValue value) {
        boolMap.put(settingID, value);
        names.put(settingID, name);
    }

    public void register(int settingID, Updatable updateable) {
        registered.put(settingID, updateable);
    }

    public void updateNumeric(int settingID, int value) {
        NumericValue nvalue = valueMap.get(settingID);
        nvalue.setValue(value);
        if (registered.containsKey(settingID)) {
            registered.get(settingID).update();
        }
    }

    public void updateBoolean(int settingID, boolean value) {
        BooleanValue bvalue = boolMap.get(settingID);
        bvalue.setValue(value);
        if (registered.containsKey(settingID)) {
            registered.get(settingID).update();
        }
    }

    public Value<?> getSetting(int settingID) {
        return valueMap.get(settingID);
    }

    public NumericValue getNumericSetting(int settingID) {
        return valueMap.get(settingID);
    }

    public BooleanValue getBooleanSetting(int settingID) {
        return boolMap.get(settingID);
    }

    public Map<Integer, NumericValue> getNumericValues() {
        return valueMap;
    }

    public String getName(int settingID) {
        return names.get(settingID);
    }

    @Override
    public void load() {
        try {
            if (fileNumData.exists()) {
                List<String> linesN = FileUtils.readLines(fileNumData);
                for (String line : linesN) {
                    if (line.startsWith("/")) continue;
                    String[] data = line.split(":");
                    // NAME : ID : VALUE
                    int key = Integer.parseInt(data[1]);
                    int value = Integer.parseInt(data[2]);
                    valueMap.get(key).setValue(value);
                }
            }
            if (fileNumData.exists()) {
                List<String> linesB = FileUtils.readLines(fileBoolData);
                for (String line : linesB) {
                    if (line.startsWith("/")) continue;
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

            List<String> linesN = new ArrayList<String>(valueMap.size());
            for (int i : valueMap.keySet()) {
                // NAME : ID : VALUE
                linesN.add(names.get(i) + ":" + i + ":" + valueMap.get(i).getCurrent().intValue());
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
