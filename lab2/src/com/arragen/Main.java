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

    public static int[] sortTest(int array[], int threadsCount, int sort) {
        SortingThread st[] = new SortingThread[threadsCount];
        int _array[] = array.clone();
        long time = System.nanoTime();
        for(int i = 0; i < threadsCount; i++) {
            st[i] = new SortingThread(_array, sort);
            st[i].start();
        }

        try{
            st[threadsCount - 1].join();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        time = System.nanoTime() - time;
        System.out.println("Sort " + sort + " -> " + time/1000000);

        return _array;
    }

    public static void genTestWithoutSyn(int array[], int threadsCount, FileOutputStream fos) {
        GenerateThreadWithoutSyn gtws[] = new GenerateThreadWithoutSyn[threadsCount];
        for(int i = 0; i < threadsCount; i++) {
            gtws[i] = new GenerateThreadWithoutSyn(array, fos, i+1, threadsCount);
            gtws[i].start();
        }

        try {
            gtws[threadsCount - 1].join();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void genTestSyn(int array[], int threadsCount, FileOutputStream fos) {
        GenerateThread gt[] = new GenerateThread[threadsCount];
        for(int i = 0; i < threadsCount; i++) {
            gt[i] = new GenerateThread(array, fos, i+1);
            gt[i].start();
        }

        try {
            gt[threadsCount - 1].join();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String args[]) {
        int threadsCount = 4;
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

        //genTestSyn(array, threadsCount, fosGenFile);
        genTestWithoutSyn(array, threadsCount, fosGenFile);


        try {        
            fosGenFile.write("\n\nShakerSort".getBytes());
            writeToFile(sortTest(array, threadsCount, 1), fosGenFile);
            fosGenFile.write("\n\nInsertionSort".getBytes());
            writeToFile(sortTest(array, threadsCount, 2), fosGenFile);
            fosGenFile.write("\n\nCombSort".getBytes());       
            writeToFile(sortTest(array, threadsCount, 3), fosGenFile);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

    }
    
}
