package com.sportclub.sportclub.sportclub.Bo.Custom;

import com.sportclub.sportclub.sportclub.Bo.SuperBO;
import com.sportclub.sportclub.sportclub.dto.memberDTO;
import com.sportclub.sportclub.sportclub.entity.member;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MemberBO extends SuperBO {
    ArrayList<String> getAllMemberIds() throws SQLException, ClassNotFoundException;

    member findById(String selectedMemId) throws SQLException, ClassNotFoundException;

    int countCustomers() throws SQLException;

    String getNext() throws SQLException, ClassNotFoundException;

    boolean save(member DTO) throws SQLException, ClassNotFoundException;

    ArrayList<memberDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean update(member DTO) throws SQLException, ClassNotFoundException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;
}
