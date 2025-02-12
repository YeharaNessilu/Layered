package com.sportclub.sportclub.sportclub.dao.custom;

import com.sportclub.sportclub.sportclub.dao.CrudDao;

import com.sportclub.sportclub.sportclub.entity.Coach;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CoachDao extends CrudDao <Coach>{

    Coach findById(String selectedcochId) throws SQLException;

    int countCoach() throws SQLException;

    ArrayList<String> getAllIds() throws SQLException;
}