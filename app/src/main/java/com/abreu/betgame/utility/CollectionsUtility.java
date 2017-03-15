package com.abreu.betgame.utility;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ricar on 11/03/2017.
 */

public class CollectionsUtility {

    public static <T> Collection<T> filter(Collection<T> target, IPredicate<T> predicate) {
        Collection<T> result = new ArrayList<T>();
        for (T element: target) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }
}
