/*
 * Created on Apr 13, 2010 - 3:44:46 PM
 */
package nl.liacs.jmseq.annotation;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public interface SequentialExecuationMetaDataLoader {

	/**
	 * @param basePackage
	 * @return
	 */
	SequentialExecutionMetaData loadMetaData(String basePackage);

}
