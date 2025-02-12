package com.sportclub.sportclub.sportclub.dao;

import com.sportclub.sportclub.sportclub.dto.Payment3DTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDao <T>  extends SuperDAO{
    String getNext() throws SQLException, ClassNotFoundException;

    boolean save(T DTO) throws SQLException, ClassNotFoundException;

    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

    boolean update(T DTO) throws SQLException, ClassNotFoundException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;



}
