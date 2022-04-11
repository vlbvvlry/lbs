package com.arragen;

import java.io.*;
import java.util.Objects;

public class GenerateThreadWithoutSyn extends Thread {

    static int threadsCount = 4;
    int array[];
    FileOutputStream fosGenFile;
    int numThread;
    String str = "";

    GenerateThreadWithoutSyn(int array[], FileOutputStream fosGenFile, int numThread, int threadsCount) {
        this.array = array;
        this.fosGenFile = fosGenFile;
        this.numThread = numThread;
        this.threadsCount = threadsCount;
    }

    synchronized public static void writeToFile(String str, FileOutputStream fosGenFile) {
        try{
            fosGenFile.write(str.getBytes());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {

        long time = System.nanoTime();
        for(int i = (array.length/threadsCount*(numThread-1)); i < (array.length/threadsCount*numThread); i++) {
            array[i] = (int)(Math.random()*array.length);
            str = String.join("\n", str, Integer.toString(numThread));
            str = String.join(" ", str, Integer.toString(i));
            str = String.join(" ", str, Integer.toString(array[i]));
        }
        time = System.nanoTime() - time;

        try{
            Thread.sleep(numThread*200);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(" " + numThread + "th" + " thread -> " + time/1000000 + " (ms)");
        writeToFile(str, fosGenFile);
    }

}