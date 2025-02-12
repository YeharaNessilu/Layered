package com.sportclub.sportclub.sportclub.controller;

import com.sportclub.sportclub.sportclub.Bo.BOFactory;
import com.sportclub.sportclub.sportclub.Bo.Custom.MemberBO;
import com.sportclub.sportclub.sportclub.Bo.Custom.MemberDetailBO;
import com.sportclub.sportclub.sportclub.dao.DAOFactory;
import com.sportclub.sportclub.sportclub.dao.custom.MemberDetailDao;
import com.sportclub.sportclub.sportclub.db.DBConnection;
import com.sportclub.sportclub.sportclub.dto.MemberDetailDTO;

import com.sportclub.sportclub.sportclub.dto.tm.MemberDetailTM;

import com.sportclub.sportclub.sportclub.dao.custom.impl.SportDaoImpl;
import com.sportclub.sportclub.sportclub.dao.custom.impl.memberdaoImpl;
import com.sportclub.sportclub.sportclub.dao.custom.impl.MemberDetailDaoImpl;
import com.sportclub.sportclub.sportclub.entity.MemberDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class MemberDetailController implements Initializable {
    public AnchorPane ancMemberDetailPage;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public Button sportIdSerch;
    public Button btnReport;
   // public Button txtMemberShipId;


    //public Button btnOrderReport;

    public TextField txtPaymentStatus;
    public TextField txtjoiningDate;
    //public TextField txtMemberShipId;
//
  //DatePicker joiningDate = new DatePicker();


    @FXML
    private TableColumn<MemberDetailTM, String> colSportId;

    @FXML
    private TableColumn<MemberDetailTM, String> colMemberId;

    @FXML
    private TableColumn<MemberDetailTM, String> colPaymentStatus;

    @FXML
    private TableColumn<MemberDetailTM, String> colJoiningDate;

    @FXML
    private TableColumn<MemberDetailTM, String> colMembershipId;

    @FXML
    private TableView<MemberDetailTM> tblMemberDetail;

    @FXML
    private Label lblSportName;

    @FXML
    private Label lblMemberName;

    @FXML
    private Label lblMembershipId;

    @FXML
    private ChoiceBox<String> cbStatus;

    @FXML
    private ComboBox<String> cmbSportId;

    @FXML
    private ComboBox<String> cmbMemberId;

    private String status = "NotPaid";

//    @FXML
//    private DatePicker joiningDate;


    MemberDetailBO memberDetailBO = (MemberDetailBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.Memberdetail);



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbStatus.getItems().addAll(status);

        colMembershipId.setCellValueFactory(new PropertyValueFactory<>("membershipId"));
        colSportId.setCellValueFactory(new PropertyValueFactory<>("sportId"));
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colPaymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        colJoiningDate.setCellValueFactory(new PropertyValueFactory<>("joiningDate"));


        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load Sport id").show();
        }

    }



    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadMemberId();
        loadSportId();
        loadNextMembershipId();
        loadTableData();

        btnSave.setDisable(false);

        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);


        txtPaymentStatus.setText("");

        txtjoiningDate.setText("");

    }

//    MemberDetailDaoImpl MemberDetailModel = new MemberDetailDaoImpl();



    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<MemberDetailDTO> MemberDetailDTOS = memberDetailBO.getAll();

        ObservableList<MemberDetailTM> MemberDetailTMS = FXCollections.observableArrayList();

        for (MemberDetailDTO MemberDetailDTO : MemberDetailDTOS) {
            MemberDetailTM MemberDetailTM = new MemberDetailTM(
                    MemberDetailDTO.getMembershipId(),
                    MemberDetailDTO.getSportId(),
                    MemberDetailDTO.getMemeberId(),
                    MemberDetailDTO.getPaymentStatus(),
                    MemberDetailDTO.getJoiningDate()

            );
            MemberDetailTMS.add(MemberDetailTM);
        }

        tblMemberDetail.setItems(MemberDetailTMS);
    }


    public void loadNextMembershipId() throws SQLException, ClassNotFoundException {
        String NextMembershipId = memberDetailBO.getNext();
        lblMembershipId.setText(NextMembershipId);
    }

    private void loadSportId() throws SQLException, ClassNotFoundException {
        ArrayList<String> coachId = SportDaoImpl.getAllSportIds() ;
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(coachId);
        cmbSportId.setItems(observableList);
    }

    private void loadMemberId() throws SQLException {
        ArrayList<String> coachId = memberDetailBO.getAllCustomerIds() ;
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(coachId);
        cmbMemberId.setItems(observableList);
    }



    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String membershipId = lblMembershipId.getText();
        String sportId = cmbSportId.getValue();
        String memeberId = cmbMemberId.getValue();
        String paymentStatus = cbStatus.getValue();
        String joiningDate = txtjoiningDate.getText();

        cmbSportId.setStyle(cmbSportId.getStyle() + ";-fx-border-color: #7367F0;");
        cmbMemberId.setStyle(cmbMemberId.getStyle() + ";-fx-border-color: #7367F0;");
        txtPaymentStatus.setStyle(txtPaymentStatus.getStyle() + ";-fx-border-color: #7367F0;");
        txtjoiningDate.setStyle(txtjoiningDate.getStyle() + ";-fx-border-color: #7367F0;");

        String sportIdPattern = "^[A-Za-z0-9]{3,10}(-[A-Za-z0-9]{1,5})?$";
        String memeberIdPattern = "^[A-Za-z0-9]{3,10}(-[A-Za-z0-9]{1,5})?$";
        String paymentStatusPattern = "^[A-Za-z ]+$";
        String joiningDatePattern = "^\\d{4}-\\d{2}-\\d{2}$";



        boolean isValidsportId = sportId.matches(sportIdPattern);
        boolean isValidmemberId = memeberId.matches(memeberIdPattern);
        boolean isValidPaymentStatus = paymentStatus.matches(paymentStatusPattern);
        boolean isValidjoiningDate = joiningDate.matches(joiningDatePattern);

        if (!isValidsportId) {
            System.out.println(cmbSportId.getStyle());
            cmbSportId.setStyle(cmbSportId.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid sportId.............");
//            return;
        }


        if (!isValidmemberId) {
            cmbMemberId.setStyle(cmbMemberId.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPaymentStatus) {
            txtPaymentStatus.setStyle(txtPaymentStatus.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidjoiningDate) {
            txtjoiningDate.setStyle(txtjoiningDate.getStyle() + ";-fx-border-color: red;");
        }



        if (isValidsportId && isValidmemberId && isValidjoiningDate && isValidPaymentStatus) {
            MemberDetail MemberDetail = new MemberDetail(
                    membershipId,
                    sportId,
                    memeberId,
                    joiningDate,
                    paymentStatus
            );

            boolean isSaved = memberDetailBO.save(MemberDetail);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "membership saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save membership...!").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        MemberDetailTM MemberDetailTM = tblMemberDetail.getSelectionModel().getSelectedItem();
        if (MemberDetailTM != null) {
            lblMembershipId.setText(MemberDetailTM.getMembershipId());
            cmbSportId.setValue(MemberDetailTM.getSportId());
            cmbMemberId.setValue(MemberDetailTM.getMemberId());
            txtjoiningDate.setText(MemberDetailTM.getJoiningDate());
            cbStatus.setValue(MemberDetailTM.getPaymentStatus());


            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    public void reportOnAction(ActionEvent event) {
        MemberDetailTM MemberDetailTM = tblMemberDetail.getSelectionModel().getSelectedItem();

        if (MemberDetailTM == null) {
            return;
        }

        try {
            InputStream jasperReport1 = getClass().getResourceAsStream("/Report/memberDetail.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperReport1);
            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("ID",lblMembershipId.getText());
            parameters.put("pDate", LocalDate.now().toString());


            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String membershipId = lblMembershipId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = memberDetailBO.delete(membershipId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "membership deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete membership...!").show();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String membershipId = lblMembershipId.getText();
        String sportId = cmbSportId.getValue();
        String memeberId = cmbMemberId.getValue();
        String paymentStatus = cbStatus.getValue();
        String joiningDate = txtjoiningDate.getText();

        cmbSportId.setStyle(cmbSportId.getStyle() + ";-fx-border-color: #7367F0;");
        cmbMemberId.setStyle(cmbMemberId.getStyle() + ";-fx-border-color: #7367F0;");
        txtPaymentStatus.setStyle(txtPaymentStatus.getStyle() + ";-fx-border-color: #7367F0;");
        txtjoiningDate.setStyle(txtjoiningDate.getStyle() + ";-fx-border-color: #7367F0;");

        String sportIdPattern = "^[A-Za-z0-9]{3,10}(-[A-Za-z0-9]{1,5})?$";
        String memeberIdPattern = "^[A-Za-z0-9]{3,10}(-[A-Za-z0-9]{1,5})?$";
        String paymentStatusPattern = "^[A-Za-z ]+$";
        String joiningDatePattern = "^\\d{4}-\\d{2}-\\d{2}$";



        boolean isValidsportId = sportId.matches(sportIdPattern);
        boolean isValidmemberId = memeberId.matches(memeberIdPattern);
        boolean isValidPaymentStatus = paymentStatus.matches(paymentStatusPattern);
        boolean isValidjoiningDate = joiningDate.matches(joiningDatePattern);

        if (!isValidsportId) {
            System.out.println(cmbSportId.getStyle());
            cmbSportId.setStyle(cmbSportId.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid sportId.............");
//            return;
        }


        if (!isValidmemberId) {
            cmbMemberId.setStyle(cmbMemberId.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidPaymentStatus) {
            txtPaymentStatus.setStyle(txtPaymentStatus.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidjoiningDate) {
            txtjoiningDate.setStyle(txtjoiningDate.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidsportId && isValidmemberId && isValidPaymentStatus && isValidjoiningDate ) {
            MemberDetail MemberDetail = new MemberDetail(
                    membershipId,
                    sportId,
                    memeberId,
                    paymentStatus,
                    joiningDate

            );

        boolean isUpdate = memberDetailBO.update(MemberDetail);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "membership update...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update membership...!").show();
            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }
}
