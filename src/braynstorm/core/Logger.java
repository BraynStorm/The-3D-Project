package braynstorm.core;

import java.util.Calendar;

public class Logger {
	public static void log(String message) {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		System.out.println(Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE + ":" + Calendar.SECOND + ":" + Calendar.MILLISECOND + " - ["
							+ stack[1].getMethodName() + "] : " + message);

	}
}
