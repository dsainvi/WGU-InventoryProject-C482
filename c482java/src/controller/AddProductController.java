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
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
/**AddProductController error message here*/
public class AddProductController implements Initializable {

    // This will belong to the new product
    @FXML private TableView<Part> associatedPartTableView;
    @FXML private TableColumn<Part, Integer> associatedPartIDColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, Integer> associatedPartStockColumn;
    @FXML private TableColumn<Part, Double> associatedPartCostColumn;

    // These parts come from the inventory
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partStockColumn;
    @FXML private TableColumn<Part, Double> partCostColumn;

    // Buttons
    @FXML private Button addToAssPartsBtn;
    @FXML private Button removeAssPartBtn;
    @FXML private Button mainMenuBtn;
    @FXML private Button exitProgramBtn;
    @FXML private Button saveBtn;
    @FXML private Button searchPartBtn;

    // TextFields
    @FXML private TextField productNameTF;
    @FXML private TextField productIDTF;
    @FXML private TextField productCostTF;
    @FXML private TextField productStockMinTF;
    @FXML private TextField productStockTF;
    @FXML private TextField productStockMaxTF;
    @FXML private TextField partSearchTF;

    // The inventory to draw from and an ObservableList to hold data until the new Product is saved/created
    Inventory inv = new Inventory();
    Product addProduct = new Product(0, "",0.00,0,0, 0);
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    Random rand = new Random();
    /**initialize  Initializes the All Parts TableView
     @param url,rb Set TableView  */
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
        associatedPartTableView.setItems(addProduct.getAssociatedParts());

        // Prompt text
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
        productIDTF.setText(getNewProductID());
    }
    /**initData
     @param inv  sets testsData to the screen */
    public void initData(Inventory inv){
        this.inv.setAllParts(inv.getAllParts());
        this.inv.setAllProducts(inv.getAllProducts());
        partTableView.setItems(this.inv.getAllParts());
    }
    /**getNewProductID
     @return int string partID*/
    public String getNewProductID() {
        int partID = rand.nextInt();
        boolean partFound = false;
        // the new partID will be 6 digits or less
        while (partID < 100000 || partID > 999999) {
            partID = rand.nextInt();
        }
        return Integer.toString(partID);
    }

    /** Search feature for parts
     gets all parts if search is empty
     case sensitive */
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

    /**addToAssPartsBtnPressed Add Part to Product*/
    public void addToAssPartsBtnPressed() throws Exception {
        addProduct.addAssociatedPart(partTableView.getSelectionModel().getSelectedItem());
    }

    /**Remove Part from Product  */
    public void removeAssPartBtnPressed() throws Exception {
        addProduct.deleteAssociatedPart(associatedPartTableView.getSelectionModel().getSelectedItem());
    }

    /** Save Button error window thrown if associatedParts is empty
     or if total cost is greater then price
     or if any field text are missing or wrong input type
     @param event  */
    public void saveBtnPressed(ActionEvent event) throws Exception {
        double totalCost = 0.0;
        for (Part p : addProduct.getAssociatedParts()) {
            totalCost += p.getPrice();
        }

        if (addProduct.getAssociatedParts().isEmpty())
            errorWindow(2);

        else if (totalCost > Double.parseDouble(productCostTF.getText()))
            errorWindow(1);

        else {
            addProduct.setProductName(productNameTF.getText());
            addProduct.setProductID(Integer.parseInt(productIDTF.getText()));
            addProduct.setProductPrice(Double.parseDouble(productCostTF.getText()));
            addProduct.setProductStock(Integer.parseInt(productStockTF.getText()));
            addProduct.setProdMax(Integer.parseInt(productStockMaxTF.getText()));
            addProduct.setProdMin(Integer.parseInt(productStockMinTF.getText()));

            // Add the associated parts to the product
            for (Part p : associatedParts)
                addProduct.addAssociatedPart(p);

            // Add the new product to the inventory
            inv.addProduct(addProduct);

            // Take the user back to the Main Menu
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

    //Clear form*/
    public void clearBtnPressed() throws Exception {
        productNameTF.setText("");
        productIDTF.setText("");
        productCostTF.setText("");
        productStockTF.setText("");
        productStockMaxTF.setText("");
        productStockMinTF.setText("");
        for(Part p : addProduct.getAssociatedParts()) {
            inv.deletePart(p);
        }
    }

    /** userSelectedAllPartsRow Disable the add and remove buttons until a row is selected */
    public void userSelectedAllPartsRow() {
        addToAssPartsBtn.setDisable(false);
    }
    /** userSelectedAssPartsRow Disable the add and remove buttons until a row is selected */
    public void userSelectedAssPartsRow() {
        removeAssPartBtn.setDisable(false);
    }
    /**errorWindow No associated part alert
     @param errorCode  2 cases and a default error message  */
    public void errorWindow(int errorCode) {
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

    /** Change Scene Methods
     @param event  btn*/
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

    /** EXIT Program */
    public void exitProgramBtnPressed(){
        // This gets a handle to the Stage
        Stage stage = (Stage) exitProgramBtn.getScene().getWindow();
        // Close program
        stage.close();
    }
}
