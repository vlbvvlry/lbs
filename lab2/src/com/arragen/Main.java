package com.arragen;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;

public class Main {

    public static final int lot = 1000;
    public static int[] array = null;
    private static final File f = new File("result.txt");
    private static final File setFile = new File("set.txt");
    private static FileOutputStream fos = null;
    private static FileInputStream fin = null;

    public static void printToFile(String str) {
        try {
            fos.write(str.getBytes());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int[] sortTest(int threadsCount, int sort) {
        SortingThread[] st = new SortingThread[threadsCount];
        int[] _array = array.clone();
        long time = System.nanoTime();
        for(int i = 0; i < threadsCount; i++) {
            st[i] = new SortingThread(_array, sort, i+1, threadsCount);
            st[i].start();
        }
        try{
            for(int i = 0; i < threadsCount; i++) {
                st[i].join();
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        mergeSort(_array, _array.length);
        time = System.nanoTime() - time;
        time /= 1000000;
        System.out.println("Sort " + sort + " -> " +
                String.format("%5d",time) + "   (" +
                threadsCount + " threads)");
        return _array;
    }

    public static void genTestWithoutSyn(int threadsCount) {
        GenerateThreadWithoutSyn[] gtws = new GenerateThreadWithoutSyn[threadsCount];
        for(int i = 0; i < threadsCount; i++) {
            gtws[i] = new GenerateThreadWithoutSyn(i+1, threadsCount);
            gtws[i].start();
        }
        try {
            for(int i = 0; i < threadsCount; i++) {
                gtws[i].join();
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void genTestSyn(int threadsCount) {
        GenerateThread[] gt = new GenerateThread[threadsCount];
        for(int i = 0; i < threadsCount; i++) {
            gt[i] = new GenerateThread(i+1, threadsCount);
            gt[i].start();
        }
        try {
            for(int i = 0; i < threadsCount; i++) {
                gt[i].join();
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
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

    private static String readFile() {
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

    private static int strToInt(String str) {
        int i = 0;
        try {
            NumberFormat nf = NumberFormat.getInstance();
            i = nf.parse(str).intValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    public static void main(String[] args) {
        creatingFileAttributes();

        String[] settings = readFile().split("\n");
        int size = strToInt(settings[0]);
        int threadsCount = strToInt(settings[1]);
        int CS = strToInt(settings[2]);
        array = new int[size];

        if(CS == 0) {
            System.out.print("Without critical section: \n");
        } else {
            System.out.print("With critical section: \n");
        }
        for(int i = 1; i < (threadsCount + 1); i+=i) {
            creatingFileAttributes();
            System.out.println();
            if(CS == 0) {
                genTestWithoutSyn(i);
            } else {
                genTestSyn(i);
            }
        }
        for(int i = 1; i < 4; i++) {
            System.out.println();
            for(int j = 1; j < 9; j+=j) {
                sortTest(j,i);
            }
        }
    }

}
