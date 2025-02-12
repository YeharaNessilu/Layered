package com.sportclub.sportclub.sportclub.dao.custom.impl;

import com.sportclub.sportclub.sportclub.dao.custom.CoachDao;
import com.sportclub.sportclub.sportclub.db.DBConnection;
import com.sportclub.sportclub.sportclub.dto.Payment3DTO;
import com.sportclub.sportclub.sportclub.entity.Coach;
import com.sportclub.sportclub.sportclub.dao.SQLUtil;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoachDaoImpl  implements CoachDao {

    public String getNext() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select coachId from coach order by coachId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("C%03d", newIdIndex); // Return the new member ID in format Cnnn
        }
        return "C001"; // Return the default member ID if no data is found
    }

    public boolean save(Coach Coach) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into coach(coachId , name , address , phone) values (?,?,?,?)",
                Coach.getCoachId(),
                Coach.getName(),
                Coach.getAddress(),
                Coach.getPhone()

        );
    }

    @SneakyThrows
    public ArrayList<Coach> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from coach");

        ArrayList<Coach> CoachDTOS = new ArrayList<>();

        while (rst.next()) {
//            Coach Coach = new Coach(
//                    rst.getString(1),  // coach ID
//                    rst.getString(2),  // Name
//                    rst.getString(3),  //address
//                    rst.getString(4)   //phone
//
//            );
            CoachDTOS.add(new Coach(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4) ));
        }
        return CoachDTOS;
    }

    @Override
    public boolean update(Coach DTO) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update coach set name=?,phone=?, address=? where coachId=?",
                DTO.getName(),
                DTO.getPhone(),
                DTO.getAddress(),
                DTO.getCoachId()
        );
    }



    @SneakyThrows
    public boolean delete(String coachId) throws SQLException {
        return SQLUtil.execute("delete from coach where coachId=?", coachId);
    }

    @SneakyThrows
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("select coachId from coach");

        ArrayList<String> coachIds = new ArrayList<>();

        while (rst.next()) {
            coachIds.add(rst.getString(1));
        }

        return coachIds;
    }




    @SneakyThrows
    public Coach findById(String selectedcochId) throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from coach where coachId=?", selectedcochId);

        if (rst.next()) {
            return new Coach(
                    rst.getString(1),  // coach ID
                    rst.getString(2),  // Name
                    rst.getString(3),
                    rst.getString(4)// Phone
            );
        }
        return null;
    }

    public int countCoach() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(coachId) from coach";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            int idd = Integer.parseInt(String.valueOf(resultSet.getInt(1)));
            return idd;
        }
        return Integer.parseInt(null);

    }


}
