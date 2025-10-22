package br.unicentro.appeventos.dao;

import java.sql.*;
import java.util.*;
import br.unicentro.appeventos.model.Cidade;
import br.unicentro.appeventos.model.Estado;

public class CidadeDAO implements ICidade {
    private String sql;

    public void inserir(Cidade cidade) {
        sql = "INSERT INTO Cidade (nome, populacao, area, estadoId) VALUES (?, ?, ?, ?)";
        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cidade.getNome());
            stmt.setInt(2, cidade.getPopulacao());
            stmt.setDouble(3, cidade.getArea());
            stmt.setInt(4, cidade.getEstado().getEstadoId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Cidade cidade) {
        sql = "UPDATE Cidade SET nome = ?, populacao = ?, area = ?, estadoId = ? WHERE cidadeId = ?";
        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cidade.getNome());
            stmt.setInt(2, cidade.getPopulacao());
            stmt.setDouble(3, cidade.getArea());
            stmt.setInt(4, cidade.getEstado().getEstadoId());
            stmt.setInt(5, cidade.getCidadeId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(int cidadeId) {
        sql = "DELETE FROM Cidade WHERE cidadeId = ?";
        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, cidadeId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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



    public List<Cidade> listarTodos() {
        List<Cidade> lista = new ArrayList<>();
        sql = """
            SELECT c.*, e.estadoId, e.nome AS estadoNome, e.sigla, e.regiao
            FROM Cidade c JOIN Estado e ON c.estadoId = e.estadoId
            ORDER BY c.nome
            """;

        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Estado estado = new Estado(
                        rs.getInt("estadoId"),
                        rs.getString("estadoNome"),
                        rs.getString("sigla"),
                        rs.getString("regiao")
                );
                Cidade cidade = new Cidade(
                        rs.getInt("cidadeId"),
                        rs.getString("nome"),
                        rs.getInt("populacao"),
                        rs.getDouble("area"),
                        estado
                );
                lista.add(cidade);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Cidade> buscarPorEstado(int estadoId) {
        List<Cidade> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cidade WHERE estadoId = ? ORDER BY nome";

        try (Connection conexao = Conexao.getConexao(Conexao.stringDeConexao, Conexao.usuario, Conexao.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, estadoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cidade cidade = new Cidade(
                        rs.getInt("cidadeId"),
                        rs.getString("nome"),
                        rs.getInt("populacao"),
                        rs.getDouble("area"),
                        new Estado(estadoId, null, null, null)
                );
                lista.add(cidade);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

