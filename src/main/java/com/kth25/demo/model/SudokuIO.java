package com.kth25.demo.model;

import java.io.*;

/**
 * Class for handling input and output operations for Sudoku boards, including saving and loading boards to/from files.
 */
public class SudokuIO {

    /**
     * Saves a SudokuBoard object to a file.
     *
     * @param board   The SudokuBoard object to be saved.
     * @param filePath The path of the file where the Sudoku board will be saved.
     * @throws IOException If an I/O error occurs while saving the board.
     */
    public static void saveSudokuBoard(SudokuBoard board, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(board);
        } catch (IOException e) {
            System.err.println("Error saving Sudoku board: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Loads a SudokuBoard object from a file.
     *
     * @param filePath The path of the file from which to load the Sudoku board.
     * @return The loaded SudokuBoard object.
     * @throws IOException If an I/O error occurs while reading the file.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */
    public static SudokuBoard loadSudokuBoard(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (SudokuBoard) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading Sudoku board: " + e.getMessage());
            throw e;
        }
    }

}