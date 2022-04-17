package com.arragen;

public class SortingThread extends Thread {

    private final int[] _array;
    private final int n_sort;
    private final int left;
    private final int right;

    SortingThread(int[] _array, int n_sort, int threadNum, int threadCount) {
        this._array = _array;
        this.n_sort = n_sort;
        this.left = _array.length/threadCount*(threadNum-1);
//        if(threadNum == threadCount) {
//            this.rigth = _array.length;
//            return;
//        }
        this.right = _array.length/threadCount*threadNum;
    }

    public void Swap(int i) {
        int tmp = _array[i];
        _array[i] = _array[i+1];
        _array[i+1] = tmp;
    }

    public void InsertionSort() {
        for (int i = left; i < right; i++) {
            int current = _array[i];
            int j = i - 1;
            while(j >= left && current < _array[j]) {
                _array[j+1] = _array[j];
                j--;
            }
            _array[j+1] = current;
        }
    }

    public void ShakerSort() {
        int L, R;
        L = left;
        R = right - 1;
        while(L <= R) {
            for (int i = R; i > L; i--) {
                if (_array[i] < _array[i - 1]) {
                    Swap(i - 1);
                }
            }
            L++;
            for(int i = L; i < R; i++) {
                if (_array[i] > _array[i + 1]) {
                    Swap(i);
                }
            }
            R--;
        }
    }

    public void CombSort() {
        int step = right;
        boolean swapped = true;
        while (step > 1 || swapped) {
            swapped = false;
            if (step > 1)
                step = (int) (step / 1.247);
            for (int i = left; i + step < right; i++) {
                if (_array[i] > _array[i + step]) {
                    Swap(i);
                    swapped = true;
                }
            }
        }
    }

    public void run() {
        switch (n_sort) {
            case 1 -> ShakerSort();
            case 2 -> InsertionSort();
            case 3 -> CombSort();
        }
    }

}