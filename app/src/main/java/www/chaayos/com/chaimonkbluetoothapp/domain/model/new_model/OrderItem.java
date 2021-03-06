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
// Generated on: 2016.01.09 at 05:35:16 PM IST 
//

package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItem implements Serializable,Parcelable {

	private static final long serialVersionUID = 4778973176476607094L;

	private String objectId;

	private Long version;

	private String detachAll;
	protected int itemId;
	protected int productId;
	protected String productName;
	protected IdCodeName productCategory;
	protected int quantity;
	protected BigDecimal price;
	protected BigDecimal totalAmount;
	protected BigDecimal amount;
	protected DiscountDetail discountDetail;
	protected ComplimentaryDetail complimentaryDetail;
	protected String dimension;
	protected BillType billType;
	protected OrderItemComposition composition;
    protected int recipeId;
	protected int productType;
	protected String orderItemStatus;

	public OrderItem(int productId,String productName,int productType,int quantity,BigDecimal price,BigDecimal totalAmount,BigDecimal amount,String dimension,BillType billType,String orderItemStatus){
		this.productId = productId;
		this.productName = productName;
		this.productType = productType;
		this.quantity = quantity;
		this.price = price;
		this.totalAmount = totalAmount;
		this.amount = amount;
		this.dimension = dimension;
		this.billType = billType;
		this.orderItemStatus = orderItemStatus;
	}

	public OrderItem(){

	}

	public OrderItem(Parcel input){
		String[] data = new String[10];
		input.readStringArray(data);
		this.productId = Integer.parseInt(data[0]);
		this.productName = data[1];
		this.productType = Integer.parseInt(data[2]);
		this.quantity = Integer.parseInt(data[3]);
		this.price = new BigDecimal(Double.parseDouble(data[4]));
		this.totalAmount = new BigDecimal(Double.parseDouble(data[5]));
		this.amount = new BigDecimal(Double.parseDouble(data[6]));
		this.dimension = data[7];
		this.billType = BillType.valueOf(data[8]);
		this.orderItemStatus = data[9];
	}


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

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int value) {
		this.itemId = value;
	}

	public int getProductId() {
		return productId;
	}


	public void setProductId(int value) {
		this.productId = value;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String value) {
		this.productName = value;
	}

	public IdCodeName getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(IdCodeName value) {
		this.productCategory = value;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int value) {
		this.quantity = value;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal value) {
		this.price = value;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal value) {
		this.totalAmount = value;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal value) {
		this.amount = value;
	}

	public DiscountDetail getDiscountDetail() {
		return discountDetail;
	}

	public void setDiscountDetail(DiscountDetail value) {
		this.discountDetail = value;
	}

	public ComplimentaryDetail getComplimentaryDetail() {
		return complimentaryDetail;
	}

	public void setComplimentaryDetail(ComplimentaryDetail value) {
		this.complimentaryDetail = value;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String value) {
		this.dimension = value;
	}

	public BillType getBillType() {
		return billType;
	}

	public void setBillType(BillType value) {
		this.billType = value;
	}

	public OrderItemComposition getComposition() {
		return composition;
	}

	public void setComposition(OrderItemComposition composition) {
		this.composition = composition;
	}

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int value) {
        this.recipeId = value;
    }

	public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[]
				{ String.valueOf(this.productId),
						this.productName,
						String.valueOf(this.productType),
						String.valueOf(this.quantity),
						String.valueOf(this.price),
						String.valueOf(this.totalAmount),
				        String.valueOf(this.amount),
						this.dimension,
						String.valueOf(this.billType),
						this.orderItemStatus

				});
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem item = (OrderItem) obj;
		if (item.getProductId() == this.getProductId()) {
			return true;
		}
		return  false;
	}

	public static final Parcelable.Creator<OrderItem> CREATOR
			= new Parcelable.Creator<OrderItem>() {
		public OrderItem createFromParcel(Parcel in) {
			return new OrderItem(in);
		}

		public OrderItem[] newArray(int size) {
			return new OrderItem[size];
		}
	};

	public String getOrderItemStatus() {
		return orderItemStatus;
	}

	public void setOrderItemStatus(String orderItemStatus) {
		this.orderItemStatus = orderItemStatus;
	}
}
