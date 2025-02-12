package com.sportclub.sportclub.sportclub.Bo.Custom.impl;

import com.sportclub.sportclub.sportclub.Bo.Custom.CoachBo;
import com.sportclub.sportclub.sportclub.dao.DAOFactory;
import com.sportclub.sportclub.sportclub.dao.custom.CoachDao;
import com.sportclub.sportclub.sportclub.dto.CoachDTO;
import com.sportclub.sportclub.sportclub.dto.memberDTO;
import com.sportclub.sportclub.sportclub.entity.Coach;

import java.sql.SQLException;
import java.util.ArrayList;

public class CoachBoImpl implements CoachBo {
    CoachDao coachDao = (CoachDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Coach);

    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        return coachDao.getNext();
    }

    @Override
    public boolean save(Coach DTO) throws SQLException, ClassNotFoundException {
        return coachDao.save(new Coach(DTO.getCoachId(),DTO.getName(),DTO.getPhone(),DTO.getAddress()));
    }


    @Override
    public ArrayList<CoachDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Coach> all = coachDao.getAll();
        ArrayList<CoachDTO> CoachDTOS = new ArrayList<>();
        for (Coach coach : all) {
            CoachDTOS.add(new CoachDTO(coach.getCoachId(),coach.getName(),coach.getPhone(),coach.getAddress()));
        }
        return CoachDTOS;
    }

    @Override
    public boolean update(CoachDTO DTO) throws SQLException, ClassNotFoundException {
        return coachDao.update(new Coach(DTO.getCoachId(),DTO.getName(),DTO.getPhone(),DTO.getAddress()));
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return coachDao.delete(Id);
    }
}
