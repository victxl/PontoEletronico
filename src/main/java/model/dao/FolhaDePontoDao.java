package model.dao;

import model.entities.FolhaDePonto;

import java.time.LocalDate;
import java.util.List;

public interface FolhaDePontoDao {
    void insert(FolhaDePonto obj);
    void update(FolhaDePonto obj);
    void deleteById(Integer id);
    FolhaDePonto findById(Integer id);
    List<FolhaDePonto> findAll();
    FolhaDePonto findByFuncionarioId(Integer funcionarioId);
    FolhaDePonto findByFuncionarioIdAndData(Integer funcionarioId, LocalDate date);
}
