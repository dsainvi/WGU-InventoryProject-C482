
package model;

/**
 * @author David Sainvilus
 */
/**outsourced*/
public class Outsourced extends Part {

    private String companyName;
    /**outsourced
     @param partID int
     @param name string
     @param price double
     @param numInStock int
     @param min int
     @param max int
     @param company string*/
    public Outsourced(int partID, String name, double price, int numInStock, int min, int max, String company) {
        super();

        setId(partID);
        setName(name);
        setPrice(price);
        setStock(numInStock);
        setMin(min);
        setMax(max);
        setCompanyName(company);
    }
    /** getCompanyName
     @return companyName*/
    public String getCompanyName() {
        return companyName;
    }
    /**setCompanyName
     @param name string*/
    public void setCompanyName(String name) {
        this.companyName = name;
    }

}