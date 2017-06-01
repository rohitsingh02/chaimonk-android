package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rohitsingh on 04/08/16.
 */
public class OrderItemsSummary implements Parcelable {
    private String productName;
    private int productQuantity;
    private double productTotalAmount;


    public OrderItemsSummary(String productName, int productQuantity, double productTotalAmount) {
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productTotalAmount = productTotalAmount;
    }

    public OrderItemsSummary() {

    }
    public OrderItemsSummary(Parcel input){
        String[] data = new String[3];
        input.readStringArray(data);
        this.productName = data[0];
        this.productQuantity = Integer.parseInt(data[1]);
        this.productTotalAmount = Double.valueOf(data[2]);
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductTotalAmount() {
        return productTotalAmount;
    }

    public void setProductTotalAmount(double productTotalAmount) {
        this.productTotalAmount = productTotalAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]
                {
                        this.productName,
                        String.valueOf(this.productQuantity),
                        String.valueOf(this.productTotalAmount)
                });
    }

    public static final Parcelable.Creator<OrderItemsSummary> CREATOR
            = new Parcelable.Creator<OrderItemsSummary>() {
        public OrderItemsSummary createFromParcel(Parcel in) {
            return new OrderItemsSummary(in);
        }

        public OrderItemsSummary[] newArray(int size) {
            return new OrderItemsSummary[size];
        }
    };
}
