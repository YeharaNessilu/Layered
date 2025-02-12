package com.sportclub.sportclub.sportclub.Bo;

import com.sportclub.sportclub.sportclub.Bo.Custom.impl.*;
import com.sportclub.sportclub.sportclub.dao.CrudDao;
import com.sportclub.sportclub.sportclub.dao.DAOFactory;
import com.sportclub.sportclub.sportclub.dao.custom.impl.CoachDaoImpl;
import com.sportclub.sportclub.sportclub.dao.custom.impl.MemberDetailDaoImpl;
import com.sportclub.sportclub.sportclub.dao.custom.impl.SportDaoImpl;
import com.sportclub.sportclub.sportclub.dao.custom.impl.memberdaoImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }

    public static BOFactory getBOFactory(){
        return (boFactory==null)?boFactory
                =new BOFactory():boFactory;
    }

    public enum BOTypes{
        Member,Coach,Sport,Memberdetail,Payment
    }
    public SuperBO getBO(BOTypes boTypes){

        switch (boTypes){
            case Member:
                return new MemberBOImpl();
            case Coach:
                return new CoachBoImpl();
            case Sport:
                return new SportBOImpl();
            case Memberdetail:
                return new MemberDetailBOImpl();
            case Payment:
                return new PaymentBOImpl();

            default:
                return null;
        }
    }
}
