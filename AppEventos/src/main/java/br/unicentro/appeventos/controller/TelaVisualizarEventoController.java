package br.unicentro.appeventos.controller;

import br.unicentro.appeventos.model.Evento;
import br.unicentro.appeventos.controller.TelaPesquisarEventoController;
import br.unicentro.appeventos.dao.EventoDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class TelaVisualizarEventoController {

    private Evento eventoAtual;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnVoltar;

    @FXML
    private Label lblCidade;

    @FXML
    private Label lblData;

    @FXML
    private Label lblDescricao;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblPreco;

    public void initialize() {
        lblNome.setText("");
        lblDescricao.setText("");
        lblData.setText("");
        lblCidade.setText("");
        lblPreco.setText("");
    }

    @FXML
    void onEditarBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/unicentro/appeventos/view/TelaEditarEvento.fxml"));
            Parent root = loader.load();

            TelaEditarEventoController ctrl = loader.getController();
            ctrl.preencherCampos(eventoAtual);
            ctrl.setIdEvento(eventoAtual.getEventoId());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Editar Evento");

            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onActionbtnExcluir(ActionEvent event) {
        if (eventoAtual == null) {return;}

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de exclusão");
        alert.setHeaderText("Excluir evento");
        alert.setContentText("Deseja realmente excluir o evento " + eventoAtual.getNome() + "?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            EventoDAO eventoDao = new EventoDAO();
            boolean sucesso = eventoDao.excluir(eventoAtual.getEventoId());

            if (sucesso) {
                Alert confirm = new Alert(Alert.AlertType.INFORMATION);
                confirm.setTitle("Exclusão realizada");
                confirm.setHeaderText(null);
                confirm.setContentText("O evento \"" + eventoAtual.getNome() + "\" foi excluído com sucesso!");
                confirm.showAndWait();

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/unicentro/appeventos/view/TelaInicial.fxml"));
                    Parent root = loader.load();

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Tela Inicial");
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Alert erro = new Alert(Alert.AlertType.ERROR);
                erro.setTitle("Erro");
                erro.setHeaderText("Não foi possível excluir");
                erro.setContentText("Ocorreu um problema ao excluir o evento.");
                erro.showAndWait();
            }
        }
    }

    @FXML
    void onActionbtnVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/unicentro/appeventos/view/TelaInicial.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tela Inicial");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void preencheCampos(Evento evento) {
        this.eventoAtual = evento;

        lblNome.setText(evento.getNome());
        lblDescricao.setText(evento.getDescricao());
        lblData.setText(evento.getDataInicio() + " a " + evento.getDataFim());
        lblCidade.setText(evento.getCidade().getNome());
        lblPreco.setText(String.valueOf(evento.getPrecoIngresso()));
    }

    public void setEvento(Evento evento) {
        this.eventoAtual = evento;
    }

}
