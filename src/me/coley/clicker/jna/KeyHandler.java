package me.coley.clicker.jna;

import me.coley.clicker.Keybinds;
import me.coley.clicker.ui.MainGUI;
import me.coley.simplejna.hook.key.KeyEventReceiver;
import me.coley.simplejna.hook.key.KeyHookManager;

/**
 * Main key-input handler of the autoclicker.
 * 
 * @author Matt
 *
 */
public class KeyHandler extends KeyEventReceiver {
	private final MainGUI gui;

	public KeyHandler(KeyHookManager hookManager, MainGUI gui) {
		super(hookManager);
		this.gui = gui;
	}
	
	@Override
	public boolean onKeyUpdate(SystemState sys, PressState press, int time, int vkCode) {
		if (press == PressState.DOWN) {
			// on press
			if (vkCode == gui.keybinds.getKey(Keybinds.BIND_TOGGLE_RECORDING)) {
				gui.stats.toggle();
			} else if (vkCode == gui.keybinds.getKey(Keybinds.BIND_TOGGLE_CLICKER)) {
				gui.clicker.toggle();
			} else if (vkCode == gui.keybinds.getKey(Keybinds.BIND_TOGGLE_GUI)) {
				gui.toggleVisible();
			}
		}
		return false;
	}
}
