package com.arragen;

import java.io.*;

public class GenerateThread extends Thread {

    static int i = 0;
    int[] array;
    FileOutputStream fosGenFile;
    int numThread;
    String str = "";

    GenerateThread(int[] array, FileOutputStream fosGenFile, int numThread) {
        this.array = array;
        this.fosGenFile = fosGenFile;
        this.numThread = numThread;
    }

    synchronized public static void writeToFile(String str, FileOutputStream fosGenFile) {
        try{
            fosGenFile.write(str.getBytes());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    synchronized public static String gen(String str, int[] array, int len, int numThread) {
        if(i < len) {
            array[i] = (int)(Math.random()*1000);
            str = String.join("\n", str, Integer.toString(numThread));
            str = String.join(" ", str, String.format("%d", i));
            str = String.join(" ", str, (String.format("%3d", array[i]) + ") "));
            i++;
        }

        return str;
    }

    public void run() {

        long time = System.nanoTime();
        while(i < array.length) {
            str = String.join("", "", gen(str, array, array.length, numThread));
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