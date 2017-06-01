package www.chaayos.com.chaimonkbluetoothapp.domain.model;

/**
 * Created by rohitsingh on 20/07/16.
 */
public class OrderDetailModel {
    private int orderDetailId;
    private int generatedOrderId;
    private String customerName;
    private double totalAmount;
    private int billGenerationTime;

    public OrderDetailModel(int orderDetailId, int generatedOrderId, String customerName, double totalAmount, int billGenerationTime) {
        this.orderDetailId = orderDetailId;
        this.generatedOrderId = generatedOrderId;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.billGenerationTime = billGenerationTime;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public int getGeneratedOrderId() {
        return generatedOrderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getBillGenerationTime() {
        return billGenerationTime;
    }
}



