package www.chaayos.com.chaimonkbluetoothapp.management.service;

import java.math.BigDecimal;

import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderItem;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Product;

/**
 * Created by rohitsingh on 05/07/16.
 */
public class OrderItemManagementService {
    public OrderItem createOrderItem(Product product, int quantity, BigDecimal price, BigDecimal totalAmount, BigDecimal amount, String dimension,String orderItemStatus){

        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(product.getId());
        orderItem.setProductName(product.getName());
        orderItem.setProductType(product.getType());
        orderItem.setQuantity(quantity);
        orderItem.setPrice(price);
        orderItem.setTotalAmount(totalAmount);
        orderItem.setAmount(amount);
        orderItem.setDimension(dimension);
        orderItem.setBillType(product.getBillType());
        orderItem.setOrderItemStatus(orderItemStatus);
        return orderItem;
    }

}
