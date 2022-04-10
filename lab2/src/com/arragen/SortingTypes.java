package com.arragen;

public class SortingTypes {

    private static void Swap(int array[], int i) {
        int tmp = array[i];
        array[i] = array[i-1];
        array[i-1] = tmp;
    }

    public static void InsertionSort(int array[]) {
        for (int i = 1; i < array.length; i++) {
            int current = array[i];
            int j = i - 1;
            while(j >= 0 && current < array[j]) {
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = current;
        }
    }

    public static void ShakerSort(int array[]) {
        int L, R;
        L = 1;
        R = array.length - 1;
        while(L <= R) {
            for (int i = R; i >= L; i--) {
                if(array[i-1] > array[i]) {
                    Swap(array, i);
                }                   
            }              
            L++;
            for(int i = L; i <= R; i++) {
                if(array[i-1] > array[i]) {
                    Swap(array, i);
                }
            }
            R--; 
        }
    }

    public static void CombSort(int array[]) {
        int step = array.length;
        boolean swapped = true;
        while (step > 1 || swapped) {
            swapped = false;
            if (step > 1)
                step = (int) (step / 1.247);
            for (int i = 0; i + step < array.length; i++) {
                if (array[i] > array[i + step]) {
                    int tmp = array[i];
                    array[i] = array[i + step];
                    array[i + step] = tmp;
                    swapped = true;
                }
            }
        }
    }
    
}