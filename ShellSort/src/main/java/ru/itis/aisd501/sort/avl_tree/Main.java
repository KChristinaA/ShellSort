package ru.itis.aisd501.sort.avl_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int size = 10000;
        int toSearch = 100;
        int toDelete = 1000;

        System.out.println("Генерация массива...");
        int[] array = generator(size);

        System.out.println("Создание дерева...");
        AVLTree avlTree = new AVLTree();

        System.out.println("-----НАЧАЛО ВЫЧИСЛЕНИЙ-----");

        System.out.println("Составление дерева...");
        List<Long> insertTime = new ArrayList<>();
        List<Integer> insertOps = new ArrayList<>();
        measuresInsert(avlTree, array, insertTime, insertOps);
        System.out.printf("Среднее значение по вычислениям:\nСреднее по времени: %.5f мс\nСреднее по количеству операций: %.1f\n", findAverageTime(insertTime)/1_000_000.0, findAverageOps(insertOps));

        System.out.println("Поиск 100 элементов...");
        List<Long> searchTime = new ArrayList<>();
        List<Integer> searchOps = new ArrayList<>();
        measuresSearch(avlTree, array, toSearch, searchTime, searchOps);
        System.out.printf("Среднее значение по вычислениям:\nСреднее по времени: %.5f мс\nСреднее по количеству операций: %.1f\n", findAverageTime(searchTime)/1_000_000.0, findAverageOps(searchOps));

        System.out.println("Удаление 1000 элементов...");
        List<Long> deleteTime = new ArrayList<>();
        List<Integer> deleteOps = new ArrayList<>();
        measuresDelete(avlTree, array, toDelete, deleteTime, deleteOps);
        System.out.printf("Среднее значение по вычислениям:\nСреднее по времени: %.5f мс\nСреднее по количеству операций: %.1f\n", findAverageTime(deleteTime)/1_000_000.0, findAverageOps(deleteOps));
    }

    private static void measuresInsert(AVLTree avlTree, int[] array, List<Long> insertTime, List<Integer> insertOps) {
        for (int x : array) {
            avlTree.resetOperationCount();
            long start = System.nanoTime();
            avlTree.insert(x);
            long end = System.nanoTime();
            insertTime.add(end - start);
            insertOps.add(avlTree.getOperationCount());
        }
        System.out.println("Вставка окончена");
    }

    private static void measuresSearch(AVLTree tree, int[] arr, int count, List<Long> searchTime, List<Integer> searchOps) {
        Random random = new Random();
        for (int i = 0; i < count; ++i) {
            int index = random.nextInt(arr.length);
            tree.resetOperationCount();
            long start = System.nanoTime();
            tree.search(arr[index]);
            long end = System.nanoTime();
            searchTime.add(end - start);
            searchOps.add(tree.getOperationCount());
        }
        System.out.println("Поиск окончен");
    }

    private static void measuresDelete(AVLTree tree, int[] arr, int count, List<Long> deleteTime, List<Integer> deleteOps) {
        Random random = new Random();
        for (int i = 0; i < count; ++i) {
            int index = random.nextInt(arr.length);
            tree.resetOperationCount();
            long start = System.nanoTime();
            tree.delete(arr[index]);
            long end = System.nanoTime();
            deleteTime.add(end - start);
            deleteOps.add(tree.getOperationCount());
        }
        System.out.println("Удаление окончено");
    }


    private static int[] generator(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; ++i) {
            array[i] = random.nextInt(100000);
        }
        return array;
    }

    public static double findAverageTime(List<Long> time) {
        int sum = 0;
        for (long x : time) sum += x;
        return (double) sum/time.size();
    }

    public static double findAverageOps(List<Integer> operations) {
        int sum = 0;
        for (int x : operations) sum += x;
        return (double) sum/operations.size();
    }
}
