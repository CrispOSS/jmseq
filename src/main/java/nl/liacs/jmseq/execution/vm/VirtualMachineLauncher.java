/*
 * Created on Mar 11, 2010 - 4:04:36 PM
 */
package nl.liacs.jmseq.execution.vm;

import java.util.Map;

import com.sun.jdi.VirtualMachine;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public interface VirtualMachineLauncher {

	String DEFAULT_LAUNCHING_CONNECTOR_NAME = "com.sun.jdi.CommandLineLaunch";

	/**
	 * @param launcherName
	 * @param programArguments
	 * @param options
	 * @return
	 */
	VirtualMachine launchTargetVirtualMachine(String launcherName, String programArguments,
			Map<Object, Object> options);

}
