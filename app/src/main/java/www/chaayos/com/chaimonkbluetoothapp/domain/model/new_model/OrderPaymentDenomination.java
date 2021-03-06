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
// Generated on: 2016.02.29 at 05:00:04 PM IST 
//

package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

import java.io.Serializable;

/**
 * <p>
 * Java class for OrderPaymentDenomination complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="OrderPaymentDenomination"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="orderId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="settlementId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="denominationDetailId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="count" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="totalAmount" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class OrderPaymentDenomination implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1917517604444772745L;

	private String objectId;


	private Long version;

	/**
	 * Added to avoid a runtime error whereby the detachAll property is checked
	 * for existence but not actually used.
	 */
	private String detachAll;
	protected int id;
	protected int orderId;
	protected int settlementId;
	protected int denominationDetailId;
	protected int count;
	protected int totalAmount;

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String _id) {
		this.objectId = _id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getDetachAll() {
		return detachAll;
	}

	public void setDetachAll(String detachAll) {
		this.detachAll = detachAll;
	}

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
	 * Gets the value of the orderId property.
	 * 
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * Sets the value of the orderId property.
	 * 
	 */
	public void setOrderId(int value) {
		this.orderId = value;
	}

	/**
	 * Gets the value of the settlementId property.
	 * 
	 */
	public int getSettlementId() {
		return settlementId;
	}

	/**
	 * Sets the value of the settlementId property.
	 * 
	 */
	public void setSettlementId(int value) {
		this.settlementId = value;
	}

	/**
	 * Gets the value of the denominationDetailId property.
	 * 
	 */
	public int getDenominationDetailId() {
		return denominationDetailId;
	}

	/**
	 * Sets the value of the denominationDetailId property.
	 * 
	 */
	public void setDenominationDetailId(int value) {
		this.denominationDetailId = value;
	}

	/**
	 * Gets the value of the count property.
	 * 
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Sets the value of the count property.
	 * 
	 */
	public void setCount(int value) {
		this.count = value;
	}

	/**
	 * Gets the value of the totalAmount property.
	 * 
	 */
	public int getTotalAmount() {
		return totalAmount;
	}

	/**
	 * Sets the value of the totalAmount property.
	 * 
	 */
	public void setTotalAmount(int value) {
		this.totalAmount = value;
	}

}
