package com.pontoeletronico;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.entities.Login;
import model.services.DepartamentoService;
import model.services.FolhaDePontoService;
import model.services.FuncionarioService;

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

    private <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane newPane = loader.load(); // Usando Pane para aceitar qualquer tipo de layout
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
