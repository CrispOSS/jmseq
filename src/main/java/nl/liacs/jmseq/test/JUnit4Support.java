package nl.liacs.jmseq.test;

import java.util.HashMap;
import java.util.Map;

import nl.liacs.jmseq.execution.DefaultProgramExecutionTracer;
import nl.liacs.jmseq.execution.vm.VirtualMachineOption;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnit4.class)
public abstract class JUnit4Support {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected DefaultProgramExecutionTracer tracer;
	protected Map<Object, Object> options;
	protected String className;

	private long start;
	private long end;

	@Before
	public void setUp() {
		tracer = new DefaultProgramExecutionTracer();
		options = new HashMap<Object, Object>();
		fillOptions(options);
		className = getPackageBase() + "." + getClassName();
		start = System.currentTimeMillis();
	}

	@After
	public void tearDown() {
		end = System.currentTimeMillis();
		logger.info("Testing {} took {}ms", getClassName(), (end - start));
	}

	protected void startJMseq() {
		tracer.trace(className, null, options);
	}

	protected Map<Object, Object> fillOptions(Map<Object, Object> options) {
		options.put(VirtualMachineOption.Classpath, true);
		options.put(VirtualMachineOption.MethodEntryEventSuspend, true);
		options.put(VirtualMachineOption.MethodExitEventSuspend, true);
		options.put(VirtualMachineOption.Excludes, getExcludedPatterns());
		options.put(VirtualMachineOption.TargetPackageBase, getPackageBase());
		if (getTraceExceptions()) {
			options.put(VirtualMachineOption.ExceptionEventSuspend, true);
		}
		return options;
	}

	protected boolean getTraceExceptions() {
		return false;
	}

	protected String[] getExcludedPatterns() {
		return new String[] { "java.*", "javax.*", "sun.*", "com.sun.*", "org.*", "net.*", "com.*" };
	}

	protected abstract String getPackageBase();

	protected abstract String getClassName();

}
