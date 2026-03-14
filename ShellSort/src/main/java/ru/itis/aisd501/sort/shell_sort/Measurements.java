package ru.itis.aisd501.sort.shell_sort;

import java.io.*;
import java.util.*;

public class Measurements {

    public static void main(String[] args) {
        File dataFolder = new File("dataNums");
        File[] files = dataFolder.listFiles();
        System.out.println("Размер Время(мс) Итерации");
        for (File file : files) {
            int[] array = readArrayFromFile(file);
            int size = fileSize(file);

            if (array.length > 0) {
                Measurement m = measureSort(array);
                System.out.printf("%d %.3f %d\n",
                        size, m.timeMs, m.iterations);
            }
        }
    }

    static class Measurement {
        double timeMs;
        long iterations;

        Measurement(double timeMs, long iterations) {
            this.timeMs = timeMs;
            this.iterations = iterations;
        }
    }

    private static Measurement measureSort(int[] original) {
        int[] array = Arrays.copyOf(original, original.length);
        long iterations = 0;
        int n = array.length;
        double startTime = System.nanoTime() / 1_000_000.0;

        for (int i = n / 2; i > 0; i /= 2) {
            for (int j = i; j < n; ++j) {
                int temp = array[j];
                int k;
                for (k = j; k >= i && array[k - i] > temp; k -= i) {
                    array[k] = array[k - i];
                    iterations++;
                }
                array[k] = temp;
            }
        }
        double endTime = System.nanoTime() / 1_000_000.0;
        return new Measurement(endTime - startTime, iterations);
    }

    private static int[] readArrayFromFile(File file) {
        List<Integer> list = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextInt()) {
                list.add(scanner.nextInt());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка при чтении файла: " + file.getName());
            return new int[0];
        }

        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    private static int fileSize(File file) {
        List<Integer> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextInt()) {
                list.add(scanner.nextInt());
            }
            return list.size();
        } catch (FileNotFoundException e) {
            return 0;
        }
    }

}
