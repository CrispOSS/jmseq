/*
 * Created on Mar 11, 2010 - 11:16:36 AM
 */
package nl.liacs.jmseq.execution;

import nl.liacs.jmseq.execution.vm.VirtualMachineOptionsAware;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.ExceptionEvent;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public interface ExecutionTraceOracle extends VirtualMachineOptionsAware {

	/**
	 * @return
	 */
	Execution getExecutionRoot();

	/**
	 * @return
	 */
	Execution getLastExecution();

	/**
	 * @param <E>
	 * @param exec
	 */
	<E extends Event> void addExecution(Execution<E> exec);

	/**
	 * @param <E>
	 * @param event
	 * @param className
	 * @param object
	 * @param objectUniqueId
	 */
	<E extends Event> void addExecution(E event, String className, ObjectReference object, Long objectUniqueId);

	<E extends Event> ExceptionExecution addException(ExceptionEvent event, String className, ObjectReference object, Long oid);

}
