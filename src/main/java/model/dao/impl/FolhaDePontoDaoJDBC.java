package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.FolhaDePontoDao;
import model.entities.FolhaDePonto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FolhaDePontoDaoJDBC implements FolhaDePontoDao {

    private Connection conn;

    public FolhaDePontoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public FolhaDePonto findByFuncionarioIdAndData(Integer funcionarioId, LocalDate date) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM folha_de_ponto WHERE funcionario_id = ? AND data = ?");
            st.setInt(1, funcionarioId);
            st.setDate(2, java.sql.Date.valueOf(date));
            rs = st.executeQuery();
            if (rs.next()) {
                FolhaDePonto obj = instantiateFolhaDePonto(rs);
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

    private FolhaDePonto instantiateFolhaDePonto(ResultSet rs) throws SQLException {
        FolhaDePonto obj = new FolhaDePonto();
        obj.setId(rs.getInt("id"));
        obj.setFuncionarioId(rs.getInt("funcionario_id"));
        obj.setData(rs.getDate("data").toLocalDate());
        obj.setHoraEntrada(rs.getTime("hora_entrada") != null ? rs.getTime("hora_entrada").toLocalTime() : null);
        obj.setHoraEntradaIntervalo(rs.getTime("hora_entrada_intervalo") != null ? rs.getTime("hora_entrada_intervalo").toLocalTime() : null);
        obj.setHoraSaidaIntervalo(rs.getTime("hora_saida_intervalo") != null ? rs.getTime("hora_saida_intervalo").toLocalTime() : null);
        obj.setHoraSaida(rs.getTime("hora_saida") != null ? rs.getTime("hora_saida").toLocalTime() : null);
        return obj;
    }

    @Override
    public void insert(FolhaDePonto obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO folha_de_ponto (funcionario_id, data, hora_entrada, hora_entrada_intervalo, hora_saida_intervalo, hora_saida) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            st.setInt(1, obj.getFuncionarioId());
            st.setDate(2, java.sql.Date.valueOf(obj.getData()));
            st.setTime(3, obj.getHoraEntrada() != null ? java.sql.Time.valueOf(obj.getHoraEntrada()) : null);
            st.setTime(4, obj.getHoraEntradaIntervalo() != null ? java.sql.Time.valueOf(obj.getHoraEntradaIntervalo()) : null);
            st.setTime(5, obj.getHoraSaidaIntervalo() != null ? java.sql.Time.valueOf(obj.getHoraSaidaIntervalo()) : null);
            st.setTime(6, obj.getHoraSaida() != null ? java.sql.Time.valueOf(obj.getHoraSaida()) : null);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    obj.setId(rs.getInt(1));
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Erro inesperado! Nenhuma linha foi inserida!");
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
                    "UPDATE folha_de_ponto " +
                            "SET funcionario_id = ?, data = ?, hora_entrada = ?, hora_entrada_intervalo = ?, hora_saida_intervalo = ?, hora_saida = ? " +
                            "WHERE id = ?");

            st.setInt(1, obj.getFuncionarioId());
            st.setDate(2, java.sql.Date.valueOf(obj.getData()));
            st.setTime(3, obj.getHoraEntrada() != null ? java.sql.Time.valueOf(obj.getHoraEntrada()) : null);
            st.setTime(4, obj.getHoraEntradaIntervalo() != null ? java.sql.Time.valueOf(obj.getHoraEntradaIntervalo()) : null);
            st.setTime(5, obj.getHoraSaidaIntervalo() != null ? java.sql.Time.valueOf(obj.getHoraSaidaIntervalo()) : null);
            st.setTime(6, obj.getHoraSaida() != null ? java.sql.Time.valueOf(obj.getHoraSaida()) : null);
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

    }

    @Override
    public FolhaDePonto findById(Integer id) {
        return null;
    }

    @Override
    public List<FolhaDePonto> findAll() {
        return List.of();
    }

    @Override
    public FolhaDePonto findByFuncionarioId(Integer funcionarioId) {
        return null;
    }
}
