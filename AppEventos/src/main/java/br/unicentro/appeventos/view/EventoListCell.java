package br.unicentro.appeventos.view;

import br.unicentro.appeventos.model.Evento;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextFlow;

//toda a lógica FXML foi trazida para cá, separando-a do controller, o que não fazia sentido antes
public class EventoListCell extends ListCell<Evento> {

    private final String termoPesquisado;
    private final DetalhesHandler detalhesHandler;

    // essa interface deve ser criada para que o EventoListCell receba o metodo abrirDetalhes depois
    public interface DetalhesHandler {
        void abrirDetalhes(Evento evento);
    }

    public EventoListCell(String termoPesquisado, DetalhesHandler handler) {
        this.termoPesquisado = termoPesquisado.toLowerCase();
        this.detalhesHandler = handler;
    }

    @Override
    protected void updateItem(Evento evento, boolean empty) {
        super.updateItem(evento, empty);

        if (empty || evento == null) {
            setText(null);
            setGraphic(null);
            return;
        }

        Node textoComHighlight = criarTextoComHighlight(evento.toString(), termoPesquisado);

        Button btnDetalhes = new Button("Ver detalhes");
        btnDetalhes.setOnAction(e -> detalhesHandler.abrirDetalhes(evento));

        HBox row = new HBox(10, textoComHighlight, btnDetalhes);
        row.setFillHeight(true);
        HBox.setHgrow(textoComHighlight, Priority.ALWAYS);

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
}
