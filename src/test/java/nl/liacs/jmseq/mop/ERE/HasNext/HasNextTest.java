package nl.liacs.jmseq.mop.ERE.HasNext;

import nl.liacs.jmseq.test.JUnit4Support;

import org.junit.Test;

public class HasNextTest extends JUnit4Support {

	@Test
	public void testFailure() throws Exception {
		tracer.trace(className, null, options);
	}

	@Override
	protected String getPackageBase() {
		return "mop.ERE.HasNext";
	}

	@Override
	protected String getClassName() {
		return "HasNextTester";
	}

}
