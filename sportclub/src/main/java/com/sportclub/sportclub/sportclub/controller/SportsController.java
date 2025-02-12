package com.sportclub.sportclub.sportclub.controller;


import com.sportclub.sportclub.sportclub.Bo.BOFactory;
import com.sportclub.sportclub.sportclub.Bo.Custom.SportBO;
import com.sportclub.sportclub.sportclub.dao.DAOFactory;
import com.sportclub.sportclub.sportclub.dao.custom.SportDao;
import com.sportclub.sportclub.sportclub.dto.SportDTO;
import com.sportclub.sportclub.sportclub.dto.tm.SportTM;

import com.sportclub.sportclub.sportclub.dao.custom.impl.SportDaoImpl;
import com.sportclub.sportclub.sportclub.dao.custom.impl.CoachDaoImpl;
import com.sportclub.sportclub.sportclub.entity.Sport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SportsController implements Initializable {

    public AnchorPane ancSportPage;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    //public Button btnOrderReport;
    public TextField txtName;

    public TextField txtPayment;


    @FXML
    private TableColumn<SportTM, String> colSportId;

    @FXML
    private TableColumn<SportTM, String> colName;

    @FXML
    private TableColumn<SportTM, String> colPayment;

    @FXML
    private TableColumn<SportTM, String> colCoachId;

    @FXML
    private TableView<SportTM> tblSport;

    @FXML
    private Label lblSportId;

    @FXML
    private ComboBox<String> cmbCoachId;




    SportBO sportBO = (SportBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.Sport);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colSportId.setCellValueFactory(new PropertyValueFactory<>("sportId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        colCoachId.setCellValueFactory(new PropertyValueFactory<>("coachId"));


        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Sport id").show();
        }

    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadCoachId();
        loadNextSportId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtPayment.setText("");
//        txtCoachId.setText("");
    }

//    SportDaoImpl SportModel = new SportDaoImpl();



    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<SportDTO> SportDTOS = sportBO.getAll();

        ObservableList<SportTM> SportTMS = FXCollections.observableArrayList();

        for (SportDTO SportDTO : SportDTOS) {
            SportTM SportTM = new SportTM(
                    SportDTO.getSportId(),
                    SportDTO.getName(),
                    SportDTO.getCoachId(),
                    SportDTO.getPayment()

            );
            SportTMS.add(SportTM);
        }

        tblSport.setItems(SportTMS);
    }

    public void loadNextSportId() throws SQLException, ClassNotFoundException {
        String NextSportId = sportBO.getNext();
        lblSportId.setText(NextSportId);
    }

    private void loadCoachId() throws SQLException, ClassNotFoundException {
        ArrayList<String> coachId = sportBO.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(coachId);
        cmbCoachId.setItems(observableList);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String sportId = lblSportId.getText();
        String name = txtName.getText();
        String payment = txtPayment.getText();
        String coachId = cmbCoachId.getValue();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
//        txtCoachId.setStyle(txtCoachId.getStyle() + ";-fx-border-color: #7367F0;");
        txtPayment.setStyle(txtPayment.getStyle() + ";-fx-border-color: #7367F0;");


        String namePattern = "^[A-Za-z ]+$";
//        String CoachIdPattern = "^[A-Za-z0-9]{3,10}(-[A-Za-z0-9]{1,5})?$";
        String PaymentPattern = "^\\$?\\d+(\\.\\d{2})?$";

        boolean isValidName = name.matches(namePattern);
//        boolean isValidCoachId = coachId.matches(CoachIdPattern);
        boolean isValidPayment = payment.matches(PaymentPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }


        if (!isValidPayment) {
            txtPayment.setStyle(txtPayment.getStyle() + ";-fx-border-color: red;");
        }



        if (isValidName  &&  isValidPayment) {
            Sport Sport = new Sport(
                    sportId,
                    name,
                    coachId,
                    payment
            );

            boolean isSaved = sportBO.save(Sport);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Sport saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Sport...!").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        SportTM SportTM = tblSport.getSelectionModel().getSelectedItem();
        if (SportTM != null) {
            lblSportId.setText(SportTM.getSportId());
            txtName.setText(SportTM.getName());
            cmbCoachId.setValue(SportTM.getCoachId());
            txtPayment.setText(SportTM.getPayment());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String coachId = lblSportId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = sportBO.delete(coachId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Sport deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Sport...!").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String sportId = lblSportId.getText();
        String name = txtName.getText();
        String coachId = cmbCoachId.getValue();
        String payment = txtPayment.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
//        txtCoachId.setStyle(txtCoachId.getStyle() + ";-fx-border-color: #7367F0;");
        txtPayment.setStyle(txtPayment.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
//        String CoachIdPattern = "^[A-Za-z0-9]{3,10}(-[A-Za-z0-9]{1,5})?$";
        String PaymentPattern = "^\\$?\\d+(\\.\\d{2})?$";

        boolean isValidName = name.matches(namePattern);
//        boolean isValidCoachId = coachId.matches(CoachIdPattern);
        boolean isValidPayment = payment.matches(PaymentPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }


        if (!isValidPayment) {
            txtPayment.setStyle(txtPayment.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName &&   isValidPayment) {
            Sport Sport = new Sport(
                    sportId,
                    name,
                    coachId,
                    payment
            );

            boolean isUpdate = sportBO.update(Sport);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "sport update...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update sport...!").show();
            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }
}


