/*
 * Created on Apr 13, 2010 - 3:37:51 PM
 */
package nl.liacs.jmseq.annotation;

import java.util.Map;

import nl.liacs.jmseq.execution.Execution;
import nl.liacs.jmseq.utils.CollectionUtils;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class SequentialExecutionMetaData {

	private final Map<String, SequencedObjectMetaData> metadata = CollectionUtils.createMap();

	public boolean contains(String className) {
		return metadata.containsKey(className);
	}

	public boolean contains(Execution<?> e) {
		if (!contains(e.getExecutingClassType())) {
			return false;
		}
		SequencedObjectMetaData md = getMetadata(e.getExecutingClassType());
		if (null == md) {
			return false;
		}
		SequencedMethod annotation = md.getAnnotation(e.getExecutingMethodName());
		if (null == annotation) {
			return false;
		}
		return true;
	}

	public void add(String className, SequencedObjectMetaData sequencedObjectMetaData) {
		this.metadata.put(className, sequencedObjectMetaData);
	}

	public void add(Class<?> clazz, SequencedObjectMetaData sequencedObjectMetaData) {
		add(clazz.getName(), sequencedObjectMetaData);
	}

	public boolean contains(Class<?> clazz) {
		return contains(clazz.getName());
	}

	public SequencedObjectMetaData getMetadata(String className) {
		if (!contains(className)) {
			return null;
		}
		return metadata.get(className);
	}

	public SequencedObjectMetaData getMetadata(Class<?> clazz) {
		return getMetadata(clazz.getName());
	}

	@Override
	public String toString() {
		return metadata.toString();
	}

	@Override
	public int hashCode() {
		return metadata.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return metadata.equals(obj);
	}

}
