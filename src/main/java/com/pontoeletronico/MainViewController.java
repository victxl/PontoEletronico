package com.pontoeletronico;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.services.DepartamentoService;
import model.services.FolhaDePontoService;
import model.services.FuncionarioService;

import java.io.IOException;
import java.util.function.Consumer;

public class MainViewController {

    @FXML
    private VBox mainPane;

    @FXML
    private void onMenuItemFormFolhaDePonto() {
        loadView("/com/pontoeletronico/FormFolhaDePonto.fxml", (FormFolhaDePontoController controller) -> {
            controller.setFolhaDePontoService(new FolhaDePontoService());
            controller.setFuncionarioService(new FuncionarioService());
        });
    }

    @FXML
    private void onMenuItemFolhaDePonto() {
        loadView("/com/pontoeletronico/ListaDeFolhaDePonto.fxml", (ListaDeFolhaDePontoController controller) -> {
            controller.setFolhaDePontoService(new FolhaDePontoService());
            controller.setFuncionarioService(new FuncionarioService());
            controller.updateTableView();
        });
    }

    @FXML
    private void onMenuItemFuncionarioAction() {
        loadView("/com/pontoeletronico/ListaDeFuncionario.fxml", controller -> {
            ListaDeFuncionarioController funcionarioController = (ListaDeFuncionarioController) controller;
            funcionarioController.setFuncionarioService(new FuncionarioService());
            funcionarioController.updateTableView();
        });
    }

    @FXML
    private void onMenuItemDepartamentoAction() {
        loadView("/com/pontoeletronico/ListaDeDepartamento.fxml", controller -> {
            ListaDeDepartamentoController departamentoController = (ListaDeDepartamentoController) controller;
            departamentoController.setDepartamentoService(new DepartamentoService());
            departamentoController.updateTableView();
        });
    }

    @FXML
    private void onMenuItemSobreAction() {
        loadView("/com/pontoeletronico/SobreView.fxml", null);
    }

    private <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane newPane = loader.load();
            mainPane.getChildren().clear();
            mainPane.getChildren().add(newPane);
            VBox.setVgrow(newPane, javafx.scene.layout.Priority.ALWAYS);

            T controller = loader.getController();
            if (initializingAction != null) {
                initializingAction.accept(controller);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}