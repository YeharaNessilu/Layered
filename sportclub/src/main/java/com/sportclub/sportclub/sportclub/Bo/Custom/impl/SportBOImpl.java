package com.sportclub.sportclub.sportclub.Bo.Custom.impl;

import com.sportclub.sportclub.sportclub.Bo.Custom.SportBO;
import com.sportclub.sportclub.sportclub.dao.DAOFactory;
import com.sportclub.sportclub.sportclub.dao.custom.SportDao;
import com.sportclub.sportclub.sportclub.dto.MemberDetailDTO;
import com.sportclub.sportclub.sportclub.dto.SportDTO;
import com.sportclub.sportclub.sportclub.entity.MemberDetail;
import com.sportclub.sportclub.sportclub.entity.Sport;

import java.sql.SQLException;
import java.util.ArrayList;

public class SportBOImpl implements SportBO {
    SportDao sportDao = (SportDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Sport);

    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        return sportDao.getNext();
    }

    @Override
    public boolean save(Sport DTO) throws SQLException, ClassNotFoundException {
        return sportDao.save(DTO);
    }

    @Override
    public ArrayList<SportDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Sport> all = sportDao.getAll();
        ArrayList<SportDTO> sportDTOS = new ArrayList<>();
        for (Sport sport : all) {
            sportDTOS.add(new SportDTO(sport.getSportId(),sport.getName(),sport.getPayment(),sport.getCoachId()));
        }
        return sportDTOS;
    }

    @Override
    public boolean update(Sport DTO) throws SQLException, ClassNotFoundException {
        return sportDao.update(DTO);
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return sportDao.delete(Id);
    }

    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        return sportDao.getAllCustomerIds();
    }
}
