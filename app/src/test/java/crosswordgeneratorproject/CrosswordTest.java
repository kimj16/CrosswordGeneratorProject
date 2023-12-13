package crosswordgeneratorproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

class CrosswordTest {
    @Test void testCheckIfEnoughWordsTrue() {
        Crossword cw = new Crossword(2, 2);
        cw.initPuzzle();
        ArrayList<String> testWords = new ArrayList<String>();
        testWords.add("aa");
        testWords.add("bb");
        testWords.add("cc");
        testWords.add("dd");
        assertTrue(cw.checkIfEnoughWords(testWords, cw.getWordSpaces()));
    }

    @Test void testCheckIfEnoughWordsFalse() {
        Crossword cw = new Crossword(2, 2);
        cw.initPuzzle();
        ArrayList<String> testWords = new ArrayList<String>();
        testWords.add("aa");
        assertFalse(cw.checkIfEnoughWords(testWords, cw.getWordSpaces()));
    }

    @Test void testCheckIfEnoughWordsFalseNoWordSpaces() {
        Crossword cw = new Crossword(2, 2);
        cw.initPuzzle();
        cw.placeBlock(0, 0, 1);
        ArrayList<String> testWords = new ArrayList<String>();
        assertFalse(cw.checkIfEnoughWords(testWords, cw.getWordSpaces()));
    }

    @Test void testLongestWordSpaceLength() {
        Crossword cw = new Crossword(2, 2);
        HashMap<Integer, ArrayList<WordSpace>> words = new HashMap<Integer, ArrayList<WordSpace>>();
        words.put(1, new ArrayList<WordSpace>());
        words.put(2, new ArrayList<WordSpace>());
        words.put(3, new ArrayList<WordSpace>());
        assertEquals(cw.longestWordSpaceLengths(words), 3);
    }

    @Test void testWordValidInSpaceValidEmpty() {
        Crossword cw = new Crossword(2, 2);
        cw.initPuzzle();
        boolean valid = cw.wordValidInSpace("ab", new WordSpace(1, 2, 0, 0, true), cw.getPuzzle());
        assertTrue(valid);
    }
}
