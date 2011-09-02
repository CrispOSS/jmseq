/*
 * Created on Mar 11, 2010 - 4:35:24 PM
 */
package nl.liacs.jmseq.execution;

import java.util.Map;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public interface ProgramExecutionTracer {

	/**
	 * @param clazz
	 * @param programArguments
	 * @param options
	 */
	void trace(Class<?> clazz, String programArguments, Map<Object, Object> options);

	/**
	 * @param className
	 * @param programArguments
	 * @param options
	 */
	void trace(String className, String programArguments, Map<Object, Object> options);

}
