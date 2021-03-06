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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * OrderItem generated by hbm2java
 */

public class OrderItem implements java.io.Serializable,Parcelable {

    private Integer orderItemId;
    private int orderDetailId;
    private int productId;
    private String productName;
    private int quantity;
    private double price;
    private double totalAmount;
    private double paidAmount;
    private String dimension;
    private String billType;
    private String status;
    private int workItemId;
    private String assignedMonk;
    private int productType;

    public OrderItem(){

    }
    public OrderItem(Integer orderItemId, int productId, String productName,int productType, int quantity,
                     String orderItemStatus,double price, double totalAmount, String billType,
                     int workItemId, String monkId) {

        this.orderItemId = orderItemId;
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.quantity = quantity;
        this.status = orderItemStatus;
        this.price = price;
        this.totalAmount = totalAmount;
        this.billType = billType;
        this.workItemId = workItemId;
        this.assignedMonk = monkId;
    }


    public OrderItem(Parcel input){
        String[] data = new String[9];
        input.readStringArray(data);
        this.productId = Integer.parseInt(data[0]);
        this.productName = data[1];
        this.productType = Integer.parseInt(data[2]);
        this.quantity = Integer.parseInt(data[3]);
        this.price = Double.parseDouble(data[4]);
        this.totalAmount = Double.parseDouble(data[5]);
        this.billType = data[6];
        this.workItemId = Integer.parseInt(data[7]);
        this.assignedMonk = data[8];
    }


    public Integer getOrderItemId() {
        return this.orderItemId;
    }

    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getDimension() {
        return this.dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getBillType() {
        return this.billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(int workItemId) {
        this.workItemId = workItemId;
    }

    public String getAssignedMonk() {
        return assignedMonk;
    }

    public void setAssignedMonk(String assignedMonk) {
        this.assignedMonk = assignedMonk;
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
                {
                    String.valueOf(this.productId),
                    this.productName,
                    String.valueOf(this.productType),
                    String.valueOf(this.quantity),
                    String.valueOf(this.price),
                    String.valueOf(this.totalAmount),
                    String.valueOf(this.billType),
                    String.valueOf(this.workItemId),
                    this.assignedMonk
                });
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


}
