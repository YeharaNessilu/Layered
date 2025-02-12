package com.sportclub.sportclub.sportclub.Bo.Custom.impl;

import com.sportclub.sportclub.sportclub.Bo.Custom.MemberDetailBO;
import com.sportclub.sportclub.sportclub.dao.DAOFactory;
import com.sportclub.sportclub.sportclub.dao.custom.MemberDetailDao;
import com.sportclub.sportclub.sportclub.dto.MemberDetailDTO;
import com.sportclub.sportclub.sportclub.dto.Payment2DTO;
import com.sportclub.sportclub.sportclub.dto.memberDTO;
import com.sportclub.sportclub.sportclub.entity.MemberDetail;
import com.sportclub.sportclub.sportclub.entity.member;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.util.ArrayList;

public class MemberDetailBOImpl implements MemberDetailBO {
    MemberDetailDao memberDetailDao = (MemberDetailDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Memberdetail);


    @Override
    public String getNext() throws SQLException, ClassNotFoundException {
        return memberDetailDao.getNext();
    }

    @Override
    public boolean save(MemberDetail DTO) throws SQLException, ClassNotFoundException {
        return memberDetailDao.save(DTO);
    }

    @Override
    public ArrayList<MemberDetailDTO> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<MemberDetail> all = memberDetailDao.getAll();
        ArrayList<MemberDetailDTO> MemberDetailDTOS = new ArrayList<>();
        for (MemberDetail memberDetail : all) {
            MemberDetailDTOS.add(new MemberDetailDTO(memberDetail.getMembershipId(),memberDetail.getSportId(),memberDetail.getMemeberId(),memberDetail.getPaymentStatus(),memberDetail.getJoiningDate()));
        }
        return MemberDetailDTOS;
    }

    @Override
    public boolean update(MemberDetail DTO) throws SQLException, ClassNotFoundException {
        return memberDetailDao.update(DTO);
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return memberDetailDao.delete(Id);
    }

    @SneakyThrows
    @Override
    public ArrayList<String> getAllCustomerIds() {
        return memberDetailDao.getAllCustomerIds();
    }


}
