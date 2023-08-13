module com.example.version5 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.version5 to javafx.fxml;
    exports com.example.version5;
}