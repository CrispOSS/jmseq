/*
 * Created on Apr 20, 2010 - 8:57:47 AM
 */
package nl.liacs.jmseq.execution;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.sun.jdi.event.ExceptionEvent;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public abstract class ExecutionUtils {

	private static final Map<String, Class<?>> classes = new HashMap<>(4096);
	private static final Map<Class<?>, Map<String, Method>> methodMappings = new HashMap<>(8192);

	public static boolean isMethodEntryExecution(Execution<?> execution) {
		return execution instanceof MethodEntryExecution;
//		return MethodEntryEvent.class.isAssignableFrom(execution.getExecutingEventType());
	}

	public static boolean isMethodExitExecution(Execution<?> execution) {
		return execution instanceof MethodExitExecution;
//		return MethodExitEvent.class.isAssignableFrom(execution.getExecutingEventType());
	}

	public static boolean isMethodExceptionExecution(Execution<?> execution) {
		return execution instanceof ExceptionExecution;
//		return ExceptionEvent.class.isAssignableFrom(execution.getExecutingEventType());
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

	public static Class<?> loadClass(String className) {
		final Class<?> c = classes.get(className);
		if (c != null) {
			return c;
		}
		try {
			Class<?> clazz = Class.forName(className, false, ClassLoader.getSystemClassLoader());
			classes.put(className, clazz);
			return clazz;
		} catch (ClassNotFoundException e) {
		}
		return null;
	}

	public static Map<String, Method> getMethodMappings(Class<?> clazz) {
		final Map<String, Method> m = methodMappings.get(clazz);
		if (m != null) {
			return m;
		}
		HashMap<String, Method> map = new HashMap<String, Method>();
		methodMappings.put(clazz, map);
		return map;
	}

	private ExecutionUtils() {
	}

}
