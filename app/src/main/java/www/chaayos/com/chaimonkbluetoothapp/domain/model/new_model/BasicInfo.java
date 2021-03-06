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

public class BasicInfo implements Serializable {

	private static final long serialVersionUID = 9196734885916273962L;

	private String _id;
	private Long version;

	/**
	 * Added to avoid a runtime error whereby the detachAll property is checked
	 * for existence but not actually used.
	 */
	private String detachAll;
	protected int infoId;
	protected String name;
	protected String code;
	protected String shortCode;
	protected String type;
	protected String status;

	public String get_id() {
		return _id;
	}

	public void set_id(String objectId) {
		this._id = objectId;
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
	 * Gets the value of the productId property.
	 * 
	 */
	public int getInfoId() {
		return infoId;
	}

	/**
	 * Sets the value of the productId property.
	 * 
	 */
	public void setInfoId(int value) {
		this.infoId = value;
	}

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Gets the value of the code property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the value of the code property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCode(String value) {
		this.code = value;
	}

	/**
	 * Gets the value of the shortCode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getShortCode() {
		return shortCode;
	}

	/**
	 * Sets the value of the shortCode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setShortCode(String value) {
		this.shortCode = value;
	}

	/**
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the value of the type property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setType(String value) {
		this.type = value;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setStatus(String value) {
		this.status = value;
	}
}
