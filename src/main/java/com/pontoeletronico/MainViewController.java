package com.pontoeletronico;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartamentoService;
import util.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
            loadView("/com/pontoeletronico/ListaDepartamento.fxml",(ListaDeDepartamentoController controller)->{
                controller.setDepartamentoService(new DepartamentoService());
                controller.updateTableView();
            });
        }

        @FXML
        public void onMenuItemSobre(ActionEvent event) {
           loadView("/com/pontoeletronico/SobreView.fxml", x -> {});
        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private synchronized <T> void  loadView(String absoluteNome, Consumer<T> inicializarAction) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteNome));
            VBox newVBox = loader.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVbox = (VBox) ((ScrollPane)mainScene.getRoot()).getContent();

            Node mainMenu = mainVbox.getChildren().get(0);
            mainVbox.getChildren().clear();
            mainVbox.getChildren().add(mainMenu);
            mainVbox.getChildren().addAll(newVBox.getChildren());

            T controller = loader.getController();
            inicializarAction.accept(controller);

        } catch (IOException e) {
            Alerts.showAlert("IO Exception","ERRO carregando view",e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
