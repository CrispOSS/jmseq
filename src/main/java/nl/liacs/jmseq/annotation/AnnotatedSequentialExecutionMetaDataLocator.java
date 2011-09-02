/*
 * Created on Apr 11, 2010 - 12:20:47 PM
 */
package nl.liacs.jmseq.annotation;

import java.lang.reflect.Method;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class AnnotatedSequentialExecutionMetaDataLocator implements SequentialExecuationMetaDataLoader {

	private Class<SequencedObject> classLevelAnnotation = SequencedObject.class;
	private Class<SequencedMethod> methodLevelAnnotation = SequencedMethod.class;

	private ClassPathScanningCandidateComponentProvider provider;

	public AnnotatedSequentialExecutionMetaDataLocator() {
		provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(classLevelAnnotation));
	}

	@Override
	public SequentialExecutionMetaData loadMetaData(String basePackage) {
		Set<BeanDefinition> bds = provider.findCandidateComponents(basePackage);
		SequentialExecutionMetaData metadata = new SequentialExecutionMetaData();
		for (BeanDefinition bd : bds) {
			String className = bd.getBeanClassName();
			Class<?> clazz = loadClass(className);
			if (null != clazz) {
				SequencedObjectMetaData semd = new SequencedObjectMetaData(clazz);
				Method[] methods = clazz.getMethods();
				for (Method method : methods) {
					SequencedMethod annotation = method.getAnnotation(methodLevelAnnotation);
					if (null != annotation) {
						semd.addMethod(method);
					}
				}
				metadata.add(clazz, semd);
			}
		}
		return metadata;
	}

	private Class<?> loadClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
		}
		return null;
	}

}
