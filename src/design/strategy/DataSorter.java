package design.strategy;

/**
 * Created by changbao on 2018/7/23 0023 星期一 .
 */
public class DataSorter {
    public static void sort(int[] a) {
        for (int i = a.length; i > 0; i--) {
            for (int j = i - 1; j < i; j--) {
                swap(a, i, j);
            }
        }
    }

    private static void swap(int[] a, int x, int y) {
        int temp = a[x];
        a[x] = a[y];
        a[y] = temp;
    }

    public static void print(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + "    ");
        }
    }
}
