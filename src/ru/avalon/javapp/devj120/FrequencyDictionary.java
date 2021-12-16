package ru.avalon.javapp.devj120;

import java.io.*;
import java.util.*;

public class FrequencyDictionary {
    private final Map<String, Integer > dictionaryList = new HashMap<>();

    // чтение файла
    public void read(String fileName){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String s;
            while ((s = br.readLine()) != null) {
                if (s.isEmpty()) continue;

                StringBuilder word = new StringBuilder();
                char[] charArray = s.toCharArray();
                char curChar;
                for (int i = 0; i < s.length(); i++) {
                    curChar = charArray[i];
                    if (Character.UnicodeBlock.of(charArray[i]).equals(Character.UnicodeBlock.CYRILLIC) || curChar == ('-')) {
                        word.append(curChar);
                        if (i!=s.length()-1) continue;
                    }

                   if (!word.isEmpty()) {
                        if (dictionaryList.containsKey(word.toString().toLowerCase(Locale.ROOT))) {
                            int savVal = dictionaryList.get(word.toString().toLowerCase(Locale.ROOT));
                            dictionaryList.put(word.toString().toLowerCase(Locale.ROOT), savVal+1);
                            word.delete(0, word.length());
                            continue;
                        } else {
                            dictionaryList.put(word.toString().toLowerCase(), 1);
                            word.delete(0, word.length());
                        }
                   }
                }
            }
        } catch (Exception e) {
            System.out.println("File \"" + fileName + "\" isn't exist or can't read.");
            System.exit(1);
        }
    }

    // запись результата в два файла отчета
    public void saveReport(){
        final Map<String , Integer> sorted = sortedMap(dictionaryList);

        try {
            PrintWriter pw = new PrintWriter(Main.REPORT1);
            for (Map.Entry<String, Integer> kv : sorted.entrySet()) {
                Map<String, Integer> finalSorted = sorted;
                sorted.forEach((k, v) -> pw.println(k + " relative=" + v + "  absolute=" + (double)v/ finalSorted.size()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Some kind of problem with creating file " + Main.REPORT1);
            System.exit(1);
        }


        try {
            PrintWriter pw = new PrintWriter(Main.REPORT2);

            // создадим компаратор, который будет сравнивать значения, а не ключи
            Comparator<String > comparator =  new Comparator<String>() {
                public int compare(String  k1,String  k2) {
                    int compare = sorted.get(k2).compareTo(sorted.get(k1));
                    if (compare == 0)
                        return 1;
                    return compare;
                }
            };
            Map<String, Integer> sortedByValues = new TreeMap<>(comparator);
            sortedByValues.putAll(sorted);

            for (Map.Entry<String, Integer> kv : sortedByValues.entrySet()) {
                sortedByValues.forEach((k,v) -> pw.println(k + " relative=" + v + "  absolute=" + (double)v/ sorted.size()));
            }
/*
            dictionaryList.entrySet().stream().sorted(Map.Entry.<String , Integer>comparingByValue().reversed())
                    .forEach(x -> pw.println(x.getKey() + "  relative=" + x.getValue() + " absolute="
                            + (double)x.getValue()/dictionaryList.size()));

 */
        } catch (FileNotFoundException e) {
            System.out.println("Some kind of problem with creating file " + Main.REPORT2);
            System.exit(1);
        }

    }

    public static void check(String fileName) {
            File file = new File(fileName);
            if (!file.isFile()) {
                System.out.println("File \"" + fileName + "\" isn't exist or can't read.");
                System.exit(1);
            }
    }

    private static Map<String , Integer> sortedMap(Map<String, Integer> list) {
        Map<String , Integer> sorted = new TreeMap<>();
        sorted.putAll(list);
        return sorted;
    }
}
