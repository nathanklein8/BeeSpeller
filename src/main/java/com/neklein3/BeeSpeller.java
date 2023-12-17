package com.neklein3;

import java.util.ArrayList;

public class BeeSpeller {

    public static void usage(String e) {
        if (e != null) { System.out.println(e);}
        System.out.println("usage:");
        System.out.println("java -jar SB.jar [letters] opt[wordlist]");
        System.out.println("letters should be of the form :'abcdefg' where a is the center letter");
    }

    public static void main(String args[]) {
        Game game = null;
        if (args.length < 1 || args.length > 2) {
            usage(null);
            return;
        } else if (args.length == 2) {
            game = new Game(args[0], args[1]);
        } else {
            game = new Game(args[0]);
        }
        if (game.isFatal()) { return; }
        ArrayList<String> words = game.getWords();
        if (words.size() > 0) { 
            System.out.println("Found " + words.size() + " words:");
            int counter = 1;
            for (String word : words) {
                System.out.println("" + counter++ + ": " + word);
            }
        } else {
            System.out.println("No words found :(");
        }
    }

}
