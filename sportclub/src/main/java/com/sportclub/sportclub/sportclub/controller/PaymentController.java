package com.sportclub.sportclub.sportclub.controller;

import com.sportclub.sportclub.sportclub.Bo.BOFactory;
import com.sportclub.sportclub.sportclub.Bo.Custom.MemberDetailBO;
import com.sportclub.sportclub.sportclub.Bo.Custom.PaymentBO;
import com.sportclub.sportclub.sportclub.dto.*;
import com.sportclub.sportclub.sportclub.dto.tm.Payment2TM;
import com.sportclub.sportclub.sportclub.dto.tm.PaymentTM;
import com.sportclub.sportclub.sportclub.entity.MemberDetail;
import com.sportclub.sportclub.sportclub.entity.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PaymentController implements Initializable {
    public AnchorPane ancPayment;
    public Button btnEnter;
    public Button btnPay;
    public Button btnBalance;
    //public Button btnReport;

    public TextField txtMembershipId;
    public TextField txtMemberId;
    public TextField txtPaymentDate;
    public TextField txtFee;
    public TextField txtAmount;
    public TextField txtStatus;
    //public Label lblMembershipId;


    //    @FXML
//    private TableColumn<Payment2TM, String> colMembershipId;
//
//    @FXML
//    private TableColumn<Payment2TM, String> colMembersId;
//
//    @FXML
//    private TableColumn<Payment2TM, String> colStatus;
//
//    @FXML
//    private TableColumn<Payment2TM, String> colFee;
    /*////////////////////////////////////////////////////////*/
    @FXML
    private TableColumn<PaymentTM, String> colPayId;

    @FXML
    private TableColumn<PaymentTM, String> colMemberId;

    @FXML
    private TableColumn<PaymentTM, String> colPaidAmount;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentDate;

    @FXML
    private TableView<Payment2TM> tblMembership;

    @FXML
    private TableView<PaymentTM> tblpay;

    @FXML
    private Label lblPayId;

    @FXML
    private Label lblBalance;

    @FXML
    private ComboBox<String> cmbMembershipId;

    @FXML
    private ChoiceBox<String> cbStatus;

    @FXML
    private Label Date;


    private String[] status = {"Paid", "NotPaid"};

    PaymentBO paymentBO = (PaymentBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.Payment);

   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = LocalTime.now().format(formatter);

        Date.setText(LocalDate.now().toString());

        cbStatus.getItems().addAll(status);


        colPayId.setCellValueFactory(new PropertyValueFactory<>("payId"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colPaidAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));


        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Sport id").show();
        }

    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadMembershipId();
        loadNextPayId();
        loadTableData();
    }


    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<Payment> PaymentDTOS = paymentBO.getAll();

        ObservableList<PaymentTM> PaymentTMS = FXCollections.observableArrayList();

        for (Payment PaymentDTO : PaymentDTOS) {
            PaymentTM PaymentTM = new PaymentTM(
                    PaymentDTO.getPayId(),
                    PaymentDTO.getMemberId(),
                    PaymentDTO.getAmount(),
                    PaymentDTO.getPaymentDate()
            );
            PaymentTMS.add(PaymentTM);
        }

        tblpay.setItems(PaymentTMS);
    }



    public void loadNextPayId() throws SQLException, ClassNotFoundException {
        String NextPayId = paymentBO.getNext();
        lblPayId.setText(NextPayId);
    }

    private void loadMembershipId() throws SQLException, ClassNotFoundException {
        ArrayList<String> MembershipId = paymentBO.getAllMembershipIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(MembershipId);
        cmbMembershipId.setItems(observableList);
    }

    public void btnPayOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String payId = lblPayId.getText();
        String memberId = txtMemberId.getText();
        String amount = txtFee.getText();
        String paymentDate = Date.getText();

        System.out.println(paymentDate);

        String memebershipId = cmbMembershipId.getValue();
        String status = cbStatus.getValue();


        txtMemberId.setStyle(txtMemberId.getStyle() + ";-fx-border-color: #7367F0;");
        txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: #7367F0;");


        String memberIdPattern = "^[A-Za-z0-9]{3,10}(-[A-Za-z0-9]{1,5})?$";
        String amountPattern = "^(\\d+(\\.\\d{1,2})?)$";

        boolean isValidMemberId = memberId.matches(memberIdPattern);
        boolean isValidAmount = amount.matches(amountPattern);

        if (!isValidMemberId) {
            System.out.println(txtMemberId.getStyle());
            txtMemberId.setStyle(txtMemberId.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid name.............");
        }


        if (!isValidAmount) {
            txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid amount.............");
        }


        if (isValidMemberId && isValidAmount) {
            Payment Payment = new Payment(
                    payId,
                    memberId,
                    amount,
                    paymentDate
            );

            Payment3DTO payment3DTO = new Payment3DTO(
                    memebershipId,
                    status
            );



            boolean isSaved = paymentBO.pay(Payment,payment3DTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Payment saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save Payment...!").show();
            }
        }
    }


    public void btnBalanceOnAction(ActionEvent actionEvent) throws SQLException {


        try {
            String amountText = txtAmount.getText().toString();
            String feeText = txtFee.getText().toString();
            System.out.println(feeText);

            Integer Balance = Integer.parseInt(amountText) - Integer.parseInt(feeText);
            int balance = Balance;
            System.out.println(balance);
            lblBalance.setText(String.valueOf(Balance));
            System.out.println(Balance);


        } catch (NumberFormatException e) {
            System.err.println(e);


        }
    }

    public void cnbOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedMembershipId = cmbMembershipId.getSelectionModel().getSelectedItem();
        Payment2DTO Payment2DTO = paymentBO.findById(selectedMembershipId);


        if (Payment2DTO != null) {

            txtMemberId.setText(Payment2DTO.getMemberId());
            txtFee.setText(Payment2DTO.getFee());
            cbStatus.setValue(Payment2DTO.getStatus());
        }
    }




}
