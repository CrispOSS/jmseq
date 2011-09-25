package nl.liacs.jmseq.verify.builder;

import static org.junit.Assert.*;

import nl.liacs.jmseq.verify.callexpression.CallExpression;

import org.junit.Before;
import org.junit.Test;

public class CallSequenceSpecCallExpressionBuilderTest {

	private CallSequenceSpecCallExpressionBuilder builder;

	@Before
	public void setUp() {
		builder = new CallSequenceSpecCallExpressionBuilder();
	}

	@Test
	public void buildCallExpression() throws Exception {
		String spec = "{call(public void mop..HasNextRunner.testSuccess()[" +
				  "{call(public boolean mop..Vector.iterator())}"
					+ "{call(public boolean mop..Vector$IteratorDelegate.hasNext())}~"
					+ "{call(public Object mop..Vector$IteratorDelegate.next())}"
					+ "]}";
		CallExpression ce = builder.buildCallExpression(spec);
		System.out.println(ce);
	}
	
	@Test
	public void buildCallExpression2() throws Exception {
		String spec = "{call(public void mop..HasNextRunner.testSuccess()[" +
				"{call(public boolean mop..Vector.iterator())}"
				+ "{call(public boolean mop..Vector$IteratorDelegate.hasNext())}~"
				+ "{call(*)}~"
				+ "{call(public Object mop..Vector$IteratorDelegate.next())}"
				+ "]}";
		CallExpression ce = builder.buildCallExpression(spec);
		System.out.println(ce);
	}

}
