module com.pontoeletronico {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens model.entities to javafx.base;

    opens com.pontoeletronico to javafx.fxml;
    exports com.pontoeletronico;
}