package model.services;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Vendedor;

import java.util.List;

public class VendedorService {

    private VendedorDao dao = DaoFactory.createVendedorDao();

    public List<Vendedor> findAll() {
       return dao.findAll();
    }

    public void saveOrUpdate(Vendedor vendedor) {
        if (vendedor.getId() == null) {
            dao.insert(vendedor);
        }
        else {
            dao.update(vendedor);
        }
    }
    public void delete(Vendedor vendedor) {
        dao.delete(vendedor.getId());
    }
}
