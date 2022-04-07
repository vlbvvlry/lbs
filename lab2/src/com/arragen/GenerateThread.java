package com.arragen;

import java.io.*;

public class GenerateThread extends Thread {

    static int i = 0;
    int array[];
    FileOutputStream fosGenFile;
    int numThread;
    String str = "";

    GenerateThread(int array[], FileOutputStream fosGenFile, int numThread) {
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

    synchronized public static String gen(String str, int array_i, int len, int numThread) {
        if(i < len) {
            array_i = (int)(Math.random()*len);
            str = String.join("\n", str, Integer.toString(numThread));
            str = String.join(" ", str, Integer.toString(i));
            str = String.join(" ", str, Integer.toString(array_i));
            i++;
        }

        return str;
    }

    public void run() {
        while(i < array.length) {
            str = String.join("", "", gen(str, array[i], array.length, numThread));
        }

        try{
            Thread.sleep(numThread*200);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        writeToFile(str, fosGenFile);
    }

}