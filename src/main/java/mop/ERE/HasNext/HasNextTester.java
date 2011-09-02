package mop.ERE.HasNext;

import nl.liacs.jmseq.annotation.SequencedMethod;
import nl.liacs.jmseq.annotation.SequencedObject;

@SequencedObject
public class HasNextTester {

	@SequencedMethod(value = "{call(* *.HasNextRunner.testFailure(..))["
			+ "{call(* *.Iterator.hasNext())}"
			+ "{call(* *.Iterator.hasNext())}$"
			+ "{call(* *.Iterator.next())}" + "]$")
	public void runFailure() {
		HasNextRunner v = new HasNextRunner();
		v.testFailure(1000);
	}

	@SequencedMethod(value = "{call(* *.HasNextRunner.testSuccess(..))["
			+ "{call(* *.Iterator.hasNext())}"
			+ "{call(* *.Iterator.hasNext())}$"
			+ "{call(* *.Iterator.next())}" + "]$")
	public void runSuccess() {
		HasNextRunner v = new HasNextRunner();
		v.testSuccess(1000);
	}

	public static void main(String[] args) {
		HasNextTester tester = new HasNextTester();
		tester.runFailure();
	}

}
