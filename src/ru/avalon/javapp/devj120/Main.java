package ru.avalon.javapp.devj120;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        // проверка на вызов программы без параметров
        if (args.length == 0) {
            help();
        }
        // проверка на корректность заданных параметров
        Check(args);

        for (String fileName: args) {
            FrequencyDictionary fd = new FrequencyDictionary(fileName);
        }


    }

    private static void Check(String[] args) {
        for (String fileName: args) {
            File file = new File(fileName);
            if (!file.isFile()) {
                System.out.println("File \"" + fileName + "\" isn't exist or can't read.");
                System.exit(1);
            }
        }
    }

    private static void help() {
        System.out.println("\nThe program is designed to calculate the frequency of using words in the text.\n" +
                "Input parameters: file names\n" +
                "Examples: FrequencyDictionary text1 text2 text3\n");
        System.exit(0);
    }
}
