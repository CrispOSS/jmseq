/*
 * Created on Sep 25, 2011 | 7:35:35 AM
 */
package nl.liacs.jmseq.verify.builder;

import com.continuent.tungsten.commons.patterns.fsm.FiniteStateException;
import com.continuent.tungsten.commons.patterns.fsm.State;
import com.continuent.tungsten.commons.patterns.fsm.Transition;

/**
 *
 * @author Behrooz Nobakht
 */
public class StateTransitionMap extends com.continuent.tungsten.commons.patterns.fsm.StateTransitionMap {
	
	@Override
	public State addState(State state) throws RuntimeException {
		try {
			return super.addState(state);
		} catch (FiniteStateException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Transition addTransition(Transition transition) throws RuntimeException {
		try {
			return super.addTransition(transition);
		} catch (FiniteStateException e) {
			throw new RuntimeException(e);
		}
	}

}
