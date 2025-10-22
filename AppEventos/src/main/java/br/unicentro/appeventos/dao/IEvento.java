package br.unicentro.appeventos.dao;
import br.unicentro.appeventos.model.Evento;

import java.util.List;

public interface IEvento {
    void inserir(Evento evento);
    void atualizar(Evento evento);
    void excluir(int eventoId);
    Evento buscarPorId(int eventoId);
    List<Evento> listarTodos();
    Evento buscarPorNome(String nome);
    List<Evento> buscarPorCidade(int cidadeId);
    List<Evento> buscarPorEstado(int estadoId);
}
