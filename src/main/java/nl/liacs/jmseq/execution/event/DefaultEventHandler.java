/*
 * Created on Mar 11, 2010 - 11:29:14 AM
 */
package nl.liacs.jmseq.execution.event;

import java.util.Map;

import nl.liacs.jmseq.execution.ExceptionExecution;
import nl.liacs.jmseq.execution.Execution;
import nl.liacs.jmseq.execution.ExecutionTraceOracle;
import nl.liacs.jmseq.execution.vm.VirtualMachineOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.InternalException;
import com.sun.jdi.Method;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.StackFrame;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.ExceptionEvent;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.ThreadDeathEvent;
import com.sun.jdi.event.VMDeathEvent;
import com.sun.jdi.event.VMDisconnectEvent;
import com.sun.jdi.event.VMStartEvent;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class DefaultEventHandler implements EventHandler {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private ExceptionEventHandler exceptionEventHandler = new ExceptionEventHandler();
	private MethodEntryEventHandler methodEntryEventHandler = new MethodEntryEventHandler();
	private MethodExitEventHandler methodExitEventHandler = new MethodExitEventHandler();
	private ThreadDeathEventHandler threadDeathEventHandler = new ThreadDeathEventHandler();
	private ClassPrepareEventHandler classPrepareEventHandler = new ClassPrepareEventHandler();
	private VMStartEventHandler vmStartEventHandler = new VMStartEventHandler();
	private VMDeathEventHandler vmDeathEventHandler = new VMDeathEventHandler();
	private VMDisconnectEventHandler vmDisconnectEventHandler = new VMDisconnectEventHandler();

	@Override
	public Execution<?> handleEvent(Event event, ExecutionTraceOracle executionTraceOracle) {
		if (event instanceof ExceptionEvent) {
			return exceptionEventHandler.handleEvent((ExceptionEvent) event, executionTraceOracle);
		} else if (event instanceof MethodEntryEvent) {
			return methodEntryEventHandler.handleEvent((MethodEntryEvent) event, executionTraceOracle);
		} else if (event instanceof MethodExitEvent) {
			return methodExitEventHandler.handleEvent((MethodExitEvent) event, executionTraceOracle);
		} else if (event instanceof ThreadDeathEvent) {
			return threadDeathEventHandler.handleEvent((ThreadDeathEvent) event, executionTraceOracle);
		} else if (event instanceof ClassPrepareEvent) {
			return classPrepareEventHandler.handleEvent((ClassPrepareEvent) event, executionTraceOracle);
		} else if (event instanceof VMStartEvent) {
			return vmStartEventHandler.handleEvent((VMStartEvent) event, executionTraceOracle);
		} else if (event instanceof VMDeathEvent) {
			return vmDeathEventHandler.handleEvent((VMDeathEvent) event, executionTraceOracle);
		} else if (event instanceof VMDisconnectEvent) {
			return vmDisconnectEventHandler.handleEvent((VMDisconnectEvent) event, executionTraceOracle);
		}
		return null;
	}

	private boolean isConstructorEvent(MethodEntryEvent event) {
		return event.method().name().equals("<init>");
	}

	private boolean isConstructorEvent(MethodExitEvent event) {
		return event.method().name().equals("<init>");
	}

	public void setExceptionEventHandler(ExceptionEventHandler exceptionEventHandler) {
		this.exceptionEventHandler = exceptionEventHandler;
	}

	public void setMethodEntryEventHandler(MethodEntryEventHandler methodEntryEventHandler) {
		this.methodEntryEventHandler = methodEntryEventHandler;
	}

	public void setMethodExitEventHandler(MethodExitEventHandler methodExitEventHandler) {
		this.methodExitEventHandler = methodExitEventHandler;
	}

	public void setThreadDeathEventHandler(ThreadDeathEventHandler threadDeathEventHandler) {
		this.threadDeathEventHandler = threadDeathEventHandler;
	}

	public void setClassPrepareEventHandler(ClassPrepareEventHandler classPrepareEventHandler) {
		this.classPrepareEventHandler = classPrepareEventHandler;
	}

	public void setVmStartEventHandler(VMStartEventHandler vmStartEventHandler) {
		this.vmStartEventHandler = vmStartEventHandler;
	}

	public void setVmDeathEventHandler(VMDeathEventHandler vmDeathEventHandler) {
		this.vmDeathEventHandler = vmDeathEventHandler;
	}

	public void setVmDisconnectEventHandler(VMDisconnectEventHandler vmDisconnectEventHandler) {
		this.vmDisconnectEventHandler = vmDisconnectEventHandler;
	}

	/**
	 * {@link ExceptionEvent} Handler
	 * 
	 * @see ExceptionEvent
	 */
	public class ExceptionEventHandler implements EventHandler<ExceptionEvent> {
		@Override
		public Execution<?> handleEvent(ExceptionEvent event, ExecutionTraceOracle executionTraceOracle) {
			try {
				ObjectReference exception = event.exception();
				String className = exception.type().name();
				StackFrame frame = event.thread().frame(0);
				ObjectReference object = frame.thisObject();
				Long oid = object.uniqueID();
				ExceptionExecution exExec = executionTraceOracle.addException(event, className, object, oid);
				return exExec;
			} catch (Exception e) {
				logger.error("Error in fetching exception event data: " + e.getMessage());
			}
			return null;
		}
	}

	/**
	 * {@link MethodEntryEvent} Handler
	 * 
	 * @see MethodEntryEvent
	 */
	public class MethodEntryEventHandler implements EventHandler<MethodEntryEvent> {
		@Override
		public Execution<?> handleEvent(MethodEntryEvent event, ExecutionTraceOracle executionTraceOracle) {
//			Method method = event.method();
			if (isConstructorEvent(event)) {
				return null;
			}
//			String className = method.declaringType().name();
//			Map<Object, Object> virtualMachineOptions = executionTraceOracle.getVirtualMachineOptions();
//			if (!virtualMachineOptions.containsKey(VirtualMachineOption.TargetPackageBase)
//					|| className.startsWith(virtualMachineOptions.get(VirtualMachineOption.TargetPackageBase)
//							.toString())) {
				try {
//					StackFrame frame = event.thread().frame(0);
//					ObjectReference object = frame.thisObject();
//					if (object != null) {
//						long uniqueID = object.uniqueID();
						executionTraceOracle.addExecution(event);
						return executionTraceOracle.getLastExecution();
//					}
				} 
//				catch (IncompatibleThreadStateException e) {
//					logger.warn("Current object could not be fetched.");
//					executionTraceOracle.addExecution(event, className, null, null);
//					return executionTraceOracle.getLastExecution();
//				} 
				catch (InternalException e) {
				}
//			}
			return null;
		}
	}

	/**
	 * {@link MethodExitEvent} Handler
	 * 
	 * @see MethodExitEvent
	 */
	public class MethodExitEventHandler implements EventHandler<MethodExitEvent> {
		@Override
		public Execution<?> handleEvent(MethodExitEvent event, ExecutionTraceOracle executionTraceOracle) {
			if (isConstructorEvent(event)) {
				return null;
			}
//			Method method = event.method();
//			String className = method.declaringType().name();
			try {
//				ObjectReference object = event.thread().frame(0).thisObject();
//				if (null != object) {
//					Map<Object, Object> virtualMachineOptions = executionTraceOracle.getVirtualMachineOptions();
//					if (!virtualMachineOptions.containsKey(VirtualMachineOption.TargetPackageBase)
//							|| className.startsWith(virtualMachineOptions.get(VirtualMachineOption.TargetPackageBase)
//									.toString())) {
//						long uniqueID = object.uniqueID();
						executionTraceOracle.addExecution(event);
						return executionTraceOracle.getLastExecution();
//					}
//				}
			} catch (Exception e) {
			} 
//			catch (IncompatibleThreadStateException e) {
//				// throw new Error("Current object detection failed on: " +
//				// event);
//			}
			return null;
		}
	}

	/**
	 * {@link ThreadDeathEvent} Handler
	 * 
	 * @see ThreadDeathEvent
	 */
	public class ThreadDeathEventHandler implements EventHandler<ThreadDeathEvent> {
		@Override
		public Execution<?> handleEvent(ThreadDeathEvent event, ExecutionTraceOracle executionTraceOracle) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * {@link ClassPrepareEvent} Handler
	 * 
	 * @see ClassPrepareEvent
	 */
	public class ClassPrepareEventHandler implements EventHandler<ClassPrepareEvent> {
		@Override
		public Execution<?> handleEvent(ClassPrepareEvent event, ExecutionTraceOracle executionTraceOracle) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * {@link VMStartEvent} Handler
	 * 
	 * @see VMStartEvent
	 */
	public class VMStartEventHandler implements EventHandler<VMStartEvent> {
		@Override
		public Execution<?> handleEvent(VMStartEvent event, ExecutionTraceOracle executionTraceOracle) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * {@link VMDeathEvent} Handler
	 * 
	 * @see VMDeathEvent
	 */
	public class VMDeathEventHandler implements EventHandler<VMDeathEvent> {
		@Override
		public Execution<?> handleEvent(VMDeathEvent event, ExecutionTraceOracle executionTraceOracle) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * {@link VMDisconnectEvent} Handler
	 * 
	 * @see VMDisconnectEvent
	 */
	public class VMDisconnectEventHandler implements EventHandler<VMDisconnectEvent> {
		@Override
		public Execution<?> handleEvent(VMDisconnectEvent event, ExecutionTraceOracle executionTraceOracle) {
			// TODO Auto-generated method stub
			return null;
		}
	}

}
