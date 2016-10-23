package me.coley.clicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Maps;

import me.coley.clicker.util.Saveable;
import me.coley.clicker.value.Updatable;

public class Keybinds implements Saveable {
    private static final File fileBinds = new File("keybinds.txt");

    private static final Map<Integer, String> conv = Maps.newHashMap();
    private Map<Integer, Updatable> registered = Maps.newHashMap();
    private Map<Integer, Integer> keybinds = Maps.newHashMap();
    private Map<Integer, String> names = Maps.newHashMap();

    public static int BIND_TOGGLE_RECORDING = 100;
    public static int BIND_TOGGLE_CLICKER = 101;
    public static int BIND_TOGGLE_GUI = 102;

    public void setKeyValue(int settingID, String name, int value) {
        keybinds.put(settingID, value);
        names.put(settingID, name);
    }

    public void update(int settingID, int value) {
        if (keybinds.containsKey(settingID)) {
            keybinds.put(settingID, value);
            if (registered.containsKey(settingID)) {
                registered.get(settingID).update();
            }
        }
    }

    public void register(int settingID, Updatable updateable) {
        registered.put(settingID, updateable);
    }

    public Map<Integer, Integer> getKeybinds() {
        return keybinds;
    }

    public int getKey(int key) {
        return keybinds.get(key);
    }

    public String getName(int settingID) {
        return names.get(settingID);
    }

    public static String getKeyName(int vkCode) {
        if (!conv.containsKey(vkCode)) { return " ??? "; }
        return conv.get(vkCode);
    }

    @Override
    public void load() {
        try {
            if (fileBinds.exists()) {
                List<String> linesN = FileUtils.readLines(fileBinds);
                for (String line : linesN) {
                    if (line.startsWith("/")) continue;
                    String[] data = line.split(":");
                    // NAME : ID : VALUE
                    int key = Integer.parseInt(data[1]);
                    int value = Integer.parseInt(data[2]);
                    update(key, value);
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
        conv.put(48, "0");
        conv.put(189, "-");
        conv.put(187, "=");
        conv.put(219, "[");
        conv.put(221, "]");
        conv.put(186, ";");
        conv.put(222, "'");
        conv.put(191, "/");
        conv.put(220, "\\");
        conv.put(190, ".");
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
