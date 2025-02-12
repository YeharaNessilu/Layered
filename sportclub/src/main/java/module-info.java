module com.sportclub.sportclub.sportclub {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires javax.mail;
    requires java.desktop;
    requires net.sf.jasperreports.core;
    requires commons.beanutils;


    //opens com.sportclub.sportclub.sportclub.dto to javafx.base;
    opens com.sportclub.sportclub.sportclub.controller to javafx.fxml;
    opens  com.sportclub.sportclub.sportclub.dto.tm to javafx.base;
    exports com.sportclub.sportclub.sportclub;
}