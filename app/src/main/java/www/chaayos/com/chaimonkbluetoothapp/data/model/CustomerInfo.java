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

package www.chaayos.com.chaimonkbluetoothapp.data.model;

// Generated 27 Jul, 2015 12:04:59 PM by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * CustomerInfo generated by hbm2java
 */

public class CustomerInfo implements java.io.Serializable {

	private int customerId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String contactNumber;
	private Integer registrationUnitId;

	public CustomerInfo() {
	}

	public CustomerInfo(int customerId, String countryCode, String contactNumber, Date addTime) {
		this.customerId = customerId;
		this.contactNumber = contactNumber;
	}

	public CustomerInfo(int customerId, String firstName, String middleName, String lastName, String countryCode,
			String contactNumber, String emailId, String isNumberVerified, String isEmailVerified, Date addTime) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.contactNumber = contactNumber;
	}

	public int getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getContactNumber() {
		return this.contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}


	public Integer getRegistrationUnitId() {
		return registrationUnitId;
	}

	public void setRegistrationUnitId(Integer registrationUnitId) {
		this.registrationUnitId = registrationUnitId;
	}


}
