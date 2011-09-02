package mop;
/*
 * Created on Mar 10, 2010 - 2:16:52 PM
 */


/**
 * 
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 */
public class Sample {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Basket b = new Basket(4);
		Apples apples = new Apples(b);
		apples.start();
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000);
	}

}
