package org.learning.basics;

import java.util.Comparator;

public class BoxingIssue {
    public static void main(String[] args) {
        // Here we see that the Boxed integer values of 1 don't compare equal to each other because of reference
        // comparison. Therefore, we should cast boxed types to primitive types before comparison
        Comparator<Integer> comparator = (i, j) -> (i < j) ? -1 : (i == j) ? 0 : 1;
        System.out.println(comparator.compare(0, 1));
        System.out.println(comparator.compare(1, 1));
        System.out.println(comparator.compare(1, 2));
        System.out.println(comparator.compare(new Integer(1), new Integer(1)));
        System.out.println(comparator.compare(42, 42));
    }
}
