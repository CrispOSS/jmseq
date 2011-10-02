/*
 * Created on Mar 14, 2010 - 5:09:16 PM
 */
package nl.liacs.jmseq.sample.apples;

/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class Basket {

	private int count = 0;

	public Basket(int count) {
		this.count = count;
	}

	public int get() {
		if (count > 0) {
			--count;
		}
		return count;
	}

	public int put(int c) {
		count += c;
		return count;
	}

	public int put() {
		return put(1);
	}

}
