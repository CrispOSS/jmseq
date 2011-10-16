/*
 * Created on Apr 13, 2010 - 12:37:09 PM
 */
package nl.liacs.jmseq.execution;

import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.MethodExitEvent;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class MethodExitExecution extends SimpleExecution<MethodExitEvent> {

	private ObjectReference callerObject;

	public MethodExitExecution(Execution<?> parent, MethodExitEvent event, String className, ObjectReference object,
			long objectUniqueId) {
		super(parent, event, className, object, objectUniqueId);
		this.callerObject = null;
//		try {
//			this.callerObject = event.thread().frame(1).thisObject();
//		} catch (IncompatibleThreadStateException e) {
//			throw new RuntimeException(e);
//		}
	}

	public MethodExitExecution(MethodExitEvent event, String className, ObjectReference object, long objectUniqueId) {
		this(null, event, className, object, objectUniqueId);
	}

	public MethodExitExecution(Execution parent, MethodExitEvent event) {
		super(parent, event);
	}

	public ObjectReference getExecutingCallerObject() {
		return callerObject;
	}
	
	@Override
	public String toString() {
		return "Method Exit: " + getExecutingClassType() + "." + getExecutingMethodName();
	}

}
