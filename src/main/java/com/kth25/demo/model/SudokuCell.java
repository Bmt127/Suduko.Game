package com.kth25.demo.model;


import java.io.Serializable;

/**
 * Represents a single cell in a Sudoku board. Each cell has a correct value, a current value,
 * and an editable status to determine if the cell's value can be modified.
 */
public class SudokuCell implements Serializable {

    private int correctValue;  // The correct value of the cell
    private int currentValue;  // The current value of the cell (can be 0 for empty cells)
    private boolean isEditable;  // Indicates if the cell is editable
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a SudokuCell with the specified correct value and editable status.
     *
     * @param correctValue The correct value of the cell.
     * @param isEditable   Indicates whether the cell is editable.
     */
    public SudokuCell(int correctValue, boolean isEditable) {
        this.correctValue = correctValue;
        this.isEditable = isEditable;
        this.currentValue = 0;  // Starts as empty (0 represents an empty cell)
    }

    /**
     * Sets the current value of the cell.
     *
     * @param value The value to set for the cell.
     */
    public void setValue(int value) {
        this.currentValue = value;
    }

    /**
     * Sets whether the cell is editable.
     *
     * @param isEditable Indicates whether the cell should be editable.
     */
    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    /**
     * Gets the current value of the cell.
     *
     * @return The current value of the cell.
     */
    public int getValue() {
        return this.currentValue;
    }

    /**
     * Gets the correct value of the cell.
     *
     * @return The correct value of the cell.
     */
    public int getCorrectValue() {
        return correctValue;
    }

    /**
     * Checks if the current value of the cell is correct.
     *
     * @return True if the current value is equal to the correct value, false otherwise.
     */
    public boolean isCorrect() {
        return this.currentValue == this.correctValue;
    }

    /**
     * Checks if the cell is editable.
     *
     * @return True if the cell is editable, false otherwise.
     */
    public boolean isEditable() {
        return this.isEditable;
    }
}
