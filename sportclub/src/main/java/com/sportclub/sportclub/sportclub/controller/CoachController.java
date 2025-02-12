package com.sportclub.sportclub.sportclub.controller;

import com.sportclub.sportclub.sportclub.Bo.BOFactory;
import com.sportclub.sportclub.sportclub.Bo.Custom.CoachBo;
import com.sportclub.sportclub.sportclub.dao.DAOFactory;
import com.sportclub.sportclub.sportclub.dao.custom.CoachDao;
import com.sportclub.sportclub.sportclub.dto.CoachDTO;
import com.sportclub.sportclub.sportclub.dto.tm.CoachTM;

import com.sportclub.sportclub.sportclub.dao.custom.impl.CoachDaoImpl;

import com.sportclub.sportclub.sportclub.entity.Coach;
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

public class CoachController implements Initializable {
    public AnchorPane ancCoachPage;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    //public Button btnOrderReport;
    public TextField txtName;
    public TextField txtPhone;
    public TextField txtAddress;


    @FXML
    private TableColumn<CoachTM, String> colCoachId;

    @FXML
    private TableColumn<CoachTM, String> colName;

    @FXML
    private TableColumn<CoachTM, String> colPhone;

    @FXML
    private TableColumn<CoachTM, String> colAddress;

    @FXML
    private TableView<CoachTM> tblCoach;

    @FXML
    private Label lblCoachId;




    CoachBo coachBo = (CoachBo) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.Coach);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colCoachId.setCellValueFactory(new PropertyValueFactory<>("coachId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));


        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Coach id").show();
        }

    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextCoachId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
    }

//    CoachDaoImpl CoachModel = new CoachDaoImpl();


    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<CoachDTO> CoachDTOS = coachBo.getAll();

        ObservableList<CoachTM> CoachTMS = FXCollections.observableArrayList();

        for (CoachDTO CoachDTO : CoachDTOS) {
            CoachTM CoachTM = new CoachTM(
                    CoachDTO.getCoachId(),
                    CoachDTO.getName(),
                    CoachDTO.getAddress(),
                    CoachDTO.getPhone()

            );
            CoachTMS.add(CoachTM);
        }

        tblCoach.setItems(CoachTMS);
    }

    public void loadNextCoachId() throws SQLException, ClassNotFoundException {
        String NextCoachId = coachBo.getNext();
        lblCoachId.setText(NextCoachId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String coachId = lblCoachId.getText();
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");
        txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: #7367F0;");


        String namePattern = "^[A-Za-z ]+$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String addressPattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidPhone = phone.matches(phonePattern);
        boolean isValidAddress = address.matches(addressPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }


        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidAddress) {
            txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: red;");
        }



        if (isValidName  && isValidPhone && isValidAddress) {
            Coach Coach = new Coach(
                    coachId,
                    name,
                    phone,
                    address
            );

            boolean isSaved = coachBo.save(Coach);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Coach saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Coach...!").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        CoachTM CoachTM = tblCoach.getSelectionModel().getSelectedItem();
        if (CoachTM != null) {
            lblCoachId.setText(CoachTM.getCoachId());
            txtName.setText(CoachTM.getName());
            txtAddress.setText(CoachTM.getAddress());
            txtPhone.setText(CoachTM.getPhone());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String coachId = lblCoachId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = coachBo.delete(coachId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Coach deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to Coach customer...!").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String coachId = lblCoachId.getText();
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");
        txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String addressPattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidPhone = phone.matches(phonePattern);
        boolean isValidAddress = address.matches(addressPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }



        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidAddress) {
            txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName &&  isValidPhone && isValidAddress) {
            CoachDTO CoachDTO = new CoachDTO(
                    coachId,
                    name,
                    phone,
                    address
            );

            boolean isUpdate = coachBo.update(CoachDTO);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Coach update...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update coach...!").show();
            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }
}
