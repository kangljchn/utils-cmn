package com.ie.common.utilities.cmnutils;

import java.util.HashMap;
import java.util.Set;

/**
 * A 3-dimensional HashMap is a HashMap that enables you to refer to values via three keys rather than one. The
 * underlying implementation is simply a HashMap containing HashMap containing a HashMap, each of which maps to values.
 *
 * @param <K1>
 *            the first key type
 * @param <K2>
 *            the second key type
 * @param <K3>
 *            the third key type
 * @param <V>
 *            the value type
 * @author Kasper B. Graversen
 * @since 2.0.0 (migrated from Spiffy 0.5)
 */
public class IE3DHashMap<K1, K2, K3, V> {

    private final HashMap<K1, HashMap<K2, HashMap<K3, V>>> map = new HashMap<>();

    /**
     * Existence check of a value (or <tt>null</tt>) mapped to the keys.
     *
     * @param firstKey
     *            first key
     * @param secondKey
     *            second key
     * @return true when an element (or <tt>null</tt>) has been stored with the keys
     */
    public boolean containsKey(final K1 firstKey, final K2 secondKey) {
        // existence check on inner map
        final HashMap<K2, HashMap<K3, V>> innerMap1 = map.get(firstKey);
        if( innerMap1 == null ) {
            return false;
        }

        return innerMap1.containsKey(secondKey);
    }

    /**
     * Existence check of a value (or <tt>null</tt>) mapped to the keys.
     *
     * @param firstKey
     *            first key
     * @param secondKey
     *            second key
     * @param thirdKey
     *            third key
     * @return true when an element (or <tt>null</tt>) has been stored with the keys
     */
    public boolean containsKey(final K1 firstKey, final K2 secondKey, final K3 thirdKey) {
        // existence check on inner map
        final HashMap<K2, HashMap<K3, V>> innerMap1 = map.get(firstKey);
        if( innerMap1 == null ) {
            return false;
        }

        // existence check on inner map1
        final HashMap<K3, V> innerMap2 = innerMap1.get(secondKey);
        if( innerMap2 == null ) {
            return false;
        }
        return innerMap2.containsKey(thirdKey);
    }

    /**
     * Fetch the outermost Hashmap.
     *
     * @param firstKey
     *            first key
     * @return the the innermost hashmap
     */
    public HashMap<K2, HashMap<K3, V>> get(final K1 firstKey) {
        return map.get(firstKey);
    }

    /**
     * Fetch the outermost Hashmap as a IE2DHashMap.
     *
     * @param firstKey
     *            first key
     * @return the the innermost hashmap
     */
    public IE2DHashMap<K2, K3, V> getAs2D(final K1 firstKey) {
        final HashMap<K2, HashMap<K3, V>> innerMap1 = map.get(firstKey);
        if( innerMap1 != null ) {
            return new IE2DHashMap<>(innerMap1);
        } else {
            return null;
        }

    }

    /**
     * Fetch the innermost Hashmap.
     *
     * @param firstKey
     *            first key
     * @param secondKey
     *            second key
     * @return the the innermost hashmap
     */
    public HashMap<K3, V> get(final K1 firstKey, final K2 secondKey) {
        // existence check on inner map
        final HashMap<K2, HashMap<K3, V>> innerMap1 = map.get(firstKey);
        if( innerMap1 == null ) {
            return null;
        }

        return innerMap1.get(secondKey);
    }

    /**
     * Fetch a value from the Hashmap.
     *
     * @param firstKey
     *            first key
     * @param secondKey
     *            second key
     * @param thirdKey
     *            third key
     * @return the element or null.
     */
    public V get(final K1 firstKey, final K2 secondKey, final K3 thirdKey) {
        // existence check on inner map
        final HashMap<K2, HashMap<K3, V>> innerMap1 = map.get(firstKey);
        if( innerMap1 == null ) {
            return null;
        }

        // existence check on inner map1
        final HashMap<K3, V> innerMap2 = innerMap1.get(secondKey);
        if( innerMap2 == null ) {
            return null;
        }
        return innerMap2.get(thirdKey);
    }

    /**
     * Insert a value
     *
     * @param firstKey
     *            first key
     * @param secondKey
     *            second key
     * @param thirdKey
     *            third key
     * @param value
     *            the value to be inserted. <tt>null</tt> may be inserted as well.
     * @return null or the value the insert is replacing.
     */
    public Object put(final K1 firstKey, final K2 secondKey, final K3 thirdKey, final V value) {
        // existence check on inner map1
        HashMap<K2, HashMap<K3, V>> innerMap1 = map.get(firstKey);

        if( innerMap1 == null ) {
            // no inner map, create it
            innerMap1 = new HashMap<>();
            map.put(firstKey, innerMap1);
        }

        // existence check on inner map1
        HashMap<K3, V> innerMap2 = innerMap1.get(secondKey);
        if( innerMap2 == null ) {
            // no inner map, create it
            innerMap2 = new HashMap<>();
            innerMap1.put(secondKey, innerMap2);
        }

        return innerMap2.put(thirdKey, value);
    }

    /**
     * Returns the number of key-value mappings in this map for the first key.
     *
     * @return Returns the number of key-value mappings in this map for the first key.
     */
    public int size() {
        return map.size();
    }

    /**
     * Returns the number of key-value mappings in this map for the second key.
     *
     * @param firstKey
     *            the first key
     * @return Returns the number of key-value mappings in this map for the second key.
     */
    public int size(final K1 firstKey) {
        // existence check on inner map
        final HashMap<K2, HashMap<K3, V>> innerMap = map.get(firstKey);
        if( innerMap == null ) {
            return 0;
        }
        return innerMap.size();
    }

    /**
     * Returns the number of key-value mappings in this map for the third key.
     *
     * @param firstKey
     *            the first key
     * @param secondKey
     *            the second key
     * @return Returns the number of key-value mappings in this map for the third key.
     */
    public int size(final K1 firstKey, final K2 secondKey) {
        // existence check on inner map
        final HashMap<K2, HashMap<K3, V>> innerMap1 = map.get(firstKey);
        if( innerMap1 == null ) {
            return 0;
        }

        // existence check on inner map1
        final HashMap<K3, V> innerMap2 = innerMap1.get(secondKey);
        if( innerMap2 == null ) {
            return 0;
        }
        return innerMap2.size();
    }

    /**
     * Returns a set of the keys of the outermost map.
     *
     * @return the key set for the outermost map
     */
    public Set<K1> keySet() {
        return map.keySet();
    }

}
