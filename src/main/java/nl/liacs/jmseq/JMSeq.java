/*
 * Created on Oct 9, 2011 | 8:32:57 AM
 */
package nl.liacs.jmseq;

import java.util.HashMap;
import java.util.Map;

import nl.liacs.jmseq.execution.DefaultProgramExecutionTracer;
import nl.liacs.jmseq.execution.vm.VirtualMachineOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Behrooz Nobakht
 */
public class JMSeq {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected DefaultProgramExecutionTracer tracer;
	protected Map<Object, Object> options;
	protected String className;

	private long start;
	private long end;

	public static void verify(String className, String packageBase, Map<Object, Object> options) {
		new JMSeq(className, packageBase, options);
	}

	private JMSeq(String className, String packageBase, Map<Object, Object> options) {
		this.className = packageBase + "." + className;
		this.options = options;
		tracer = new DefaultProgramExecutionTracer();
		start = System.currentTimeMillis();
		tracer.trace(this.className, null, options);
		end = System.currentTimeMillis();
		logger.info("Verifying {} took {}ms", this.className, (end - start));
	}

	public static void main(String[] args) {
		String className = "HasNextTester";
		String packageBase = "mop.ERE.HasNext";
		Map<Object, Object> options = new HashMap<Object, Object>();
		options.put(VirtualMachineOption.Classpath, true);
		options.put(VirtualMachineOption.MethodEntryEventSuspend, true);
		options.put(VirtualMachineOption.MethodExitEventSuspend, true);
		options.put(VirtualMachineOption.Excludes, new String[] { "java.*", "javax.*", "sun.*", "com.sun.*", "org.*",
				"net.*", "com.*" });
		options.put(VirtualMachineOption.TargetPackageBase, packageBase);
		verify(className, packageBase, options);
	}
}
