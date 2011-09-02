/*
 * Created on Apr 13, 2010 - 12:36:02 PM
 */
package nl.liacs.jmseq.execution;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.MethodEntryEvent;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class MethodEntryExecution extends SimpleExecution<MethodEntryEvent> {

	public MethodEntryExecution(Execution<?> parent, MethodEntryEvent event, String className, ObjectReference object,
			Long objectUniqueId) {
		super(parent, event, className, object, objectUniqueId);
	}

	public MethodEntryExecution(MethodEntryEvent event, String className, ObjectReference object, Long objectUniqueId) {
		super(event, className, object, objectUniqueId);
	}

}
