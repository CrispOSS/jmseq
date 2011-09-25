/*
 * Created on Sep 25, 2011 | 7:14:16 AM
 */
package nl.liacs.jmseq.verify.builder;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.StateType;

/**
 * 
 * @author Behrooz Nobakht
 */
public class State extends com.continuent.tungsten.commons.patterns.fsm.State {

	public State(Enum<?> stateEnum, StateType type, com.continuent.tungsten.commons.patterns.fsm.State parent,
			Action entryAction, Action exitAction) {
		super(stateEnum, type, parent, entryAction, exitAction);
		// TODO Auto-generated constructor stub
	}

	public State(Enum<?> stateEnum, StateType type) {
		super(stateEnum, type);
		// TODO Auto-generated constructor stub
	}

	public State(String name, StateType type, Action entryAction, Action exitAction) {
		super(name, type, entryAction, exitAction);
		// TODO Auto-generated constructor stub
	}

	public State(String name, StateType type, com.continuent.tungsten.commons.patterns.fsm.State parent,
			Action entryAction, Action exitAction) {
		super(name, type, parent, entryAction, exitAction);
		// TODO Auto-generated constructor stub
	}

	public State(String name, StateType type, com.continuent.tungsten.commons.patterns.fsm.State parent) {
		super(name, type, parent);
		// TODO Auto-generated constructor stub
	}

	public State(String name, StateType type) {
		super(name, type);
		// TODO Auto-generated constructor stub
	}

}
