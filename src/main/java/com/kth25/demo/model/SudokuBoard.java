package com.kth25.demo.model;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a Sudoku board consisting of a 9x9 grid of Sudoku cells.
 * Provides methods to initialize, set, clear, and validate the board.
 */
public class SudokuBoard implements Serializable {
    public static final int GRID_SIZE = 9;
    private SudokuCell[][] board;  // 2D array of SudokuCell to represent the board
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an empty Sudoku board with all cells set as editable.
     */
    public SudokuBoard() {
        board = new SudokuCell[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                // Initialize all cells as empty and editable initially
                board[row][col] = new SudokuCell(0, true);
            }
        }
    }

    /**
     * Initializes the board with given initial and correct values.
     *
     * @param initialValues A 3-dimensional array where [row][col][0] contains initial values
     *                      and [row][col][1] contains solution values.
     */
    public void initializeBoard(int[][][] initialValues) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = initialValues[row][col][0];  // Initial value
                int correctValue = initialValues[row][col][1];  // Solution value
                boolean isEditable = value == 0;  // Only empty cells are editable

                // Initialize each cell with initial and correct value
                board[row][col] = new SudokuCell(correctValue, isEditable);

                // Set the initial value, regardless of whether the cell is editable or not
                board[row][col].setValue(value);
            }
        }
    }

    /**
     * Sets a value in a specific cell.
     *
     * @param row   The row index of the cell.
     * @param col   The column index of the cell.
     * @param value The value to set for the cell.
     */
    public void setValue(int row, int col, int value) {
        board[row][col].setValue(value);
    }

    /**
     * Gets the value from a specific cell.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return The current value of the cell.
     */
    public int getValue(int row, int col) {
        return board[row][col].getValue();
    }

    /**
     * Checks if all cells in the board are correctly filled.
     *
     * @return True if all cells are correctly filled, false otherwise.
     */
    public boolean isComplete() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (!board[row][col].isCorrect()) {
                    return false;  // If any cell is empty or incorrect
                }
            }
        }
        return true;  // All cells are filled and correct
    }


    /**
     * Gets the entire Sudoku board.
     *
     * @return A 2D array of SudokuCell representing the board.
     */
    public SudokuCell[][] getBoard() {
        return board;
    }

    /**
     * Converts the Sudoku board into a 3-dimensional matrix of values.
     *
     * @return A 3-dimensional array where [row][col][0] contains the current value of each cell.
     */
    public int[][][] getBoardMatrix() {
        int[][][] matrix = new int[GRID_SIZE][GRID_SIZE][1];

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                SudokuCell cell = board[row][col];
                matrix[row][col][0] = cell.getValue();  // Fill matrix with the current value of each cell
            }
        }

        return matrix;
    }

    /**
     * Provides a hint by filling a random empty cell with the correct value.
     *
     * @return An array containing the row and column of the cell that was filled, or null if no empty cells are available.
     */
    public int[] provideHint() {
        List<int[]> emptyCells = new ArrayList<>();

        // Find all empty or cleared editable cells
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board[row][col].getValue() == 0 && board[row][col].isEditable()) {  // Empty and editable cell
                    emptyCells.add(new int[]{row, col});
                }
            }
        }

        // If there are empty cells, choose one randomly and provide a hint
        if (!emptyCells.isEmpty()) {
            Random rand = new Random();
            int[] randomCell = emptyCells.get(rand.nextInt(emptyCells.size()));
            int row = randomCell[0];
            int col = randomCell[1];
            board[row][col].setValue(board[row][col].getCorrectValue());  // Fill the cell with the correct value
            return new int[]{row, col};  // Return the position of the cell that was hinted
        } else {
            System.out.println("No empty cells left to provide a hint.");
            return null;  // Return null if no empty cells are available
        }
    }

    /**
     * Initializes the board based on the specified difficulty level.
     *
     * @param level The difficulty level of the Sudoku puzzle (EASY, MEDIUM, HARD).
     */
    public void initializeBoardWithLevel(SudokuUtilities.SudokuLevel level) {
        // Use SudokuUtilities to generate a matrix based on the selected level
        int[][][] initialValues = SudokuUtilities.generateRandomSudokuMatrix(level);

        // Initialize the board with the generated values
        initializeBoard(initialValues);
    }

    /**
     * Checks if all currently filled values in the board are correct.
     *
     * @return True if all filled cells are correct, false otherwise.
     */
    public boolean checkBoard() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board[row][col].getValue() != 0 && !board[row][col].isCorrect()) {
                    return false;  // If any filled cell has an incorrect value
                }
            }
        }
        return true;  // All filled values are correct
    }


    /**
     * Clears all editable cells on the board.
     */
    public void clearAllEditableCells() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board[row][col].isEditable()) {
                    board[row][col].setValue(0);  // Clear the cell if it is editable
                }
            }
        }
    }

}