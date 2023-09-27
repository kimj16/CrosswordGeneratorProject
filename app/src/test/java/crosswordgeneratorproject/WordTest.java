package crosswordgeneratorproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WordTest {
    @Test void wordAddHint() {
        Word word = new Word("TEST", "TESTCAT");
        word.addHint("testhint");
        assertEquals(word.getHints().size(), 1);
    }
}
