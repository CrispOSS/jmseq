/*
 * Created on Apr 13, 2010 - 11:47:23 AM
 */
package nl.liacs.jmseq.execution.vm;

import java.util.Map;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public interface VirtualMachineOptionsAware {

	/**
	 * @param options
	 */
	void setVirtualMachineOptions(Map<Object, Object> options);

	/**
	 * @return
	 */
	Map<Object, Object> getVirtualMachineOptions();

}
