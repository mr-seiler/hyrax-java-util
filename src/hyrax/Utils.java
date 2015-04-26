package hyrax;

import java.util.List;

public class Utils {
	
	/**
	 * Check if a string is null before checking if it is empty.
	 * @param s string to check
	 * @return true if the string is null or empty
	 */
	public boolean isNullorEmpty(String s) {
		return (s == null || s.isEmpty());
	}
	
	/**
	 * Repeat string `s` `x` times
	 * @param s string to repeat
	 * @param x number of times to repeat
	 * @return string built by repeating `s`
	 */
	public static String stringTimes(String s, int x) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < x; i += 1) {
			sb.append(s);
		}
		return sb.toString();
	}
	
	/**
	 * Produce a single string from the elements in a list, with no separator between elements.
	 * @param list list of things to combine into a string
	 * @return one big string
	 */
	public static <T> String implode(List<T> list) {
		return implode(list, "");
	}
	
	/**
	 * Produce a single string from the elements in a list.
	 * Concatenates the toString() of all the list elements, separated by the given delimiter.
	 * @param list of things to combine into the string
	 * @param delim separator for elements
	 * @return one big string
	 */
	public static <T> String implode(List<T> list, String delim) {
		StringBuilder sb = new StringBuilder();
		int count = list.size();
		for (int i = 0; i < count; i += 1) {
			sb.append(list.get(i).toString());
			if (i < count - 1) {
				sb.append(delim);
			}
		}
		return sb.toString();
	}
	
	/**
	 * Check integer range before casting a long to an int.  Will throw an
	 * IllegalArgumentException if the inetegr is out of range.
	 * @param l long value
	 * @return l cast to int
	 */
	public static int safeLongToInt(long l) {
		if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(String.format("Long %d cannot be cast to int w/out changing its value.", l));
		}
		return (int) l;
	}
	
	public static <T extends Number & Comparable<T>> T clamp(T value, T min, T max) {
		if (value.compareTo(min) < 0) {
			return min;
		}
		else if (value.compareTo(max) > 0) {
			return max;
		}
		else {
			return value;
		}
	}
	
	/**
	 * Try to convert a string to an int.  Returns the specified default value if unsuccessful.
	 * @param s string to parse
	 * @param def default to return if parse fails
	 * @return integer value parsed from string, or default
	 */
	public static int tryParseInt(String s, int def) {
		int r;
		try {
			r = Integer.parseInt(s);
		}
		catch (NumberFormatException ex) {
			r = def;
		}
		return r;
	}
	
	/**
	 * Try to convert a string to an int.  Returns -1 if unsuccessful.
	 * @param s string to parse
	 * @return integer value parsed from the string, or -1
	 */
	public static int tryParseInt(String s) {
		return Utils.tryParseInt(s, -1);
	}
	
	/**
	 * Get the file extension from a file path string. Returns the empty string if there
	 * doesn't appear to be an extension.  If a file has more than one extension, this only
	 * returns the last one.  The returned extension does not include the "." prefix.
	 * @param filePath string to get file extension from
	 * @return the file extension from the path or ""
	 */
	public static String getFileExt(String filePath) {
		int dotAt = filePath.lastIndexOf(".");
		if (dotAt != -1) {
			return filePath.substring(dotAt + 1, filePath.length());
		}
		else {
			return "";
		}
	}
}
