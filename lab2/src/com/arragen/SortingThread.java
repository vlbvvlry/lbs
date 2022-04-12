package com.arragen;

public class SortingThread extends Thread {
    
    private int array[];
    private int sort;
    private int numThread;

    SortingThread(int array[], int sort, int numThread) {
        this.array = array;
        this.sort = sort;
        this.numThread = numThread;
    }

    public void run() {
        try {
            Thread.sleep(numThread*1);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
         switch(sort) {
             case 1:
                SortingTypes.ShakerSort(array);
                break;
            case 2:
                SortingTypes.InsertionSort(array);
                break;
            case 3:
                SortingTypes.CombSort(array);
                break;
         }
    }

}