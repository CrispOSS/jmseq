package mop.ERE.HasNext;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class HasNextRunner {

	private Random r = new Random(System.currentTimeMillis());

	
	public void testFailure(int repetitions) {
		Vector<Integer> v = fillVector(repetitions);
		Iterator<Integer> i = v.iterator();
		i.hasNext();
		i.next();
		i.next();
	}

	public void testSuccess(int repetitions) {
		Vector<Integer> v = fillVector(repetitions);
		Iterator<Integer> i = v.iterator();
		while (i.hasNext()) {
			i.next();
			if (Math.random() > 0.5) {
				i.hasNext();
			}
		}
	}

	private Vector<Integer> fillVector(int repetitions) {
		Vector<Integer> v = new Vector<Integer>();
		for (int i = 0; i < repetitions; ++i) {
			v.add(r.nextInt());
		}
		return v;
	}

}
