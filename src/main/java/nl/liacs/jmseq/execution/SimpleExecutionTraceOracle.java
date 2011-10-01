/*
 * Created on Mar 11, 2010 - 11:16:52 AM
 */
package nl.liacs.jmseq.execution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class SimpleExecutionTraceOracle implements ExecutionTraceOracle {

	private final Object mutex = new Object();

	private final List<Execution<?>> executions = new ArrayList<Execution<?>>();
	private final List<Execution<MethodEntryEvent>> methodEntryExecutions = new ArrayList<Execution<MethodEntryEvent>>();
	private List<Execution<MethodExitEvent>> methodExitExecutions = new ArrayList<Execution<MethodExitEvent>>();
	private List<Execution<ExceptionEvent>> exceptionExecutions = new ArrayList<Execution<ExceptionEvent>>();

	protected Map<Object, Object> vmOptions = new HashMap<Object, Object>();

	@Override
	public void setVirtualMachineOptions(Map<Object, Object> options) {
		this.vmOptions = options;
	}

	@Override
	public Map<Object, Object> getVirtualMachineOptions() {
		return this.vmOptions;
	}

	@Override
	public Execution getExecutionRoot() {
		if (null == executions || executions.isEmpty()) {
			return null;
		}
		return executions.get(0);
	}

	@Override
	public Execution getLastExecution() {
		if (null == executions || executions.isEmpty()) {
			return null;
		}
		return executions.get(executions.size() - 1);
	}

	public <E extends Event> void addExecution(E event, String className, ObjectReference object, Long objectUniqueId) {
		Class<E> eventType = (Class<E>) event.getClass();
		if (MethodEntryEvent.class.isAssignableFrom(eventType)) {
			addMethodEntryExecution((MethodEntryEvent) event, className, object, objectUniqueId);
		} else if (MethodExitEvent.class.isAssignableFrom(eventType)) {
			addMethodExitExecution((MethodExitEvent) event, className, object, objectUniqueId);
		}
	}

	@Override
	public <E extends Event> void addExecution(Execution<E> exec) {
		Class<E> eventType = exec.getExecutingEventType();
		if (MethodEntryEvent.class.equals(eventType)) {
			addMethodEntryExecution((Execution<MethodEntryEvent>) exec);
		} else if (MethodExitEvent.class.equals(eventType)) {
			addMethodExitExecution((Execution<MethodExitEvent>) exec);
		}
	}
	
	@Override
	public <E extends Event> ExceptionExecution addException(ExceptionEvent event, String className,
			ObjectReference object, Long oid) {
		ExceptionExecution ee = new ExceptionExecution(event, className, object, oid);
		exceptionExecutions.add(ee);
		return ee;
	}

	protected void addMethodExitExecution(Execution<MethodExitEvent> exec) {
		synchronized (mutex) {
			executions.add(exec);
			methodExitExecutions.add(exec);
		}
	}

	protected void addMethodExitExecution(MethodExitEvent event, String className, ObjectReference object,
			Long objectUniqueId) {
		synchronized (mutex) {
			MethodExitExecution exec = new MethodExitExecution(getLastExecution(), event, className, object,
					objectUniqueId);
			executions.add(exec);
			methodExitExecutions.add(exec);
		}
	}

	protected void addMethodEntryExecution(MethodEntryEvent event, String className, ObjectReference object,
			Long objectUniqueId) {
		synchronized (mutex) {
			MethodEntryExecution exec = new MethodEntryExecution(getLastExecution(), event, className, object,
					objectUniqueId);
			executions.add(exec);
			methodEntryExecutions.add(exec);
		}
	}

	protected void addMethodEntryExecution(Execution<MethodEntryEvent> exec) {
		synchronized (mutex) {
			executions.add(exec);
			methodEntryExecutions.add(exec);
		}
	}

}
