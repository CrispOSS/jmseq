/*
 * Created on Oct 15, 2011 | 7:53:30 PM
 */
package nl.liacs.jmseq.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Behrooz Nobakht
 */
public class CollectionUtils {
	
	public static <K, V> Map<K, V> createMap() {
		return new HashMap<K, V>();
	}
	
	private CollectionUtils() {
	}

	public static <V> List<V> createList() {
		return new ArrayList<V>();
	}

}
