package nl.liacs.jmseq.verify.matcher;

import java.util.Collections;

import nl.liacs.jmseq.execution.MockExecution;
import nl.liacs.jmseq.verify.builder.AspecJCallExpressionBuilder;
import nl.liacs.jmseq.verify.builder.AspectJCallExpressionBuilderTest;
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

	public void match() throws Exception {
		CallExpression target = new SimpleAspectJCallExpression(
				"call(public boolean mop.ERE.HasNext.Vector$IteratorDelegate.hasNext())");
		target.setReocurrence(Occurrence.Once);
		CallExpression candidate = new SimpleAspectJCallExpression(
				"call(public boolean mop..Vector$IteratorDelegate.hasNext())");
		candidate.setReocurrence(Occurrence.Plus);
		CallExpression match = matcher.match(candidate, target);
		System.out.println(match);
	}

	@Test
	public void matchPointcutExpression() throws Exception {
		CallExpression ce1 = new SimpleAspectJCallExpression("call(* nl..Apples.start())");
		CallExpression ce2 = new SimpleAspectJCallExpression("public", "void", "nl.liacs.jmseq.sample.apples.Apples",
				"start", Collections.EMPTY_LIST);
		AspecJCallExpressionBuilder builder = new AspecJCallExpressionBuilder();
		CallExpression ce3 = builder.buildCallExpression(new MockExecution("nl.liacs.jmseq.sample.apples.Apples",
				"start", new String[] {}, "void"));
		matcher.match(ce3, ce1);
	}

}
