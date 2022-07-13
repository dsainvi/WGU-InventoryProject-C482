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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;

import java.net.URL;
import java.util.ResourceBundle;
/**ModifyPartController*/
public class ModifyPartController implements Initializable {


    private Part selectedPart;

    // Top Radio buttons
    @FXML private RadioButton inHouseRB;
    @FXML private RadioButton outsourcedRB;
    ToggleGroup inhouseOrOutsourced = new ToggleGroup();

    // Part Table View
    @FXML private TableView<Part> tableView;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partStockColumn;
    @FXML private TableColumn<Part, Double> partCostColumn;

    // Middle Text fields
    @FXML private TextField partIDTF;
    @FXML private TextField partNameTF;
    @FXML private TextField partCostTF;
    @FXML private TextField partStockTF;
    @FXML private TextField partMaxStockTF;
    @FXML private TextField partMinStockTF;
    @FXML private TextField machIDorCoNameTF;
    @FXML private Label machIDorCoNameLabel;

    // Lower save, cancel, and back buttons
    @FXML private Button saveBtn;
    @FXML private Button cancelBtn;
    @FXML private Button exitProgramButton;

    Inventory inv = new Inventory();
    /**initialize
     @param url,rb */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    /**initData
     @param inv inv data
     @param part part data*/
    public void initData(Part part, Inventory inv) {
        selectedPart = part;
        this.inv = inv;
        // Set the TextField prompts
        partNameTF.setPromptText("Name");
        partCostTF.setPromptText("Cost");
        partStockTF.setPromptText("Current Stock");
        partMaxStockTF.setPromptText("Maximum Stock Allowed");
        partMinStockTF.setPromptText("Minimum Stock Allowed");

        //this.inv.setAllParts(inv.getAllParts());
        partIDTF.setText(Integer.toString(selectedPart.getId()));
        partNameTF.setText(part.getName());
        partCostTF.setText(Double.toString(part.getPrice()));
        partStockTF.setText(Integer.toString(part.getStock()));
        partMaxStockTF.setText(Integer.toString(part.getMin()));
        partMinStockTF.setText(Integer.toString(part.getMax()));

        tableView.setItems(this.inv.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partName"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partStock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("partCost"));

        // partIDTF set disabled
        partIDTF.setDisable(true);

        // Set in-house / outsourced Radio Button
        inHouseRB.setToggleGroup(inhouseOrOutsourced);
        outsourcedRB.setToggleGroup(inhouseOrOutsourced);
        if (part instanceof InHouse){
            inhouseOrOutsourced.selectToggle(inHouseRB);
            machIDorCoNameTF.setText(Integer.toString(((InHouse) part).getMachineID()));
            machIDorCoNameTF.setPromptText("Machine ID");
            machIDorCoNameLabel.setText("Machine ID");
        }
        if (part instanceof Outsourced) {
            inhouseOrOutsourced.selectToggle(outsourcedRB);
            machIDorCoNameTF.setText(((Outsourced) part).getCompanyName());
            machIDorCoNameTF.setPromptText("Company Name");
            machIDorCoNameLabel.setText("Company Name");
        }
    }
    /**radioBtnChanged */
    public void radioBtnChanged(){
        if(this.inhouseOrOutsourced.getSelectedToggle().equals(this.inHouseRB)) {
            machIDorCoNameTF.setPromptText("Machine ID");
            machIDorCoNameLabel.setText("Machine ID");
        }
        if(this.inhouseOrOutsourced.getSelectedToggle().equals(this.outsourcedRB)) {
            machIDorCoNameTF.setPromptText("Company Name");
            machIDorCoNameLabel.setText("Company Name");
        }
    }
    /**saveBtnPressed
     @param event save btn*/
    public void saveBtnPressed(MouseEvent event) throws Exception {
        int index;
        if (inHouseRB.isSelected()) {
            index = 1;
            inv.updatePart(index, new InHouse(Integer.parseInt(partIDTF.getText()),
                    partNameTF.getText(),
                    Double.parseDouble(partCostTF.getText()),
                    Integer.parseInt(partStockTF.getText()),
                    Integer.parseInt(partMaxStockTF.getText()),
                    Integer.parseInt(partMinStockTF.getText()),
                    Integer.parseInt(machIDorCoNameTF.getText())));
        }
        if (outsourcedRB.isSelected()) {
            index = 2;
            inv.updatePart(index, new Outsourced(Integer.parseInt(partIDTF.getText()),
                    partNameTF.getText(),
                    Double.parseDouble(partCostTF.getText()),
                    Integer.parseInt(partStockTF.getText()),
                    Integer.parseInt(partMaxStockTF.getText()),
                    Integer.parseInt(partMinStockTF.getText()),
                    machIDorCoNameTF.getText()));
        }

        /*if (inHouseRB.isSelected()) {
            index = 1;
            inv.updatePart(index, selectedPart);
        }
        if (outsourcedRB.isSelected()) {
            index = 2;
            inv.updatePart(index, selectedPart);
        }

        // if (in-house radio button is selected) {add an in-house part to the inventory}
        // int index;
        /*
        if (inHouseRB.isSelected()) {
            // index = 1;
            // Look up the part and delete it
            inv.deletePart(inv.lookupPart(Integer.parseInt(partIDTF.getText())));

            // Create a new part to replace the old
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
        if (outsourcedRB.isSelected()) {
            index = 2;
            // Look up the part and delete it
            inv.deletePart(inv.lookupPart(Integer.parseInt(partIDTF.getText())));
//
            // Create a new part to replace the old
            Part outsourced = new Outsourced(Integer.parseInt(partIDTF.getText()),
                    partNameTF.getText(),
                    Double.parseDouble(partCostTF.getText()),
                    Integer.parseInt(partStockTF.getText()),
                    Integer.parseInt(partMaxStockTF.getText()),
                    Integer.parseInt(partMinStockTF.getText()),
                    machIDorCoNameTF.getText());
            // Add to the inventory
            inv.addPart(outsourced);
        }*/
        // After saving, go to main menu
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/mainMenu.fxml"));
        Parent tableViewParent = loader.load();

        Scene mainMenuScene = new Scene(tableViewParent);

        // Pass the information to the main menu
        MainMenuController controller = loader.getController();
        controller.initData(inv);

        // Stage
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainMenuScene);
        window.show();
    }

    /**mainMenuButtonPressed
     @param event btn*/
    public void mainMenuButtonPressed(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/mainMenu.fxml"));
        Parent tableViewParent = loader.load();

        Scene mainMenuScene = new Scene(tableViewParent);

        // Pass the information to the main menu
        MainMenuController controller = loader.getController();
        controller.initData(inv);

        // Stage
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainMenuScene);
        window.show();
    }
    /**setExitProgramButtonPressed
     gets a handle to the Stage and Close program
     */
    public void setExitProgramButtonPressed(){
        // this gets a handle to the Stage
        Stage stage = (Stage) exitProgramButton.getScene().getWindow();
        //Close program
        stage.close();
    }
}
