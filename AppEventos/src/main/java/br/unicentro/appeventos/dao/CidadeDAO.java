package br.unicentro.appeventos.dao;

import java.sql.*;
import java.util.*;
import br.unicentro.appeventos.model.Cidade;
import br.unicentro.appeventos.model.Estado;

public class CidadeDAO implements ICidade {
    private String sql;

    public Cidade buscarPorId(int cidadeId) {
        sql = """
            SELECT c.*, e.estadoId, e.nome AS estadoNome, e.sigla, e.regiao
            FROM Cidade c JOIN Estado e ON c.estadoId = e.estadoId
            WHERE c.cidadeId = ?
            """;
        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, cidadeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Estado estado = new Estado(
                        rs.getInt("estadoId"),
                        rs.getString("estadoNome"),
                        rs.getString("sigla"),
                        rs.getString("regiao")
                );
                return new Cidade(
                        rs.getInt("cidadeId"),
                        rs.getString("nome"),
                        rs.getInt("populacao"),
                        rs.getDouble("area"),
                        estado
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cidade buscarPorNome(String nome) {
        sql = """
            SELECT c.*, e.estadoId, e.nome AS estadoNome, e.sigla, e.regiao
            FROM Cidade c JOIN Estado e ON c.estadoId = e.estadoId
            WHERE c.nome = ?
            """;
        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Estado estado = new Estado(
                        rs.getInt("estadoId"),
                        rs.getString("estadoNome"),
                        rs.getString("sigla"),
                        rs.getString("regiao")
                );
                return new Cidade(
                        rs.getInt("cidadeId"),
                        rs.getString("nome"),
                        rs.getInt("populacao"),
                        rs.getDouble("area"),
                        estado
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

