package com.arragen;

import java.util.Arrays;
import java.io.*;

public class Main {

    public static void writeToFile(int array[], FileOutputStream fos) {
        try {
            fos.write("\n".getBytes());
            fos.write(Arrays.toString(array).getBytes());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int[] sortTest(int array[], int threadCount, int sort) {
        SortingThread st[] = new SortingThread[threadCount];
        int _array[] = array.clone();
        for(int i = 0; i < threadCount; i++) {
            st[i] = new SortingThread(_array, sort);
            st[i].start();
        }

        try{
            st[threadCount - 1].join();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return _array;
    }

    public static void genTestWithoutSyn(int array[], int threadCount, FileOutputStream fos) {
        //
    }

    public static void genTestSyn(int array[], int threadCount, FileOutputStream fos) {
        GenerateThread gt[] = new GenerateThread[threadCount];
        for(int i = 0; i < threadCount; i++) {
            gt[i] = new GenerateThread(array, fos, i+1);
            gt[i].start();
        }

        try {
            gt[threadCount - 1].join();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String args[]) {
        int threadsCount = 1;
        int array[] = new int[1000];
        File genFile = new File("result.txt");
        FileOutputStream fosGenFile = null;

        try {
            genFile.createNewFile();
            (new FileOutputStream(genFile, false)).write("".getBytes());
            fosGenFile = new FileOutputStream(genFile, true);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        genTestSyn(array, threadsCount, fosGenFile);
        genTestWithoutSyn(array, threadsCount, fosGenFile);


        try {
            
            fosGenFile.write("\n\nShakerSort".getBytes());
            writeToFile(sortTest(array, threadsCount, 1), fosGenFile);
            fosGenFile.write("\n\nInsertionSort".getBytes());
            writeToFile(sortTest(array, threadsCount, 2), fosGenFile);
            fosGenFile.write("\n\nCombSort".getBytes());       
            writeToFile(sortTest(array, threadsCount, 3), fosGenFile);

            // Arrays.sort(array);
            // fosGenFile.write("\n\nMAIN".getBytes());       
            // writeToFile(array, fosGenFile);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

    }
    
}
