/*
 * Created on Oct 2, 2011 | 6:46:35 AM
 */
package nl.liacs.jmseq.sample.pingpong;

/**
 * 
 * @author Behrooz Nobakht
 */
public class Pong {

	private Ping ping;
	private int count = 0;

	public Pong(Ping ping) {
		this.ping = ping;
	}

	public void pong() {
		System.out.println("Ponged " + (++count) + ", pinging ...");
		ping.ping();
	}

}
