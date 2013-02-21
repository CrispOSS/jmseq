/*
 * Created on Mar 11, 2010 - 12:07:15 PM
 */
package nl.liacs.jmseq.execution;

import java.util.Map;

import com.sun.jdi.Method;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class SimpleExecution<E extends Event> implements Execution<E> {
	

	private E event;
	private String className;
	private ObjectReference object;
	private Long objectUniqueId;
	private Execution<?> parent;

	private String simpleClassName;
	private String methodName;
	private Class<?> clazz;
	private java.lang.reflect.Method theMethod;

	public SimpleExecution(E event, String className, ObjectReference object, Long objectUniqueId) {
		this(null, event, className, object, objectUniqueId);
	}

	public SimpleExecution(Execution<?> parent, E event, String className, ObjectReference object, Long objectUniqueId) {
		this.event = event;
		this.className = className;
		this.object = object;
		this.objectUniqueId = objectUniqueId;
		this.parent = parent;
		if (MethodEntryEvent.class.isAssignableFrom(getExecutingEventType())) {
			methodName = ((MethodEntryEvent) event).method().name();
		} else if (MethodExitEvent.class.isAssignableFrom(getExecutingEventType())) {
			methodName = ((MethodExitEvent) event).method().name();
		}
		clazz = ExecutionUtils.loadClass(className);
		this.simpleClassName = this.className.substring(this.className.lastIndexOf('.') + 1);
	}

	public SimpleExecution(Execution parent, E event) {
		this.event = event;
		this.parent = parent;
		parseEvent(event);
		// this.simpleClassName =
		// this.className.substring(this.className.lastIndexOf('.') + 1);
		this.clazz = ExecutionUtils.loadClass(className);
	}

	private void parseEvent(E event) {
//		if (MethodEntryEvent.class.isAssignableFrom(getExecutingEventType())) {
		if (event instanceof MethodEntryEvent) {
			MethodEntryEvent e = (MethodEntryEvent) event;
			Method method = e.method();
			this.className = method.declaringType().name();
			methodName = method.name();
		} else if (event instanceof MethodExitEvent) {
//		} else if (MethodExitEvent.class.isAssignableFrom(getExecutingEventType())) {
			Method method = ((MethodExitEvent) event).method();
			this.className = method.declaringType().name();
			methodName = method.name();
		}
	}

	@Override
	public java.lang.reflect.Method findMethod() {
		if (theMethod != null) {
			return theMethod;
		}
		Class<?> clazz = getExecutingClass();
		String methodName = getExecutingMethodName();
		Map<String, java.lang.reflect.Method> map = ExecutionUtils.getMethodMappings(clazz);
		theMethod = map.get(methodName);
		if (theMethod != null) {
			return theMethod;
		}
		java.lang.reflect.Method[] methods = clazz.getMethods();
		for (java.lang.reflect.Method method : methods) {
			if (method.getName().equals(methodName)) {
				theMethod = method;
				map.put(methodName, method);
				break;
			}
		}
		return theMethod;
	}

	@Override
	public E getExecutingEvent() {
		return event;
	}

	@Override
	public Class getExecutingEventType() {
		return event.getClass();
	}

	@Override
	public ObjectReference getExecutingObject() {
		return object;
	}

	@Override
	public long getExecutingObjectUniqueId() {
		return objectUniqueId;
	}

	@Override
	public String getExecutingClassType() {
		return className;
	}

	@Override
	public Class<?> getExecutingClass() {
		return clazz;
	}

	@Override
	public String getExecutingClassTypeSimpleName() {
		return this.simpleClassName;
	}

	@Override
	public String getExecutingMethodName() {
		return methodName;
	}

	@Override
	public Execution<?> getParentExecution() {
		return this.parent;
	}

	@Override
	public String getSignature() {
		return event.toString();
	}

}
