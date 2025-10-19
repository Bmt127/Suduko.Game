package view;



import com.kth25.demo.model.SudokuBoard;
import com.kth25.demo.model.SudokuIO;
import com.kth25.demo.model.SudokuUtilities;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.Random;

/**
 * Controller class for managing the interactions between the Sudoku model and the view.
 * Handles game logic, including saving, loading, checking game progress, and providing hints.
 */
public class GameController {
    private SudokuBoard sudokuBoard;
    private GridView gridView;
    private SudokuUtilities.SudokuLevel currentLevel;

    /**
     * Constructor that takes in the Sudoku model (SudokuBoard).
     *
     * @param sudokuBoard The SudokuBoard model to be managed by the controller.
     */
    public GameController(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
    }

    /**
     * Sets the GridView that displays the Sudoku board.
     *
     * @param gridView The GridView to be set for updating the view.
     */
    public void setGridView(GridView gridView) {
        this.gridView = gridView;
    }

    /**
     * Saves the current state of the Sudoku game to a file.
     *
     * @param filePath The file path where the game state will be saved.
     */
    public void saveGame(String filePath) {
        try {
            SudokuIO.saveSudokuBoard(sudokuBoard, filePath);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save the game: " + e.getMessage());
        }
    }

    /**
     * Loads the Sudoku game state from a file.
     *
     * @param filePath The file path from which the game state will be loaded.
     */
    public void loadGame(String filePath) {
        try {
            sudokuBoard = SudokuIO.loadSudokuBoard(filePath);
            System.out.println("Game loaded successfully.");
            gridView.updateView(sudokuBoard.getBoardMatrix());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load the game: " + e.getMessage());
        }
    }

    /**
     * Initializes a new game by resetting the board to the current difficulty level.
     * If no level is set, defaults to the MEDIUM level.
     */
    public void onNewGame() {
        if (currentLevel == null) {
            currentLevel = SudokuUtilities.SudokuLevel.MEDIUM;  // Default to MEDIUM if no level is selected
        }
        sudokuBoard.initializeBoardWithLevel(currentLevel);
        gridView.updateView(sudokuBoard.getBoardMatrix());
    }

    /**
     * Handles user input for entering a number into a specific cell.
     *
     * @param row    The row index of the cell.
     * @param col    The column index of the cell.
     * @param number The number to be entered into the cell.
     */
    public void handleNumberInput(int row, int col, int number) {
        if (sudokuBoard.getBoard()[row][col].isEditable()) {
            sudokuBoard.setValue(row, col, number);
            gridView.updateView(sudokuBoard.getBoardMatrix());
        } else {
            System.out.println("This cell is already filled or not editable.");
        }
    }

    /**
     * Checks if the current state of the board has all correct values so far.
     *
     * @return True if all filled cells are correct, false otherwise.
     */
    public boolean checkProgress() {
        return sudokuBoard.checkBoard();
    }

    /**
     * Checks if the entire Sudoku board is correctly solved.
     *
     * @return True if the board is fully and correctly solved, false otherwise.
     */
    public boolean isGameComplete() {
        return sudokuBoard.isComplete();
    }

    /**
     * Displays an alert informing the player that they have completed the Sudoku puzzle successfully.
     */
    public void showCompletionAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations!");
        alert.setHeaderText("You solved the Sudoku!");
        alert.setContentText("All numbers are correct. Great job!");
        alert.show();
    }

    /**
     * Displays an alert indicating that the player has made incorrect entries.
     */
    public void isNotDoneAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Incorrect Path");
        alert.setHeaderText("You're on the wrong track!");
        alert.setContentText("Some of the numbers you've entered are incorrect. Double-check your entries and try again.");
        alert.show();
    }

    /**
     * Displays an alert indicating that the player is on the right path to solving the Sudoku puzzle.
     */
    public void onTheRightPathAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Keep Going!");
        alert.setHeaderText("You're on the right path!");
        alert.setContentText("The numbers you've entered so far are correct. Keep up the good work and you'll solve the puzzle!");
        alert.show();
    }

    /**
     * Provides a hint to the player by filling an empty cell with the correct value.
     * Updates the board view to reflect the hint.
     */
    public void giveHint() {
        int[] hintCell = sudokuBoard.provideHint();
        if (hintCell != null) {
            int row = hintCell[0];
            int col = hintCell[1];
            gridView.updateView(sudokuBoard.getBoardMatrix());
        } else {
            System.out.println("No empty cells left to provide a hint.");
        }
    }

    /**
     * Starts a new game at the specified difficulty level.
     *
     * @param level The difficulty level to start the new game with.
     */
    public void startNewGameWithLevel(SudokuUtilities.SudokuLevel level) {
        this.currentLevel = level;
        sudokuBoard.initializeBoardWithLevel(level);
        gridView.updateView(sudokuBoard.getBoardMatrix());
    }

    /**
     * Clears all editable cells on the Sudoku board.
     * Updates the view to reflect the cleared cells.
     */
    public void clearAllEditableCells() {
        sudokuBoard.clearAllEditableCells();
        gridView.updateView(sudokuBoard.getBoardMatrix());
    }
}