package br.unicentro.appeventos.dao;

import java.util.List;
import br.unicentro.appeventos.model.Estado;

public interface IEstado {
    void inserir(Estado estado);
    void atualizar(Estado estado);
    void excluir(int estadoId);
    Estado buscarPorId(int estadoId);
    List<Estado> listarTodos();
}