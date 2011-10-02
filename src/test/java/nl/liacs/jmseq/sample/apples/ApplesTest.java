/*
 * Created on Mar 14, 2010 - 5:16:48 PM
 */
package nl.liacs.jmseq.sample.apples;

import nl.liacs.jmseq.test.JUnit4Support;

import org.junit.Test;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class ApplesTest extends JUnit4Support {

	@Test
	public void testTrace() throws Exception {
		startJMseq();
	}
	
	@Override
	protected String getClassName() {
		return "Apples";
	}
	
	@Override
	protected String getPackageBase() {
		return "nl.liacs.jmseq.sample.apples";
	}

}
