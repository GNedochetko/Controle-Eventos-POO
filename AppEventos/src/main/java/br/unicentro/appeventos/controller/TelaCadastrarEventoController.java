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

public class TelaCadastrarEventoController {

    @FXML
    private Button botaoCadastrarEvento;

    @FXML
    private TextField cidadeDigitado;

    @FXML
    private DatePicker dataFimDigitada;

    @FXML
    private DatePicker dataInicioDigitada;

    @FXML
    private TextArea descricaoDigitada;

    @FXML
    private TextField nomeDigitado;

    @FXML
    private TextField precoDigitado;

    private  EventoDAO eventoDAO = new EventoDAO();
    private CidadeDAO cidadeDAO = new CidadeDAO();
    private SceneController sceneController = new SceneController();

    @FXML
    private Button btnVoltar;

    @FXML
    void botaoCadastrarEventoOnAction(ActionEvent event) {
        String nome = nomeDigitado.getText().trim();
        if (nome.isEmpty()) {
            alertar("O nome do evento é obrigatório.");
            return;
        }

        if (eventoDAO.buscarPorNome(nome) != null) {
            alertar("Já existe um evento cadastrado com esse nome.");
            return;
        }

        LocalDate dataInicio = dataInicioDigitada.getValue();
        LocalDate dataFim = dataFimDigitada.getValue();
        if (dataInicio == null || dataFim == null) {
            alertar("Preencha as datas de início e fim.");
            return;
        }
        if (dataInicio.isAfter(dataFim)) {
            alertar("A data de início não pode ser depois da data de fim.");
            return;
        }

        double preco;
        try {
            preco = Double.parseDouble(precoDigitado.getText());
            if (preco < 0) throw new Exception();
        } catch (Exception e) {
            alertar("Preço inválido. Informe um número maior ou igual a zero.");
            return;
        }

        String nomeCidade = cidadeDigitado.getText().trim();
        Cidade cidade = cidadeDAO.buscarPorNome(nomeCidade);
        if (cidade == null) {
            alertar("Cidade não encontrada no banco de dados.");
            return;
        }

        Evento evento = new Evento(0, nome, descricaoDigitada.getText(), dataInicio, dataFim, preco, cidade);
        eventoDAO.inserir(evento);

        alertar("Evento cadastrado com sucesso!");

        sceneController.trocarTela("/br/unicentro/appeventos/view/TelaInicial.fxml", "Tela Inicial", event);
    }

    private void alertar(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    void BotaoVoltarOnAction(ActionEvent event){
        sceneController.trocarTela("/br/unicentro/appeventos/view/TelaInicial.fxml", "Tela Inicial", event);
    }
}

