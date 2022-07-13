package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.net.URL;
import java.util.ResourceBundle;
/**mainMenuController*/
public class MainMenuController implements Initializable {

    // items are for the part buttons
    @FXML private Button addPartBtn;
    @FXML private Button mainModifyPartBtn;
    @FXML private Button mainDeletePartBtn;
    @FXML private Button partSearchBtn;

    //  items are for the product buttons
    @FXML private Button mainAddProductBtn;
    @FXML private Button mainModifyProductBtn;
    @FXML private Button mainDeleteProductBtn;
    @FXML private Button productSearchBtn;

    //  items are in the Part Table View
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partStockColumn;
    @FXML private TableColumn<Part, Double> partCostColumn;

    //  items are in the Product Table View
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> productIDCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, Integer> productStockCol;
    @FXML private TableColumn<Product, Double> productCostCol;

    @FXML private TextField partSearchTF;
    @FXML private TextField productSearchTF;

    //    The Exit button
    @FXML private Button exitProgramButton;

    Inventory inv = new Inventory();
    /**initialize
     @param url,rb Set up parts tableview */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("Name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("Price"));

        // Set up products tableview
        productIDCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productStock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<Product, Double>("productPrice"));

        // Initialize the test data
        initData(inv);
        //inv.setAllParts(Main.inv.getAllParts());

        // Fill the tableviews for part and product
        partTableView.setItems(inv.getAllParts());
        productTableView.setItems(inv.getAllProducts());

        // Both part and product's modify and delete buttons are initially disable until a row is selected
        mainModifyPartBtn.setDisable(true);
        mainDeletePartBtn.setDisable(true);

        mainModifyProductBtn.setDisable(true);
        mainDeleteProductBtn.setDisable(true);
    }

    /** userSelectedRow Enables the Modify and Delete buttons when user clicks a row */
    public void userSelectedRow() {
        mainModifyPartBtn.setDisable(false);
        mainDeletePartBtn.setDisable(false);
    }
    /** userSelectedProduct Enables the Modify and Delete buttons when user clicks a row */
    public void userSelectedProduct(){
        mainDeleteProductBtn.setDisable(false);
        mainModifyProductBtn.setDisable(false);
    }

    /** partSearchBtnPressed
       Search methods for Part and Product
    */
    public void partSearchBtnPressed() {
        partTableView.setItems(inv.lookupPart(partSearchTF.getText()));
    }

    public void productSearchBtnPressed(){
        productTableView.setItems(inv.lookupProduct(productSearchTF.getText()));
    }

    // Delete part Button alert
    public void deletePartBtn() {
        if (inv.deletePart(partTableView.getSelectionModel().getSelectedItem()))
            mainDeletePartBtn.setDisable(false);
        else mainDeletePartBtn.setDisable(true);
    };

    // Delete product Button alert
    public void deleteProductBtn() {
        if (inv.deleteProduct(productTableView.getSelectionModel().getSelectedItem()))
            mainDeleteProductBtn.setDisable(false);
        else mainDeleteProductBtn.setDisable(true);
    };

    /** initData TEST DATA
     @param inv data*/
    public void initData(Inventory inv) {
        this.inv.setAllParts(inv.getAllParts());
        this.inv.setAllProducts(inv.getAllProducts());
        partTableView.setItems(this.inv.getAllParts());
        productTableView.setItems(inv.getAllProducts());
        // Check to see if the tables in Main Menu are empty. If so, fill with test data.
        if(inv.getAllParts().isEmpty() && inv.getAllProducts().isEmpty()) loadTestData();
    }

    /** laodTestData Test Data*/
    public void loadTestData(){
        // Parts
        Part part1 = new InHouse(114287, "A01", 29.99, 150, 500, 20, 155121);
        Part part2 = new InHouse(114962, "D04", 49.99, 150, 500, 20, 155566);
        inv.addPart(new Outsourced(523145,"A02", 19.99, 68, 150, 10, "D Lab"));
        inv.addPart(new Outsourced(523698,"A03", 19.99, 68, 150, 10, "D Lab"));
        inv.addPart(new Outsourced(523147,"B01", 9.99, 68, 150, 10, "D Lab"));
        inv.addPart(new Outsourced(523896,"B02", 19.99, 68, 150, 10, "D Lab"));
        inv.addPart(new Outsourced(523456,"C01", 19.99, 68, 150, 10, "D Lab"));
        inv.addPart(new Outsourced(523123,"C02", 19.99, 68, 150, 10, "D Lab"));
        inv.addPart(new Outsourced(523985,"C03", 79.99, 68, 150, 10, "D Lab"));
        inv.addPart(new Outsourced(523159,"D01", 79.99, 68, 150, 10, "D Lab"));
        inv.addPart(new Outsourced(523575,"D02", 79.99, 68, 150, 10, "D Lab"));
        inv.addPart(new Outsourced(523998,"B03", 39.99, 68, 150, 10, "D Lab"));
        inv.addPart(part1);
        inv.addPart(part2);


        // Products
        Product prod1 = new Product(598745, "Product A", 99.99, 6, 25, 1);
        inv.addProduct(prod1);
        prod1.addAssociatedPart(part1);
        prod1.addAssociatedPart(part2);
        Product prod2 = new Product(144785, "Product 1B", 49.99, 19, 25, 1);
        prod2.addAssociatedPart(part1);
        prod2.addAssociatedPart(part2);
        inv.addProduct(prod2);

    }

    /* Change Scene Methods ************************************
     * ***********************************************************/

    /** Add part button was pressed
     @param event btn*/
    public void addPartButton(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/addPart.fxml"));
        Parent tableViewParent = loader.load();

        Scene addPartScene = new Scene(tableViewParent);

        // Pass the information
        AddPartController controller = loader.getController();
        controller.initData(inv);

        // Set the stage and show the window
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }
    /**modifyPartButton
     @param event btn*/
    public void modifyPartButton(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/modifyPart.fxml"));
        Parent tableViewParent = loader.load();

        Scene modifyPartScene = new Scene(tableViewParent);

        // Pass the information
        ModifyPartController controller = loader.getController();
        controller.initData(partTableView.getSelectionModel().getSelectedItem(), inv);

        // Stage
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(modifyPartScene);
        window.show();
    }
    /**addProductButton
     @param event btn*/
    public void addProductButton(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/addProduct.fxml"));
        Parent tableViewParent = loader.load();

        Scene addProductScene = new Scene(tableViewParent);

        AddProductController controller = loader.getController();
        controller.initData(inv);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addProductScene);
        window.show();
    }
    /**modifyProductButton
     @param event btn*/
    public void modifyProductButton(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/modifyProduct.fxml"));
        Parent tableViewParent = loader.load();

        Scene modifyProductScene = new Scene(tableViewParent);

        ModifyProductController controller = loader.getController();
        controller.initData(productTableView.getSelectionModel().getSelectedItem(), inv);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(modifyProductScene);
        window.show();
    }

    /** exitProgramButtonPressed  EXIT Program*/
    public void exitProgramButtonPressed(){
        // This gets a handle to the Stage
        Stage stage = (Stage) exitProgramButton.getScene().getWindow();
        // Close program
        stage.close();
    }
}
