package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

import java.util.List;

/**
 * Created by Paramvir on 11/13/2015.
 */
public class Customer {

    protected int id;
    protected String firstName;
    protected String middleName;
    protected String lastName;
    protected String countryCode;
    protected String contactNumber;
    protected String emailId;
    protected boolean emailVerified;
    protected int loyaltyPoints;
    protected Integer registrationUnitId;
    protected String acquisitionSource;
    protected String acquisitionToken;
    protected boolean contactNumberVerified;
    protected List<Address> addresses;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Integer getRegistrationUnitId() {
        return registrationUnitId;
    }

    public void setRegistrationUnitId(Integer registrationUnitId) {
        this.registrationUnitId = registrationUnitId;
    }

    public String getAcquisitionSource() {
        return acquisitionSource;
    }

    public void setAcquisitionSource(String acquisitionSource) {
        this.acquisitionSource = acquisitionSource;
    }

    public String getAcquisitionToken() {
        return acquisitionToken;
    }

    public void setAcquisitionToken(String acquisitionToken) {
        this.acquisitionToken = acquisitionToken;
    }

    public boolean isContactNumberVerified() {
        return contactNumberVerified;
    }

    public void setContactNumberVerified(boolean contactNumberVerified) {
        this.contactNumberVerified = contactNumberVerified;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
