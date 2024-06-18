package model.services;

import model.dao.DaoFactory;
import model.dao.FolhaDePontoDao;
import model.entities.FolhaDePonto;

import java.time.LocalDate;
import java.util.List;

public class FolhaDePontoService {

    private FolhaDePontoDao dao = DaoFactory.createFolhaDePontoDao();

    public FolhaDePonto findPontoByFuncionario(Integer funcionarioId) {
        return dao.findByFuncionarioId(funcionarioId);
    }

    public FolhaDePonto findPontoByFuncionarioIdAndData(Integer funcionarioId, LocalDate date) {
        return dao.findByFuncionarioIdAndData(funcionarioId, date);
    }

    public void save(FolhaDePonto obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }


    public List<FolhaDePonto> findAll() {
        return dao.findAll();
    }

    public FolhaDePonto findPontoByFuncionarioId(Integer funcionarioId) {
        return dao.findByFuncionarioId(funcionarioId);
    }
}
