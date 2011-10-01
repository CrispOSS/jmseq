/*
 * Created on Sep 29, 2011 | 4:25:29 PM
 */
package nl.liacs.jmseq.execution;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.ExceptionEvent;

/**
 * 
 * @author Behrooz Nobakht
 */
public class ExceptionExecution extends SimpleExecution<ExceptionEvent> {

	public ExceptionExecution(ExceptionEvent event, String className, ObjectReference object, Long objectUniqueId) {
		super(event, className, object, objectUniqueId);
	}

	public ExceptionExecution(Execution<?> parent, ExceptionEvent event, String className, ObjectReference object,
			Long objectUniqueId) {
		super(parent, event, className, object, objectUniqueId);
	}

}
