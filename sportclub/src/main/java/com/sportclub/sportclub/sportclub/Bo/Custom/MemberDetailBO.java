package com.sportclub.sportclub.sportclub.Bo.Custom;

import com.sportclub.sportclub.sportclub.Bo.SuperBO;
import com.sportclub.sportclub.sportclub.dto.MemberDetailDTO;
import com.sportclub.sportclub.sportclub.dto.Payment2DTO;
import com.sportclub.sportclub.sportclub.entity.MemberDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MemberDetailBO extends SuperBO {

    String getNext() throws SQLException, ClassNotFoundException;

    boolean save(MemberDetail DTO) throws SQLException, ClassNotFoundException;

    ArrayList<MemberDetailDTO> getAll() throws SQLException, ClassNotFoundException;

    boolean update(MemberDetail DTO) throws SQLException, ClassNotFoundException;

    boolean delete(String Id) throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllCustomerIds();


}
