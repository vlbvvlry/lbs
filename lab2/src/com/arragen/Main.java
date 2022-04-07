package com.arragen;

import java.util.Arrays;
import java.io.*;

public class Main {

    public static void lab2Test(int array[], int threadCount, FileOutputStream fos) {
        GenerateThread gt[] = new GenerateThread[threadCount];
        for(int i = 0; i < threadCount; i++) {
            gt[i] = new GenerateThread(array, fos, i+1);
            gt[i].start();
        }
    }

    public static void main(String args[]) {
        int array[] = new int[10];
        File genFile = new File("result.txt");
        FileOutputStream fosGenFile = null;

        try {
            genFile.createNewFile();
            fosGenFile = new FileOutputStream(genFile, false);
            fosGenFile.write("".getBytes());
            fosGenFile = new FileOutputStream(genFile, true);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        lab2Test(array, 4, fosGenFile);
    }
    
}
