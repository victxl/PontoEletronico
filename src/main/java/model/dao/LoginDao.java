package model.dao;

import model.entities.Login;

import java.util.List;

public interface LoginDao {

    void insert(Login obj);
    void update(Login obj);
    void delete(int id);
    Login findById(int id);
    List<Login> findAll();
}
