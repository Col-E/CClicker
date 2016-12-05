package me.coley.clicker.jna;

import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;

import me.coley.clicker.Keybinds;
import me.coley.clicker.ui.MainGUI;
import me.coley.simplejna.hook.key.KeyEventReceiver;

/**
 * Main key-input handler of the autoclicker.
 * 
 * @author Matt
 *
 */
public class KeyHandler extends KeyEventReceiver {
	private final MainGUI gui;

	public KeyHandler(MainGUI gui) {
		this.gui = gui;
	}

	@Override
	public boolean onKeyPress(boolean sys, KBDLLHOOKSTRUCT info) {
		int i = info.vkCode;
		if (i == gui.keybinds.getKey(Keybinds.BIND_TOGGLE_RECORDING)) {
			gui.stats.toggle();
		} else if (i == gui.keybinds.getKey(Keybinds.BIND_TOGGLE_CLICKER)) {
			gui.clicker.toggle();
		} else if (i == gui.keybinds.getKey(Keybinds.BIND_TOGGLE_GUI)) {
			gui.toggleVisible();
		}
		return false;
	}

	@Override
	public boolean onKeyRelease(boolean sys, KBDLLHOOKSTRUCT info) {
		return false;
	}
}
