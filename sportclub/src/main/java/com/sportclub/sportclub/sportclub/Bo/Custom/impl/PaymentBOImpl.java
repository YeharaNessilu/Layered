package com.sportclub.sportclub.sportclub.Bo.Custom.impl;

import com.sportclub.sportclub.sportclub.Bo.Custom.PaymentBO;
import com.sportclub.sportclub.sportclub.dao.DAOFactory;
import com.sportclub.sportclub.sportclub.dao.SQLUtil;
import com.sportclub.sportclub.sportclub.dao.custom.MemberDetailDao;
import com.sportclub.sportclub.sportclub.dao.custom.PaymentDao;
import com.sportclub.sportclub.sportclub.db.DBConnection;
import com.sportclub.sportclub.sportclub.dto.MemberDetailDTO;
import com.sportclub.sportclub.sportclub.dto.Payment2DTO;
import com.sportclub.sportclub.sportclub.dto.Payment3DTO;
import com.sportclub.sportclub.sportclub.dto.PaymentDTO;
import com.sportclub.sportclub.sportclub.entity.MemberDetail;
import com.sportclub.sportclub.sportclub.entity.Payment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {


    PaymentDao paymentDao = (PaymentDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Payment);

    MemberDetailDao memberDetailDao = (MemberDetailDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Memberdetail);



    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        return paymentDao.getNext();
    }



//    @Override
//    public boolean pay(PaymentDTO PaymentDTO, Payment3DTO Payment3DTO) throws SQLException {
//        return paymentDao.pay(PaymentDTO,Payment3DTO);
//    }


    @Override
    public int countIncome() throws SQLException {
        return paymentDao.countIncome();
    }

    @Override
    public ArrayList<Payment> getAll() throws SQLException, ClassNotFoundException {
        return paymentDao.getAll();
    }

    @Override
    public Payment2DTO findById(String selectedMembershipId) throws SQLException, ClassNotFoundException {
        return memberDetailDao.findById(selectedMembershipId);
    }

    @Override
    public ArrayList<String> getAllMembershipIds() throws SQLException, ClassNotFoundException {
        return memberDetailDao.getAllMembershipIds();
    }

    public boolean pay(Payment payment, Payment3DTO payment3DTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try{
            boolean isPaymentSaved = paymentDao.pay(payment);

            if (!isPaymentSaved){

                connection.rollback();
                return false;
            }

            boolean updateMemberDetail = paymentDao.updatemem(payment3DTO);


            if (!updateMemberDetail){

                connection.rollback();
                return false;
            }

            connection.commit();
            return true;

        }
        catch (Exception e){
            e.printStackTrace();
            connection.rollback();
            return false;
        }
        finally {
            connection.setAutoCommit(true);
        }

    }


}
