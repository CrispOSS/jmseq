/*
 * Created on Oct 2, 2011 | 6:49:46 AM
 */
package nl.liacs.jmseq.sample.pingpong;

import nl.liacs.jmseq.annotation.SequencedMethod;
import nl.liacs.jmseq.annotation.SequencedObject;

/**
 * 
 * @author Behrooz Nobakht
 */
@SequencedObject
public class TableTennis {

	private Ping ping = null;
	private Pong pong = null;

	public TableTennis() {
		ping = new Ping();
		pong = new Pong(ping);
		ping.setPong(pong);
	}

	@SequencedMethod("{call(* nl..Ping.ping())" + "[{call(* nl..Pong.pong())}{call(* nl..Ping.ping())}]}")
	public void play() {
		ping.ping();
	}

	public static void main(String[] args) {
		TableTennis tt = new TableTennis();
		tt.play();
	}

}
