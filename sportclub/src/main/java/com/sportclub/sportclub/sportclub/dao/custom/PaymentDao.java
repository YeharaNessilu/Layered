package com.sportclub.sportclub.sportclub.dao.custom;

import com.sportclub.sportclub.sportclub.dao.CrudDao;
import com.sportclub.sportclub.sportclub.dto.Payment2DTO;
import com.sportclub.sportclub.sportclub.dto.Payment3DTO;
import com.sportclub.sportclub.sportclub.dto.PaymentDTO;
import com.sportclub.sportclub.sportclub.dto.SportDTO;
import com.sportclub.sportclub.sportclub.entity.MemberDetail;
import com.sportclub.sportclub.sportclub.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentDao extends CrudDao<Payment> {

     int countIncome() throws SQLException;

     public boolean updatemem(Payment3DTO payment3DTO) throws SQLException, ClassNotFoundException;

     boolean pay(Payment payment) throws SQLException;
}
