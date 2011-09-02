/*
 * Created on Mar 11, 2010 - 4:35:39 PM
 */
package nl.liacs.jmseq.execution;

import java.util.HashMap;
import java.util.Map;

import nl.liacs.jmseq.execution.vm.DefaultVirtualMachineEventProcessor;
import nl.liacs.jmseq.execution.vm.DefaultVirtualMachineLauncher;
import nl.liacs.jmseq.execution.vm.VirtualMachineEventProcessor;
import nl.liacs.jmseq.execution.vm.VirtualMachineLauncher;

import com.sun.jdi.VirtualMachine;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class DefaultProgramExecutionTracer implements ProgramExecutionTracer {

	private VirtualMachineLauncher virtualMachineLauncher = new DefaultVirtualMachineLauncher();

	public void trace(Class<?> clazz, String programArguments, Map<Object, Object> options) {
		String className = clazz.getName();
		trace(className, programArguments, options);
	}

	public void trace(String className, String programArguments, Map<Object, Object> options) {
		if (null == programArguments) {
			programArguments = "";
		}
		if (null == options) {
			options = new HashMap<Object, Object>();
		}
		programArguments = " " + className + " " + programArguments;
		VirtualMachine virtualMachine = virtualMachineLauncher.launchTargetVirtualMachine(
				VirtualMachineLauncher.DEFAULT_LAUNCHING_CONNECTOR_NAME,
				programArguments, options);
		traceInternal(virtualMachine, programArguments, options);
	}

	/**
	 * @param vm
	 * @param programArguments
	 * @param options
	 */
	protected void traceInternal(VirtualMachine vm, String programArguments, Map<Object, Object> options) {
		VirtualMachineEventProcessor vmep = createVirtualMachineEventProcessor(vm, options);
		Thread tracerThread = createExecutionTracerThread(vmep);
		tracerThread.start();
		vm.resume();
		// Shutdown begins when event thread terminates
		try {
			tracerThread.join();
		} catch (InterruptedException exc) {
			// we don't interrupt
		}
	}

	/**
	 * @param vmep
	 * @return
	 */
	protected Thread createExecutionTracerThread(VirtualMachineEventProcessor vmep) {
		Thread tracerThread = new Thread(vmep, vmep.getName());
		return tracerThread;
	}

	/**
	 * @param vm
	 * @param options
	 * @return
	 */
	protected VirtualMachineEventProcessor createVirtualMachineEventProcessor(VirtualMachine vm,
			Map<Object, Object> options) {
		VirtualMachineEventProcessor vmep = new DefaultVirtualMachineEventProcessor();
		vmep.setVirtualMachine(vm);
		vmep.setVirtualMachineOptions(options);
		return vmep;
	}

	public void setVirtualMachineLauncher(VirtualMachineLauncher virtualMachineLauncher) {
		this.virtualMachineLauncher = virtualMachineLauncher;
	}

}
