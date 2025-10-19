package view;

import com.kth25.demo.model.SudokuUtilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class MenuBarView extends MenuBar {

    private GameController controller;  // Referens till GameController

    // Konstruktorn tar emot en GameController för att hantera menyhändelser
    public MenuBarView(GameController controller) {
        this.controller = controller;

        // File-meny
        Menu fileMenu = new Menu("File");
        MenuItem loadGame = new MenuItem("Load game");
        MenuItem saveGame = new MenuItem("Save game");
        MenuItem exitGame = new MenuItem("Exit");
        fileMenu.getItems().addAll(loadGame, saveGame, exitGame);

        // Game-meny
        Menu gameMenu = new Menu("Game");
        MenuItem newGame = new MenuItem("New Game");
        MenuItem easy = new MenuItem("Difficulty: Easy");
        MenuItem medium = new MenuItem("Difficulty: Medium");
        MenuItem hard = new MenuItem("Difficulty: Hard");
        gameMenu.getItems().addAll(newGame, easy, medium, hard);

        // Lägg till eventhanterare för svårighetsnivåer och nytt spel
        newGame.setOnAction(e -> controller.onNewGame());  // Starta ett nytt spel
        easy.setOnAction(e -> controller.startNewGameWithLevel(SudokuUtilities.SudokuLevel.EASY));
        medium.setOnAction(e -> controller.startNewGameWithLevel(SudokuUtilities.SudokuLevel.MEDIUM));
        hard.setOnAction(e -> controller.startNewGameWithLevel(SudokuUtilities.SudokuLevel.HARD));

        // Help-meny
        Menu helpMenu = new Menu("Help");
        MenuItem aboutGame = new MenuItem("Game rules");
        MenuItem clear = new MenuItem("Clear");
        helpMenu.getItems().addAll(aboutGame, clear);

        // Lägg till alla menyer (File, Game, Help) till menyraden
        this.getMenus().addAll(fileMenu, gameMenu, helpMenu);

        // Hantera avsluta spelet
        exitGame.setOnAction(e -> System.exit(0));  // Avsluta applikationen

        // Hantera "Load game" och "Save game"
        loadGame.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load Sudoku Game");
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                String filePath = file.getAbsolutePath();
                controller.loadGame(filePath);
            }
        });

        saveGame.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Sudoku Game");
            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) {
                String filePath = file.getAbsolutePath();
                controller.saveGame(filePath);
            }
        });

        clear.setOnAction(e -> controller.clearAllEditableCells());

        // Hantera "About"
        aboutGame.setOnAction(e -> showAboutAlert());  // Visa en dialog med information om spelet
    }

    private void showAboutAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Sudoku");
        alert.setHeaderText("Sudoku Game Information");
        alert.setContentText("Sudoku är ett logikspel där målet är att fylla ett 9x9 rutnät med siffror.\n" +
                "Varje rad, kolumn och 3x3 sektion måste innehålla siffrorna 1 till 9 en gång var.\n" +
                "Förifyllda siffror hjälper dig att börja, och din uppgift är att fylla resten korrekt.");
        alert.show();
    }
}