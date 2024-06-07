package com.pontoeletronico;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

        @FXML
        private MenuItem menuItemVendedor;
        @FXML
        private MenuItem menuItemDepartamento;
        @FXML
        private MenuItem menuItemSobre;

        @FXML
        public void onMenuItemVendedor(ActionEvent event) {
            System.out.println("Vendedor");
        }
    @FXML
        public void onMenuItemDepartamento(ActionEvent event) {
            System.out.println("Departamento");
        }
    @FXML
        public void onMenuItemSobre(ActionEvent event) {
            System.out.println("Sobre");
        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
