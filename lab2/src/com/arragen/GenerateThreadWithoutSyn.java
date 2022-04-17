package com.arragen;

public class GenerateThreadWithoutSyn extends Thread {

    private final int threadNumber;
    private final int left;
    private final int right;

    GenerateThreadWithoutSyn(int threadNumber, int threadsCount) {
        this.threadNumber = threadNumber;
        this.left = Main.array.length/threadsCount*(threadNumber-1);
        if(threadNumber == threadsCount) {
            this.right = Main.array.length;
            return;
        }
        this.right = Main.array.length/threadsCount*threadNumber;
    }

    public void run() {
        long time = System.nanoTime();
        for(int i = left; i < right; i++) {
            Main.array[i] = (int)(Math.random()*Main.lot);
            String str = "";
            str = String.join("[", str, Integer.toString(threadNumber));
            Main.printToFile(str);
            str = String.join(" - ", "", String.format("%5d", i));
            Main.printToFile(str);
            str = String.join(" - ", "", (String.format("%3d", Main.array[i]) + "] "));
            Main.printToFile(str + "\n");
        }
        time = System.nanoTime() - time;
        time /= 1000000;
        System.out.println("[ " + threadNumber + " -> " +
                String.format("%5d",time) + " ms ]");
    }

}