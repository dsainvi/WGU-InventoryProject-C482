package model;
/**InHouse*/
public class InHouse extends Part {

    private int machineID;
    /**InHouse
     @param partID int
     @param name String
     @param price double
     @param Stock int
     @param min int
     @param max int
     @param machineID int*/
    public InHouse(int partID, String name, double price, int Stock, int min, int max, int machineID) {
        setId(partID);
        setName(name);
        setPrice(price);
        setStock(Stock);
        setMin(min);
        setMax(max);
        setMachineID(machineID);
    }
    /** getMachineID
     @return machineID*/
    public int getMachineID() {
        return machineID;
    }
    /**setMachineID
     @param machineID int*/
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
