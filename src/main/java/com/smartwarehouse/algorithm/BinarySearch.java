package com.smartwarehouse.algorithm;

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
}
