package com.parasort;

//import java.util.Arrays;

public class Main {   

    public static void Filling(int _array[], int _lot) {
        for (int i = 0; i < _array.length; i++)
            _array[i] = (int) (Math.random()*_lot);
    }
    public static void Swap(int _array[], int i) {
        int tmp = _array[i];
        _array[i] = _array[i-1];
        _array[i-1] = tmp;
    }
    public static void InsertionSort(int _array[]) {
         for (int i = 1; i < _array.length; i++) {
            int current = _array[i];
            int j = i - 1;
            while(j >= 0 && current < _array[j]) {
                _array[j+1] = _array[j];
                j--;
            }
            _array[j+1] = current;
        }
    }
    public static void ShakerSort(int _array[]) {
        int L, R;
        L = 1;
        R = _array.length - 1;
        while(L <= R) {
            for (int i = R; i >= L; i--)
                if(_array[i-1] > _array[i])
                    Swap(_array, i);
            L++;
            for(int i = L; i <= R; i++)
                if(_array[i-1] > _array[i])
                    Swap(_array, i);
            R--; 
        }
    }
    public static void CombSort(int _array[]) {
        int step = _array.length;
        boolean swapped = true;
        while (step > 1 || swapped) {
            swapped = false;
            if (step > 1)
                step = (int) (step / 1.247);
            for (int i = 0; i + step < _array.length; i++) {
                if (_array[i] > _array[i + step]) {
                    int tmp = _array[i];
                    _array[i] = _array[i + step];
                    _array[i + step] = tmp;
                    swapped = true;
                }
            }
        }
    }
    public static long Test(int _array[], int threads, int sort) {
        SortThread st[] = new SortThread[threads];
        int clone_array[] = _array.clone();
        long start = System.nanoTime();
        for(int i = 0; i < threads; i++) {
            st[i] = new SortThread(clone_array, sort);
            st[i].start();
        }
        try {
            st[threads - 1].join();
        } catch(InterruptedException e) {
            System.out.println("Something's wrong..");
        }
        long time = System.nanoTime() - start;
        return time/1000000;
    }

    public static void main(String args[]) {

        int _arr25k[] = new int[25000];
        Filling(_arr25k, _arr25k.length);

        System.out.print("Start ->");

        long time11 = Test(_arr25k, 1, 1);
        long time12 = Test(_arr25k, 2, 1);
        long time13 = Test(_arr25k, 4, 1);
        long time14 = Test(_arr25k, 8, 1);

        long time21 = Test(_arr25k, 1, 2);
        long time22 = Test(_arr25k, 2, 2);
        long time23 = Test(_arr25k, 4, 2);
        long time24 = Test(_arr25k, 8, 2);

        long time31 = Test(_arr25k, 1, 3);
        long time32 = Test(_arr25k, 2, 3);
        long time33 = Test(_arr25k, 4, 3);
        long time34 = Test(_arr25k, 8, 3);

        System.out.print(" Complete\n");

        System.out.print("\nShakerSort\n\t1: " + time11 + "\n\t2: " + time12 + "\n\t4: " + time13 + "\n\t8: " + time14 + "\n\t(ms)");
        System.out.print("\nInsertionSort\n\t1: " + time21 + "\n\t2: " + time22 + "\n\t4: " + time23 + "\n\t8: " + time24 + "\n\t(ms)");
        System.out.print("\nCombSort\n\t1: " + time31 + "\n\t2: " + time32 + "\n\t4: " + time33 + "\n\t8: " + time34 + "\n\t(ms)");
    }
 
}