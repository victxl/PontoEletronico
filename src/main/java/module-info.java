module com.pontoeletronico {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.ooxml;
    opens model.entities to javafx.base;

    opens com.pontoeletronico to javafx.fxml;
    exports com.pontoeletronico;
}

