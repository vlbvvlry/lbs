package com.parasort;

import java.util.Arrays;
import java.io.*;

public class Main {

    private static final File f = new File("result.txt");
    private static final File setFile = new File("set.txt");
    private static FileOutputStream fos = null;
    private static FileInputStream fin = null;

    public static void filling(int[] _array, int _lot) {
        for (int i = 0; i < _array.length; i++)
            _array[i] = (int) (Math.random()*_lot);
    }

    public static void merge(int[] a, int[] l, int[] r, int left, int right) {

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

    public static void test(int[] _array, int threads, int sort) {
        SortThread[] st = new SortThread[threads];
        int[] clone_array = _array.clone();
        long time = System.nanoTime();
        for(int i = 0; i < threads; i++) {
            st[i] = new SortThread(clone_array, sort, i+1, threads);
            st[i].start();
        }
        try {
            for(int i = 0; i < threads; i++) {
                st[i].join();
            }
        } catch(InterruptedException e) {
            System.out.println(e.getMessage());
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
                System.out.println("File \"result.txt\" created.");
            }
            fin = new FileInputStream(setFile);
            fos = new FileOutputStream(f, true);
            (new FileOutputStream(f, false)).write("".getBytes());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String readFile(FileInputStream fin) {
        String str = "";
        try {
            int i = -1;
            while((i = fin.read()) != -1) {
                str = String.join("", str, String.format("%c",(char)i));
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return str;
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

        int size = Integer.parseInt(readFile(fin));
        int[] _array = new int[size];
        filling(_array, 1000);
        printToFile("Source Array: \n" + Arrays.toString(_array));
        printToFile("\n\nClone Array: \n" + Arrays.toString(_array.clone()));

        printToFile("\n\nShakerSort:\n");
        test(_array, 1, 1);
        test(_array, 2, 1);
        test(_array, 4, 1);
        test(_array, 8, 1);

        printToFile("\n\nInsertionSort:\n");
        test(_array, 1, 2);
        test(_array, 2, 2);
        test(_array, 4, 2);
        test(_array, 8, 2);

        printToFile("\n\nCombSort:\n");
        test(_array, 1, 3);
        test(_array, 2, 3);
        test(_array, 4, 3);
        test(_array, 8, 3);
    }
 
}