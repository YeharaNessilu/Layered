package com.sportclub.sportclub.sportclub.dao.custom;

import com.sportclub.sportclub.sportclub.dao.CrudDao;
import com.sportclub.sportclub.sportclub.dto.MemberDetailDTO;
import com.sportclub.sportclub.sportclub.dto.Payment2DTO;
import com.sportclub.sportclub.sportclub.entity.MemberDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MemberDetailDao extends CrudDao <MemberDetail> {

     ArrayList<String> getAllSportIds() throws SQLException, ClassNotFoundException;

     Payment2DTO findById(String selectedMembershipId) throws SQLException, ClassNotFoundException;

     ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException;

     public  ArrayList<String> getAllMembershipIds()throws SQLException, ClassNotFoundException;

}
