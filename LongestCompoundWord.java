import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.io.*;

class TrieNode {
    boolean isEnd;
    TrieNode[] children;

    TrieNode() {
        isEnd = false;
        children = new TrieNode[26]; // Assuming only lowercase alphabetical characters
    }
}

class Trie {
    TrieNode root;

    Trie() {
        root = new TrieNode();
    }

    void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.isEnd = true;
    }

    boolean search(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            int index = ch - 'a';
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
            if (node.isEnd) {
                return true;
            }
        }
        return false;
    }
}

public class LongestCompoundWord {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // Process Input_01.txt
        Trie trie1 = new Trie();
        ArrayList<String> words1 = readWordsFromFile("input1.txt");
        processWords(trie1, words1, "input1");

        // Process Input_02.txt
        Trie trie2 = new Trie();
        ArrayList<String> words2 = readWordsFromFile("Input2.txt");
        processWords(trie2, words2, "Input2");

        long endTime = System.currentTimeMillis();
        System.out.println("Total time taken: " + (endTime - startTime) + " milliseconds");
    }

    private static ArrayList<String> readWordsFromFile(String filePath) {
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    private static void processWords(Trie trie, ArrayList<String> words, String inputLabel) {
        long startTime = System.currentTimeMillis();

        for (String word : words) {
            trie.insert(word);
        }

        String l1 = CompundedWord(words, trie);
        String longestWord = LongestCompundedWord(words, trie, l1);
        String secondLongestWord = findsecondLongestCompoundedWord(words, trie);

        long endTime = System.currentTimeMillis();
        System.out.println(inputLabel + ":");
        System.out.println("Longest Compound Word: " + longestWord);
        System.out.println("Second Longest Compound Word: " + secondLongestWord);
        System.out.println("Time taken to process file " + inputLabel + ".txt: " + (endTime - startTime) + " milliseconds\n");
    }

    private static String CompundedWord(ArrayList<String> words, Trie trie) {
        String l1 = "";
        for (String word : words) {
            if (isCompoundWord(trie, word) && word.length() > l1.length() && !word.contains("x")) {
                l1 = word;
            }
        }
        return l1;
    }

    private static String LongestCompundedWord(ArrayList<String> words, Trie trie, String l1) {
        String longest = l1;
        String secondLongest = "";

        for (String word : words) {
            if (isCompoundWord(trie, word) && word.length() > longest.length() && !word.contains("x")) {
                secondLongest = longest;
                longest = word;
            }
        }

        return longest;
    }

    private static boolean isCompoundWord(Trie trie, String word) {
        int n = word.length();
        for (int i = 1; i < n; ++i) {
            String prefix = word.substring(0, i);
            String suffix = word.substring(i);

            if ((trie.search(word) || trie.search(prefix)) && (trie.search(suffix) || isCompoundWord(trie, suffix))) {
                return true;
            }
        }
        return false;
    }

    private static String findsecondLongestCompoundedWord(ArrayList<String> words, Trie trie) {
        String l1 = "";
        String secondLongest = "";

        for (String word : words) {
            if (isCompoundWord(trie, word) && word.length() > l1.length() && !word.contains("x")) {
                secondLongest = l1;
                l1 = word;
            }
        }

        return secondLongest;
    }
}
