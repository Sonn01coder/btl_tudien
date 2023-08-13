module com.example.version4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.version4 to javafx.fxml;
    exports com.example.version4;
}