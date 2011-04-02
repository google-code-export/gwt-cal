package com.gwtcal.client.util;

/**
 * Assertion utilities for flow control.
 *
 * @author Carlos D. Morales
 */
public class Assert {

    /**
     * Asserts that <code>o</code> is not <code>null</code> or throws an
     * <code>IllegalArgumentException</code> with the specified message.
     *
     * @param o The object to assert not to be null
     * @param message A description of why the exception was thrown
     */
    public static void notNull(Object o, String message){
        if ( o == null ) {
            throw new IllegalArgumentException(message);
        }
    }
}
