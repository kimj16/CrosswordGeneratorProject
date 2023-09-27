package crosswordgeneratorproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class CrosswordTest {
    @Test void testCheckIfEnoughWordsTrue() {
        Crossword cw = new Crossword(2, 2);
        ArrayList<String> testWords = new ArrayList<String>();
        testWords.add("aa");
        testWords.add("bb");
        testWords.add("cc");
        testWords.add("dd");
        assertTrue(cw.checkIfEnoughWords(testWords));
    }

    @Test void testCheckIfEnoughWordsFalse() {
        Crossword cw = new Crossword(2, 2);
        ArrayList<String> testWords = new ArrayList<String>();
        testWords.add("aa");
        assertFalse(cw.checkIfEnoughWords(testWords));
    }

    @Test void testCheckIfEnoughWordsFalseNoWordSpaces() {
        Crossword cw = new Crossword(2, 2);
        cw.placeBlock(0, 0, 1);
        ArrayList<String> testWords = new ArrayList<String>();
        assertFalse(cw.checkIfEnoughWords(testWords));
    }
}
