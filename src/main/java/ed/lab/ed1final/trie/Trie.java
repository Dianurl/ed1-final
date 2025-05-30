package ed.lab.ed1final.trie;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Trie {
    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        int countprefix = 0;
        int countword = 0;
    }

    public boolean isEmpty() {
        return root.countprefix == 0;
    }

    public void clear() {
        root.children.clear();
        root.countprefix = 0;
        root.countword = 0;
    }

    private TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        if (word == null || word.isEmpty())
            return;

        word = word.trim().toLowerCase();
        TrieNode current = root;
        current.countprefix++;

        for (char letters : word.toCharArray()) {
            current = current.children.computeIfAbsent(letters, k -> new TrieNode());
            current.countprefix++;
        }

        current.countword++;
    }

    public int countWordsEqualTo(String word) {
        if (word == null || word.isEmpty())
            return 0;

        word = word.trim().toLowerCase();
        TrieNode current = root;

        for (char letter : word.toCharArray()) {
            current = current.children.get(letter);
            if (current == null) return 0;
        }

        return current.countword;
    }


    public int countWordsStartingWith(String prefix) {
        if (prefix == null || prefix.isEmpty())
            return 0;

        prefix = prefix.trim().toLowerCase();
        TrieNode current = root;

        for (char c : prefix.toCharArray()) {
            current = current.children.get(c);
            if (current == null)
                return 0;
        }

        return current.countprefix;
    }

    public void erase(String word) {
        if (word == null || word.isEmpty() || countWordsEqualTo(word) == 0)
            return;

        word = word.trim().toLowerCase();
        TrieNode current = root;
        current.countprefix--;

        for (int i = 0; i < word.length(); i++) {
            char letters = word.charAt(i);
            TrieNode child = current.children.get(letters);

            if (child.countprefix == 1) {
                current.children.remove(letters);
                return;
            }

            child.countprefix--;
            current = child;
        }

        current.countword--;
    }
}