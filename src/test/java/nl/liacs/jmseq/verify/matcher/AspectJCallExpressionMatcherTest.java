package nl.liacs.jmseq.verify.matcher;

import static org.junit.Assert.*;

import nl.liacs.jmseq.verify.callexpression.CallExpression;
import nl.liacs.jmseq.verify.callexpression.Occurrence;
import nl.liacs.jmseq.verify.callexpression.SimpleAspectJCallExpression;

import org.junit.Before;
import org.junit.Test;

public class AspectJCallExpressionMatcherTest {
	
	private AspectJCallExpressionMatcher matcher;

	@Before
	public void setUp() throws Exception {
		matcher = new AspectJCallExpressionMatcher();
	}
	
	@Test
	public void match() throws Exception {
		CallExpression target = new SimpleAspectJCallExpression("call(public boolean mop.ERE.HasNext.Vector$IteratorDelegate.hasNext())");
		target.setReocurrence(Occurrence.Once);
		CallExpression candidate = new SimpleAspectJCallExpression("call(public boolean mop..Vector$IteratorDelegate.hasNext())");
		candidate.setReocurrence(Occurrence.Plus);
		CallExpression match = matcher.match(candidate, target);
		System.out.println(match);
	}

}
