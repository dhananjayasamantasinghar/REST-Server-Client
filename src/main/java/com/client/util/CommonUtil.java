package com.client.util;

public class CommonUtil {

	public static String stackTraceToString(Throwable e) {
		StringBuilder sb = new StringBuilder();
		sb.append(e.getMessage());
		sb.append("\n ========================================================================");
		if (e.getCause() != null)
			sb.append(e.getCause().getMessage()).append("\n");
		for (StackTraceElement element : e.getStackTrace()) {
			sb.append(element.toString());
			sb.append("\n");
		}
		sb.append("\n ========================================================================");
		return sb.toString();
	}
}
