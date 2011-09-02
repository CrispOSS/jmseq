/*
 * Created on Mar 11, 2010 - 2:28:25 PM
 */
package nl.liacs.jmseq.execution.vm;

import nl.liacs.jmseq.execution.ExecutionTraceOracle;
import nl.liacs.jmseq.execution.event.EventHandler;

import com.sun.jdi.VirtualMachine;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public interface VirtualMachineEventProcessor extends Runnable, VirtualMachineOptionsAware {

	/**
	 * @return
	 */
	String getName();

	/**
	 * @param eventHandler
	 */
	void setEventHandler(EventHandler eventHandler);

	/**
	 * @param executionTraceOracle
	 */
	void setExecutionTraceOracle(ExecutionTraceOracle executionTraceOracle);

	/**
	 * @param vm
	 */
	void setVirtualMachine(VirtualMachine vm);

}
