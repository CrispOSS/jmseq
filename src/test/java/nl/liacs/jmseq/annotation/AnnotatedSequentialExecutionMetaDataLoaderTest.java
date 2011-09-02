/*
 * Created on Apr 29, 2010 - 12:15:43 PM
 */
package nl.liacs.jmseq.annotation;

import nl.liacs.jmseq.annotation.AnnotatedSequentialExecutionMetaDataLocator;
import nl.liacs.jmseq.annotation.SequentialExecuationMetaDataLoader;
import nl.liacs.jmseq.annotation.SequentialExecutionMetaData;

import org.junit.Test;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class AnnotatedSequentialExecutionMetaDataLoaderTest {

	@Test
	public void testLoadMetaData() {
		SequentialExecuationMetaDataLoader loader = new AnnotatedSequentialExecutionMetaDataLocator();
		SequentialExecutionMetaData metadata = loader.loadMetaData("nl.liacs.jmseq.sample");
		System.out.println(metadata);
	}

}
