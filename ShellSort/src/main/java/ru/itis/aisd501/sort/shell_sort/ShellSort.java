package ru.itis.aisd501.sort.shell_sort;

public class ShellSort {
    public static void shellSort(int[] m) {
        int n = m.length;
        for (int i = n / 2; i > 0; i /= 2) {
            for (int j = i; j < n; ++j) {
                int temp = m[j];
                int k;
                for (k = j; k >= i && m[k - i] > temp; k -= i) {
                    m[k] = m[k - i];
                }
                m[k] = temp;
            }
        }
    }
}
