package com.sportclub.sportclub.sportclub.dao.custom.impl;

import com.sportclub.sportclub.sportclub.dao.custom.MemberDetailDao;
import com.sportclub.sportclub.sportclub.dto.Payment2DTO;
import com.sportclub.sportclub.sportclub.dto.Payment3DTO;
import com.sportclub.sportclub.sportclub.entity.MemberDetail;
import com.sportclub.sportclub.sportclub.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberDetailDaoImpl implements MemberDetailDao {

    public String getNext() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select membershipId from memberDetail order by membershipId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("T%03d", newIdIndex); // Return the new member ID in format Cnnn
        }
        return "T001"; // Return the default member ID if no data is found
    }

    public boolean save(MemberDetail MemberDetail) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into  memberDetail(membershipId, sportId, memberId, paymentStatus, joiningDate) VALUES (?, ?, ?, ?, ?)",
                MemberDetail.getMembershipId(),
                MemberDetail.getSportId(),
                MemberDetail.getMemeberId(),
                MemberDetail.getJoiningDate(),
                MemberDetail.getPaymentStatus()

        );
    }

    public ArrayList<MemberDetail> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from memberDetail");

        ArrayList<MemberDetail> MemberDetailDTOS = new ArrayList<>();

        while (rst.next()) {
            MemberDetail MemberDetail = new MemberDetail(
                    rst.getString(1),
                    rst.getString(2),  // sport ID
                    rst.getString(3),  // Name
                    rst.getString(4),  //address
                    rst.getString(5)   //phone

            );
            MemberDetailDTOS.add(MemberDetail);
        }
        return MemberDetailDTOS;
    }

    public boolean update(MemberDetail MemberDetail) throws SQLException, ClassNotFoundException {
        System.out.println("up");
        return SQLUtil.execute(
                "update memberDetail set sportId=?,memberId=?, paymentStatus=?,joiningDate=? where membershipId=?",
                MemberDetail.getSportId(),
                MemberDetail.getMemeberId(),
                MemberDetail.getPaymentStatus(),
                MemberDetail.getJoiningDate(),
                MemberDetail.getMembershipId()


        );
    }

    public boolean delete(String sportId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from memberDetail where membershipId=?", sportId);
    }

    public ArrayList<String> getAllSportIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select sportId from coach");

        ArrayList<String> sportsIds = new ArrayList<>();

        while (rst.next()) {
            sportsIds.add(rst.getString(1));
        }

        return sportsIds;
    }


    public  Payment2DTO findById(String selectedMembershipId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select mem.membershipId,mem.memberId,mem.paymentStatus,s.payment from memberDetail mem join  sport s  on mem.sportId = s.sportId where membershipId=?", selectedMembershipId);

        if (rst.next()) {
            return new Payment2DTO(
                    rst.getString(1),  // coach ID
                    rst.getString(2),  // Name
                    rst.getString(3),
                    rst.getString(4)
            );
        }
        return null;
    }
    public  ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select memberId from member");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }

    public  ArrayList<String> getAllMembershipIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select membershipId from memberDetail");

        ArrayList<String> customerIds = new ArrayList<>();

        while (rst.next()) {
            customerIds.add(rst.getString(1));
        }

        return customerIds;
    }



}
