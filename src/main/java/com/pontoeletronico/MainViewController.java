package com.pontoeletronico;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.entities.Login;
import model.services.DepartamentoService;
import model.services.FolhaDePontoService;
import model.services.FuncionarioService;
import util.Alerts;

import java.io.IOException;
import java.util.function.Consumer;

public class MainViewController {

    @FXML
    private VBox mainPane;

    @FXML
    private MenuItem menuItemRegistrarUsuario;
    @FXML
    private MenuItem menuItemFormFolhaDePonto;
    @FXML
    private MenuItem menuItemFolhaDePonto;
    @FXML
    private MenuItem menuItemFuncionario;
    @FXML
    private MenuItem menuItemDepartamento;
    @FXML
    private MenuItem menuItemSobre;
    @FXML
    private MenuItem menuItemLogout;

    private Login usuarioLogado;

    @FXML
    private void initialize() {
        // Inicialização
    }

    public void setUsuarioLogado(Login usuario) {
        this.usuarioLogado = usuario;
        // configurarPermissoes();
        System.out.println("Usuario Logado: " + usuarioLogado.getNome() + ", Tipo: " + usuarioLogado.getTipo());
    }

    @FXML
    private void onMenuItemRegistrarUsuario() {
        loadView("/com/pontoeletronico/RegistroUsuario.fxml", null);
    }

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

    @FXML
    private void onMenuItemLogout() {
        Main.restartApplication();
    }

    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Parent newView = loader.load(); // Usando Parent em vez de VBox

            Scene mainScene = Main.getMainScene();
            VBox mainVBox;
            if (mainScene.getRoot() instanceof ScrollPane) {
                mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            } else if (mainScene.getRoot() instanceof VBox) {
                mainVBox = (VBox) mainScene.getRoot();
            } else {
                throw new IllegalStateException("Unsupported root node: " + mainScene.getRoot().getClass().getName());
            }

            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().add(newView); // Adicionando a nova visualização carregada

            T controller = loader.getController();
            if (initializingAction != null) {
                initializingAction.accept(controller);
            }
        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

}



