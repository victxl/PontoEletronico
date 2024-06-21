package com.pontoeletronico;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
        // Initialization logic if needed
    }

    public void setUsuarioLogado(Login usuario) {
        this.usuarioLogado = usuario;
        configurarPermissoes();
        System.out.println("Usuario Logado: " + usuario.getNome() + ", Tipo: " + usuario.getTipo());
    }

    private void configurarPermissoes() {
        if ("admin".equals(usuarioLogado.getTipo())) {
            menuItemRegistrarUsuario.setVisible(true);
            menuItemFormFolhaDePonto.setVisible(true);
            menuItemFuncionario.setVisible(true);
            menuItemDepartamento.setVisible(true);
            menuItemFolhaDePonto.setVisible(true);
            System.out.println("Permissões para admin configuradas");
        } else if ("Funcionario".equals(usuarioLogado.getTipo())) {
            menuItemRegistrarUsuario.setVisible(false);
            menuItemFormFolhaDePonto.setVisible(true);
            menuItemFuncionario.setVisible(false);
            menuItemDepartamento.setVisible(false);
            menuItemFolhaDePonto.setVisible(false);
            System.out.println("Permissões para funcionario configuradas");
        }
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

    private <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane newPane = loader.load();
            mainPane.getChildren().clear();
            mainPane.getChildren().add(newPane);

            T controller = loader.getController();
            if (initializingAction != null) {
                initializingAction.accept(controller);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onMenuItemLogout() {
        try {
            // Carregar a tela de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pontoeletronico/Login.fxml"));
            VBox vbox = loader.load();
            Scene loginScene = new Scene(vbox);
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("Erro", null, "Erro ao carregar a tela de login.", Alert.AlertType.ERROR);
        }
    }

}
