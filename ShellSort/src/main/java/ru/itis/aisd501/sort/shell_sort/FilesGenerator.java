package ru.itis.aisd501.sort.shell_sort;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class FilesGenerator {
    public static void main(String[] args) {
        for (int size = 100, count = 1; size <= 10000 && count <= 50; size += 201, count++) {
            generateFile(size, count);
        }
    }

    private static void generateFile(int size, int fileNumber) {
        String filename = "dataNums/array_" + size + "_" + fileNumber + ".txt";
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            Random rand = new Random();
            for (int i = 0; i < size; i++) {
                writer.print(rand.nextInt(10000) + " ");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла " + filename);
            e.printStackTrace();
        }
    }
}
