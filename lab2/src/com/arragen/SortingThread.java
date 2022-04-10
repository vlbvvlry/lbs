package com.arragen;

public class SortingThread extends Thread {
    
    private int array[];
    private int sort;

    SortingThread(int array[], int sort) {
        this.array = array;
        this.sort = sort;
    }

    public void run() {
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