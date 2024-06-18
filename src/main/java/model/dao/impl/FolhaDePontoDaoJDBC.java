package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.FolhaDePontoDao;
import model.entities.FolhaDePonto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FolhaDePontoDaoJDBC implements FolhaDePontoDao {

    private Connection conn;

    public FolhaDePontoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(FolhaDePonto obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO folha_de_ponto "
                            + "(funcionario_id, data, hora_entrada, hora_saida) "
                            + "VALUES (?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            st.setInt(1, obj.getFuncionarioId());
            st.setDate(2, java.sql.Date.valueOf(obj.getData()));
            st.setTime(3, Time.valueOf(obj.getHoraEntrada()));
            st.setTime(4, Time.valueOf(obj.getHoraSaida()));

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    obj.setId(rs.getInt(1));
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(FolhaDePonto obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE folhadeponto "
                            + "SET funcionario_id = ?, data = ?, hora_entrada = ?, hora_saida_intervalo = ?, hora_entrada_intervalo = ?, hora_saida = ? "
                            + "WHERE id = ?");

            st.setInt(1, obj.getFuncionarioId());
            st.setDate(2, java.sql.Date.valueOf(obj.getData()));
            st.setTime(3, java.sql.Time.valueOf(obj.getHoraEntrada()));
            st.setTime(4, java.sql.Time.valueOf(obj.getHoraSaidaIntervalo()));
            st.setTime(5, java.sql.Time.valueOf(obj.getHoraEntradaIntervalo()));
            st.setTime(6, java.sql.Time.valueOf(obj.getHoraSaida()));
            st.setInt(7, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        // Implementação omitida
    }

    @Override
    public FolhaDePonto findById(Integer id) {
        // Implementação omitida
        return null;
    }

    @Override
    public List<FolhaDePonto> findAll() {
        return List.of();
    }

    @Override
    public FolhaDePonto findByFuncionarioId(Integer funcionarioId) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM folhadeponto WHERE funcionario_id = ?");
            st.setInt(1, funcionarioId);
            rs = st.executeQuery();
            if (rs.next()) {
                FolhaDePonto obj = new FolhaDePonto();
                obj.setId(rs.getInt("id"));
                obj.setFuncionarioId(rs.getInt("funcionario_id"));
                obj.setData(rs.getDate("data").toLocalDate());
                obj.setHoraEntrada(rs.getTime("hora_entrada").toLocalTime());
                obj.setHoraSaidaIntervalo(rs.getTime("hora_saida_intervalo").toLocalTime());
                obj.setHoraEntradaIntervalo(rs.getTime("hora_entrada_intervalo").toLocalTime());
                obj.setHoraSaida(rs.getTime("hora_saida").toLocalTime());
                return obj;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
