/*
 * SUNSHINE TEAHOUSE PRIVATE LIMITED CONFIDENTIAL
 * __________________
 *
 * [2015] - [2017] Sunshine Teahouse Private Limited
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Sunshine Teahouse Private Limited and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Sunshine Teahouse Private Limited
 * and its suppliers, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Sunshine Teahouse Private Limited.
 */

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.04 at 12:22:00 PM IST 
//


package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Unit implements Serializable, Comparable<Unit>{

	private static final long serialVersionUID = 1536706683006809232L;
	protected int id;
    protected String name;
    protected UnitRegion region;
    protected UnitCategory family;
    protected UnitSubCategory subCategory;
    protected UnitStatus status;
    protected String unitEmail;
    protected Date startDate;
    protected Date lastBusinessDate;
    protected String tin;
    protected Division division;
    protected Address address;
    protected Integer cloneUnitId;
    protected Integer inventoryCloneUnitId;
    protected int noOfTerminals;
    protected int noOfTakeawayTerminals;
    protected boolean workstationEnabled;
    protected int noOfTables;
    protected List<Product> products;
    protected List<PartnerDetail> deliveryPartners;
    protected List<TaxProfile> taxProfiles;
    protected List<UnitHours> operationalHours;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link UnitRegion }
     *     
     */
    public UnitRegion getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitRegion }
     *     
     */
    public void setRegion(UnitRegion value) {
        this.region = value;
    }

    /**
     * Gets the value of the family property.
     * 
     * @return
     *     possible object is
     *     {@link UnitCategory }
     *     
     */
    public UnitCategory getFamily() {
        return family;
    }

    /**
     * Sets the value of the family property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitCategory }
     *     
     */
    public void setFamily(UnitCategory value) {
        this.family = value;
    }

    /**
     * Gets the value of the subCategory property.
     * 
     * @return
     *     possible object is
     *     {@link UnitSubCategory }
     *     
     */
    public UnitSubCategory getSubCategory() {
        return subCategory;
    }

    /**
     * Sets the value of the subCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitSubCategory }
     *     
     */
    public void setSubCategory(UnitSubCategory value) {
        this.subCategory = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link UnitStatus }
     *     
     */
    public UnitStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnitStatus }
     *     
     */
    public void setStatus(UnitStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the unitEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitEmail() {
        return unitEmail;
    }

    /**
     * Sets the value of the unitEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitEmail(String value) {
        this.unitEmail = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDate(Date value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the lastBusinessDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getLastBusinessDate() {
        return lastBusinessDate;
    }

    /**
     * Sets the value of the lastBusinessDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastBusinessDate(Date value) {
        this.lastBusinessDate = value;
    }

    /**
     * Gets the value of the tin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTin() {
        return tin;
    }

    /**
     * Sets the value of the tin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTin(String value) {
        this.tin = value;
    }

    /**
     * Gets the value of the division property.
     * 
     * @return
     *     possible object is
     *     {@link Division }
     *     
     */
    public Division getDivision() {
        return division;
    }

    /**
     * Sets the value of the division property.
     *
     * @param value
     *     allowed object is
     *     {@link Division }
     *
     */
    public void setDivision(Division value) {
        this.division = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setAddress(Address value) {
        this.address = value;
    }

    /**
     * Gets the value of the cloneUnitId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCloneUnitId() {
        return cloneUnitId;
    }

    /**
     * Sets the value of the cloneUnitId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCloneUnitId(Integer value) {
        this.cloneUnitId = value;
    }

    /**
     * Gets the value of the inventoryCloneUnitId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInventoryCloneUnitId() {
        return inventoryCloneUnitId;
    }

    /**
     * Sets the value of the inventoryCloneUnitId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInventoryCloneUnitId(Integer value) {
        this.inventoryCloneUnitId = value;
    }

    /**
     * Gets the value of the noOfTerminals property.
     * 
     */
    public int getNoOfTerminals() {
        return noOfTerminals;
    }

    /**
     * Sets the value of the noOfTerminals property.
     * 
     */
    public void setNoOfTerminals(int value) {
        this.noOfTerminals = value;
    }

    /**
     * Gets the value of the noOfTakeawayTerminals property.
     * 
     */
    public int getNoOfTakeawayTerminals() {
        return noOfTakeawayTerminals;
    }

    /**
     * Sets the value of the noOfTakeawayTerminals property.
     * 
     */
    public void setNoOfTakeawayTerminals(int value) {
        this.noOfTakeawayTerminals = value;
    }

    /**
     * Gets the value of the workstationEnabled property.
     * 
     */
    public boolean isWorkstationEnabled() {
        return workstationEnabled;
    }

    /**
     * Sets the value of the workstationEnabled property.
     * 
     */
    public void setWorkstationEnabled(boolean value) {
        this.workstationEnabled = value;
    }

    /**
     * Gets the value of the noOfTables property.
     * 
     */
    public int getNoOfTables() {
        return noOfTables;
    }

    /**
     * Sets the value of the noOfTables property.
     * 
     */
    public void setNoOfTables(int value) {
        this.noOfTables = value;
    }

    /**
     * Gets the value of the products property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the products property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProducts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Product }
     * 
     * 
     */
    public List<Product> getProducts() {
        if (products == null) {
            products = new ArrayList<Product>();
        }
        return this.products;
    }

    /**
     * Gets the value of the deliveryPartners property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deliveryPartners property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeliveryPartners().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PartnerDetail }
     * 
     * 
     */
    public List<PartnerDetail> getDeliveryPartners() {
        if (deliveryPartners == null) {
            deliveryPartners = new ArrayList<PartnerDetail>();
        }
        return this.deliveryPartners;
    }

    /**
     * Gets the value of the taxProfiles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taxProfiles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaxProfiles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TaxProfile }
     * 
     * 
     */
    public List<TaxProfile> getTaxProfiles() {
        if (taxProfiles == null) {
            taxProfiles = new ArrayList<TaxProfile>();
        }
        return this.taxProfiles;
    }

	@Override
	public int compareTo(Unit o) {
		return Integer.compare(this.getId(), o.getId());
	}
    
	/**
     * Gets the value of the operationalHours property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the operationalHours property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOperationalHours().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UnitHours }
     * 
     * 
     */
    public List<UnitHours> getOperationalHours() {
        if (operationalHours == null) {
            operationalHours = new ArrayList<UnitHours>();
        }
        return this.operationalHours;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Unit other = (Unit) obj;
		if (id != other.id)
			return false;
		return true;
	}
    
    

}