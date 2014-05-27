/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.models;

/**
 *
 * @author NERTIL
 */
public class Pair<U, V> {

 /**
     * The first element of this <code>Pair</code>
     */
    private U first;

    /**
     * The second element of this <code>Pair</code>
     */
    private V second;

    /**
     * Constructs a new <code>Pair</code> with the given values.
     * 
     * @param first  the first element
     * @param second the second element
     */
    public Pair(U first, V second) {

        this.first = first;
        this.second = second;
    }

    /**
     * @return the first
     */
    public U getFirst() {
        return first;
    }

    /**
     * @return the second
     */
    public V getSecond() {
        return second;
    }
    
}
