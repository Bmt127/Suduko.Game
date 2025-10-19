package view;



import com.kth25.demo.model.SudokuBoard;
import com.kth25.demo.model.SudokuCell;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import static com.kth25.demo.model.SudokuUtilities.GRID_SIZE;


/**
 * Represents the visual grid view for the Sudoku game, including handling user interactions
 * and updating the visual representation of the board.
 */
public class GridView extends GridPane {
    private Label[][] numberTiles; // UI grid tiles
    private GridPane numberPane;
    private SudokuBoard board;
    private GameController controller;
    private Buttons buttons;

    /**
     * Constructor to initialize the GridView with the given Sudoku board and controller.
     *
     * @param board      The SudokuBoard model used to initialize the view.
     * @param controller The GameController that manages interactions.
     */
    public GridView(SudokuBoard board, GameController controller) {
        super();
        this.board = board;
        this.controller = controller;
        this.numberTiles = new Label[GRID_SIZE][GRID_SIZE];
        this.numberPane = new GridPane();

        // Initialize and render the grid view
        initNumberTiles();
        numberPane = makeNumberPane();
    }

    /**
     * Provides a reference to the numberPane, allowing external classes to access the UI grid.
     *
     * @return The numberPane GridPane object representing the Sudoku board.
     */
    public GridPane getNumberPane() {
        return numberPane;
    }

    /**
     * Updates the view to reflect the current state of the Sudoku board.
     *
     * @param sudokuMatrix A 3-dimensional array representing the current values of the board.
     */
    public void updateView(int[][][] sudokuMatrix) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = sudokuMatrix[row][col][0]; // Initial value

                if (value != 0) {
                    numberTiles[row][col].setText(String.valueOf(value));
                    numberTiles[row][col].setStyle("-fx-background-color: white; -fx-border-color: black; -fx-font-weight: bold;");
                } else {
                    numberTiles[row][col].setText(""); // Empty if 0
                    numberTiles[row][col].setStyle("-fx-background-color: white; -fx-border-color: black; -fx-font-weight: normal;");
                }
            }
        }
    }

    /**
     * Initializes the individual number tiles for the grid, setting default styles and attaching event handlers.
     */
    private void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = board.getValue(row, col);

                // Prepare display value, empty string for zeroes
                String displayValue = value == 0 ? "" : String.valueOf(value);

                // Initialize label (tile)
                Label tile = new Label(displayValue);
                tile.setPrefWidth(32);
                tile.setPrefHeight(32);
                tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");
                tile.setOnMouseClicked(tileClickHandler); // Attach event handler

                numberTiles[row][col] = tile; // Store in array
            }
        }
    }

    /**
     * Creates the main number grid pane, which includes 3x3 sections to represent the Sudoku layout.
     *
     * @return The root GridPane object containing all 3x3 sections.
     */
    private GridPane makeNumberPane() {
        GridPane root = new GridPane();
        root.setStyle("-fx-border-color: black; -fx-border-width: 1.0px; -fx-background-color: white;");

        // Create 3x3 sections and add number tiles
        for (int srow = 0; srow < 3; srow++) {
            for (int scol = 0; scol < 3; scol++) {
                GridPane section = new GridPane();
                section.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;");

                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {
                        // Add tiles to the section grid
                        section.add(numberTiles[srow * 3 + row][scol * 3 + col], col, row);
                    }
                }

                root.add(section, scol, srow); // Add section to root pane
            }
        }

        return root;
    }

    /**
     * Sets a reference to the Buttons class to access user-selected numbers.
     *
     * @param buttons The Buttons instance that provides access to selected numbers.
     */
    public void setButtons(Buttons buttons) {
        this.buttons = buttons;
    }

    // Event handler for handling tile clicks and updating the board accordingly
    private EventHandler<MouseEvent> tileClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    if (event.getSource() == numberTiles[row][col]) {
                        SudokuCell cell = board.getBoard()[row][col];

                        if (cell.isEditable()) {
                            // Get selected number from Buttons
                            int selectedNumber = buttons.getSelectedNumber();

                            // Update the model and controller
                            controller.handleNumberInput(row, col, selectedNumber);

                            // Update the view tile text
                            numberTiles[row][col].setText(selectedNumber == 0 ? "" : String.valueOf(selectedNumber));

                            return; // Exit after updating the clicked tile
                        }
                    }
                }
            }
        }
    };
}