module com.example.dictionary {
    requires org.controlsfx.controls;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.json;
    requires json.simple;
    requires org.kordamp.bootstrapfx.core;
    opens com.example.dictionary to javafx.fxml;
    exports com.example.dictionary;
}