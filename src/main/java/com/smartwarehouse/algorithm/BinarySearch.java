package com.smartwarehouse.algorithm;

import java.util.Comparator;
import java.util.List;

public final class BinarySearch {

    private BinarySearch() {
    }

    public static int search(int[] sortedArray, int target) {
        int left = 0;
        int right = sortedArray.length - 1;

        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (sortedArray[middle] == target) {
                return middle;
            }
            if (sortedArray[middle] < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return -1;
    }

    public static <T> int search(List<T> sortedValues, T target, Comparator<T> comparator) {
        int left = 0;
        int right = sortedValues.size() - 1;

        while (left <= right) {
            int middle = left + (right - left) / 2;
            int comparison = comparator.compare(sortedValues.get(middle), target);
            if (comparison == 0) {
                return middle;
            }
            if (comparison < 0) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return -1;
    }
}
