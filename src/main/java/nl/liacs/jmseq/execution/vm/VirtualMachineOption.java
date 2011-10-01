/*
 * Created on Mar 11, 2010 - 3:12:24 PM
 */
package nl.liacs.jmseq.execution.vm;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public enum VirtualMachineOption {

	ExceptionEventSuspend,
	MethodEntryEventSuspend,
	MethodExitEventSuspend,
	ThreadDeathEventSuspend,
	ClassPrepareEventSuspend,
	Excludes,
	Classpath,
	TargetPackageBase, Exceptions;

}
