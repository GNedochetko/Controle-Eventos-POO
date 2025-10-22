package br.unicentro.appeventos.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaInicialController {

    @FXML
    private TextField barraPesquisa;

    @FXML
    private Button botaoCadastro;

    @FXML
    private Button botaoPesquisa;

    @FXML
    void barraPesquisaOnAction(ActionEvent event) {
        String termo = barraPesquisa.getText().trim();

        if (!termo.isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/unicentro/appeventos/view/TelaPesquisarEvento.fxml"));
                Parent root = loader.load();

                TelaPesquisarEventoController controller = loader.getController();
                controller.setTermoPesquisado(termo);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Pesquisar Evento");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void botaoCadastroOnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/unicentro/appeventos/view/TelaCadastrarEvento.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cadastrar Evento");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void botaoPesquisaOnAction(ActionEvent event) {
        barraPesquisaOnAction(event);
    }
}
