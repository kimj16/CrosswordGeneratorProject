package crosswordgeneratorproject;

import java.util.ArrayList;
import java.util.Random;

public class Word {
    private String word;
    private ArrayList<String> hints;
    private String category;
    private Random hintRandom;

    public Word(String word, String hint, String category) {
        this.word = word;
        this.category = category;
        this.hints = new ArrayList<String>();
        this.hints.add(hint);
        this.hintRandom = new Random();
    }

    public Word(String word, String category) {
        this.word = word;
        this.category = category;
        this.hints = new ArrayList<String>();
        this.hintRandom = new Random();
    }

    public String getWord() {
        return this.word;
    }
    public ArrayList<String> getHints() {
        return this.hints;
    }
    public String getCategory() {
        return this.category;
    }

    public String randomHint() {
        return hints.get(hintRandom.nextInt(hints.size()));
    }

    public void addHint(String newHint) {
        this.hints.add(newHint);
    }
}
