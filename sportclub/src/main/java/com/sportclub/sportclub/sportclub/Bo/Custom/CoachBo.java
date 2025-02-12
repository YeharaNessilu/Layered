package com.sportclub.sportclub.sportclub.Bo.Custom;

import com.sportclub.sportclub.sportclub.Bo.SuperBO;
import com.sportclub.sportclub.sportclub.dto.CoachDTO;
import com.sportclub.sportclub.sportclub.entity.Coach;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CoachBo extends SuperBO {
    String getNext() throws SQLException, ClassNotFoundException;

//    boolean save(CoachDTO DTO) throws SQLException;

    boolean save(Coach DTO) throws SQLException, ClassNotFoundException;

    ArrayList<CoachDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean update(CoachDTO DTO) throws SQLException, ClassNotFoundException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;
}
