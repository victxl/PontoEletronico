package model.dao.impl;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.FuncionarioDao;
import model.entities.Departamento;
import model.entities.Funcionario;

public class FuncionarioDaoJDBC implements FuncionarioDao {

    private Connection conn;

    public FuncionarioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Funcionario obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO funcionario "
                            + "(Nome, Email, DataNascimento, Cpf, HorarioExpediente, IdDepartamento) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());
            st.setDate(3, Date.valueOf(obj.getDataNascimento()));
            st.setString(4, obj.getCpf());
            st.setTime(5, Time.valueOf(obj.getHorarioExpediente()));
            st.setInt(6, obj.getDepartamento().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Erro inesperado! Nenhuma linha afetada!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Funcionario obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE funcionario "
                            + "SET Nome = ?, Email = ?, DataNascimento = ?, Cpf = ?, HorarioExpediente = ?, IdDepartamento = ? "
                            + "WHERE Id = ?");

            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());
            st.setDate(3, Date.valueOf(obj.getDataNascimento()));
            st.setString(4, obj.getCpf());
            st.setTime(5, Time.valueOf(obj.getHorarioExpediente()));
            st.setInt(6, obj.getDepartamento().getId());
            st.setInt(7, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void delete(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM funcionario WHERE Id = ?");

            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Funcionario findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT funcionario.*, departamento.Nome as DepNome "
                            + "FROM funcionario INNER JOIN departamento "
                            + "ON funcionario.IdDepartamento = departamento.Id "
                            + "WHERE funcionario.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Departamento dep = instantiateDepartamento(rs);
                Funcionario obj = instantiateFuncionario(rs, dep);
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

    private Funcionario instantiateFuncionario(ResultSet rs, Departamento dep) throws SQLException {
        Funcionario obj = new Funcionario();
        obj.setId(rs.getInt("Id"));
        obj.setNome(rs.getString("Nome"));
        obj.setEmail(rs.getString("Email"));
        obj.setCpf(rs.getString("Cpf"));
        obj.setHorarioExpediente(rs.getTime("HorarioExpediente").toLocalTime());
        obj.setDataNascimento(rs.getDate("DataNascimento").toLocalDate());
        obj.setDepartamento(dep);
        return obj;
    }

    private Departamento instantiateDepartamento(ResultSet rs) throws SQLException {
        Departamento dep = new Departamento();
        dep.setId(rs.getInt("IdDepartamento"));
        dep.setNome(rs.getString("DepNome"));
        return dep;
    }

    @Override
    public List<Funcionario> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT funcionario.*, departamento.Nome as DepNome "
                            + "FROM funcionario INNER JOIN departamento "
                            + "ON funcionario.IdDepartamento = departamento.Id "
                            + "ORDER BY Nome");

            rs = st.executeQuery();

            List<Funcionario> list = new ArrayList<>();
            Map<Integer, Departamento> map = new HashMap<>();

            while (rs.next()) {
                Departamento dep = map.get(rs.getInt("IdDepartamento"));

                if (dep == null) {
                    dep = instantiateDepartamento(rs);
                    map.put(rs.getInt("IdDepartamento"), dep);
                }

                Funcionario obj = instantiateFuncionario(rs, dep);
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Funcionario> findByDepartamento(Departamento departamento) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT funcionario.*, departamento.Nome as DepNome "
                            + "FROM funcionario INNER JOIN departamento "
                            + "ON funcionario.IdDepartamento = departamento.Id "
                            + "WHERE IdDepartamento = ? "
                            + "ORDER BY Nome");

            st.setInt(1, departamento.getId());

            rs = st.executeQuery();

            List<Funcionario> list = new ArrayList<>();
            Map<Integer, Departamento> map = new HashMap<>();

            while (rs.next()) {
                Departamento dep = map.get(rs.getInt("IdDepartamento"));

                if (dep == null) {
                    dep = instantiateDepartamento(rs);
                    map.put(rs.getInt("IdDepartamento"), dep);
                }

                Funcionario obj = instantiateFuncionario(rs, dep);
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
