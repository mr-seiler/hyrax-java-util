package hyrax;

import java.util.List;

public class Utils {
	
	public static String stringTimes(String s, int x) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < x; i += 1) {
			sb.append(s);
		}
		return sb.toString();
	}
	
	public static <T> String implode(List<T> list) {
		return implode(list, "");
	}
	
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
	
}
