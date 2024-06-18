package model.services;

import model.dao.DaoFactory;
import model.dao.FolhaDePontoDao;
import model.entities.FolhaDePonto;

import java.util.List;

public class FolhaDePontoService {

    private FolhaDePontoDao dao = DaoFactory.createFolhaDePontoDao();

    public FolhaDePonto findPontoByFuncionario(Integer funcionarioId) {
        return dao.findByFuncionarioId(funcionarioId);
    }

    public void save(FolhaDePonto folhaDePonto) {
        if (folhaDePonto.getId() == 0) {
            dao.insert(folhaDePonto);
        } else {
            dao.update(folhaDePonto);
        }
    }

    public List<FolhaDePonto> findAll() {
        return dao.findAll();
    }

    public FolhaDePonto findPontoByFuncionarioId(Integer funcionarioId) {
        return dao.findByFuncionarioId(funcionarioId);
    }
}
