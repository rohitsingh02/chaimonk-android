package www.chaayos.com.chaimonkbluetoothapp;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.Fabric;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.TaxListModel;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.ListData;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Order;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderInfo;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderItem;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Product;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.TransactionDetail;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Unit;
import www.chaayos.com.chaimonkbluetoothapp.management.service.ChaiMonkManager;
import www.chaayos.com.chaimonkbluetoothapp.management.service.OrderManagementService;
import www.chaayos.com.chaimonkbluetoothapp.printer.PrintFeature;

/**
 * Created by rohitsingh on 11/07/16.
 */
public class GlobalVariables extends Application {
    private Unit unit;
    private List<ListData> listDatas;
    private Map<String,List<Product>> categoryAndProductMap;
    private ChaiMonkManager chaiMonkManager = null;
    private OrderManagementService orderManagementService = null;
    private ArrayList<TaxListModel> taxListModel;
    private ArrayList<Order> orderArrayList = new ArrayList<>();
    private OrderInfo latestCreatedOrderInfo;
    private ArrayList<OrderInfo> orderInfoArrayList = new ArrayList<>();
    private ArrayList<OrderItem> latestOrderItemArrayList;
    private TransactionDetail latestTransactionDetail;
    private PrintFeature mPrintFeature;
    private String currentBusinessDate;
    private Context context;
    private int endOrderId;


    @Override
    public void onCreate() {
        super.onCreate();
        if(!    BuildConfig.DEBUG){
            Fabric.with(this, new Crashlytics());
        }

    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public List<ListData> getListDatas() {
        return listDatas;
    }

    public void setListDatas(List<ListData> listDatas) {
        this.listDatas = listDatas;
    }

    public Map<String, List<Product>> getCategoryAndProductMap() {
        return categoryAndProductMap;
    }

    public void setCategoryAndProductMap(Map<String, List<Product>> categoryAndProductMap) {
        this.categoryAndProductMap = categoryAndProductMap;
    }

    public ArrayList<TaxListModel> getTaxListModel() {
        return taxListModel;
    }

    public void setTaxListModel(ArrayList<TaxListModel> taxListModel) {
        this.taxListModel = taxListModel;
    }

    public ArrayList<Order> getOrderArrayList() {
        return orderArrayList;
    }

    public void setOrderArrayList(Order order) {

        this.orderArrayList.add(order);
    }

    public void setOrderArrayList(ArrayList<Order> orderArrayList) {
        this.orderArrayList = orderArrayList;
    }

    public OrderInfo getLatestCreatedOrderInfo() {
        return latestCreatedOrderInfo;
    }

    public void setLatestCreatedOrderInfo(OrderInfo latestCreatedOrderInfo) {
        this.latestCreatedOrderInfo = latestCreatedOrderInfo;
    }

    public void createOrderInfoArrayList(OrderInfo orderInfo){
        orderInfoArrayList.add(orderInfo);
    }

    public ArrayList<OrderInfo> getOrderInfoArrayList() {
        return orderInfoArrayList;
    }

    public void setOrderInfoArrayList(ArrayList<OrderInfo> orderInfoArrayList) {
        this.orderInfoArrayList = orderInfoArrayList;
    }

    public ArrayList<OrderItem> getLatestOrderItemArrayList() {
        return latestOrderItemArrayList;
    }

    public void setLatestOrderItemArrayList(ArrayList<OrderItem> latestOrderItemArrayList) {
        this.latestOrderItemArrayList = latestOrderItemArrayList;
    }

    public ChaiMonkManager getChaiMonkManager() {
        return chaiMonkManager;
    }

    public void setChaiMonkManager(ChaiMonkManager chaiMonkManager) {
        this.chaiMonkManager = chaiMonkManager;
    }

    public OrderManagementService getOrderManagementService() {
        return orderManagementService;
    }

    public void setOrderManagementService(OrderManagementService orderManagementService) {
        this.orderManagementService = orderManagementService;
    }

    public TransactionDetail getLatestTransactionDetail() {
        return latestTransactionDetail;
    }

    public void setLatestTransactionDetail(TransactionDetail latestTransactionDetail) {
        this.latestTransactionDetail = latestTransactionDetail;
    }

    public PrintFeature getmPrintFeature() {
        return mPrintFeature;
    }

    public void setmPrintFeature(PrintFeature mPrintFeature) {
        this.mPrintFeature = mPrintFeature;
    }

    public String getCurrentBusinessDate() {
        return currentBusinessDate;
    }

    public void setCurrentBusinessDate(String currentBusinessDate) {
        this.currentBusinessDate = currentBusinessDate;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getEndOrderId() {
        return endOrderId;
    }

    public void setEndOrderId(int endOrderId) {
        this.endOrderId = endOrderId;
    }
}
