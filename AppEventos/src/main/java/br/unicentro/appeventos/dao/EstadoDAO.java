package br.unicentro.appeventos.dao;
import br.unicentro.appeventos.model.Estado;

import java.sql.*;
import java.util.*;

public class EstadoDAO implements IEstado, IConst {
    private String sql;

    public void inserir(Estado estado) {
        sql = "INSERT INTO Estado (nome, sigla, regiao) VALUES (?, ?, ?)";
        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, estado.getNome());
            stmt.setString(2, estado.getSigla());
            stmt.setString(3, estado.getRegiao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Estado estado) {
        sql = "UPDATE Estado SET nome = ?, sigla = ?, regiao = ? WHERE estadoId = ?";
        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, estado.getNome());
            stmt.setString(2, estado.getSigla());
            stmt.setString(3, estado.getRegiao());
            stmt.setInt(4, estado.getEstadoId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(int estadoId) {
        sql = "DELETE FROM Estado WHERE estadoId = ?";
        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, estadoId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Estado buscarPorId(int estadoId) {
        sql = "SELECT * FROM Estado WHERE estadoId = ?";
        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, estadoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Estado(
                        rs.getInt("estadoId"),
                        rs.getString("nome"),
                        rs.getString("sigla"),
                        rs.getString("regiao")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Estado> listarTodos() {
        List<Estado> lista = new ArrayList<>();
        String sql = "SELECT * FROM Estado ORDER BY nome";
        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Estado(
                        rs.getInt("estadoId"),
                        rs.getString("nome"),
                        rs.getString("sigla"),
                        rs.getString("regiao")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

