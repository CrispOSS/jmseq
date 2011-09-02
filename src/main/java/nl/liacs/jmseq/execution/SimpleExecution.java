/*
 * Created on Mar 11, 2010 - 12:07:15 PM
 */
package nl.liacs.jmseq.execution;

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

	private final E event;
	private final String className;
	private final ObjectReference object;
	private final Long objectUniqueId;
	private final Execution<?> parent;

	private final String simpleClassName;
	private String methodName;

	public SimpleExecution(E event, String className, ObjectReference object, Long objectUniqueId) {
		this(null, event, className, object, objectUniqueId);
	}

	public SimpleExecution(Execution<?> parent, E event, String className, ObjectReference object, Long objectUniqueId) {
		this.event = event;
		this.className = className;
		this.object = object;
		this.objectUniqueId = objectUniqueId;
		this.parent = parent;
		this.simpleClassName = this.className.substring(this.className.lastIndexOf('.') + 1);
		if (MethodEntryEvent.class.isAssignableFrom(getExecutingEventType())) {
			methodName = ((MethodEntryEvent) event).method().name();
		} else if (MethodExitEvent.class.isAssignableFrom(getExecutingEventType())) {
			methodName = ((MethodExitEvent) event).method().name();
		}
	}

	@Override
	public E getExecutingEvent() {
		return event;
	}

	@Override
	public Class<E> getExecutingEventType() {
		return (Class<E>) event.getClass();
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
