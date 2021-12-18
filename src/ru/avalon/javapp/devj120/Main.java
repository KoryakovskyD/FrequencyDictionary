package ru.avalon.javapp.devj120;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static final String REPORT1 = "report1.txt";
    public static final String REPORT2 = "report2.txt";


    public static void main(String[] args) {

        // проверка на вызов программы без параметров
        if (args.length == 0) {
            help();
        }

        FrequencyDictionary fd = new FrequencyDictionary();
        for (String fileName : args) {
            try {
                 fd.read(fileName);
            } catch (IOException e) {
                 System.out.println("File \"" + fileName + "\" isn't exist or can't read.");
                 System.exit(1);
            }
        }

        try {
            fd.saveReport();
        } catch (FileNotFoundException e) {
            System.out.println("Some kind of problem with writing in file " + REPORT1 + " or " + REPORT2);
            System.exit(1);
        }
    }

    private static void help() {
        System.out.println("\nThe program is designed to calculate the frequency of using words in the text.\n" +
                "Input parameters: file names\n" +
                "Examples: FrequencyDictionary text1 text2 text3\n");
        System.exit(0);
    }
}