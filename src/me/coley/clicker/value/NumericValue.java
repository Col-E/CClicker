package me.coley.clicker.value;

/**
 * Wrapper for a numeric value.
 * 
 * @author Matt
 */
public class NumericValue extends Value<Integer> {
	private final int min, max;

	/**
	 * Create the wrapper with a given initial value. Minimum and maximum values
	 * assume value will be >= 1.
	 * 
	 * @param value
	 */
	public NumericValue(int value) {
		this(value, -1, 9999999);
	}

	/**
	 * Create the wrapper with a given initial, minimum, and maximum values.
	 * 
	 * @param value
	 */
	public NumericValue(int value, int min, int max) {
		super(value);
		this.min = min;
		this.max = max;
	}

	@Override
	public void setValue(Integer value) {
		if (value > max) {
			value = max;
		} else if (value < min) {
			value = min;
		}
		super.setValue(value);
	}

	/**
	 * Minimum bound for allowed values.
	 * 
	 * @return
	 */
	public int getMin() {
		return min;
	}

	/**
	 * Maximum bound for allowed values.
	 * 
	 * @return
	 */
	public int getMax() {
		return max;
	}
}
