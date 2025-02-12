package com.sportclub.sportclub.sportclub.dao.custom.impl;

import com.sportclub.sportclub.sportclub.dao.custom.PaymentDao;
import com.sportclub.sportclub.sportclub.db.DBConnection;
import com.sportclub.sportclub.sportclub.dto.*;
import com.sportclub.sportclub.sportclub.dao.SQLUtil;
import com.sportclub.sportclub.sportclub.entity.MemberDetail;
import com.sportclub.sportclub.sportclub.entity.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDaoImpl implements PaymentDao {

    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select payId from payment order by payId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("P%03d", newIdIndex); // Return the new member ID in format Cnnn
        }
        return "P001"; // Return the default member ID if no data is found
    }

    @Override
    public boolean save(Payment DTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into  payment(payId, memberId, paymentAmount, paymentDate) VALUES (?, ?, ?, ?)",
                DTO.getPayId(),
                DTO.getMemberId(),
                DTO.getPaymentDate(),
                DTO.getAmount()
        );
    }




    public ArrayList<Payment> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from payment");

        ArrayList<Payment> PaymentDTOS = new ArrayList<>();

        while (rst.next()) {
            Payment Payment = new Payment(
                    rst.getString(1),  // sport ID
                    rst.getString(2),  // Name
                    rst.getString(3),  //address
                    rst.getString(4)   //phone

            );
            PaymentDTOS.add(Payment);
        }
        return PaymentDTOS;
    }

    @Override
    public boolean update(Payment DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updatemem(Payment3DTO payment3DTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("update memberDetail set paymentStatus=? where membershipId=?",
                payment3DTO.getStatus(),
                payment3DTO.getMemebershipId()


        );
    }



    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return false;
    }



    public int countIncome() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT SUM(paymentAmount)  FROM payment";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            int idd = Integer.parseInt(String.valueOf(resultSet.getInt(1)));
            return idd;
        }
        return Integer.parseInt(null);

    }

    @Override
    public boolean pay(Payment payment) throws SQLException {
        System.out.println("helo");
        return SQLUtil.execute(
                "insert into  payment(payId, memberId, paymentAmount, paymentDate) VALUES (?, ?, ?, ?)",
                payment.getPayId(),
                payment.getMemberId(),
                payment.getPaymentDate(),
                payment.getAmount()
        );

    }


}
