package com.sportclub.sportclub.sportclub.dao.custom;

import com.sportclub.sportclub.sportclub.dao.CrudDao;
import com.sportclub.sportclub.sportclub.dto.memberDTO;
import com.sportclub.sportclub.sportclub.entity.member;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MemberDao extends CrudDao<member> {


     ArrayList<String> getAllMemberIds() throws SQLException, ClassNotFoundException;

     member findById(String selectedMemId) throws SQLException, ClassNotFoundException;

     int countCustomers() throws SQLException;
}
