package model.dao;

import model.entities.Departamento;
import model.entities.Vendedor;

import java.util.List;

public interface VendedorDao {

    void insert(Vendedor obj);
    void update(Vendedor obj);
    void delete(Integer obj);
    Vendedor findById(Integer id);



    List<Vendedor> findAll();
    List<Vendedor> findByDepartamento(Departamento departamento);


}
