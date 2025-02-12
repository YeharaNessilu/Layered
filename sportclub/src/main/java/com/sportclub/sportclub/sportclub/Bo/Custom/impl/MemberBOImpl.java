package com.sportclub.sportclub.sportclub.Bo.Custom.impl;

import com.sportclub.sportclub.sportclub.Bo.Custom.MemberBO;
import com.sportclub.sportclub.sportclub.dao.DAOFactory;
import com.sportclub.sportclub.sportclub.dao.custom.MemberDao;
import com.sportclub.sportclub.sportclub.dto.CoachDTO;
import com.sportclub.sportclub.sportclub.dto.memberDTO;
import com.sportclub.sportclub.sportclub.entity.Coach;
import com.sportclub.sportclub.sportclub.entity.member;

import java.sql.SQLException;
import java.util.ArrayList;

public class MemberBOImpl implements MemberBO {

    MemberDao memberDao = (MemberDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Member);


    @Override
    public ArrayList<String> getAllMemberIds() throws SQLException, ClassNotFoundException {
        return memberDao.getAllMemberIds();
    }

    @Override
    public member findById(String selectedMemId) throws SQLException, ClassNotFoundException {
        return memberDao.findById(selectedMemId);
    }

    @Override
    public int countCustomers() throws SQLException {
        return memberDao.countCustomers();
    }

    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        return memberDao.getNext();
    }

    @Override
    public boolean save(member DTO) throws SQLException, ClassNotFoundException {
        return memberDao.save(DTO);
    }

    @Override
    public ArrayList<memberDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<member> all = memberDao.getAll();
        ArrayList<memberDTO> memberDTOS = new ArrayList<>();
        for (member member : all) {
            memberDTOS.add(new memberDTO(member.getMemberId(),member.getName(),member.getNic(),member.getEmail(),member.getPhone(),member.getAddress()));
        }
        return memberDTOS;
    }

    @Override
    public boolean update(member DTO) throws SQLException, ClassNotFoundException {
        return memberDao.update(DTO);
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return memberDao.delete(Id);
    }
}
