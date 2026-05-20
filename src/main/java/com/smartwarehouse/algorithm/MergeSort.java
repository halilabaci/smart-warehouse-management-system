package com.smartwarehouse.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class MergeSort {

    private MergeSort() {
    }

    public static void sort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        int[] temp = new int[array.length];
        mergeSort(array, temp, 0, array.length - 1);
    }

    public static <T> List<T> sort(List<T> values, Comparator<T> comparator) {
        List<T> copy = new ArrayList<>(values);
        if (copy.size() < 2) {
            return copy;
        }
        mergeSort(copy, new ArrayList<>(copy), 0, copy.size() - 1, comparator);
        return copy;
    }

    private static void mergeSort(int[] array, int[] temp, int left, int right) {
        if (left >= right) {
            return;
        }
        int middle = left + (right - left) / 2;
        mergeSort(array, temp, left, middle);
        mergeSort(array, temp, middle + 1, right);
        merge(array, temp, left, middle, right);
    }

    private static <T> void mergeSort(List<T> values, List<T> temp, int left, int right, Comparator<T> comparator) {
        if (left >= right) {
            return;
        }
        int middle = left + (right - left) / 2;
        mergeSort(values, temp, left, middle, comparator);
        mergeSort(values, temp, middle + 1, right, comparator);
        merge(values, temp, left, middle, right, comparator);
    }

    private static void merge(int[] array, int[] temp, int left, int middle, int right) {
        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i <= middle) {
            temp[k++] = array[i++];
        }

        while (j <= right) {
            temp[k++] = array[j++];
        }

        for (int index = left; index <= right; index++) {
            array[index] = temp[index];
        }
    }

    private static <T> void merge(List<T> values, List<T> temp, int left, int middle, int right,
                                  Comparator<T> comparator) {
        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            if (comparator.compare(values.get(i), values.get(j)) <= 0) {
                temp.set(k++, values.get(i++));
            } else {
                temp.set(k++, values.get(j++));
            }
        }

        while (i <= middle) {
            temp.set(k++, values.get(i++));
        }

        while (j <= right) {
            temp.set(k++, values.get(j++));
        }

        for (int index = left; index <= right; index++) {
            values.set(index, temp.get(index));
        }
    }
}
