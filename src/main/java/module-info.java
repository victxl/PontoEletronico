module com.pontoeletronico {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.pontoeletronico to javafx.fxml;
    exports com.pontoeletronico;
}