module com.kth25.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kth25.demo to javafx.fxml;
    exports com.kth25.demo;
    exports com.kth25.demo.model;
    opens com.kth25.demo.model to javafx.fxml;
}