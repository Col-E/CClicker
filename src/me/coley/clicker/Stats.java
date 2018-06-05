package me.coley.clicker;

import java.util.logging.Level;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import me.coley.clicker.jna.StatRecorder;
import me.coley.clicker.ui.MainGUI;
import me.coley.simplejna.hook.mouse.MouseEventReceiver;

/**
 * Handler for the recording mouse inputs.
 * 
 * @author Matt
 *
 */
public class Stats implements Togglable {
	private final MainGUI gui;
	private boolean status;
	private DescriptiveStatistics frequency;
	private MouseEventReceiver mouseHook;
	
	public Stats(MainGUI gui){
		this.gui = gui;
	}

	@Override
	public void onEnable() {
		// Start new stats
		MainGUI.log.log(Level.INFO, "Beginning recording of mouse input.");
		frequency = new DescriptiveStatistics();
		MainGUI.log.log(Level.INFO, "Creating keybind-listener...");
		mouseHook = new StatRecorder(gui.mouseHooks, this, gui.graph);
		gui.mouseHooks.hook(mouseHook);
	}

	@Override
	public void onDisable() {
		// Finished, save stats
		MainGUI.log.log(Level.INFO, "Finished recording.");
		if (mouseHook != null) {
			gui.mouseHooks.unhook(mouseHook);
		}
		gui.onRecordingFinished();
	}

	/**
	 * Retrieve the statistics about click frequency. Measured in milliseconds.
	 * 
	 * @return
	 */
	public DescriptiveStatistics getFrequencyData() {
		return frequency;
	}

	@Override
	public void setStatus(boolean value) {
		this.status = value;
	}

	@Override
	public boolean getStatus() {
		return status;
	}
}
