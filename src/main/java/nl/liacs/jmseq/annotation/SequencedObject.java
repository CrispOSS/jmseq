/*
 * Created on Apr 11, 2010 - 7:37:57 PM
 */
package nl.liacs.jmseq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is a marker annotation for those classes that will contain methods that
 * need to be monitored or verified at runtime.
 * 
 * @author Behrooz Nobakht [behrooz dot nobakht at gmail dot com]
 * @see SequencedMethod
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SequencedObject {

}
