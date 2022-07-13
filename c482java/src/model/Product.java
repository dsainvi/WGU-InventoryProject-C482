
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 Product
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String productName;
    private double productPrice;
    private int productStock;
    private int prodMax;
    private int prodMin;
    /**
     Product
     @param productID int
     @param productName string
     @param productPrice double
     @param productStock int
     @param prodMax int
     @param prodMin int
     products
     */
    public Product(int productID, String productName, double productPrice, int productStock, int prodMax, int prodMin) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.prodMax = prodMax;
        this.prodMin = prodMin;
    }

    /** addAssociatedPart
     Add part to the product's Observable List
     @param part  add*/
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }
    /**deleteAssociatedPart
     @param part delete
     */
    public void deleteAssociatedPart(Part part){
        this.associatedParts.remove(part);
    }
    /**getAssociatedParts getter observableList
     @return this.associatedParts*/
    public ObservableList<Part> getAssociatedParts() {
        return this.associatedParts;
    }
    /** Setters id
     @param productID int*/
    public void setProductID(int productID) {
        this.productID = productID;
    }
    /** Setters name
     @param productName string*/
    public void setProductName(String productName) {
        this.productName = productName;
    }
    /** Setters price
     @param productPrice double*/
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    /** Setters stock
      @param productStock int */
    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }
    /** Setters max
     @param prodMax int */
    public void setProdMax(int prodMax) {
        this.prodMax = prodMax;
    }
    /** Setters min
     @param prodMin int*/
    public void setProdMin(int prodMin) {
        this.prodMin = prodMin;
    }

    /** Getters id
     @return productID*/
    public int getProductID() {
        return productID;
    }
    /** Getters name
     @return productName*/
    public String getProductName() {
        return productName;
    }
    /** Getters Price
     @return productPrice*/
    public double getProductPrice() {
        return productPrice;
    }
    /** Getters stock
     @return productStock*/
    public int getProductStock() {
        return productStock;
    }
    /** Getters max
     @return prodMax*/
    public int getProdMax() {
        return prodMax;
    }
    /** Getters min
     @return prodMIn*/
    public int getProdMin() {
        return prodMin;
    }

}