package me.coley.clicker.value;

/**
 * Wrapper for a value.
 * 
 * @author Matt
 *
 * @param <T>
 *            Generic type
 */
public class Value<T> {
	/**
	 * Value.
	 */
	private T value;

	/**
	 * Create the wrapper and set the intial value.
	 * 
	 * @param value
	 */
	public Value(T value) {
		this.value = value;
	}

	/**
	 * Get the current value.
	 * 
	 * @return
	 */
	public T getCurrent() {
		return value;
	}

	/**
	 * Update the value.
	 * 
	 * @param value
	 */
	public void setValue(T value) {
		this.value = value;
	}
}
