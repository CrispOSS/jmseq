/*
 * Created on Oct 2, 2011 | 7:00:35 AM
 */
package nl.liacs.jmseq.sample.pingpong;

import nl.liacs.jmseq.test.JUnit4Support;

import org.junit.Test;

/**
 * 
 * @author Behrooz Nobakht
 */
public class TableTennisTest extends JUnit4Support {

	@Test
	public void play() throws Exception {
		tracer.trace(className, null, options);
	}

	@Override
	protected String getPackageBase() {
		return "nl.liacs.jmseq.sample.pingpong";
	}

	@Override
	protected String getClassName() {
		return "TableTennis";
	}

}
