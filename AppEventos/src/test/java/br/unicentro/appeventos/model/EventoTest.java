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
        });

        assertDoesNotThrow(() -> {
            e.setDataInicio(LocalDate.of(2025, 5, 9));
        });
    }

    @Test
    void setDataFim() {
        Evento e = new Evento();

        e.setDataInicio(LocalDate.of(2025, 5, 10));

        assertThrows(IllegalArgumentException.class, () -> {
            e.setDataFim(LocalDate.of(2025, 5, 9));
        });

        assertDoesNotThrow(() -> {
            e.setDataFim(LocalDate.of(2025, 5, 11));
        });
    }

    @Test
    void setPrecoIngresso() {
        Evento e = new Evento();

        assertThrows(IllegalArgumentException.class, () -> {
            e.setPrecoIngresso(-1.0);
        });

        assertDoesNotThrow(() -> {
            e.setPrecoIngresso(20.50);
        });
    }

    @Test
    void setCidade() {
        Evento e = new Evento();
        Cidade cidade = new Cidade();
        cidade.setCidadeId(1);
        cidade.setNome("Guarapuava");

        assertDoesNotThrow(() -> e.setCidade(cidade));

        assertThrows(IllegalArgumentException.class, () -> {
            e.setCidade(null);
        });
    }
}