package com.sportclub.sportclub.sportclub.Bo.Custom;

import com.sportclub.sportclub.sportclub.Bo.SuperBO;
import com.sportclub.sportclub.sportclub.dto.Payment2DTO;
import com.sportclub.sportclub.sportclub.dto.Payment3DTO;
import com.sportclub.sportclub.sportclub.entity.MemberDetail;
import com.sportclub.sportclub.sportclub.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {

    public String getNext() throws SQLException, ClassNotFoundException;

    int countIncome() throws SQLException;

    public ArrayList<Payment> getAll() throws SQLException, ClassNotFoundException;

    Payment2DTO findById(String selectedMembershipId) throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllMembershipIds() throws SQLException, ClassNotFoundException;

    boolean pay(Payment payment, Payment3DTO payment3DTO) throws SQLException;
}
