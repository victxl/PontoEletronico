package com.pontoeletronico;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.services.DepartamentoService;
import model.services.FolhaDePontoService;
import model.services.FuncionarioService;

import java.io.IOException;
import java.util.function.Consumer;

public class MainViewController {

    @FXML
    private VBox mainPane;

//    @FXML
//    private void onMenuItemFormFolhaDePonto() {
//        loadView("/com/pontoeletronico/FormFolhaDePonto.fxml", controller -> {
//            FormFolhaDePontoController folhaController = (FormFolhaDePontoController) controller;
//            folhaController.setFolhaDePontoService(new FolhaDePontoService());
//            folhaController.setFuncionarioService(new FuncionarioService());
//        });
//    }


    @FXML
    private void onMenuItemFormFolhaDePonto() {
        loadView("/com/pontoeletronico/FormFolhaDePonto.fxml", (FormFolhaDePontoController controller) -> {
            controller.setFolhaDePontoService(new FolhaDePontoService());
            controller.setFuncionarioService(new FuncionarioService());
        });
    }



    @FXML
    private void onMenuItemFolhaDePonto() {
        loadView("/com/pontoeletronico/FolhaDePonto.fxml", controller -> {
            FolhaDePontoController folhaController = (FolhaDePontoController) controller;
            folhaController.setFolhaDePontoService(new FolhaDePontoService());
            folhaController.setFuncionarioService(new FuncionarioService());
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

//    private void loadView(String fxml, ControllerInitializer initializer) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
//            Pane newLoadedPane = loader.load();
//
//            if (initializer != null) {
//                initializer.initialize(loader.getController());
//            }
//
//            mainPane.getChildren().clear();
//            mainPane.getChildren().add(newLoadedPane);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
private <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        T controller = loader.getController();
        if (initializingAction != null) {
            initializingAction.accept(controller);
        }
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}






    @FunctionalInterface
    private interface ControllerInitializer {
        void initialize(Object controller);
    }
}
