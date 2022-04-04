package com.parasort;

public class SortThread extends Thread {

    private int _array[];
    private int n_sort;

    SortThread(int _array[], int n_sort) {
        this._array = _array;
        this.n_sort = n_sort;
    }

    public void run() {
         switch(n_sort) {
             case 1:
                Main.ShakerSort(_array);
                break;
            case 2:
                Main.InsertionSort(_array);
                break;
            case 3:
                Main.CombSort(_array);
                break;
         }
    }

}