
package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**Inventory*/
public class Inventory {
    // Variables
    private ObservableList<Part> allParts = FXCollections.observableArrayList();

    private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Methods
     add part
     @param part adds*/
    public void addPart(Part part) {
        this.allParts.add(part);

    }
    /** Methods
     add product
     @param product adds*/
    public void addProduct(Product product){
        this.allProducts.add(product);
    }
    /** Methods
     set all parts
     @param allParts setter*/
    public void setAllParts(ObservableList<Part> allParts) {
        this.allParts = allParts;
    }
    /** Methods
     set all products
     @param products setter*/
    public void setAllProducts(ObservableList<Product> products) {
        this.allProducts = products;
    }
    /** Methods
     look up part
     @param partID int
     @return p*/
    public Part lookupPart(int partID) {
        for (Part p : allParts) {
            if (p.getId() == partID) {
                return p;
            }
        }
        return null;
    }
    /** Methods
     look up Product
     @param productID int
     @return p*/
    public Product lookupProduct(int productID){
        for (Product p : allProducts) {
            if (p.getProductID() == productID){
                return p;
            }
        }
        return null;
    }
    /** Methods
     look up Part
     @param partName string
     @return partsShortList */
    public ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsShortList = FXCollections.observableArrayList();
        for (Part p : allParts) {
            if (p.getName().toLowerCase().contains(partName.toLowerCase().trim())) {
                partsShortList.add(p);
            }
        }
        return partsShortList;
    }
    /** Methods
     look up Part
     @param productName string search
     @return productsShortList */
    public ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productShortList = FXCollections.observableArrayList();
        for (Product p : allProducts) {
            if (p.getProductName().toLowerCase().contains(productName.toLowerCase().trim())) {
                productShortList.add(p);
            }
        }
        return productShortList;
    }
    /** Methods
     update parts
     @param index update int
     @param selectedPart  select */
    public void updatePart(int index, Part selectedPart){
        if (index == 1) {
            deletePart(lookupPart(selectedPart.getId()));
            InHouse newPart = new InHouse(selectedPart.getId(),
                    selectedPart.getName(),
                    selectedPart.getPrice(),
                    selectedPart.getStock(),
                    selectedPart.getMin(),
                    selectedPart.getMax(),
                    ((InHouse)selectedPart).getMachineID());
            addPart(newPart);
        }
        if (index == 2) {
            deletePart(lookupPart(selectedPart.getId()));
            Outsourced newPart = new Outsourced(selectedPart.getId(),
                    selectedPart.getName(),
                    selectedPart.getPrice(),
                    selectedPart.getStock(),
                    selectedPart.getMin(),
                    selectedPart.getMax(),
                    ((Outsourced)selectedPart).getCompanyName());
            addPart(newPart);
        }
    }
    /** Methods
     update Product
     @param index int
     @param selectedProduct  select products*/
    public void updateProduct(int index, Product selectedProduct) {
        for (Product p : allProducts) {
            if (index == p.getProductID()) {
                deleteProduct(lookupProduct(p.getProductID()));
                this.addProduct(selectedProduct);
                break;
            }
        }
    }
    /** Methods
     delete parts
     @param selectedPart  selects parts
     @return true*/
    public boolean deletePart(Part selectedPart) {
        for (Part p : allParts) {
            if (p.getId() == selectedPart.getId()){
                allParts.remove(p);
                break;
            }
        }
        return true;
    }
    /** Methods
     delete Product
     @param selectedProduct deletes selected products
     @return true*/
    public boolean deleteProduct(Product selectedProduct){
        for (Product p : allProducts) {
            if (p.getProductID() == selectedProduct.getProductID()){
                allProducts.remove(p);
                break;
            }
        }
        return true;
    }
    /** Getters all parts
     @return allParts*/
    public ObservableList<Part> getAllParts(){
        return this.allParts;
    }
    /** Getters all products
     @return allProducts*/
    public ObservableList<Product> getAllProducts() {
        return this.allProducts;
    }
}