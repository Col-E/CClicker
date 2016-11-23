package me.coley.clicker;

public interface Togglable {

	/**
	 * Toggles the status of the togglable object, calls the onEnable or
	 * onDisable appropriately.
	 */
	default void toggle() {
		setStatus(!getStatus());
		if (getStatus()) {
			onEnable();
		} else {
			onDisable();
		}
	}

	/**
	 * Sets the status of the togglable object.
	 * 
	 * @param value
	 */
	void setStatus(boolean value);

	/**
	 * Returns the status of the togglable object.
	 * 
	 * @return
	 */
	boolean getStatus();

	/**
	 * Called when the toggle is activated.
	 */
	void onEnable();

	/**
	 * Called when the toggle is deactivated.
	 */
	void onDisable();
}
