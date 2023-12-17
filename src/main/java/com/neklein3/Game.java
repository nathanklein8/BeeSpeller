package com.neklein3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Game {
    
    private char center;
    private char[] letters;
    private boolean fatal = false;
    private ArrayList<String> words;

    private boolean isLegal(String word) {
        boolean usedCenter = false;
        int x = word.length();
        int count = 0;
        for (char l : letters) {
            if (word.indexOf(l) != -1) {
                count++;
            }
        }
        if (word.indexOf(center) != -1) {
            usedCenter = true;
            count++;
        }
        return usedCenter && (count == x) && (x > 3);
    }

    private void parseLetters(String letters) {
        if (letters.length() != 7) {
            BeeSpeller.usage("Incorrect number of letters");
            fatal = true;
        }
        center = letters.charAt(0);
        this.letters = letters.substring(1).toCharArray();
    }

    public Game(String letters) {
        this.words = new ArrayList<>();
        parseLetters(letters);
        if (fatal) { return; }
        String fileName = "wordlist.txt";
        ClassLoader classLoader = Game.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IOException("File not found: " + fileName);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (isLegal(line)) {
                        this.words.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        words.sort(new UniqueCharComparator());
    }

    public Game(String letters, String wordlist) {
        this.words = new ArrayList<>();
        HashSet<String> seenWords = new HashSet<>();
        parseLetters(letters);
        if (fatal) { return; }
        System.out.println("using " + wordlist + " as word list");
        File file = new File(wordlist);
        try (Scanner in = new Scanner(file)) {
            while (in.hasNextLine()) {
                String line = in.nextLine().strip();
                if (line != "") {
                    String word = line.split(" ")[0].toLowerCase();
                    if (isLegal(word)) {
                        if (!seenWords.contains(word)) {
                            this.words.add(word);
                            seenWords.add(word);
                        }
                    }
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Error opening file");
        }
        words.sort(new UniqueCharComparator());
    }



    public boolean isFatal() { return fatal; }

    public ArrayList<String> getWords() {
        return this.words;
    }

    @Override
    public String toString() {
        String r = "[" + center + "]";
        for (char c : letters) { r += c; }
        return r;
    }

}
