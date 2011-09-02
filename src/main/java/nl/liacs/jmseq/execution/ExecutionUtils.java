/*
 * Created on Apr 20, 2010 - 8:57:47 AM
 */
package nl.liacs.jmseq.execution;

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

	private ExecutionUtils() {
	}

}
