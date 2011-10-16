package mop.ERE.HasNext;

import nl.liacs.jmseq.annotation.SequencedMethod;
import nl.liacs.jmseq.annotation.SequencedObject;

@SequencedObject
public class HasNextRunner {
	
	private int repetition;
	private Vector<Integer> v;

	public HasNextRunner(Vector<Integer> v, int repetition) {
		this.v = v;
		this.repetition = repetition;
	}

	@SequencedMethod(value = "" + "{call(* mop..Vector.iterator())}~"
			+ "{call(* mop..IteratorDelegate.hasNext())}~"
			+ "{call(* mop..IteratorDelegate.next())}" + "")
	public void testFailure() {
		Iterator<Integer> i = v.iterator();
		i.hasNext();
		i.next();
		i.next();
	}

	@SequencedMethod(value = "{call(* mop..Vector.iterator())}~"
			+ "{call(* mop..IteratorDelegate.hasNext())}~"
			+ "{call(* mop..IteratorDelegate.next())}", allowExceptions = false, expect = { UnsupportedOperationException.class })
	public void testSuccess() {
		Iterator<Integer> i = v.iterator();
		for (int k = 0; k < repetition && i.hasNext(); ++k) {
			i.hasNext();
		}
		i.next();
	}

}
