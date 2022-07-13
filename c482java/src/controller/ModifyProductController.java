package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.*;

import java.net.URL;
import java.util.ResourceBundle;
/**ModifyProductController */
public class ModifyProductController implements Initializable {

    @FXML private HBox searchHbox;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partStockColumn;
    @FXML private TableColumn<Part, Double> partCostColumn;
    @FXML private Button addToAssPartsBtn;
    @FXML private Button removeAssPartBtn;
    @FXML private TextField productNameTF;
    @FXML private TableView<Part> associatedPartTableView;
    @FXML private TableColumn<Part, Integer> associatedPartIDColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, Integer> associatedPartStockColumn;
    @FXML private TableColumn<Part, Double> associatedPartCostColumn;
    @FXML private TextField productIDTF;
    @FXML private TextField productCostTF;
    @FXML private TextField productStockMinTF;
    @FXML private TextField productStockTF;
    @FXML private TextField productStockMaxTF;
    @FXML private TextField partSearchTF;
    @FXML private Button saveBtn;
    @FXML private Button exitProgramBtn;
    @FXML private Button mainMenuBtn;

    Inventory inv = new Inventory();
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**initialize
      Inities the All Parts TableView
     @param url,rb*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialiazes the All Parts TableView
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partName"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partStock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("partCost"));
        partTableView.setItems(inv.getAllParts());

        // Initialize the Associated Parts TableView [ setItems() is in the initData method ]
        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partName"));
        associatedPartStockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partStock"));
        associatedPartCostColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("partCost"));

        // Prompt text for the TextFields
        productNameTF.setPromptText("Product Name");
        productIDTF.setPromptText("Product ID");
        productCostTF.setPromptText("Price");
        productStockTF.setPromptText("Stock");
        productStockMinTF.setPromptText("Min Stock");
        productStockMaxTF.setPromptText("Max Stock");

        // These buttons are initially disabled until a user selects a row
        addToAssPartsBtn.setDisable(true);
        removeAssPartBtn.setDisable(true);

        // Grab a new Product ID and disable the TextField
        productIDTF.setDisable(true);
    }
    /**initData
     @param inv inv data
     @param selectedProduct data*/
    public void initData(Product selectedProduct, Inventory inv){
        this.inv.setAllParts(inv.getAllParts());
        this.inv.setAllProducts(inv.getAllProducts());
        partTableView.setItems(this.inv.getAllParts());
        this.associatedParts = selectedProduct.getAssociatedParts();
        associatedPartTableView.setItems(associatedParts);

        // Set the text for the TextFields
        productNameTF.setText(selectedProduct.getProductName());
        productIDTF.setText(Integer.toString(selectedProduct.getProductID()));
        productCostTF.setText(Double.toString(selectedProduct.getProductPrice()));
        productStockTF.setText(Integer.toString(selectedProduct.getProductStock()));
        productStockMaxTF.setText(Integer.toString(selectedProduct.getProdMax()));
        productStockMinTF.setText(Integer.toString(selectedProduct.getProdMin()));
    }
    /**searchPartBtnPressed*/
    public void searchPartBtnPressed() throws Exception {
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();
        if (partSearchTF.getText().trim().isEmpty()){
            partTableView.setItems(inv.getAllParts());
        }
        if (!partSearchTF.getText().trim().isEmpty()){
            for(Part p : inv.getAllParts()){
                if(p.getName().toLowerCase().contains(partSearchTF.getText().toLowerCase().trim())){
                    searchedParts.add(p);
                }
            }
            partTableView.setItems(searchedParts);
        }
    }
    /**addToAssPartsBtnPressed
     @param event btn*/
    public void addToAssPartsBtnPressed(MouseEvent event) {
        associatedParts.add(partTableView.getSelectionModel().getSelectedItem());
    }

    /**removeAssPartsBtnPressed
     @param event btn*/
    public void removeAssPartBtnPressed(MouseEvent event) {
        associatedParts.remove(associatedPartTableView.getSelectionModel().getSelectedItem());
    }

    /**saveBtnPressed
     @param event btn*/
    public void saveBtnPressed(MouseEvent event) throws Exception {
        double totalCost = 0.0;
        for (Part p : associatedParts) {
            totalCost += p.getPrice();
        }

        if (associatedParts.isEmpty())
            errorWindow(2);

        else if (totalCost > Double.parseDouble(productCostTF.getText()))
            errorWindow(1);

        else {
            // Update the product thru the Inventory class
            inv.updateProduct(Integer.parseInt(productIDTF.getText()), new Product(Integer.parseInt(productIDTF.getText()),
                    productNameTF.getText(),
                    Double.parseDouble(productCostTF.getText()),
                    Integer.parseInt(productStockTF.getText()),
                    Integer.parseInt(productStockMaxTF.getText()),
                    Integer.parseInt(productStockMinTF.getText())));
            // Add the associated parts to the new product's Observable List
            for (Product prod : inv.getAllProducts()) {
                if (prod.getProductID() == Integer.parseInt(productIDTF.getText())) {
                    for (Part p : associatedParts)
                        prod.addAssociatedPart(p);
                }
            }

            // Take the user back to the main menu
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
    }
    /** userSelectedAllPartsRow
     @param event Disable the add buttons until a row is selected */
    public void userSelectedAllPartsRow(MouseEvent event) {
        addToAssPartsBtn.setDisable(false);
    }
    /** userSelectedAssPartsRow
     @param event remove buttons until a row is selected */
    public void userSelectedAssPartsRow(MouseEvent event) {
        removeAssPartBtn.setDisable(false);
    }
    /**errorWindow
     @param errorCode int error */
    public void errorWindow (int errorCode) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (errorCode) {
            case 1:
                // No associated part alert
                alert.setTitle("Error");
                alert.setHeaderText("Price check.");
                alert.setContentText("The product's price cannot be less than the sum of its associated parts' prices.");
                alert.showAndWait();
                break;
            case 2:
                // No associated part alert
                alert.setTitle("Error");
                alert.setHeaderText("No part(s) selected.");
                alert.setContentText("Please add at least 1 part.");
                alert.showAndWait();
                break;
            default:
                // No associated part alert
                alert.setTitle("Error");
                alert.setHeaderText("Sorry!");
                alert.setContentText("Something went horribly wrong.");
                alert.showAndWait();
                break;
        }
    }

    /****Change Scene Methods ************************************
     *************************************************************/

    /**exitProgramBtnPressed
     @param event btn*/
    public void exitProgramBtnPressed(ActionEvent event) {
        // This gets a handle to the Stage
        Stage stage = (Stage) exitProgramBtn.getScene().getWindow();
        // Close program
        stage.close();
    }

    /**mainMenuBtnPressed
     @param event btn*/
    public void mainMenuBtnPressed(ActionEvent event) throws Exception {
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
}
