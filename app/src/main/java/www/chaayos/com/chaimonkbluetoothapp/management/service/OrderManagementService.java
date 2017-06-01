package www.chaayos.com.chaimonkbluetoothapp.management.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.chaayos.com.chaimonkbluetoothapp.data.model.OrderDetail;
import www.chaayos.com.chaimonkbluetoothapp.data.model.OrderItem;
import www.chaayos.com.chaimonkbluetoothapp.db.DatabaseAdapter;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.ChaiMonk;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderStatus;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.TaskState;
import www.chaayos.com.chaimonkbluetoothapp.network.RetrofitHelper;

/**
 * Created by rohitsingh on 05/07/16.
 */
public class OrderManagementService {
    private static OrderManagementService instance = null;
    private Map<Integer,List<Integer>> categoryWiseProducts;
    private String jwtToken = null;
    private Integer lastSyncedOrderId = null;


    private OrderManagementService(Map<Integer,List<Integer>> categoryWiseProducts,String jwtToken) {
        this.categoryWiseProducts = categoryWiseProducts;
        this.jwtToken = jwtToken;
    }

    public static OrderManagementService getInstance(Map<Integer,List<Integer>> categoryWiseProducts, String jwtToken){
        if(instance==null){
           instance = new OrderManagementService(categoryWiseProducts,jwtToken);
        }
        return instance;
    }



    public void assignOrders(List<OrderDetail> orderList, ChaiMonkManager chaiMonkManager,DatabaseAdapter databaseAdapter) {
        Map<TaskState, List<ChaiMonk>> monks = chaiMonkManager.findFreeMonks();
        for (OrderDetail order : orderList) {
            if (order.getOrderStatus().equals(OrderStatus.INITIATED)) {
                List<OrderItem> orderItems = order.getOrderItems();

                Collections.sort(orderItems, new Comparator<OrderItem>() {
                    @Override
                    public int compare(OrderItem lhs, OrderItem rhs) {
                        return rhs.getQuantity() - lhs.getQuantity();
                    }
                });

                List<OrderItem> filteredItems = new ArrayList<>();
                for(OrderItem orderItem : orderItems){
                    if(categoryWiseProducts.get(5).contains(orderItem.getProductId())){
                        if((orderItem.getAssignedMonk() == null && orderItem.getWorkItemId() == 0)
                                && (orderItem.getStatus().equals(OrderStatus.INITIATED.toString())
                                    || orderItem.getStatus().equals(OrderStatus.WAITING.toString()))){

                            filteredItems.add(orderItem);
                        }
                    }
                }

                for (OrderItem orderItem : filteredItems) {
                    ChaiMonk assignedmonk = chaiMonkManager.assignToMonk(monks, orderItem, order.getOrderId(), databaseAdapter);
                    monks = chaiMonkManager.findFreeMonks();
                    if (assignedmonk != null) {
                        orderItem.setStatus(OrderStatus.WAITING.toString());
                        databaseAdapter.updateOrderItemStatus(orderItem.getOrderItemId(), orderItem.getStatus().toString());
                        databaseAdapter.updateWorkItemIdAtOrderItemLabel(orderItem.getOrderItemId(), orderItem.getWorkItemId(), assignedmonk.getMonkName());
                    }
                }
            }
        }
    }

    public Integer syncOrders(List<OrderDetail> orderDetails){
        RetrofitHelper.getAnalyticsDataService().syncOrders(instance.jwtToken,orderDetails).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response!=null && response.body()!=null){
                    Integer syncedOrder = Integer.parseInt(response.body().toString());
                    setSyncedLastOrder(syncedOrder);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
        return getSyncedLastOrder();
    }

    public void setSyncedLastOrder(Integer response){
        instance.lastSyncedOrderId = response;
    }

    public Integer getSyncedLastOrder(){
        return instance.lastSyncedOrderId;
    }
}
