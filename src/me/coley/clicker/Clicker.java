package me.coley.clicker;

import java.util.logging.Level;

import me.coley.clicker.jna.ClickerThread;
import me.coley.clicker.ui.MainGUI;

/**
 * Handler for the clicking process.
 * 
 * @author Matt
 *
 */
public class Clicker implements Togglable {
	private final MainGUI gui;
	private boolean status;
	private String target;

	public Clicker(MainGUI gui) {
		this.gui = gui;
	}

	@Override
	public void onEnable() {
		MainGUI.log.log(Level.INFO, "Launching clicker thread...");
		new ClickerThread(gui).start();
	}

	@Override
	public void onDisable() {
		MainGUI.log.log(Level.INFO, "Stopping clicker thread...");
	}

	/**
	 * Sets the clicker's target window.
	 * 
	 * @param target
	 *            window title
	 */
	public void setTargetWindow(String target) {
		this.target = target;
	}

	/**
	 * Returns the title of the target window.
	 * 
	 * @return window title
	 */
	public String getTarget() {
		return target;
	}

	@Override
	public boolean getStatus() {
		return status;
	}

	@Override
	public void setStatus(boolean value) {
		this.status = value;
	}
}
