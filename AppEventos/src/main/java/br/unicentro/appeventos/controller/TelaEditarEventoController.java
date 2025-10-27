package br.unicentro.appeventos.controller;

import br.unicentro.appeventos.dao.CidadeDAO;
import br.unicentro.appeventos.dao.EventoDAO;
import br.unicentro.appeventos.model.Cidade;
import br.unicentro.appeventos.model.Evento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class TelaEditarEventoController {
    private int idEvento;
    private EventoDAO eventoDao = new EventoDAO();

    @FXML
    public Button btnSalvar;

    @FXML
    private Button btnVoltar;

    @FXML
    private TextField txtCidade;

    @FXML
    private DatePicker dpDataFim;

    @FXML
    private DatePicker dpDataIni;

    @FXML
    private TextArea txtDescricao;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPreco;

    @FXML
    void onActionBtnSalvar(ActionEvent event) {
        try {
            Evento e = new Evento();

            e.setEventoId(idEvento);
            e.setNome(txtNome.getText());
            e.setDescricao(txtDescricao.getText());
            e.setDataInicio(dpDataIni.getValue());
            e.setDataFim(dpDataFim.getValue());
            e.setPrecoIngresso(Double.parseDouble(txtPreco.getText()));

            CidadeDAO cidadeDao = new CidadeDAO();
            String nomeCidade = txtCidade.getText();
            Cidade c = cidadeDao.buscarPorNome(nomeCidade);
            e.setCidade(c);

            eventoDao.atualizar(e);

            alertar("Evento editado com sucesso!");
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/br/unicentro/appeventos/view/TelaInicial.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Tela Inicial");
                stage.show();
            } catch (IOException ex2) {
                ex2.printStackTrace();
            }

        } catch (Exception ex) {
            alertar("Ocorreu algum erro, evento não editado!");
            ex.printStackTrace();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/br/unicentro/appeventos/view/TelaInicial.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Tela Inicial");
                stage.show();
            } catch (IOException ex2) {
                ex2.printStackTrace();
            }

        }
    }

    @FXML
    void onActionbtnVoltar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/unicentro/appeventos/view/TelaInicial.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Tela Inicial");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void preencherCampos(Evento evento) {
        txtNome.setText(evento.getNome());
        dpDataIni.setValue(evento.getDataInicio());
        dpDataFim.setValue(evento.getDataFim());
        txtPreco.setText(String.valueOf(evento.getPrecoIngresso()));
        txtCidade.setText(evento.getCidade().getNome());
        txtDescricao.setText(evento.getDescricao());
    }

    private void alertar(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }
}