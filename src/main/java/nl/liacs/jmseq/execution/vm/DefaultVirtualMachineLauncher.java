/*
 * Created on Mar 11, 2010 - 4:04:49 PM
 */
package nl.liacs.jmseq.execution.vm;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import nl.liacs.jmseq.execution.ExecutionUtils;

import org.slf4j.LoggerFactory;

import com.sun.jdi.Bootstrap;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.VMStartException;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class DefaultVirtualMachineLauncher implements VirtualMachineLauncher {

	protected final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @param programArguments
	 * @param options
	 * @return
	 */
	public VirtualMachine launchTargetVirtualMachine(String launcherName, String programArguments,
			Map<Object, Object> options) {
		long start = System.currentTimeMillis();
		LaunchingConnector connector = findLaunchingConnector(launcherName);
		Map arguments = prepareLaunchingConnectorArguments(connector, programArguments, options);
		try {
			VirtualMachine vm = connector.launch(arguments);
			loadAllClasses(vm);
			long end = System.currentTimeMillis();
			double time = ((end + 0.0) - start) / 1000.0;
			logger.info("Initialized and launched a virtual machine in [" + time + "] seconds");
			return vm;
		} catch (IOException exc) {
			throw new Error("Unable to launch target VM: " + exc);
		} catch (IllegalConnectorArgumentsException exc) {
			throw new Error("Internal error: " + exc);
		} catch (VMStartException exc) {
			throw new Error("Target VM failed to initialize: " + exc.getMessage());
		}
	}

	private void loadAllClasses(VirtualMachine vm) {
		List<ReferenceType> all = vm.allClasses();
		logger.warn("Load all classes for the virual machine: " + all.size());
		long start = System.currentTimeMillis();
		for (ReferenceType rt : all) {
			String name = rt.name();
			ExecutionUtils.loadClass(name);
		}
		long end = System.currentTimeMillis();
		logger.warn("Loading all classes for the virual machine took: " + (end - start));
	}

	/**
	 * @return
	 */
	protected LaunchingConnector findLaunchingConnector(String name) {
		// List<Connector> connectors =
		// org.eclipse.jdi.Bootstrap.virtualMachineManager().allConnectors();
		List connectors = Bootstrap.virtualMachineManager().allConnectors();
		Iterator iter = connectors.iterator();
		while (iter.hasNext()) {
			Connector connector = (Connector) iter.next();
			if (connector.name().equals(name)) {
				return (LaunchingConnector) connector;
			}
		}
		throw new Error("No launching connector");
	}

	/**
	 * @param connector
	 * @param programArguments
	 * @param options
	 * @return
	 */
	protected Map prepareLaunchingConnectorArguments(LaunchingConnector connector, String programArguments,
			Map<Object, Object> options) {
		Map arguments = connector.defaultArguments();
		Connector.Argument mainArg = (Connector.Argument) arguments.get("main");
		if (null == mainArg) {
			throw new Error("Bad launching connector");
		}
		mainArg.setValue(programArguments);

		// classpath
		Connector.Argument optionArg = (Connector.Argument) arguments.get("options");
		if (optionArg == null) {
			throw new Error("Bad launching connector");
		}
		if (options.containsKey(VirtualMachineOption.Classpath)) {
			String separator = System.getProperty("path.separator");
			String classpath = System.getProperty("java.class.path") + separator + ".";
			optionArg.setValue("-classpath " + classpath + " ");
		}

		return arguments;
	}

}
