package br.unicentro.appeventos.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CidadeTest {
    @Test
    public void testEstadoNotNull() {
        Estado e = new Estado();
        Cidade c = new Cidade(11, "Pitanga", 10200, 200000.51, e);
        assertTrue(c.getEstado() != null, "O estado deve ser um objeto do tipo estado");
    }

    @Test
    public void testEstadoNull() {
        Cidade c = new Cidade();

        assertTrue(c.getEstado() == null, "O estado não deve ser null");
    }
}
