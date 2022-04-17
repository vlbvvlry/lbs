//package com.arragen;
//
//import java.io.*;
//
//public class GenerateThread extends Thread {
//
//    static int i = 0;
//    int[] array;
//    FileOutputStream fosGenFile;
//    int numThread;
//    String str = "";
//
//    GenerateThread(int[] array, FileOutputStream fosGenFile, int numThread) {
//        this.array = array;
//        this.fosGenFile = fosGenFile;
//        this.numThread = numThread;
//    }
//
//    synchronized public static void writeToFile(String str, FileOutputStream fosGenFile) {
//        try{
//            fosGenFile.write(str.getBytes());
//        } catch(IOException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    synchronized public static String gen(String str, int[] array, int len, int numThread) {
//        if(i < len) {
//            array[i] = (int)(Math.random()*1000);
//            str = String.join("\n", str, Integer.toString(numThread));
//            str = String.join(" ", str, String.format("%d", i));
//            str = String.join(" ", str, (String.format("%3d", array[i]) + ") "));
//            i++;
//        }
//
//        return str;
//    }
//
//    public void run() {
//
//        long time = System.nanoTime();
//        while(i < array.length) {
//            str = String.join("", "", gen(str, array, array.length, numThread));
//        }
//        time = System.nanoTime() - time;
//
//        try{
//            Thread.sleep(numThread*200);
//        } catch(Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println(" " + numThread + "th" + " thread -> " + time/1000000 + " (ms)");
//        writeToFile(str, fosGenFile);
//    }
//
//}


package com.arragen;

public class GenerateThread extends Thread {

    private final int threadNumber;
    private final int left;
    private final int right;

    GenerateThread(int threadNumber, int threadsCount) {
        this.threadNumber = threadNumber;
        this.left = Main.array.length/threadsCount*(threadNumber-1);
        if(threadNumber == threadsCount) {
            this.right = Main.array.length;
            return;
        }
        this.right = Main.array.length/threadsCount*threadNumber;
    }

    synchronized private static void generate(int threadNumber, int i) {
        Main.array[i] = (int)(Math.random()*Main.lot);
        String str = "";
        str = String.join("[", str, Integer.toString(threadNumber));
        Main.printToFile(str);
        str = String.join(" - ", "", String.format("%5d", i));
        Main.printToFile(str);
        str = String.join(" - ", "", (String.format("%3d", Main.array[i]) + "] "));
        Main.printToFile(str + "\n");
    }

    public void run() {
        long time = System.nanoTime();
        for(int i = left; i < right; i++) {
            generate(threadNumber, i);
        }
        time = System.nanoTime() - time;
        time /= 1000000;
        System.out.println("[ " + threadNumber + " -> " +
                String.format("%5d",time) + " ms ]");
    }

}