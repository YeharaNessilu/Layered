package com.sportclub.sportclub.sportclub.controller;

import com.sportclub.sportclub.sportclub.Bo.BOFactory;
import com.sportclub.sportclub.sportclub.Bo.Custom.MemberBO;
import com.sportclub.sportclub.sportclub.dao.DAOFactory;
import com.sportclub.sportclub.sportclub.dao.custom.MemberDao;
import com.sportclub.sportclub.sportclub.db.DBConnection;
import com.sportclub.sportclub.sportclub.dto.memberDTO;
import com.sportclub.sportclub.sportclub.dto.tm.memberTM;
import com.sportclub.sportclub.sportclub.entity.member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.sportclub.sportclub.sportclub.dao.custom.impl.memberdaoImpl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class memberController implements Initializable {
    public AnchorPane ancCustomerPage;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    //public Button btnOrderReport;
    public TextField txtName;
    public TextField txtNic;
    public TextField txtEmail;
    public TextField txtPhone;
    public TextField txtAddress;
    public  Button btnReport;
    public Button btnMail;


    @FXML
    private TableColumn<memberTM, String> colMemberId;

    @FXML
    private TableColumn<memberTM, String> colName;

    @FXML
    private TableColumn<memberTM, String> colNic;

    @FXML
    private TableColumn<memberTM, String> colEmail;

    @FXML
    private TableColumn<memberTM, String> colPhone;

    @FXML
    private TableColumn<memberTM, String> colAddress;

    @FXML
    private TableView<memberTM> tblCustomer;

    @FXML
    private Label lblMemberId;


    MemberBO memberBO = (MemberBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.Member);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));


        try {
            refreshPage();
        }
        catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Member id").show();
        }

    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextMemberId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtName.setText("");
        txtNic.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
    }

//    memberdaoImpl memberModel = new memberdaoImpl();


    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<memberDTO> memberDTOS = memberBO.getAll();

        ObservableList<memberTM> memberTMS = FXCollections.observableArrayList();

        for (memberDTO memberDTO : memberDTOS) {
            memberTM memberTM = new memberTM(
                    memberDTO.getMemberId(),
                    memberDTO.getName(),
                    memberDTO.getNic(),
                    memberDTO.getEmail(),
                    memberDTO.getPhone(),
                    memberDTO.getAddress()
            );
            memberTMS.add(memberTM);
        }

        tblCustomer.setItems(memberTMS);
    }

    public void loadNextMemberId() throws SQLException, ClassNotFoundException {
        String NextMemberId = memberBO.getNext();
        lblMemberId.setText(NextMemberId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = lblMemberId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");
        txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: #7367F0;");


        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String addressPattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);
        boolean isValidAddress = address.matches(addressPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (!isValidNic) {
            txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
//            return;
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidAddress) {
            txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: red;");
        }



        if (isValidName && isValidNic && isValidEmail && isValidPhone && isValidAddress) {
            member member = new member(
                    customerId,
                    name,
                    nic,
                    email,
                    phone,
                    address
            );

            boolean isSaved = memberBO.save(member);
            if (isSaved) {
                refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "Member saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Member...!").show();

            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        memberTM memberTM = tblCustomer.getSelectionModel().getSelectedItem();
        if (memberTM != null) {
            lblMemberId.setText(memberTM.getMemberId());
            txtName.setText(memberTM.getName());
            txtPhone.setText(memberTM.getNic());
            txtAddress.setText(memberTM.getEmail());
            txtNic.setText(memberTM.getPhone());
            txtEmail.setText(memberTM.getAddress());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String memberId = lblMemberId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = memberBO.delete(memberId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Member deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Member...!").show();
            }

        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String customerId = lblMemberId.getText();
        String name = txtName.getText();
        String nic = txtNic.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");
        txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String addressPattern = "^[A-Za-z ]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidNic = nic.matches(nicPattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhone = phone.matches(phonePattern);
        boolean isValidAddress = address.matches(addressPattern);

        if (!isValidName) {
            System.out.println(txtName.getStyle());
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
//            return;
        }

        if (!isValidNic) {
            txtNic.setStyle(txtNic.getStyle() + ";-fx-border-color: red;");
//            return;
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidAddress) {
            txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidNic && isValidEmail && isValidPhone && isValidAddress) {
            member member = new member(
                    customerId,
                    name,
                    nic,
                    email,
                    phone,
                    address
            );

            boolean isUpdate = memberBO.update(member);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Member update...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update Member...!").show();
            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    public void btnReportOnAction(ActionEvent event) {
        memberTM memberTM = tblCustomer.getSelectionModel().getSelectedItem();

        if (memberTM == null) {
            return;
        }

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/Report/Member.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("ID",lblMemberId.getText());
            parameters.put("P_Date", String.valueOf(LocalDate.now()));


            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
//           e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }


    }

    public void btnMailOnAction(ActionEvent event) {

        memberTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                new Alert(Alert.AlertType.WARNING, "Please select customer..!");
                return;
            }

            try {
                // Load the mail dialog from FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/email.fxml"));
                Parent load = loader.load();

                EmailController sendMailController = loader.getController();

                String email = selectedItem.getAddress();
                sendMailController.setCustomerEmail(email);

                Stage stage = new Stage();
                stage.setScene(new Scene(load));
                stage.setTitle("Send email");
                //stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/mail_icon.png"))


                // Set window as modal
                stage.initModality(Modality.APPLICATION_MODAL);

                Window underWindow = btnUpdate.getScene().getWindow();
                stage.initOwner(underWindow);

                stage.showAndWait();
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
                e.printStackTrace();
            }
        }


}
