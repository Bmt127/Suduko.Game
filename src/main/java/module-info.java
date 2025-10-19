module com.kth25.demo {
    requires javafx.controls;
    requires javafx.fxml;

    // JavaFX måste få access till din Main-klass
    exports com.kth25.demo to javafx.graphics;

    // Om du har controllers eller modeller:
    exports com.kth25.demo.model;
    exports view;

    // Om FXML laddar controllers i dessa paket, måste de öppnas:
    opens com.kth25.demo.model to javafx.fxml;
    opens view to javafx.fxml;
}
