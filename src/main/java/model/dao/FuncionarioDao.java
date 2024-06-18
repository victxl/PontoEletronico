package model.dao;


import model.entities.Departamento;
import model.entities.Funcionario;

import java.util.List;

public interface FuncionarioDao {

    void insert(Funcionario obj);
    void update(Funcionario obj);
    void delete(Integer obj);
    Funcionario findById(Integer id);



    List<Funcionario> findAll();
    List<Funcionario> findByDepartamento(Departamento departamento);


}
