package ru.avalon.javapp.devj120;

import java.io.*;
import java.util.*;

public class FrequencyDictionary {
    private final Map<String, Integer > dictionaryList = new HashMap<>();

    public FrequencyDictionary(String fileName) throws IOException{
        read(fileName);
        save();
    }

    private void read(String fileName) throws IOException{
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String s;
            while ((s = br.readLine()) != null) {
                if (s.isEmpty()) continue;

                String word = "";
                char[] charArray = s.toCharArray();
                Character curChar;
                for (int i = 0; i < s.length(); i++) {
                    curChar = charArray[i];
                    if (Character.UnicodeBlock.of(charArray[i]).equals(Character.UnicodeBlock.CYRILLIC) || curChar.equals('-')) {
                        word = word + curChar;
                        if (i!=s.length()-1) continue;
                    }

                   if (word != "") {
                        if (dictionaryList.containsKey(word.toLowerCase(Locale.ROOT))) {
                            int savVal = dictionaryList.get(word.toLowerCase(Locale.ROOT));
                            dictionaryList.replace(word.toLowerCase(Locale.ROOT), savVal, savVal+1);
                            word="";
                            continue;
                        } else {
                            dictionaryList.put(word.toLowerCase(), 1);
                            word = "";
                        }
                   }
                }
            }
        }
    }

    private void save() throws IOException {
        try(PrintWriter pw = new PrintWriter("report1.txt")) {
            TreeMap<String , Integer> sorted = new TreeMap<>();
            sorted.putAll(dictionaryList);
            for (Map.Entry<String, Integer> kv : sorted.entrySet()) {
                sorted.forEach((k,v) -> pw.println(k + " relative=" + v + "  absolute=" + (double)v/sorted.size()));
            }

        }

        try(PrintWriter pw = new PrintWriter("report2.txt")) {
            TreeMap<String , Integer> sorted = new TreeMap<>();
            sorted.putAll(dictionaryList);

            sorted.entrySet().stream().sorted(Map.Entry.<String , Integer>comparingByValue().reversed())
                    .forEach(x -> pw.println(x));
        }
    }
 }
