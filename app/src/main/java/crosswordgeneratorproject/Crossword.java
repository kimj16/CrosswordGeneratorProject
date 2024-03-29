package crosswordgeneratorproject;

import java.util.ArrayList;
import java.util.HashMap;

public class Crossword {
    private char[][] puzzle;
    private String title;
    private ArrayList<WordSpace> wordSpaces;
    private boolean isReady;
    private int totalCombinations;
    private int triedCombinations;

    public Crossword(int width, int height) {
        puzzle = new char[width][height];
        wordSpaces = new ArrayList<WordSpace>();
        getWordSpaces();
        isReady = false;
        totalCombinations = 0;
        triedCombinations = 0;
    }

    public char[][] getPuzzle() {
        return this.puzzle;
    }

    public char[][] fillPuzzleSetup(ArrayList<String> words, ArrayList<WordSpace> wordSpaces, char[][] puzzle) {
        totalCombinations = words.size();
        return fillPuzzle(words, wordSpaces, puzzle, null);
    }

    public char[][] fillPuzzle(ArrayList<String> words, ArrayList<WordSpace> wordSpaces, char[][] puzzle, WordSpace prevSpaceFilled) {
        if(!checkIfEnoughWords(words, wordSpaces)) {
            System.out.println("Not enough words to fill puzzle");
            return puzzle;
        }
        if(wordSpaces.isEmpty()) {
            System.out.println("Out of Spaces");
            // Set ready flag
            isReady = true;
            return puzzle;
        }

        // Copy puzzle
        char[][] newPuzzle = new char[puzzle.length][puzzle[0].length];
        for(int row = 0; row < newPuzzle.length; row++) {
            for(int col = 0; col < newPuzzle[0].length; col++) {
                newPuzzle[row][col] = puzzle[row][col];
            }
        }

        // Get space to check
        WordSpace currSpace = wordSpaces.get(0);
        if(prevSpaceFilled == null) {
            currSpace = wordSpaces.get(0);
        } else {
            for(WordSpace ws : wordSpaces) {
                if(wordSpacesIntersect(ws, prevSpaceFilled)) {
                    currSpace = ws;
                    break;
                }
            }
        }

        // Get words of same length as space
        ArrayList<String> sameLengthWords = new ArrayList<String>();
        for(String word : words) {
            if(word.length() == currSpace.length()) {
                sameLengthWords.add(word);
            }
        }

        for(String word : sameLengthWords) {
            triedCombinations++;
            if(triedCombinations % 10000 == 0) {
                System.out.println(triedCombinations);
                for(int row = 0; row < newPuzzle.length; row++) {
                    for(int col = 0; col < newPuzzle[0].length; col++) {
                        System.out.print(newPuzzle[row][col]);
                        System.out.print(" ");
                    }
                    System.out.println();
                }
            }
            if(wordValidInSpace(word, currSpace, newPuzzle)) {
                if(currSpace.isRow()) {
                    for(int i = 0; i < word.length(); i++) {
                        newPuzzle[currSpace.rowIndex()][currSpace.colIndex() + i] = word.charAt(i);
                    }
                } else {
                    for(int i = 0; i < word.length(); i++) {
                        newPuzzle[currSpace.rowIndex() + i][currSpace.colIndex()] = word.charAt(i);
                    }
                }

                ArrayList<String> newWords = new ArrayList<String>();
                newWords.addAll(words);
                newWords.remove(word);

                ArrayList<WordSpace> newWordSpaces = new ArrayList<WordSpace>();
                newWordSpaces.addAll(wordSpaces);
                newWordSpaces.remove(currSpace);

                newPuzzle = fillPuzzle(newWords, newWordSpaces, newPuzzle, currSpace);
                if(isReady) {
                    return newPuzzle;
                }

                for(int row = 0; row < newPuzzle.length; row++) {
                    for(int col = 0; col < newPuzzle[0].length; col++) {
                        newPuzzle[row][col] = puzzle[row][col];
                    }
                } 
            } 
        }
        // System.out.println(isReady);
        return newPuzzle;
    }

    public boolean wordSpacesIntersect(WordSpace ws1, WordSpace ws2) {
        if(ws1.isRow() == ws2.isRow()){
            return false;
        } else {
            if(!ws1.isRow() && 
            (ws1.colIndex() >= ws2.colIndex() &&
            ws1.colIndex() <= ws2.colIndex() + ws2.length() - 1 &&
            ws1.colIndex() >= ws2.colIndex() &&
            ws1.colIndex() <= ws2.colIndex() + ws2.length() - 1) && 
            (ws2.rowIndex() >= ws1.rowIndex() &&
            ws2.rowIndex() <= ws1.rowIndex() + ws1.length() - 1 &&
            ws2.rowIndex() >= ws1.rowIndex() &&
            ws2.rowIndex() <= ws1.rowIndex() + ws1.length() - 1)) {
                return true;
            } else if(ws1.isRow() && 
            (ws1.rowIndex() >= ws2.rowIndex() &&
            ws1.rowIndex() <= ws2.rowIndex() + ws2.length() - 1 &&
            ws1.rowIndex() >= ws2.rowIndex() &&
            ws1.rowIndex() <= ws2.rowIndex() + ws2.length() - 1) &&
            (ws2.colIndex() >= ws1.colIndex() &&
            ws2.colIndex() <= ws1.colIndex() + ws1.length() - 1 &&
            ws2.colIndex() >= ws1.colIndex() &&
            ws2.colIndex() <= ws1.colIndex() + ws1.length() - 1)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean wordValidInSpace(String word, WordSpace wordSpace, char[][] puzzle) {
        if(word.length() != wordSpace.length()) {
            return false;
        }
        if(wordSpace.isRow()) {
            for(int i = 0; i < word.length(); i++) {
                char puzzleChar = puzzle[wordSpace.rowIndex()][wordSpace.colIndex() + i];
                if(puzzleChar != word.charAt(i) && puzzleChar != ' ') {
                    return false;
                }
            }
        } else {
            for(int i = 0; i < word.length(); i++) {
                char puzzleChar = puzzle[wordSpace.rowIndex() + i][wordSpace.colIndex()];
                if(puzzleChar != word.charAt(i) && puzzleChar != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public int longestWordSpaceLengths(HashMap<Integer, ArrayList<WordSpace>> wordSpaces) {
        int max = 0;
        for(Integer length : wordSpaces.keySet()) {
            if(length > max) {
                max = length;
            }
        }
        return max;
    }

    public void addNewWord(String word) {

    }

    public boolean checkIfEnoughWords(ArrayList<String> words, ArrayList<WordSpace> wordSpaces) {
        // getWordSpaces();
        ArrayList<String> tempWords = new ArrayList<String>();
        tempWords.addAll(words);
        for(WordSpace ws : wordSpaces) {
            // System.out.println(ws);
            int spaceLength = ws.length();
            for(int i = 0; i < tempWords.size(); i++) {
                if(spaceLength == tempWords.get(i).length()) {
                    // System.out.println("LENGTH MATCH " + ws + " | " + words.get(i));
                    tempWords.remove(i);
                    break;
                }
                if(i >= tempWords.size() - 1) {
                    return false;
                }
            }
        }
        return true;
        
    }

    public void initPuzzle() {
        for(int row = 0; row < puzzle.length; row++) {
            for(int col = 0; col < puzzle[0].length; col++) {
                puzzle[row][col] = ' ';
            }
        }
    }

    public boolean placeBlock(int row, int col, int puzzleSymmetry) {
        if(puzzle[row][col] != ' ') {
            return false;
        } else {
            puzzle[row][col] = '|';
            if(puzzleSymmetry == 1) {
                puzzle[col][row] = '|';
            } else if(puzzleSymmetry == 2) {
                puzzle[puzzle.length - row - 1][puzzle[0].length - col - 1] = '|';
            }
            this.getWordSpaces();
            return true;
        }
    }

    public ArrayList<WordSpace> getWordSpaces() {
        // ArrayList<WordSpace> foundSpaces = new ArrayList<WordSpace>();

        this.wordSpaces.clear();
        int wordLength = 0;
        int wordNumber = 1;
        int startingCol = 0;
        for(int row = 0; row < puzzle.length; row++) {
            for(int col = 0; col < puzzle[0].length; col++) {
                // System.out.print("ROW " + row + ", COL " + col + " | ");
                if(puzzle[row][col] == '|') {
                    // System.out.print("BLOCK | ");
                    if(wordLength > 0) {
                        // System.out.print("LENGTH " + wordLength + ", WORD START (" + row + ", " + col + ")");
                        wordSpaces.add(new WordSpace(wordNumber, wordLength, row, startingCol, true));
                        wordNumber++;
                        wordLength = 0;
                    }
                    startingCol = col + 1;
                } else if(col >= puzzle.length - 1) {
                    // System.out.print("END OF ROW");
                    wordLength++;
                    wordSpaces.add(new WordSpace(wordNumber, wordLength, row, startingCol, true));
                    wordLength = 0;
                    wordNumber++;
                } else {
                    wordLength++;
                }
            }
            startingCol = 0;
        }
        wordLength = 0;
        wordNumber = 1;
        int startingRow = 0;
        for(int col = 0; col < puzzle.length; col++) {
            for(int row = 0; row < puzzle[0].length; row++) {
                if(puzzle[row][col] == '|') {
                    if(wordLength > 0) {
                        wordSpaces.add(new WordSpace(wordNumber, wordLength, startingRow, col, false));
                        wordNumber++;
                        wordLength = 0;
                    }
                    startingRow = row + 1;
                } else if(row >= puzzle[0].length - 1) {
                    wordLength++;
                    wordSpaces.add(new WordSpace(wordNumber, wordLength, startingRow, col, false));
                    wordLength = 0;
                    wordNumber++;
                } else {
                    wordLength++;
                }
            }
            startingRow = 0;
        }
        return this.wordSpaces;
    }

    public HashMap<Integer, ArrayList<WordSpace>> separateWordSpacesByLength() {
        HashMap<Integer, ArrayList<WordSpace>> separatedWordSpaces = new HashMap<Integer, ArrayList<WordSpace>>();
        for(WordSpace ws : this.wordSpaces) {
            if(separatedWordSpaces.get(ws.length()) == null) {
                separatedWordSpaces.put(ws.length(), new ArrayList<WordSpace>());
            }
            separatedWordSpaces.get(ws.length()).add(ws);
        }
        return separatedWordSpaces;
    }

    public HashMap<Integer, ArrayList<String>> separateWordsByLength(ArrayList<String> words) {
        HashMap<Integer, ArrayList<String>> separatedWords = new HashMap<Integer, ArrayList<String>>();
        for(String word : words) {
            if(separatedWords.get(word.length()) == null) {
                separatedWords.put(word.length(), new ArrayList<String>());
            }
            separatedWords.get(word.length()).add(word);
        }
        return separatedWords;
    }
}
