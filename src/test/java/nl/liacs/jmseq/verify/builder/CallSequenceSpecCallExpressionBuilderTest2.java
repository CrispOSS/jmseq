/*
 * Created on Sep 25, 2011 | 7:59:33 AM
 */
package nl.liacs.jmseq.verify.builder;

import static org.junit.Assert.*;

import nl.liacs.jmseq.verify.callexpression.CallExpression;
import nl.liacs.jmseq.verify.callexpression.SimpleAspectJCallExpression;

import org.junit.Before;
import org.junit.Test;

import com.continuent.tungsten.commons.patterns.fsm.Entity;
import com.continuent.tungsten.commons.patterns.fsm.EntityAdapter;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.State;
import com.continuent.tungsten.commons.patterns.fsm.StateChangeListener;
import com.continuent.tungsten.commons.patterns.fsm.StateMachine;

/**
 * 
 * @author Behrooz Nobakht
 */
public class CallSequenceSpecCallExpressionBuilderTest2 {

	private CallSequenceSpecCallExpressionBuilder builder;

	@Before
	public void setUp() {
		builder = new CallSequenceSpecCallExpressionBuilder();
	}

	@Test
	public void buildCallExpression() throws Exception {
		String spec = 
				"{call(iterator)}"
				+ "{call(hasNext)}~"
				+ "{call(next)}";
		CallExpression ce = builder.buildCallExpression(spec);
		StateTransitionMap transitions = builder.getTransitions();
		transitions.build();
		StateMachine sm = new StateMachine(transitions, new EntityAdapter(new Object()));
		sm.addListener(new StateChangeListener() {
			@Override
			public void stateChanged(Entity entity, State oldState, State newState) {
				System.err.println(oldState + " => " + newState);
			}
		});
		sm.applyEvent(new Event(new SimpleAspectJCallExpression("call(iterator)")));
		sm.applyEvent(new Event(new SimpleAspectJCallExpression("call(hasNext)")));
		sm.applyEvent(new Event(new SimpleAspectJCallExpression("call(hasNext)")));
		sm.applyEvent(new Event(new SimpleAspectJCallExpression("call(next)")));
	}

}
