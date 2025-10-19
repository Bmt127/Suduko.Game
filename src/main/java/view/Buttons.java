package view;



import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Buttons extends BorderPane {
    private GridView gridView;

    private Button hintButton;
    private Button checkButton;
    private VBox leftSideButtonsBox;  // For Hint and Check buttons

    private Button[] numberButtons;
    private VBox rightSideButtonsBox;  // For number buttons (1-9 and C)

    private GameController controller;
    private int selectedNumber;  // Keep track of the selected number

    public Buttons(GameController controller, GridView gridView) {
        super();
        this.gridView = gridView;
        this.controller = controller;

        // Initialize the "Hint" and "Check" buttons
        this.hintButton = new Button("Hint");
        this.hintButton.setOnAction(this::buttonClicks);
        this.checkButton = new Button("Check");
        this.checkButton.setOnAction(this::buttonClicks);

        // Create left-side VBox for Hint and Check buttons
        this.leftSideButtonsBox = new VBox(10);  // Spacing of 10 between buttons
        leftSideButtonsBox.getChildren().addAll(hintButton, checkButton);

        // Initialize number buttons (1-9) and a Clear button
        this.numberButtons = new Button[10];  // 9 number buttons + 1 Clear button
        for (int i = 0; i < numberButtons.length - 1; i++) {
            numberButtons[i] = new Button(Integer.toString(i + 1));
            numberButtons[i].setOnAction(this::buttonClicks);
        }
        // Initialize Clear button to reset selection
        this.numberButtons[9] = new Button("C");
        this.numberButtons[9].setOnAction(this::buttonClicks);

        // Create right-side VBox for number buttons and Clear button
        this.rightSideButtonsBox = new VBox(10);  // Spacing of 10 between buttons
        rightSideButtonsBox.getChildren().addAll(numberButtons);  // Add all buttons to VBox

        // Add the components to the BorderPane
        this.setPadding(new Insets(10, 10, 10, 10));  // Padding around the BorderPane
        this.setLeft(leftSideButtonsBox);  // Set Hint and Check buttons on the left
        this.setRight(rightSideButtonsBox);  // Set number buttons on the right

        // Initialize selectedNumber to 0
        this.selectedNumber = 0;
    }

    // Event handler for button clicks
    private void buttonClicks(ActionEvent event) {
        // Handle number buttons (1-9)
        for (int i = 0; i < numberButtons.length - 1; i++) {
            if (event.getSource() == numberButtons[i]) {
                selectedNumber = i + 1;  // Update the selected number
                System.out.println("Selected number: " + selectedNumber);
                return;
            }
        }
        // Handle Clear button
        if (event.getSource() == numberButtons[9]) {
            selectedNumber = 0;  // Clear the selected number
            System.out.println("Selected number cleared.");
            ;
        }

        // Handle Check button
        else if (event.getSource() == checkButton) {
            boolean isSolved = controller.isGameComplete();
            if (isSolved) {
                controller.showCompletionAlert();
            } else {
                boolean isCorrect = controller.checkProgress();  // Använder din checkProgress-metod
                if (isCorrect) {
                    controller.onTheRightPathAlert();
                } else {
                    controller.isNotDoneAlert();
                }
            }
        }

        // Handle Hint button
        else if (event.getSource() == hintButton) {
            controller.giveHint();
            boolean isSolved = controller.isGameComplete();// Använder giveHint-metoden från GameController
            if (isSolved) {
                controller.showCompletionAlert();
            }
            System.out.println("A hint has been provided.");
        }
    }


    // Getter for the selected number so that GridView can retrieve it
    public int getSelectedNumber() {
        return selectedNumber;
    }
    // Add these methods in Buttons.java to expose the left and right side button boxes
    public VBox getLeftSideButtonsBox() {
        return leftSideButtonsBox;  // Returns the VBox with Hint and Check buttons
    }

    public VBox getRightSideButtonsBox() {
        return rightSideButtonsBox;  // Returns the VBox with Number buttons and Clear button
    }

}