/*
 * Created on Apr 12, 2010 - 10:33:37 AM
 */
package nl.liacs.jmseq.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class SequencedObjectMetaData {

	private Class<?> targetClass;
	private List<Method> sequentiallyExecutedMethods;

	public SequencedObjectMetaData(Class<?> targetClass) {
		this.targetClass = targetClass;
		this.sequentiallyExecutedMethods = new ArrayList<Method>();
	}

	public SequencedObjectMetaData(Class<?> targetClass, List<Method> methods) {
		this(targetClass);
		this.sequentiallyExecutedMethods.addAll(methods);
	}

	public void addMethod(Method method) {
		if (!targetClass.equals(method.getDeclaringClass())) {
			return;
		}
		this.sequentiallyExecutedMethods.add(method);
	}

	public SequencedMethod getAnnotation(String methodName) {
		for (Method m : sequentiallyExecutedMethods) {
			if (m.getName().equals(methodName)) {
				return m.getAnnotation(SequencedMethod.class);
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "[" + targetClass.getSimpleName() + ": " + sequentiallyExecutedMethods + "]";
	}

}
