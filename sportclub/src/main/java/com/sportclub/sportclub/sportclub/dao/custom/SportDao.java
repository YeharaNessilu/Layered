package com.sportclub.sportclub.sportclub.dao.custom;

import com.sportclub.sportclub.sportclub.dao.CrudDao;
import com.sportclub.sportclub.sportclub.dto.SportDTO;
import com.sportclub.sportclub.sportclub.entity.Sport;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SportDao extends CrudDao <Sport>{

     SportDTO findById(String selectedSportId) throws SQLException, ClassNotFoundException;

     ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException;
}
