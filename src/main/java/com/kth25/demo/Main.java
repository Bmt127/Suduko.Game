package com.kth25.demo;

import com.kth25.demo.model.SudokuBoard;
import com.kth25.demo.model.SudokuUtilities;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import view.Buttons;
import view.GameController;
import view.GridView;
import view.MenuBarView;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        SudokuBoard board = new SudokuBoard();  // Skapa modellen

        // Initiera brädet med slumpmässiga startvärden (här använder vi generateRandomSudokuMatrix)
        int[][][] initialValues = SudokuUtilities.generateRandomSudokuMatrix(SudokuUtilities.SudokuLevel.EASY);
        board.initializeBoard(initialValues);  // Initiera brädet med värden

        GameController controller = new GameController(board);  // Skapa kontrollern och skicka in modellen
        GridView gridView = new GridView(board, controller);    // Skapa GridView och skicka in kontrollern och modellen
        controller.setGridView(gridView);  // Knyt GridView till controllern
        Buttons borderPaneView = new Buttons(controller, gridView);  // Skapa Buttons och skicka GridView

        // Sätt Buttons i GridView så den kan hämta det valda numret
        gridView.setButtons(borderPaneView);

        MenuBarView menuBarView = new MenuBarView(controller);  // Skapa menyn

        HBox layout = new HBox(20);  // Skapa layout med 20px mellanrum mellan komponenterna

        // Lägg till vänstra knapparna (Hint/Check) till vänster i HBox
        layout.getChildren().add(borderPaneView.getLeftSideButtonsBox());

        // Lägg till Sudoku-rutnätet i mitten
        layout.getChildren().add(gridView.getNumberPane());

        // Lägg till högra knapparna (Siffertangenter) till höger i HBox
        layout.getChildren().add(borderPaneView.getRightSideButtonsBox());

        // Skapa en BorderPane och placera menyraden överst och HBox-layouten i mitten
        BorderPane root = new BorderPane();
        root.setTop(menuBarView);  // Menyrad överst
        root.setCenter(layout);    // HBox-layout i mitten
        Scene scene = new Scene(root);

        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
