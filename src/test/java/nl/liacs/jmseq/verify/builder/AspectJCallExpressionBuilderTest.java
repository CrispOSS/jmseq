/*
 * Created on Apr 22, 2010 - 1:05:25 PM
 */
package nl.liacs.jmseq.verify.builder;

import static org.junit.Assert.*;
import nl.liacs.jmseq.execution.Execution;
import nl.liacs.jmseq.execution.MockExecution;
import nl.liacs.jmseq.verify.builder.AspecJCallExpressionBuilder;
import nl.liacs.jmseq.verify.builder.CallExpressionBuilder;
import nl.liacs.jmseq.verify.callexpression.CallExpression;

import org.junit.Test;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class AspectJCallExpressionBuilderTest {

	@Test
	public void testBuildCallExpression1() {
		CallExpressionBuilder builder = new AspecJCallExpressionBuilder();
		Execution<?> execution = new MockExecution("nl.liacs.sample.Sample", "myMethod", new String[] { "int",
				"java.lang.String", "java.util.List" }, "java.util.List");
		CallExpression expression = builder.buildCallExpression(execution);
		System.out.println(expression);
	}

	@Test
	public void testBuildCallExpression2() {
		CallExpressionBuilder builder = new AspecJCallExpressionBuilder();
		String spec = "{call(* * *.Dample.hisMethod(..))}";
		CallExpression expression = builder.buildCallExpression(spec);
		System.out.println(expression);
	}

	@Test
	public void testBuildCallExpression3() {
		CallExpressionBuilder builder = new AspecJCallExpressionBuilder();
		String spec = "{call(* * *.Dample.hisMethod(..))[{call(* * *.Sample.myMethod(..))}]}";
		CallExpression expression = builder.buildCallExpression(spec);
		System.out.println(expression);
	}

	@Test
	public void testBuildCallExpression4() {
		CallExpressionBuilder builder = new AspecJCallExpressionBuilder();
		String spec = "{call(* * *.Dample.hisMethod(..))[{call(* * *.Sample.myMethod(..))}{call(* * *.Mample.yourMethod(..))}]}";
		CallExpression expression = builder.buildCallExpression(spec);
		System.out.println(expression);
	}
	
	@Test
	public void testBuildCallExpression5() throws Exception {
		CallExpressionBuilder builder = new AspecJCallExpressionBuilder();
		String spec = "{call(public boolean mop..Vector.iterator())}"
				+ "{call(public boolean mop..Vector$IteratorDelegate.hasNext())}~"
				+ "{call(public Object mop..Vector$IteratorDelegate.next())}";
		CallExpression expression = builder.buildCallExpression(spec);
		System.out.println(expression);
	}

}
