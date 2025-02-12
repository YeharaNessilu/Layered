package com.sportclub.sportclub.sportclub.dao.custom.impl;

import com.sportclub.sportclub.sportclub.dao.custom.SportDao;
import com.sportclub.sportclub.sportclub.dto.Payment3DTO;
import com.sportclub.sportclub.sportclub.dto.SportDTO;
import com.sportclub.sportclub.sportclub.entity.Sport;
import com.sportclub.sportclub.sportclub.dao.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SportDaoImpl implements SportDao {
    public String getNext() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select sportId from sport order by sportId desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("S%03d", newIdIndex); // Return the new member ID in format Cnnn
        }
        return "S001"; // Return the default member ID if no data is found
    }

    public boolean save(Sport sport) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "insert into  sport(sportId, name, payment, coachId) VALUES (?, ?, ?, ?)",
                sport.getSportId(),
                sport.getName(),
                sport.getCoachId(),
                sport.getPayment()
        );
    }

    public ArrayList<Sport> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from sport");

        ArrayList<Sport> SportDTOS = new ArrayList<>();

        while (rst.next()) {
            Sport Sport = new Sport(
                    rst.getString(1),  // sport ID
                    rst.getString(2),  // Name
                    rst.getString(4),  //address
                    rst.getString(3)   //phone

            );
            SportDTOS.add(Sport);
        }
        return SportDTOS;
    }

    public boolean update(Sport Sport) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute(
                "update sport set name=?,coachId=?, payment=? where sportId=?",
                Sport.getName(),
                Sport.getPayment(),
                Sport.getCoachId(),
                Sport.getSportId()

        );
    }

    public boolean delete(String sportId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from sport where sportId=?", sportId);
    }

    public static ArrayList<String> getAllSportIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select sportId from sport");

        ArrayList<String> sportsIds = new ArrayList<>();

        while (rst.next()) {
            sportsIds.add(rst.getString(1));
        }

        return sportsIds;
    }


    public SportDTO findById(String selectedSportId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from sport where coachId=?", selectedSportId);

        if (rst.next()) {
            return new SportDTO(
                    rst.getString(1),  // coach ID
                    rst.getString(2),  // Name
                    rst.getString(3),
                    rst.getString(4)// Phone
            );
        }
        return null;
    }

    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select coachId from coach");

        ArrayList<String> sportsIds = new ArrayList<>();

        while (rst.next()) {
            sportsIds.add(rst.getString(1));
        }

        return sportsIds;
    }
}
