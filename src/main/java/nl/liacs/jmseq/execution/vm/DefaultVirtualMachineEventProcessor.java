/*
 * Created on Mar 11, 2010 - 2:44:05 PM
 */
package nl.liacs.jmseq.execution.vm;

import java.util.Map;

import nl.liacs.jmseq.annotation.AnnotatedSequentialExecutionMetaDataLocator;
import nl.liacs.jmseq.annotation.SequentialExecuationMetaDataLoader;
import nl.liacs.jmseq.annotation.SequentialExecutionMetaData;
import nl.liacs.jmseq.execution.Execution;
import nl.liacs.jmseq.execution.ExecutionTraceOracle;
import nl.liacs.jmseq.execution.SimpleExecutionTraceOracle;
import nl.liacs.jmseq.execution.event.DefaultEventHandler;
import nl.liacs.jmseq.execution.event.EventHandler;
import nl.liacs.jmseq.verify.ExecutionVerifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventIterator;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.VMDeathEvent;
import com.sun.jdi.event.VMDisconnectEvent;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.ExceptionRequest;
import com.sun.jdi.request.MethodEntryRequest;
import com.sun.jdi.request.MethodExitRequest;
import com.sun.jdi.request.ThreadDeathRequest;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class DefaultVirtualMachineEventProcessor implements
		VirtualMachineEventProcessor, EventHandler {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private EventHandler eventHandler;
	private ExecutionTraceOracle executionTraceOracle;
	private SequentialExecuationMetaDataLoader sequentialExecuationMetaDataLoader;

	private VirtualMachine virtualMachine;
	private boolean connectedToVirtualMachine = true;
	private boolean virtualMachineDied = true;

	private Map<Object, Object> options;
	private String name = "Default Virtual Machine Event Processor";

	// Verification
	private SequentialExecutionMetaData sequentialExecutionMetaData;
	private ExecutionVerifier executionVerifier;

	public DefaultVirtualMachineEventProcessor() {
		eventHandler = new DefaultEventHandler();
		executionTraceOracle = new SimpleExecutionTraceOracle();
		sequentialExecuationMetaDataLoader = new AnnotatedSequentialExecutionMetaDataLocator();
	}

	public DefaultVirtualMachineEventProcessor(Map<Object, Object> options) {
		this();
		this.options = options;
		executionTraceOracle.setVirtualMachineOptions(options);
	}

	public DefaultVirtualMachineEventProcessor(String name,
			Map<Object, Object> options) {
		this(options);
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void run() {
		checkConfig();
		EventQueue eventQueue = this.virtualMachine.eventQueue();
		for (; connectedToVirtualMachine;) {
			try {
				EventSet eventSet = eventQueue.remove();
				EventIterator eventIterator = eventSet.eventIterator();
				for (; eventIterator.hasNext();) {
					Event event = eventIterator.next();
					handleEvent(event, executionTraceOracle);
				}
				eventSet.resume();
			} catch (InterruptedException e) {
				// interruption is ignored
			} catch (VMDisconnectedException e) {
				handleVMDisconnection();
				break;
			}
		}
	}

	@Override
	public Execution<?> handleEvent(Event event,
			ExecutionTraceOracle executionTraceOracle) {
		if (event instanceof VMDeathEvent) {
			handleVMDeathEvent((VMDeathEvent) event);
		} else if (event instanceof VMDisconnectEvent) {
			handleVMDisconnectEvent((VMDisconnectEvent) event);
		} else {
			Execution execution = this.eventHandler.handleEvent(event,
					executionTraceOracle);
			if (execution != null) {
				logger.debug("Received event: {}", event);
				executionVerifier.verfiyExecution(execution);
			}
		}
		return null;
	}

	/**
	 * @param event
	 */
	protected void handleVMDisconnectEvent(VMDisconnectEvent event) {
		connectedToVirtualMachine = false;
	}

	/**
	 * @param event
	 */
	protected void handleVMDeathEvent(VMDeathEvent event) {
		virtualMachineDied = true;
	}

	/**
	 * 
	 */
	protected synchronized void handleVMDisconnection() {
		EventQueue eventQueue = virtualMachine.eventQueue();
		for (; connectedToVirtualMachine;) {
			try {
				EventSet eventSet = eventQueue.remove();
				EventIterator eventIterator = eventSet.eventIterator();
				for (; eventIterator.hasNext();) {
					Event event = eventIterator.next();
					handleEvent(event, executionTraceOracle);
				}
				eventSet.resume();
			} catch (InterruptedException e) {
				// interruption is ignored
			}
		}
	}

	/**
	 * 
	 */
	protected void checkConfig() {
		if (null == virtualMachine) {
			throw new Error("An instance of Virtual Machine is required.");
		}
		if (null == eventHandler) {
			throw new Error("eventHandler is required.");
		}
		if (null == executionTraceOracle) {
			throw new Error("executionTraceOracle is required.");
		}
		String basePackage = this.options.get(
				VirtualMachineOption.TargetPackageBase).toString();
		sequentialExecutionMetaData = sequentialExecuationMetaDataLoader
				.loadMetaData(basePackage);
		executionVerifier = new ExecutionVerifier(sequentialExecutionMetaData);
	}

	@Override
	public void setEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	@Override
	public void setExecutionTraceOracle(
			ExecutionTraceOracle executionTraceOracle) {
		this.executionTraceOracle = executionTraceOracle;
	}

	@Override
	public void setVirtualMachine(VirtualMachine vm) {
		this.virtualMachine = vm;
	}

	@Override
	public void setVirtualMachineOptions(Map<Object, Object> options) {
		this.options = options;
		this.executionTraceOracle.setVirtualMachineOptions(options);
		processOptions(this.options, this.virtualMachine);
	}

	@Override
	public Map<Object, Object> getVirtualMachineOptions() {
		return this.options;
	}

	/**
	 * @param options
	 * @param vm
	 */
	protected void processOptions(Map<Object, Object> options, VirtualMachine vm) {
		setExceptionEventOptions(options, vm);
		setMethodEventOptions(options, vm);
		setClassPrepareEventOptions(options, vm);
		setThreadDeathEventOptions(options, vm);
	}

	/**
	 * @param options
	 * @param vm
	 */
	protected void setThreadDeathEventOptions(Map<Object, Object> options,
			VirtualMachine vm) {
		if (options.containsKey(VirtualMachineOption.ThreadDeathEventSuspend)) {
			Boolean value = (Boolean) options
					.get(VirtualMachineOption.ThreadDeathEventSuspend);
			ThreadDeathRequest tdr = vm.eventRequestManager()
					.createThreadDeathRequest();
			tdr.setSuspendPolicy(value ? EventRequest.SUSPEND_ALL
					: EventRequest.SUSPEND_NONE);
			tdr.enable();
		}
	}

	/**
	 * @param options
	 * @param vm
	 */
	protected void setClassPrepareEventOptions(Map<Object, Object> options,
			VirtualMachine vm) {
		if (options.containsKey(VirtualMachineOption.ClassPrepareEventSuspend)) {
			Boolean value = (Boolean) options
					.get(VirtualMachineOption.ClassPrepareEventSuspend);
			ClassPrepareRequest cpr = vm.eventRequestManager()
					.createClassPrepareRequest();
			cpr.setSuspendPolicy(value ? EventRequest.SUSPEND_ALL
					: EventRequest.SUSPEND_NONE);
			cpr.enable();
		}
	}

	/**
	 * @param options
	 * @param vm
	 */
	protected void setMethodEventOptions(Map<Object, Object> options,
			VirtualMachine vm) {
		MethodEntryRequest mer = vm.eventRequestManager()
				.createMethodEntryRequest();
		MethodExitRequest mexr = vm.eventRequestManager()
				.createMethodExitRequest();
		if (options.containsKey(VirtualMachineOption.Excludes)) {
			Object ex = options.get(VirtualMachineOption.Excludes);
			String[] excludes = null;
			if (ex instanceof String) {
				excludes = ((String) ex).split(",");
			} else if (ex instanceof String[]) {
				excludes = (String[]) ex;
			}
			if (null == excludes) {
				return;
			}
			for (int i = 0; i < excludes.length; ++i) {
				mer.addClassExclusionFilter(excludes[i]);
				mexr.addClassExclusionFilter(excludes[i]);
			}
		}
		if (options.containsKey(VirtualMachineOption.MethodEntryEventSuspend)) {
			Boolean value = (Boolean) options
					.get(VirtualMachineOption.MethodEntryEventSuspend);
			mer.setSuspendPolicy(value ? EventRequest.SUSPEND_EVENT_THREAD
					: EventRequest.SUSPEND_NONE);
			mer.enable();
		}
		if (options.containsKey(VirtualMachineOption.MethodExitEventSuspend)) {
			Boolean value = (Boolean) options
					.get(VirtualMachineOption.MethodExitEventSuspend);
			mexr.setSuspendPolicy(value ? EventRequest.SUSPEND_EVENT_THREAD
					: EventRequest.SUSPEND_NONE);
			mexr.enable();
		}
	}

	/**
	 * @param options
	 * @param vm
	 */
	protected void setExceptionEventOptions(Map<Object, Object> options,
			VirtualMachine vm) {
		if (options.containsKey(VirtualMachineOption.ExceptionEventSuspend)) {
			Boolean value = (Boolean) options
					.get(VirtualMachineOption.ExceptionEventSuspend);
			ExceptionRequest er = vm.eventRequestManager()
					.createExceptionRequest(null, true, true);
			er.setSuspendPolicy(value ? EventRequest.SUSPEND_ALL
					: EventRequest.SUSPEND_NONE);
			er.enable();
		}
	}

}
