package model.services;

import model.dao.DaoFactory;
import model.dao.DepartamentoDao;
import model.entities.Departamento;

import java.util.ArrayList;
import java.util.List;

public class DepartamentoService {

    private DepartamentoDao dao = DaoFactory.createDepartamentoDao();

    public List<Departamento> findAll() {
       return dao.findAll();
    }
}
