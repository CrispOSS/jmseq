/*
 * Created on Apr 20, 2010 - 8:57:47 AM
 */
package nl.liacs.jmseq.execution;

import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public abstract class ExecutionUtils {

	public static boolean isMethodEntryExecution(Execution<?> execution) {
		return MethodEntryEvent.class.isAssignableFrom(execution.getExecutingEventType());
	}

	public static boolean isMethodExitExecution(Execution<?> execution) {
		return MethodExitEvent.class.isAssignableFrom(execution.getExecutingEventType());
	}

	public static boolean isMethodExceptionExecution(Execution<?> execution) {
		return ExceptionEvent.class.isAssignableFrom(execution.getExecutingEventType());
	}

	public static boolean isClassLoaderException(ExceptionExecution execution) {
		ExceptionEvent event = execution.getExecutingEvent();
		String location = event.location().method().toString();
		if (location.contains("java.net.URLClassLoader")) {
			String name = event.exception().type().name();
			if (name.equals("java.lang.ClassNotFoundException")) {
				return true;
			}
		}
		return false;
	}

	private ExecutionUtils() {
	}

}
