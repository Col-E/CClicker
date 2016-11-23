package me.coley.clicker.util;

/**
 * Assorted utilities dealing with numbers.
 * 
 * @author Matt
 */
public class NumberUtil {

	/**
	 * Clamps a double value.
	 * 
	 * @param val
	 * @param min
	 * @param max
	 * @return
	 */
	public static double clamp(double val, double min, double max) {
		// Java neess to implement a clamp function in java.util.math FFS.
		return Math.max(min, Math.min(max, val));
	}

	/**
	 * Formats a decimal number.
	 * 
	 * @param d
	 * @param le
	 * @return
	 */
	public static String fmtNum(double d, int le) {
		String s = ("" + d);
		if (s.length() > le) {
			s = s.substring(0, le);
		}
		return s;
	}

}
