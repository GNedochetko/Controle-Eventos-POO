package br.unicentro.appeventos.dao;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import br.unicentro.appeventos.model.Evento;
import br.unicentro.appeventos.model.Cidade;

public class EventoDAO implements IEvento {
    private String sql;

    public void inserir(Evento evento) {
        sql = """
            INSERT INTO Evento (nome, descricao, dataInicio, dataFim, precoIngresso, cidadeId)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            stmt.setDate(3, Date.valueOf(evento.getDataInicio()));
            stmt.setDate(4, Date.valueOf(evento.getDataFim()));
            stmt.setDouble(5, evento.getPrecoIngresso());
            stmt.setInt(6, evento.getCidade().getCidadeId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Evento evento) {
        sql = """
            UPDATE Evento SET nome=?, descricao=?, dataInicio=?, dataFim=?, precoIngresso=?, cidadeId=?
            WHERE eventoId=?
            """;
        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            stmt.setDate(3, Date.valueOf(evento.getDataInicio()));
            stmt.setDate(4, Date.valueOf(evento.getDataFim()));
            stmt.setDouble(5, evento.getPrecoIngresso());
            stmt.setInt(6, evento.getCidade().getCidadeId());
            stmt.setInt(7, evento.getEventoId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean excluir(int eventoId) {
        sql = "DELETE FROM Evento WHERE eventoId=?";
        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, eventoId);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Evento> listarTodos() {
        List<Evento> lista = new ArrayList<>();
        sql = "SELECT * FROM Evento ORDER BY dataInicio";

        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CidadeDAO cidadeDAO = new CidadeDAO();
                Cidade cidade = cidadeDAO.buscarPorId(rs.getInt("cidadeId"));

                Evento evento = new Evento(
                        rs.getInt("eventoId"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDate("dataInicio").toLocalDate(),
                        rs.getDate("dataFim").toLocalDate(),
                        rs.getDouble("precoIngresso"),
                        cidade
                );
                lista.add(evento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Evento buscarPorNome(String nome) {
        sql = "SELECT * FROM Evento WHERE LOWER(nome) = LOWER(?) LIMIT 1";

        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome); // NÃO precisa de % pois é MATCH EXATO
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cidade cidade = new Cidade();
                cidade.setCidadeId(rs.getInt("cidadeId"));

                return new Evento(
                        rs.getInt("eventoId"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDate("dataInicio").toLocalDate(),
                        rs.getDate("dataFim").toLocalDate(),
                        rs.getDouble("precoIngresso"),
                        cidade
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}