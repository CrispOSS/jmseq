/*
 * Created on Apr 29, 2010 - 3:51:31 PM
 */
package nl.liacs.jmseq.verify;

import nl.liacs.jmseq.annotation.AnnotatedSequentialExecutionMetaDataLocator;
import nl.liacs.jmseq.annotation.SequentialExecutionMetaData;
import nl.liacs.jmseq.execution.MockExecution;
import nl.liacs.jmseq.verify.CallExpressionStateMachine;
import nl.liacs.jmseq.verify.CallExpressionStateMachineFactory;

import org.junit.Test;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class CallExpressionMachineTest {

	@Test
	public void test() {
		AnnotatedSequentialExecutionMetaDataLocator locator = new AnnotatedSequentialExecutionMetaDataLocator();
		SequentialExecutionMetaData metadata = locator.loadMetaData("nl.liacs.jmseq.sample");
		System.out.println(metadata);

		MockExecution e = new MockExecution("nl.liacs.jmseq.sample.Apples", "start", new String[] {}, "void");
		CallExpressionStateMachine cesm = CallExpressionStateMachineFactory.getInstance().create(e, metadata);

		MockExecution e1 = new MockExecution("nl.liacs.jmseq.sample.Pat", "eat", new String[] {}, "void");
		boolean valid = cesm.isValidNextExecution(e1);
		System.out.println(valid);
	}

}
