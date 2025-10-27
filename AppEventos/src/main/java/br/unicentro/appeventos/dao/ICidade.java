package br.unicentro.appeventos.dao;

import br.unicentro.appeventos.model.Cidade;
import java.util.List;

public interface ICidade {
    Cidade buscarPorId(int cidadeId);
    Cidade buscarPorNome(String nome);
}
