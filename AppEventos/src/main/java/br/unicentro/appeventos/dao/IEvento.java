package br.unicentro.appeventos.dao;
import br.unicentro.appeventos.model.Evento;

import java.util.List;

public interface IEvento {
    void inserir(Evento evento);
    void atualizar(Evento evento);
    boolean excluir(int eventoId);
    List<Evento> listarTodos();
    Evento buscarPorNome(String nome);
}
