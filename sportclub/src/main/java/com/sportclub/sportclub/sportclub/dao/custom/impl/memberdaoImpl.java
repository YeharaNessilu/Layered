package com.sportclub.sportclub.sportclub.dao.custom.impl;

import com.sportclub.sportclub.sportclub.dao.custom.MemberDao;
import com.sportclub.sportclub.sportclub.db.DBConnection;
import com.sportclub.sportclub.sportclub.dto.Payment3DTO;
import com.sportclub.sportclub.sportclub.entity.member;
import com.sportclub.sportclub.sportclub.dao.SQLUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class memberdaoImpl  implements MemberDao {



    public String getNext() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select memberId from member order by memberId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("M%03d", newIdIndex); // Return the new member ID in format Cnnn
        }
        return "M001"; // Return the default member ID if no data is found
    }

    public boolean save(member member) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into member(memberId , name , address , nic , email , phone) values (?,?,?,?,?,?)",
                member.getMemberId(),
                member.getName(),
                member.getAddress(),
                member.getNic(),
                member.getEmail(),
                member.getPhone()

        );
    }

    public ArrayList<member> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from member");

        ArrayList<member> memberDTOS = new ArrayList<>();

        while (rst.next()) {
            member member = new member(
                    rst.getString(1),  // member ID
                    rst.getString(2),  // Name
                    rst.getString(6),  //address
                    rst.getString(3),  // NIC
                    rst.getString(4),  // Email
                    rst.getString(5)//phone

            );
            memberDTOS.add(member);
        }
        return memberDTOS;
    }


    @Override
    public boolean update(member member) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update member set name=?, nic=?, email=?, phone=?, address=? where memberId=?",
                member.getName(),
                member.getNic(),
                member.getEmail(),
                member.getPhone(),
                member.getAddress(),
                member.getMemberId()
        );
    }



    public boolean delete(String memberId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from member where memberId=?", memberId);
    }

    public  ArrayList<String> getAllMemberIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select memberId from member");

        ArrayList<String> memberIds = new ArrayList<>();

        while (rst.next()) {
            memberIds.add(rst.getString(1));
        }

        return memberIds;
    }


    public member findById(String selectedMemId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from member where memberId=?", selectedMemId);

        if (rst.next()) {
            return new member(
                    rst.getString(1),  // member ID
                    rst.getString(2),  // Name
                    rst.getString(5),  // NIC
                    rst.getString(6),  // Email
                    rst.getString(3),
                    rst.getString(4)// Phone
            );
        }
        return null;
    }



    public int countCustomers() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(memberId) from member";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            int idd = Integer.parseInt(String.valueOf(resultSet.getInt(1)));
            return idd;
        }
        return Integer.parseInt(null);

    }


}