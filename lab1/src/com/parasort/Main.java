package com.parasort;

import java.util.Arrays;
import java.io.*;

public class Main {

    private static final File f = new File("result.txt");
    private static FileOutputStream fos = null;

    public static void Filling(int[] _array, int _lot) {
        for (int i = 0; i < _array.length; i++)
            _array[i] = (int) (Math.random()*_lot);
    }

//    public static void merging(int _array, int threads) {
//        //
//
//    }

    public static void merge(
            int[] a, int[] l, int[] r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

    public static void mergeSort(int[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(a, l, r, mid, n - mid);
    }

    public static void Test(int[] _array, int threads, int sort) {
        SortThread[] st = new SortThread[threads];
        int[] clone_array = _array.clone();
        long time = System.nanoTime();
        for(int i = 0; i < threads; i++) {
            st[i] = new SortThread(clone_array, sort, i+1, threads);
            st[i].start();
        }
        try {
            st[threads - 1].join();
        } catch(InterruptedException e) {
            System.out.println("Something's wrong..");
        }
        mergeSort(clone_array, clone_array.length);
        time = System.nanoTime() - time;
        time /= 1000000;
        printToFile(sort + "th sort - " + threads + " threads - " +
                String.format("%5d",time) + " ms --> " +
                Arrays.toString(clone_array) + "\n");
    }

    private static void creatingFileAttributes() {
        try {
            if(f.createNewFile()) {
                System.out.println("File created.");
            }
            fos = new FileOutputStream(f, true);
            (new FileOutputStream(f, false)).write("".getBytes());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printToFile(String str) {
        try {
            fos.write(str.getBytes());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        creatingFileAttributes();

        int[] _arr25k = new int[5000];
        Filling(_arr25k, _arr25k.length);
        printToFile("Source Array: \n" + Arrays.toString(_arr25k));

        printToFile("\n\nShakerSort:\n");
        Test(_arr25k, 1, 1);
        Test(_arr25k, 2, 1);
        Test(_arr25k, 4, 1);
        Test(_arr25k, 8, 1);

        printToFile("\n\nInsertionSort:\n");
        Test(_arr25k, 1, 2);
        Test(_arr25k, 2, 2);
        Test(_arr25k, 4, 2);
        Test(_arr25k, 8, 2);

        printToFile("\n\nCombSort:\n");
        Test(_arr25k, 1, 3);
        Test(_arr25k, 2, 3);
        Test(_arr25k, 4, 3);
        Test(_arr25k, 8, 3);
    }
 
}