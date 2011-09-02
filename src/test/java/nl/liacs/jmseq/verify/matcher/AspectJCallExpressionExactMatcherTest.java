/*
 * Created on Apr 22, 2010 - 1:36:42 PM
 */
package nl.liacs.jmseq.verify.matcher;

import nl.liacs.jmseq.execution.Execution;
import nl.liacs.jmseq.execution.MockExecution;
import nl.liacs.jmseq.verify.builder.AspecJCallExpressionBuilder;
import nl.liacs.jmseq.verify.builder.CallExpressionBuilder;
import nl.liacs.jmseq.verify.builder.CallSequenceSpecCallExpressionBuilder;
import nl.liacs.jmseq.verify.callexpression.CallExpression;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class AspectJCallExpressionExactMatcherTest {

	@Test
	public void testMatch() {

		CallExpressionBuilder builder = new AspecJCallExpressionBuilder();
		CallExpressionMatcher matcher = new AspectJCallExpressionMatcher();

		Execution<?> execution1 = new MockExecution("nl.liacs.jmseq.sample.Pat", "eat", new String[] {}, "void");
		Execution<?> execution2 = new MockExecution("nl.liacs.jmseq.sample.Pat", "eat", new String[] {}, "void");

		CallExpression buildCallExpression1 = builder.buildCallExpression(execution1);
		CallExpression buildCallExpression2 = builder.buildCallExpression(execution2);

		CallExpression match = matcher.match(buildCallExpression1, buildCallExpression2);
		Assert.assertNotNull(match);

	}

	@Test
	public void testMatch2() {
		
		CallExpressionBuilder builder = new AspecJCallExpressionBuilder();
		CallExpressionMatcher matcher = new AspectJCallExpressionMatcher();
		
		Execution<?> execution1 = new MockExecution("nl.liacs.jmseq.sample.Pat", "eat", new String[] {"int"}, "void");
		Execution<?> execution2 = new MockExecution("nl.liacs.jmseq.sample.Pat", "eat", new String[] {"int"}, "void");
		
		CallExpression buildCallExpression1 = builder.buildCallExpression(execution1);
		CallExpression buildCallExpression2 = builder.buildCallExpression(execution2);
		
		CallExpression match = matcher.match(buildCallExpression1, buildCallExpression2);
		Assert.assertNotNull(match);
		
	}
	
	@Test
	public void testMatch3() {
		
		CallExpressionBuilder builder = new AspecJCallExpressionBuilder();
		CallExpressionBuilder seqBuilder = new CallSequenceSpecCallExpressionBuilder();
		CallExpressionMatcher matcher = new AspectJCallExpressionMatcher();
		
		CallExpression candidate = seqBuilder.buildCallExpression("{call(public void nl..Pat.eat())}");
		Execution<?> execution = new MockExecution("nl.liacs.jmseq.sample.Pat", "eat", new String[] {}, "void");
		
		CallExpression runtimeCE = builder.buildCallExpression(execution);
		
		CallExpression match = matcher.match(candidate, runtimeCE);
		Assert.assertNotNull(match);
		
	}
	
}
