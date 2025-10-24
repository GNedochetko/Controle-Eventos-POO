package br.unicentro.appeventos.controller;

import br.unicentro.appeventos.dao.EventoDAO;
import br.unicentro.appeventos.model.Evento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    @FXML
    private Button botaoVoltar;

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

    @FXML
    public void initialize() {
        listaEventos.setCellFactory(list -> new ListCell<Evento>() {
            @Override
            protected void updateItem(Evento evento, boolean empty) {
                super.updateItem(evento, empty);

                if (empty || evento == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                String linha  = evento.toString();

                Node marcado = criarTextoComHighlight(linha, termoPesquisado);

                Button btnDetalhes = new Button("Ver detalhes");
                btnDetalhes.setOnAction(e -> botaoDetalhesOnAction(evento));

                HBox row = new HBox(10, marcado, btnDetalhes);
                row.setFillHeight(true);

                HBox.setHgrow(marcado, Priority.ALWAYS);

                setText(null);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(row);
            }

            private Node criarTextoComHighlight(String texto, String termo) {
                if (termo == null || termo.isBlank()) {
                    return new Label(texto);
                }

                String base = texto;
                String tLower = termo.toLowerCase();
                TextFlow flow = new TextFlow();
                flow.setLineSpacing(0);

                while (true) {
                    int idx = base.toLowerCase().indexOf(tLower);
                    if (idx < 0) {
                        if (!base.isEmpty()) {
                            Label lbl = new Label(base);
                            lbl.setStyle("-fx-font-weight: normal;");
                            flow.getChildren().add(lbl);
                        }
                        break;
                    }
                    String antes = base.substring(0, idx);
                    if (!antes.isEmpty()) {
                        Label lAntes = new Label(antes);
                        lAntes.setStyle("-fx-font-weight: normal;");
                        flow.getChildren().add(lAntes);
                    }

                    String achado = base.substring(idx, idx + tLower.length());
                    Label lAchado = new Label(achado);
                    lAchado.setStyle(
                            "-fx-background-color: yellow; " +
                                    "-fx-background-radius: 3; " +
                                    "-fx-padding: 0 2 0 2; " +
                                    "-fx-font-weight: bold;"
                    );
                    flow.getChildren().add(lAchado);

                    base = base.substring(idx + tLower.length());
                }

                return flow;
            }

        });
    }

    private void carregarResultados(ActionEvent event) {
        EventoDAO eventoDAO = new EventoDAO();
        List<Evento> todosEventos = eventoDAO.listarTodos();

        resultados.clear();
        for (Evento evento : todosEventos) {
            String nome = evento.getNome().toLowerCase();
            String cidade = evento.getCidade().getNome().toLowerCase();
            String estado = evento.getCidade().getEstado().getNome().toLowerCase();

            if (nome.contains(termoPesquisado) || cidade.contains(termoPesquisado) || estado.contains(termoPesquisado)) {
                resultados.add(evento);
            }
        }

        resultados.sort(Comparator.comparing((Evento e) -> !e.getNome().toLowerCase().contains(termoPesquisado))
                .thenComparing(e -> !e.getCidade().getNome().toLowerCase().contains(termoPesquisado))
                .thenComparing(e -> !e.getCidade().getEstado().getSigla().toLowerCase().contains(termoPesquisado)));

        if(resultados.size() == 0){
            alertar("Nenhum evento Encontrado.");

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/br/unicentro/appeventos/view/TelaInicial.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Tela Inicial");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            exibirPagina(paginaAtual);
        }
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

    private void alertar(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void botaoDetalhesOnAction(Evento evento) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/unicentro/appeventos/view/TelaVisualizarEvento.fxml"));
            Parent root = loader.load();

            TelaVisualizarEventoController ctrl = loader.getController();
            ctrl.setEvento(evento);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
