/*
 * Created on Apr 22, 2010 - 1:42:56 PM
 */
package nl.liacs.jmseq.verify.proposer;

import java.util.Set;

import nl.liacs.jmseq.execution.Execution;
import nl.liacs.jmseq.execution.MockExecution;
import nl.liacs.jmseq.verify.builder.AspecJCallExpressionBuilder;
import nl.liacs.jmseq.verify.builder.CallExpressionBuilder;
import nl.liacs.jmseq.verify.callexpression.CallExpression;
import nl.liacs.jmseq.verify.callexpression.Occurrence;

import org.junit.Test;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class SimpleCallExpressionProposerTest {

	@Test
	public void testPropose1() {

		// ce1 : Occurrence.Once
		// ce2 : Occurrence.Once
		// + sibling relation

		CallExpressionBuilder builder = new AspecJCallExpressionBuilder();
		CallExpressionProposer proposer = new SimpleCallExpressionProposer();

		Execution<?> execution1 = new MockExecution("nl.liacs.sample.Sample", "myMethod", new String[] {}, "int");
		Execution<?> execution2 = new MockExecution("nl.liacs.sample.Mample", "yourMethod", new String[] {}, "int");

		CallExpression ce1 = builder.buildCallExpression(execution1);

		CallExpression ce2 = builder.buildCallExpression(execution2);
		ce1.getSiblingCallExpressions().add(ce2);

		Set<CallExpression> possibilities = proposer.proposePossibleNextExpressions(ce1);
		System.out.println(possibilities);

	}

	@Test
	public void testPropose2() {

		// ce1 : Occurrence.Plus || Occurrence.Star
		// ce2 : Occurrence.Once
		// + sibling relation

		CallExpressionBuilder builder = new AspecJCallExpressionBuilder();
		CallExpressionProposer proposer = new SimpleCallExpressionProposer();

		Execution<?> execution1 = new MockExecution("nl.liacs.sample.Sample", "myMethod", new String[] {}, "int");
		Execution<?> execution2 = new MockExecution("nl.liacs.sample.Mample", "yourMethod", new String[] {}, "int");

		CallExpression ce1 = builder.buildCallExpression(execution1);
		ce1.setReocurrence(Occurrence.Star);

		CallExpression ce2 = builder.buildCallExpression(execution2);

		ce1.getSiblingCallExpressions().add(ce2);

		Set<CallExpression> possibilities = proposer.proposePossibleNextExpressions(ce1);
		System.out.println(possibilities);

	}

	@Test
	public void testPropose3() {

		// ce1 : Occurrence.Plus || Occurrence.Star
		// ce2 : Occurrence.Once
		// + sibling relation
		// + inner relation

		CallExpressionBuilder builder = new AspecJCallExpressionBuilder();
		CallExpressionProposer proposer = new SimpleCallExpressionProposer();

		Execution<?> execution1 = new MockExecution("nl.liacs.sample.Sample", "myMethod", new String[] {}, "int");
		Execution<?> execution2 = new MockExecution("nl.liacs.sample.Mample", "yourMethod", new String[] {}, "int");
		Execution<?> execution3 = new MockExecution("nl.liacs.sample.Dample", "hisMethod", new String[] {}, "int");

		CallExpression ce1 = builder.buildCallExpression(execution1);
		ce1.setReocurrence(Occurrence.Star);

		CallExpression ce2 = builder.buildCallExpression(execution2);

		CallExpression ce3 = builder.buildCallExpression(execution3);

		ce1.getSiblingCallExpressions().add(ce2);
		ce1.getInnerCallExpressions().add(ce3);

		Set<CallExpression> possibilities = proposer.proposePossibleNextExpressions(ce1);
		System.out.println(possibilities);

	}
	
	@Test
	public void testIterator() throws Exception {
		CallExpressionBuilder builder = new AspecJCallExpressionBuilder();
		CallExpressionProposer proposer = new SimpleCallExpressionProposer();
		
		Execution<?> e1 = new MockExecution("mop.ERE.HasNext.Iterator", "hasNext", new String[] {}, "boolean");
		Execution<?> e2 = new MockExecution("mop.ERE.HasNext.Iterator", "hasNext", new String[] {}, "boolean");
		Execution<?> e3 = new MockExecution("mop.ERE.HasNext.Iterator", "next", new String[] {}, "Object");
		
		CallExpression ce1 = builder.buildCallExpression(e1);
		ce1.setReocurrence(Occurrence.Once);
		
		CallExpression ce2 = builder.buildCallExpression(e2);
		ce2.setReocurrence(Occurrence.Star);
		
		CallExpression ce3 = builder.buildCallExpression(e3);
		ce3.setReocurrence(Occurrence.Once);
		
		ce1.getSiblingCallExpressions().add(ce2);
		ce2.getSiblingCallExpressions().add(ce3);
		
		Set<CallExpression> p1 = proposer.proposePossibleNextExpressions(ce1);
		System.out.println(p1 + "\n ============== \n");
		
		Set<CallExpression> p2 = proposer.proposePossibleNextExpressions(ce2);
		System.out.println(p2);
		
	}

}
