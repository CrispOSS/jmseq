/*
 * Created on Mar 10, 2010 - 3:59:39 PM
 */
package nl.liacs.jmseq.execution;

import java.lang.reflect.Method;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.event.Event;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public interface Execution<E extends Event> {

	/**
	 * @return
	 */
	Execution<?> getParentExecution();

	/**
	 * @return
	 */
	String getSignature();

	/**
	 * @return
	 */
	E getExecutingEvent();

	/**
	 * @return
	 */
	Class<E> getExecutingEventType();

	/**
	 * @return
	 */
	String getExecutingClassType();

	/**
	 * @return
	 */
	String getExecutingClassTypeSimpleName();

	/**
	 * @return
	 */
	String getExecutingMethodName();

	/**
	 * @return
	 */
	ObjectReference getExecutingObject();

	/**
	 * @return
	 */
	long getExecutingObjectUniqueId();

	/**
	 * @return
	 */
	Class<?> getExecutingClass();
	
	Method findMethod();

}
