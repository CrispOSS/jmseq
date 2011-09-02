/*
 * Created on Mar 11, 2010 - 11:24:34 AM
 */
package nl.liacs.jmseq.execution.event;

import nl.liacs.jmseq.execution.Execution;
import nl.liacs.jmseq.execution.ExecutionTraceOracle;

import com.sun.jdi.event.Event;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public interface EventHandler<E extends Event> {

	/**
	 * @param event
	 * @param executionTraceOracle
	 */
	Execution<?> handleEvent(E event, ExecutionTraceOracle executionTraceOracle);

}
