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
// Generated on: 2015.12.19 at 12:10:26 PM IST 
//

package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

import java.io.Serializable;

/**
 * <p>
 * Java class for ComplimentaryDetail complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ComplimentaryDetail"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="isComplimentary" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="reasonCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class ComplimentaryDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3316880555276904189L;

	private String objectId;

	private Long version;

	/**
	 * Added to avoid a runtime error whereby the detachAll property is checked
	 * for existence but not actually used.
	 */
	private String detachAll;
	protected boolean isComplimentary;
	protected Integer reasonCode;
	protected String reason;

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
	 * Gets the value of the isComplimentary property.
	 * 
	 */
	public boolean isIsComplimentary() {
		return isComplimentary;
	}

	/**
	 * Sets the value of the isComplimentary property.
	 * 
	 */
	public void setIsComplimentary(boolean value) {
		this.isComplimentary = value;
	}

	/**
	 * Gets the value of the reasonCode property.
	 * 
	 * @return possible object is {@link Integer }
	 * 
	 */
	public Integer getReasonCode() {
		return reasonCode;
	}

	/**
	 * Sets the value of the reasonCode property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 * 
	 */
	public void setReasonCode(Integer value) {
		this.reasonCode = value;
	}

	/**
	 * Gets the value of the reason property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Sets the value of the reason property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setReason(String value) {
		this.reason = value;
	}

}
