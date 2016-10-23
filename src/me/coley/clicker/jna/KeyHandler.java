package me.coley.clicker.jna;

import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;

import me.coley.clicker.Keybinds;
import me.coley.clicker.ui.BotGUI;
import me.coley.jnathread.hook.key.KeyEventReceiver;

/**
 * Main key-input handler of the autoclicker.
 * 
 * @author Matt
 *
 */
public class KeyHandler extends KeyEventReceiver {

    @Override
    public boolean onKeyPress(boolean sys, KBDLLHOOKSTRUCT info) {
        int i = info.vkCode;
        if (i == BotGUI.keybinds.getKey(Keybinds.BIND_TOGGLE_RECORDING)) {
            BotGUI.stats.toggle();
        } else if (i == BotGUI.keybinds.getKey(Keybinds.BIND_TOGGLE_CLICKER)) {
            BotGUI.clicker.toggle();
        } else if (i == BotGUI.keybinds.getKey(Keybinds.BIND_TOGGLE_GUI)) {
            BotGUI.toggleVisible();
        }
        return false;
    }

    @Override
    public boolean onKeyRelease(boolean sys, KBDLLHOOKSTRUCT info) {
        return false;
    }
}
