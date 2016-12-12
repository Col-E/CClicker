package me.coley.clicker.util;

public class Version {
	private static final int MAJOR = 1, MINOR = 3;

	/**
	 * Returns the current version as a string.
	 * 
	 * @return
	 */
	public static String getCurrentVersion() {
		return MAJOR + "." + MINOR;
	}

	/**
	 * Compares the current version to a given one.
	 * <ul>
	 * <li>-1 means we are newer.
	 * <li>+1 means we are outdated.
	 * <li>0 means we are up-to-date.
	 * </ul>
	 * 
	 * @param version
	 * @return
	 */
	public static int compare(String version) {
		String[] split = version.split(".");
		int major = Integer.parseInt(split[0]);
		if (MAJOR > major) {
			return -1;
		} else if (MAJOR == major) {
			int minor = Integer.parseInt(split[1]);
			if (MINOR > minor) {
				return -1;
			} else if (MINOR == minor) {
				return 0;
			} else {
				return 1;
			}
		} else {
			return 1;
		}
	}
}
