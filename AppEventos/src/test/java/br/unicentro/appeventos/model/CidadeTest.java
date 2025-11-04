package br.unicentro.appeventos.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CidadeTest {
    @Test
    public void testEstadoNotNull() {
        Cidade c = new Cidade();

        c.setCidadeId(1);
        assertTrue(c.getEstado() != null, "O estado deve ser um objeto");
    }

    @Test
    public void testEstadoNull() {
        Cidade c = new Cidade();

        assertTrue(c.getEstado() == null, "O estado não deve ser null");
    }
}
