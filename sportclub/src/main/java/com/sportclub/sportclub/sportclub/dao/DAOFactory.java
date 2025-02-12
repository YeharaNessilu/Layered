package com.sportclub.sportclub.sportclub.dao;

import com.sportclub.sportclub.sportclub.Bo.Custom.impl.MemberBOImpl;
import com.sportclub.sportclub.sportclub.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){
    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)?daoFactory
                =new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        Member,Coach,Sport,Memberdetail,Payment
    }
    public SuperDAO getDAO(DAOTypes daoTypes){

        switch (daoTypes){
            case Member:
                return new memberdaoImpl();
            case Coach:
                return new CoachDaoImpl();
            case Sport:
                return new SportDaoImpl();
            case Memberdetail:
                return new MemberDetailDaoImpl();
            case Payment:
                return new PaymentDaoImpl();

            default:
                return null;
        }
    }
}
