package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
/**addPartController*/
public class AddPartController implements Initializable {
    //Buttons
    @FXML private Button mainMenuButton;
    @FXML private Button exitProgramButton;
    @FXML private Button partSearchBtn;
    @FXML private Button partClearBtn;
    @FXML private Button partSaveBtn;
    @FXML private Label machIDorCoNameLabel;

    // Text fields (each of these variables end in TF)
    @FXML private TextField partIDTF;
    @FXML private TextField partNameTF;
    @FXML private TextField partCostTF;
    @FXML private TextField partStockTF;
    @FXML private TextField partMaxStockTF;
    @FXML private TextField partMinStockTF;
    @FXML private TextField machIDorCoNameTF;
    // Tableview
    @FXML private TableView<Part> tableView;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partStockColumn;
    @FXML private TableColumn<Part, Double> partCostColumn;

    @FXML private RadioButton inHouseRB;
    @FXML private RadioButton outSourcedRB;

    ToggleGroup iNHOUSEorOUTSOURCED = new ToggleGroup();
    Random rand = new Random();

    // Holds the inventory
    Inventory inv = new Inventory();

    /**initialize
     @param url,rb Set TableView  */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set TableView columns up with corresponding Part object variables
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("Name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("Price"));
        //
        initData(inv);
        // Set TableView prompt text
        // partIDTF.setPromptText("Part ID"); <-- This field is auto-generated and not user editable
        partNameTF.setPromptText("Name");
        partCostTF.setPromptText("Price");
        partStockTF.setPromptText("Current Stock");
        partMaxStockTF.setPromptText("Maximum Stock Allowed");
        partMinStockTF.setPromptText("Minimum Stock Allowed");

        // Set RadioButtons as part of the ToggleGroup
        this.inHouseRB.setToggleGroup(iNHOUSEorOUTSOURCED);
        this.outSourcedRB.setToggleGroup(iNHOUSEorOUTSOURCED);
        machIDorCoNameTF.setPromptText("");
        machIDorCoNameLabel.setText("");

        iNHOUSEorOUTSOURCED.selectToggle(inHouseRB);
        radioBtnChanged();
    }
    /**getNewPartID
     @return Integer.toString(partID)*/
    public String getNewPartID() {
        int partID = rand.nextInt();
        boolean partFound = false;
        // the new partID will be 6 digits or less
        while (partID < 100000 || partID > 999999) {
            partID = rand.nextInt();
        }
        return Integer.toString(partID);
    }
    /**radioBtnChanged */
    public void radioBtnChanged(){
        if(this.iNHOUSEorOUTSOURCED.getSelectedToggle().equals(this.inHouseRB)) {
            machIDorCoNameTF.setPromptText("Machine ID");
            machIDorCoNameLabel.setText("Machine ID");
        }
        if(this.iNHOUSEorOUTSOURCED.getSelectedToggle().equals(this.outSourcedRB)) {
            machIDorCoNameTF.setPromptText("Company Name");
            machIDorCoNameLabel.setText("Company Name");
        }
    }
    /**saveBtnPressed if no data is entered error message exception thrown  empty string
     @param event saves */
    public void saveBtnPressed(ActionEvent event) throws Exception {
        // if (in-house radio button is selected) {add an in-house part to the inventory}
        if (inHouseRB.isSelected()) {
            Part inhouse = new InHouse(Integer.parseInt(partIDTF.getText()),
                    partNameTF.getText(),
                    Double.parseDouble(partCostTF.getText()),
                    Integer.parseInt(partStockTF.getText()),
                    Integer.parseInt(partMaxStockTF.getText()),
                    Integer.parseInt(partMinStockTF.getText()),
                    Integer.parseInt(machIDorCoNameTF.getText()));
            // Add to the inventory
            inv.addPart(inhouse);
        }
        // if (outsourced radio button is selected) {add an outsourced part to the inventory}
        if (outSourcedRB.isSelected()) {
            Part outsourced = new Outsourced(Integer.parseInt(partIDTF.getText()),
                    partNameTF.getText(),
                    Double.parseDouble(partCostTF.getText()),
                    Integer.parseInt(partStockTF.getText()),
                    Integer.parseInt(partMaxStockTF.getText()),
                    Integer.parseInt(partMinStockTF.getText()),
                    machIDorCoNameTF.getText());
            // Add this new part to the inventory
            inv.addPart(outsourced);
        }

        // Take the user to the Main Menu
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/mainMenu.fxml"));
        Parent mainMenuParent = loader.load();

        Scene mainMenuScene = new Scene(mainMenuParent);

        // Pass the inventory
        MainMenuController controller = loader.getController();
        controller.initData(inv);

        // Stage
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainMenuScene);
        window.show();
    }
    /**clearBtnPressed*/
    public void clearBtnPressed() {
        iNHOUSEorOUTSOURCED.selectToggle(inHouseRB);

        partNameTF.setText("");
        partCostTF.setText("");
        partStockTF.setText("");
        partMaxStockTF.setText("");
        partMinStockTF.setText("");
        machIDorCoNameTF.setText("");
    }
    /**initData
     @param inv data inv*/
    public void initData(Inventory inv){
        this.inv.setAllParts(inv.getAllParts());
        this.inv.setAllProducts(inv.getAllProducts());
        tableView.setItems(inv.getAllParts());
        // partIDTF
        partIDTF.setDisable(true);
        partIDTF.setText(getNewPartID());
    }

    /**mainMenuButtonPressed
     @param event Change Scene Methods  */
    public void mainMenuButtonPressed(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/mainMenu.fxml"));
        Parent mainMenuParent = loader.load();

        Scene mainMenuScene = new Scene(mainMenuParent);

        // Pass the inventory
        MainMenuController controller = loader.getController();
        controller.initData(inv);

        // Stage
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainMenuScene);
        window.show();
    }

    /** EXIT Program */
    public void setExitProgramButtonPressed(){
        // This gets a handle to the Stage
        Stage stage = (Stage) exitProgramButton.getScene().getWindow();
        // Close program
        stage.close();
    }
}
