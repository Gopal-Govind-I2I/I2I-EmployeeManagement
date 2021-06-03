package com.ideas2it.employeemanagement.model;

import com.ideas2it.employeemanagement.model.Employee;

/**
 * Address class is a template for addresses of employee in an organization
 *
 * @version 1.1   09-04-2021
 *
 * @author Gopal G
 */

public class Address {
    private int id;
    private boolean isPermanent;
    Employee employee;
    private String doorNumber;
    private String street;
    private String locality;
    private String pincode;
    private String district;
    private String state;
    private boolean isDeleted;
	
   /*
    * Default constructor
    */
    public Address() {
    }
	
   /**
    * User defined constructor to initialize variables without employee
    */ 
    public Address(int id, boolean isPermanent, String doorNumber, String street,
                   String locality, String pincode, String district, String state) {
        this.id = id;
        this.isPermanent = isPermanent;
        this.doorNumber = doorNumber;
        this.street = street;
        this.locality = locality;
        this.pincode = pincode;
        this.district = district;
        this.state = state;
        this.isDeleted = false;
	}

   /**
    * User defined constructor to initialize variables
    */ 
    public Address(int id, boolean isPermanent, Employee employee, String doorNumber, String street,
                   String locality, String pincode, String district, String state) {
        this.id = id;
        this.isPermanent = isPermanent;
        this.employee = employee;
        this.doorNumber = doorNumber;
        this.street = street;
        this.locality = locality;
        this.pincode = pincode;
        this.district = district;
        this.state = state;
        this.isDeleted = false;
	}
	
   /**
    * User defined constructor to initialize variables
    * without id, employee
    */ 
    public Address(boolean isPermanent, String doorNumber, String street,
                   String locality, String pincode, String district, String state) {
        this.isPermanent = isPermanent;
        this.doorNumber = doorNumber;
        this.street = street;
        this.locality = locality;
        this.pincode = pincode;
        this.district = district;
        this.state = state;
        this.isDeleted = false;
	}
	
   /**
    * Sets ID attribute with a value
    *
    * @param id ID value which is set to the address
    */
    public void setId(int id) {
        this.id = id;
    }
	
   /**
    * Returns ID value
    *
    * @return id ID attribute of the address
    */
    public int getId() {
        return this.id;
    }
	
   /**
    * Sets isPermanent boolean attribute with a value
    *
    * @param isPermanent boolean value for referring to permananent/temporary addresses
    */
    public void setIsPermanent(boolean isPermanent) {
        this.isPermanent = isPermanent;
    }

   /**
    * Returns isPermanent value of address
    *
    * @return isPermanent permanent address boolean reference for the address
    */
    public boolean getIsPermanent() {
        return this.isPermanent;
    }
	
   /**
    * Sets employee reference with a value
    *
    * @param employee Employee object referring to the employee who belongs to this address
    */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
	
   /**
    * Returns employee reference of this address
    *
    * @return employee Object of Employee referring to the employee who belongs to this address
    */
    public Employee getEmployee() {
        return this.employee;
    }
	
   /**
    * Sets door number attribute with a value
    *
    * @param doorNumber value for doorNumber which is set to the address
    */
    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }
	
   /**
    * Returns door number value
    *
    * @return doorNumber Door number attribute of the address
    */
    public String getDoorNumber() {
        return this.doorNumber;
    }
	
   /**
    * Sets street attribute with a value
    *
    * @param street value for street which is set to the address
    */
	public void setStreet(String street) {
        this.street = street;
    }
	
   /**
    * Returns street value
    *
    * @return street Street attribute of the address
    */
    public String getStreet() {
        return this.street;
    }
	
   /**
    * Sets locality attribute with a value
    *
    * @param locality value for locality which is set to the address
    */
	public void setLocality(String locality) {
        this.locality = locality;
    }
	
   /**
    * Returns locality value
    *
    * @return locality Locality attribute of the address
    */
    public String getLocality() {
        return this.locality;
    }
	
   /**
    * Sets pincode attribute with a value
    *
    * @param pincode value for pincode which is set to the address
    */
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
	
   /**
    * Returns pincode value
    *
    * @return pincode Pincode attribute of the address
    */
    public String getPincode() {
        return this.pincode;
    }
	
   /**
    * Sets district attribute with a value
    *
    * @param district value for district which is set to the address
    */
	public void setDistrict(String district) {
        this.district = district;
    }
	
   /**
    * Returns district value
    *
    * @return district District attribute of the address
    */
    public String getDistrict() {
        return this.district;
    }
	
   /**
    * Sets state attribute with a value
    *
    * @param state value for state which is set to the address
    */
    public void setState(String state) {
        this.state = state;
    }
	
   /**
    * Returns state value
    *
    * @return state State attribute of the address
    */
    public String getState() {
        return this.state;
    }
	
   /**
    * Sets isDeleted attribute with a value
    *
    * @param isDeleted boolean value for deletion status
    */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
	
   /**
    * Returns isDeleted value
    *
    * @return isDeleted boolean value for deletion status
    */
    public boolean getIsDeleted() {
        return this.isDeleted;
    }
	
   /**
    * Address details concatenated together as a  string
    *
    * @param addressDetails Address details concatenated together as a single string
    */
	public String toString() {
        String addressDetails;
        if (-1 == this.id) {
            addressDetails = "This address is either deleted or unavailable";
        }
        else {
            addressDetails = ("\n\tAddress ID: " + id + "\n\tIs permanent address? " + isPermanent
                             + "\n\tDoor number: " + doorNumber + "\n\tStreet: " + street
                             + "\n\tLocality: " + locality + "\n\tPincode: " + pincode
                             + "\n\tDistrict: " + district + "\n\tState: " + state);
        }
        return addressDetails;
    }
}