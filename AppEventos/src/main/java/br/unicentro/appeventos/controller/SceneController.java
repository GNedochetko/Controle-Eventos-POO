package br.unicentro.appeventos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*A criação da classe SceneController é uma refatoração que tem objetivo de fazer com que função trocarTela, que antes era feita manualmente toda vez,
agora seja chamada atraves de um SceneController, minimizando o código repetido que estava espalhado por todos os controllers.
 */
public class SceneController {
    public void trocarTela(String fxmlPath, String titulo, ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
