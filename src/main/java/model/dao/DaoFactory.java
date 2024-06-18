package model.dao;

import db.DB;
import model.dao.impl.DepartamentoDaoJDBC;
import model.dao.impl.FolhaDePontoDaoJDBC;
import model.dao.impl.FuncionarioDaoJDBC;

public class DaoFactory {

    public static FuncionarioDao createFuncionarioDao() {
        return new FuncionarioDaoJDBC(DB.getConnection());
    }

    public static DepartamentoDao createDepartamentoDao() {
        return new DepartamentoDaoJDBC(DB.getConnection());
    }

    public static FolhaDePontoDao createFolhaDePontoDao() {
        return new FolhaDePontoDaoJDBC(DB.getConnection());
    }
}
