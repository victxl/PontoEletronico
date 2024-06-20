//package model.services;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import model.dao.DaoFactory;
//import model.dao.FolhaDePontoDao;
//import model.entities.FolhaDePonto;
//
//import java.time.LocalDate;
//import java.util.Collections;
//import java.util.List;
//
//public class FolhaDePontoService {
//
//    private FolhaDePontoDao dao = DaoFactory.createFolhaDePontoDao();
//
//    //public FolhaDePonto findPontoByFuncionario(Integer funcionarioId) {
//      //  return dao.findByFuncionarioId(funcionarioId);
//    //}
//    public List<FolhaDePonto> findByFuncionarioId(Integer funcionarioId) {
//        return dao.findByFuncionarioId(funcionarioId);
//    }
//
//    public FolhaDePonto findPontoByFuncionarioIdAndData(Integer funcionarioId, LocalDate date) {
//        return dao.findByFuncionarioIdAndData(funcionarioId, date);
//    }
//
//    public void save(FolhaDePonto obj) {
//        if (obj.getId() == null) {
//            dao.insert(obj);
//        } else {
//            dao.update(obj);
//        }
//    }
//
//
//
//    public List<FolhaDePonto> findByFuncionarioId(Integer funcionarioId) {
//        return dao.findByFuncionarioId(funcionarioId);
//    }
//
//
//
//
//
//
//    public ObservableList<FolhaDePonto> findAll() {
//        List<FolhaDePonto> list = dao.findAll();
//        return FXCollections.observableArrayList(list);
//    }
//
//    public FolhaDePonto findPontoByFuncionarioId(Integer funcionarioId) {
//        return dao.findByFuncionarioId(funcionarioId);
//    }
//
//    public void insert(FolhaDePonto ponto) {
//        dao.insert(ponto);
//    }
//
//    public void update(FolhaDePonto ponto) {
//        dao.update(ponto);
//    }
//}


package model.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dao.DaoFactory;
import model.dao.FolhaDePontoDao;
import model.entities.FolhaDePonto;

import java.time.LocalDate;
import java.util.List;

public class FolhaDePontoService {
    private FolhaDePontoDao dao = DaoFactory.createFolhaDePontoDao();

    public List<FolhaDePonto> findByFuncionarioId(Integer funcionarioId) {
        return dao.findByFuncionarioId(funcionarioId);
    }

    public ObservableList<FolhaDePonto> findAll() {
        List<FolhaDePonto> list = dao.findAll();
        return FXCollections.observableArrayList(list);
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

    public void insert(FolhaDePonto ponto) {
        dao.insert(ponto);
    }

    public void update(FolhaDePonto ponto) {
        dao.update(ponto);
    }
}
