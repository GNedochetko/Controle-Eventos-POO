package br.unicentro.appeventos.controller;

import br.unicentro.appeventos.dao.EventoDAO;
import br.unicentro.appeventos.view.EventoListCell;
import br.unicentro.appeventos.model.Evento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TelaPesquisarEventoController {
    private List<Evento> resultados = new ArrayList<>();
    private String termoPesquisado;
    private int paginaAtual = 0;
    private final int eventosPorPagina = 5;
    private SceneController sceneController = new SceneController();

    @FXML
    private Button botaoAnterior;

    @FXML
    private Button botaoProximo;

    @FXML
    private ListView<Evento> listaEventos;


    public void setTermoPesquisado(String termo) {
        this.termoPesquisado = termo.toLowerCase();
        ActionEvent event = new ActionEvent();
        carregarResultados(event);
    }

    /*agora a lógica por trás de apresentar a listView na tela foi transferida para o eventoListCell, deixando o controller organizado,
    sem métodos da interface gráfica dentro dele
     */
    @FXML
    public void initialize() {
        listaEventos.setCellFactory(list ->
                new EventoListCell(termoPesquisado, this::abrirDetalhes)
        );
    }

    /*esse metodo é passado por parametro quando se inicia o EventoListCell     porque o botão de ver detalhes está naquela classe, mas
    a responsabilidade de trocar de tela tem que estar no controller
     */
    private void abrirDetalhes(Evento evento) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/br/unicentro/appeventos/view/TelaVisualizarEvento.fxml"));
            Parent root = loader.load();

            TelaVisualizarEventoController ctrl = loader.getController();
            ctrl.preencheCampos(evento);

            Stage stage = (Stage) listaEventos.getScene().getWindow();
            stage.setTitle("Visualizar Evento");

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /*A refatoração do metodo carregar é feita a fim de deixar a operação de acesso ao banco de dados assíncrona, para que
    a Thread de UI não trave enquanto a operação de busca e listagem está sendo feita (com um banco muito populoso isso se torna um problema)
     */
    @FXML
    public void carregarResultados(ActionEvent event) {
        //botões de alternar páginas devem ficar desativados enquanto o resultado está sendo carregado
        botaoAnterior.setDisable(true);
        botaoProximo.setDisable(true);
        //label para informar que resultados estão sendo carregados
        listaEventos.setPlaceholder(new Label("Carregando..."));

        //criação da task que vai listar todos os eventos do banco de dados e retornar em uma lista
        Task<List<Evento>> task = new Task<>() {
            @Override
            protected List<Evento> call() {
                EventoDAO eventoDAO = new EventoDAO();
                return eventoDAO.listarTodos();
            }
        };

        //caso tudo ocorra corretamente, os eventos serão apresentados na tela normalmente como era antes
        task.setOnSucceeded(e -> {
            List<Evento> todosEventos = task.getValue();
            resultados.clear();
            for (Evento evento : todosEventos) {
                String nome = evento.getNome().toLowerCase();
                String cidade = evento.getCidade().getNome().toLowerCase();
                String estado = evento.getCidade().getEstado().getNome().toLowerCase();

                if (nome.contains(termoPesquisado) || cidade.contains(termoPesquisado) || estado.contains(termoPesquisado)) {
                    resultados.add(evento);
                }
            }
            paginaAtual = 0;
            exibirPagina(paginaAtual);
            botaoAnterior.setDisable(resultados.isEmpty());
            botaoProximo.setDisable(resultados.size() <= eventosPorPagina);
        });

        //caso algum erro ocorra, será alertado ao usuário
        task.setOnFailed(e -> {
            alertar("Erro ao buscar eventos: " + task.getException().getMessage());
        });

        //inicia a Thread que vai fazer o processamento, agora a UI não trava enquanto os resultados estão sendo carregados
        new Thread(task).start();
    }

    private void exibirPagina(int pagina) {
        int inicio = pagina * eventosPorPagina;
        int fim = Math.min(inicio + eventosPorPagina, resultados.size());

        ObservableList<Evento> paginaEventos = FXCollections.observableArrayList(resultados.subList(inicio, fim));
        listaEventos.setItems(paginaEventos);

        botaoAnterior.setDisable(pagina == 0);
        botaoProximo.setDisable(fim >= resultados.size());
    }

    @FXML
    void botaoAnteriorOnAction(ActionEvent event) {
        if (paginaAtual > 0) {
            paginaAtual--;
            exibirPagina(paginaAtual);
        }
    }

    @FXML
    void botaoProximoOnAction(ActionEvent event) {
        if ((paginaAtual + 1) * eventosPorPagina < resultados.size()) {
            paginaAtual++;
            exibirPagina(paginaAtual);
        }
    }

    @FXML
    void botaoVoltarOnAction(ActionEvent event){
        sceneController.trocarTela("/br/unicentro/appeventos/view/TelaInicial.fxml", "Tela Inicial", event);
    }

    private void alertar(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
