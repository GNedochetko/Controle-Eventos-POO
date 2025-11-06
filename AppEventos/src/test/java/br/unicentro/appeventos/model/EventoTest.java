package br.unicentro.appeventos.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EventoTest {

    @Test
    void setDataInicio() {
        Evento e = new Evento();

        e.setDataFim(LocalDate.of(2025, 5, 10));

        assertThrows(IllegalArgumentException.class, () -> {
            e.setDataInicio(LocalDate.of(2025, 5, 11));
        }, "A exceção tem que ser lançada porque a data de inicio é depois da data de fim");

        assertDoesNotThrow(() -> {
            e.setDataInicio(LocalDate.of(2025, 5, 9));
        }, "A exceção não deve ser lançada quando a data de inicio é antes da data de fim");
    }

    @Test
    void setDataFim() {
        Evento e = new Evento();

        e.setDataInicio(LocalDate.of(2025, 5, 10));

        assertThrows(IllegalArgumentException.class, () -> {
            e.setDataFim(LocalDate.of(2025, 5, 9));
        }, "A exceção tem que ser lançada porque a data de fim é antes da data de início");

        assertDoesNotThrow(() -> {
            e.setDataFim(LocalDate.of(2025, 5, 11));
        }, "A exceção não deve ser lançada quando a data de fim ´e depois da data de início");
    }

    @Test
    void setPrecoIngresso() {
        Evento e = new Evento();

        assertThrows(IllegalArgumentException.class, () -> {
            e.setPrecoIngresso(-1.0);
        }, "A exceção deve ser lançada porque o preço do ingresso não pode ser negativo");

        assertDoesNotThrow(() -> {
            e.setPrecoIngresso(20.50);
        }, "A exceção não deve ser lançada quando o preço do ingresso é maior ou igual a 0");
    }

    @Test
    void setCidade() {
        Evento e = new Evento();
        Cidade cidade = new Cidade();
        cidade.setCidadeId(1);
        cidade.setNome("Guarapuava");

        assertDoesNotThrow(() -> e.setCidade(cidade), "Nenhuma execeção deve ser lançada quando a cidade foi adicionada com sucesso");

        assertThrows(IllegalArgumentException.class, () -> {
            e.setCidade(null);
        }, "A exceção deve ser lançada quando a cidade não foi encontrada no banco de dados e retornou nula");
    }

    @Test
    public void testSetEventoIdErro() {
        Evento e = new Evento();

        e.setEventoId(-1);
        assertFalse(e.getEventoId() > 0, "O ID do evento não deve ser negativo");
    }

    @Test
    public void testSetEventoIdCrt() {
        Evento e = new Evento();

        e.setEventoId(1);
        assertTrue(e.getEventoId() > 0, "O ID colocado deve ser positivo");
    }
}