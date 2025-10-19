package com.kth25.demo.model;


import java.util.Random;

/**
 * Utility class for generating and manipulating Sudoku puzzles.
 */
public class SudokuUtilities {
    /** Enum for representing Sudoku difficulty levels */
    public enum SudokuLevel {EASY, MEDIUM, HARD}
    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;

    /**
     * Generates a random Sudoku matrix based on the specified difficulty level.
     *
     * @param level The level of difficulty (EASY, MEDIUM, HARD).
     * @return A 3-dimensional int matrix representing the Sudoku puzzle and its solution.
     *         [row][col][0] contains the initial values (0 represents an empty cell).
     *         [row][col][1] contains the solution.
     */
    public static int[][][] generateRandomSudokuMatrix(SudokuLevel level) {
        String representationString;
        switch (level) {
            case EASY: representationString = easy; break;
            case MEDIUM: representationString = medium; break;
            case HARD: representationString = hard; break;
            default: representationString = medium;
        }

        // Generate the base matrix
        int[][][] matrix = convertStringToIntMatrix(representationString);

        Random rand = new Random();
        // Perform multiple random operations for better variation
        for (int i = 0; i < 10; i++) {  // Increase the number of random operations
            int randomChoice = rand.nextInt(2);  // Random choice between 4 operations

            switch (randomChoice) {
                case 0:  // Swap numbers
                    int num1 = 1 + rand.nextInt(9);  // Random number between 1 and 9
                    int num2 = 1 + rand.nextInt(9);
                    matrix = swapMatrix(matrix, num1, num2);
                    break;
                case 1:  // Vertical flip
                    matrix = verticalMatrix(matrix);
                    break;
            }
        }

        return matrix;
    }

    /**
     * Vertically flips the given Sudoku matrix.
     *
     * @param matrix The original Sudoku matrix.
     * @return A new Sudoku matrix that is vertically flipped.
     */
    public static int[][][] verticalMatrix(int[][][] matrix) {
        int[][][] mirroredMatrix = new int[GRID_SIZE][GRID_SIZE][2];  // Create a new mirrored matrix

        // Swap rows for both initial and solution values (vertical mirroring)
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                // Mirror initial values
                mirroredMatrix[GRID_SIZE - 1 - row][col][0] = matrix[row][col][0];
                // Mirror solution values
                mirroredMatrix[GRID_SIZE - 1 - row][col][1] = matrix[row][col][1];
            }
        }

        return mirroredMatrix;  // Return the mirrored matrix
    }

    /**
     * Swaps all occurrences of two specified numbers within the given Sudoku matrix.
     *
     * @param matrix The Sudoku matrix.
     * @param num1   The first number to swap.
     * @param num2   The second number to swap.
     * @return The Sudoku matrix with the swapped numbers.
     */
    public static int[][][] swapMatrix(int[][][] matrix, int num1, int num2) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                // Swap initial values (matrix[row][col][0] represents pre-filled values in the board)
                if (matrix[row][col][0] == num1) {
                    matrix[row][col][0] = num2;
                } else if (matrix[row][col][0] == num2) {
                    matrix[row][col][0] = num1;
                }

                // Swap solution values (correct solution of the Sudoku board)
                if (matrix[row][col][1] == num1) {
                    matrix[row][col][1] = num2;
                } else if (matrix[row][col][1] == num2) {
                    matrix[row][col][1] = num1;
                }
            }
        }
        return matrix;
    }

    /**
     * Converts a string representation of a Sudoku puzzle into a 3-dimensional integer matrix.
     *
     * @param stringRepresentation A string of 162 characters, representing both the initial values and the solution.
     *                             The first 81 characters represent the initial values ('0' for empty cells),
     *                             and the following 81 characters represent the solution.
     * @return A 3-dimensional int matrix: [row][col][0] for initial values, [row][col][1] for solution values.
     * @throws IllegalArgumentException If the length of stringRepresentation is not 162 characters, or contains characters other than '0'-'9'.
     */
    static int[][][] convertStringToIntMatrix(String stringRepresentation) {
        if (stringRepresentation.length() != GRID_SIZE * GRID_SIZE * 2)
            throw new IllegalArgumentException("representation length " +
                    stringRepresentation.length());

        int[][][] values = new int[GRID_SIZE][GRID_SIZE][2];
        char[] charRepresentation = stringRepresentation.toCharArray();
        int charIndex = 0;
        // Initial values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][0] = convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }
        // Solution values
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                values[row][col][1] = convertCharToSudokuInt(charRepresentation[charIndex++]);
            }
        }
        return values;
    }

    /**
     * Converts a character to a Sudoku integer value (0-9).
     *
     * @param ch The character to convert.
     * @return The integer value of the character.
     * @throws IllegalArgumentException If the character is not between '0' and '9'.
     */
    private static int convertCharToSudokuInt(char ch) {
        if (ch < '0' || ch > '9') throw new IllegalArgumentException("character " + ch);
        return ch - '0';
    }

    // Pre-defined Sudoku puzzles with solutions for each difficulty level
    private static final String easy =
            "000914070" +
                    "010000054" +
                    "040002000" +
                    "007569001" +
                    "401000500" +
                    "300100000" +
                    "039000408" +
                    "650800030" +
                    "000403260" + // solution values after this substring
                    "583914672" +
                    "712386954" +
                    "946752183" +
                    "827569341" +
                    "461238597" +
                    "395147826" +
                    "239675418" +
                    "654821739" +
                    "178493265";
    private static final String medium =
            "300000010" +
                    "000050906" +
                    "050401200" +
                    "030000080" +
                    "002069400" +
                    "000000002" +
                    "900610000" +
                    "200300058" +
                    "100800090" +
                    "324976815" +
                    "718253946" +
                    "659481273" +
                    "536142789" +
                    "872569431" +
                    "491738562" +
                    "985617324" +
                    "267394158" +
                    "143825697";
    private static final String hard =
            "030600000" +
                    "000010070" +
                    "080000000" +
                    "000020000" +
                    "340000800" +
                    "500030094" +
                    "000400000" +
                    "150800200" +
                    "700006050" +
                    "931687542" +
                    "465219378" +
                    "287345916" +
                    "876924135" +
                    "349561827" +
                    "512738694" +
                    "693452781" +
                    "154873269" +
                    "728196453";
}