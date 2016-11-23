package me.coley.clicker.util;

/**
 * Object that can be saved and loaded.
 * 
 * @author Matt
 *
 */
public interface Saveable {

	/**
	 * Loads data.
	 */
	public void load();

	/**
	 * Stores data.
	 */
	public void save();
}
