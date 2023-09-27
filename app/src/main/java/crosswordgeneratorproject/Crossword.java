package crosswordgeneratorproject;

import java.util.ArrayList;
import java.util.HashMap;

public class Crossword {
    private char[][] puzzle;
    private String title;
    private ArrayList<WordSpace> wordSpaces;

    public Crossword(int width, int height) {
        puzzle = new char[width][height];
        wordSpaces = new ArrayList<WordSpace>();
        getWordSpaces();
    }

    public char[][] getPuzzle() {
        return this.puzzle;
    }

    public void fillPuzzle(ArrayList<String> words) {
        if(!checkIfEnoughWords(words)) {
            return;
        }
        HashMap<Integer, ArrayList<WordSpace>> separatedWordSpaces = separateWordSpacesByLength();
        HashMap<Integer, ArrayList<String>> separatedWords = separateWordsByLength(words);
    }

    public boolean checkIfEnoughWords(ArrayList<String> words) {
        getWordSpaces();
        for(WordSpace ws : this.wordSpaces) {
            int spaceLength = ws.length();
            for(int i = 0; i < words.size(); i++) {
                if(spaceLength == words.get(i).length()) {
                    words.remove(i);
                    break;
                }
                if(i >= words.size() - 1) {
                    return true;
                }
            }
        }
        return false;
        
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
        this.wordSpaces.clear();
        int wordLength = 0;
        int wordNumber = 1;
        int startingCol = 0;
        for(int row = 0; row < puzzle.length; row++) {
            for(int col = 0; col < puzzle[0].length; col++) {
                if(puzzle[row][col] == '|') {
                    if(wordLength > 0) {
                        wordSpaces.add(new WordSpace(wordNumber, wordLength, row, startingCol, true));
                        wordNumber++;
                        wordLength = 0;
                    }
                    startingCol = col + 1;
                } else if(col >= puzzle.length - 1) {
                    wordLength++;
                    wordSpaces.add(new WordSpace(wordNumber, wordLength, row, startingCol, true));
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

    // public <T> HashMap<Integer, ArrayList<T>> separateListByElementLength(ArrayList<T> list) {
    //     HashMap<Integer, ArrayList<T>> separatedElements = new HashMap<Integer, ArrayList<T>>();
    //     for(T element : list) {
    //         if(separatedElements.get(element.length()) == null) {
    //             separatedElements.put(element.length(), new ArrayList<T>());
    //         }
    //         separatedElements.get(element.length()).add(element);
    //     }
    //     return separatedElements;
    // }

    public void placeWord(String word) {

    }
}
