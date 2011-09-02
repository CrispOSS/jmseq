package mop;

/*
 * Created on Mar 14, 2010 - 5:09:16 PM
 */

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

	public int retrieve() {
		if (count > 0) {
			--count;
		}
		return count;
	}

	public int store(int c) {
		count += c;
		return count;
	}

	public int store() {
		return store(1);
	}

}
