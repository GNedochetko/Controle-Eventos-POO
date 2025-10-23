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

    @FXML
    private Button botaoEditarEvento;

    @FXML
    private Button btnVoltar;

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

    @FXML
    void BotaoEditarEventoOnAction(ActionEvent event) {

    }
}