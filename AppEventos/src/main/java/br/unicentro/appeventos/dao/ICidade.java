package br.unicentro.appeventos.dao;

import br.unicentro.appeventos.model.Cidade;
import java.util.List;

public interface ICidade {
    void inserir(Cidade cidade);
    void atualizar(Cidade cidade);
    void excluir(int cidadeId);
    Cidade buscarPorId(int cidadeId);
    List<Cidade> listarTodos();
    List<Cidade> buscarPorEstado(int estadoId);
}
