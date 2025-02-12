package com.sportclub.sportclub.sportclub.Bo.Custom;

import com.sportclub.sportclub.sportclub.Bo.SuperBO;
import com.sportclub.sportclub.sportclub.dto.SportDTO;
import com.sportclub.sportclub.sportclub.entity.Sport;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SportBO  extends SuperBO {
    String getNext() throws SQLException, ClassNotFoundException;

    boolean save(Sport DTO) throws SQLException, ClassNotFoundException;

    ArrayList<SportDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean update(Sport DTO) throws SQLException, ClassNotFoundException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException;
}
