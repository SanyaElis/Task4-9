package ru.vsu.cs.eliseev;

import java.util.Arrays;

public class SortState {
    private final int[] arr;
    private final int tempI;
    private final int tempJ;

    @Override
    public String toString() {
        return "SortState{" +
                "arr=" + Arrays.toString(arr) +
                ", tempI=" + tempI +
                ", tempJ=" + tempJ +
                '}' + "\n";
    }

    public SortState(int[] arr, int tempI, int tempJ) {
        this.arr = arr;
        this.tempI = tempI;
        this.tempJ = tempJ;
    }

    public int[] getArr() {
        return arr;
    }

    public int getTempJ() {
        return tempJ;
    }
}
