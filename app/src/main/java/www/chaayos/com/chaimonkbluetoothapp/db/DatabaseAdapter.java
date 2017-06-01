package www.chaayos.com.chaimonkbluetoothapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import www.chaayos.com.chaimonkbluetoothapp.data.model.ConnectionLog;
import www.chaayos.com.chaimonkbluetoothapp.data.model.OrderDetail;
import www.chaayos.com.chaimonkbluetoothapp.data.model.OrderItem;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.CounterEnum;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderStatus;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.TransactionDetail;
import www.chaayos.com.chaimonkbluetoothapp.utils.AppUtils;

/**
 * Created by rohitsingh on 19/07/16.
 */
public class DatabaseAdapter {

    DbHelper dbHelper;

    public DatabaseAdapter(Context context) {
        dbHelper = new DbHelper(context);
    }

    public long insertDataToCounterTable(String counterName, int counterValue) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(DbHelper.COUNTER_NAME, counterName);
        contentValues1.put(DbHelper.COUNTER_VALUE, counterValue);
        long id = db.insert(DbHelper.COUNTER_TABLE, null, contentValues1);
        return id;
    }

    public long insertDataToOrderDetailTable(int generatedOrderId, int token, int employeeId,
                                             String customerName, String orderStatus, int settlementType,
                                             int unitId, String billGenerationTime, TransactionDetail transactionDetail,
                                             String currentBusinessDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.GENERATED_ORDER_ID, generatedOrderId);
        contentValues.put(DbHelper.TOKEN, token);
        contentValues.put(DbHelper.EMPLOYEE_ID, employeeId);
        contentValues.put(DbHelper.CUSTOMER_NAME, customerName);
        contentValues.put(DbHelper.ORDER_STATUS, orderStatus);
        contentValues.put(DbHelper.SETTLEMENT_TYPE, settlementType);
        contentValues.put(DbHelper.UNIT_ID, unitId);
        contentValues.put(DbHelper.BILL_GENERATION_TIME, billGenerationTime);
        contentValues.put(DbHelper.TOTAL_AMOUNT, transactionDetail.getTotalAmount().doubleValue());
        contentValues.put(DbHelper.TAXABLE_AMOUNT, transactionDetail.getTaxableAmount().doubleValue());
        contentValues.put(DbHelper.NET_PRICE_VAT_PERCENTAGE, transactionDetail.getNetPriceVat().getPercentage().doubleValue());
        contentValues.put(DbHelper.NET_PRICE_VAT_AMOUNT, transactionDetail.getNetPriceVat().getValue().doubleValue());
        contentValues.put(DbHelper.MRP_VAT_PERCENTAGE, transactionDetail.getMrpVat().getPercentage().doubleValue());
        contentValues.put(DbHelper.MRP_VAT_AMOUNT, transactionDetail.getMrpVat().getValue().doubleValue());
        contentValues.put(DbHelper.SERVICE_TAX_PERCENTAGE, transactionDetail.getServiceTax().getPercentage().doubleValue());
        contentValues.put(DbHelper.SERVICE_TAX_AMOUNT, transactionDetail.getServiceTax().getValue().doubleValue());
        contentValues.put(DbHelper.SBCESS_PERCENTAGE, transactionDetail.getSbCess().getPercentage().doubleValue());
        contentValues.put(DbHelper.SBCESS_AMOUNT, transactionDetail.getSbCess().getValue().doubleValue());
        contentValues.put(DbHelper.KKCESS_PERCENTAGE, transactionDetail.getKkCess().getPercentage().doubleValue());
        contentValues.put(DbHelper.KKCESS_AMOUNT, transactionDetail.getKkCess().getValue().doubleValue());
        contentValues.put(DbHelper.SURCHARGE_TAX_PERCENTAGE, transactionDetail.getSurchargeOnTax().getPercentage().doubleValue());
        contentValues.put(DbHelper.SURCHARGE_TAX_AMOUNT, transactionDetail.getSurchargeOnTax().getValue().doubleValue());
        contentValues.put(DbHelper.SETTLED_AMOUNT, transactionDetail.getTotalAmount().doubleValue());
        contentValues.put(DbHelper.PRINT_COUNT, 0);
        contentValues.put(DbHelper.CURRENT_BUSINESS_DATE, currentBusinessDate);
        long id = db.insert(DbHelper.ORDER_DETAIL_TABLE, null, contentValues);
        return id;
    }

    public long insertDataToOrderItemTable(int productId, int orderDetailId, String productName,int productType, int quantity, String orderItemStatus, double price, double totalAmount, int workItemId, double paidAmount, String billType) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.PRODUCT_ID, productId);
        contentValues.put(DbHelper.ORDER_DETAIL_ID, orderDetailId);
        contentValues.put(DbHelper.PRODUCT_NAME, productName);
        contentValues.put(DbHelper.PRODUCT_TYPE,productType);
        contentValues.put(DbHelper.QUANTITY, quantity);
        contentValues.put(DbHelper.ORDER_ITEM_STATUS, orderItemStatus);
        contentValues.put(DbHelper.PRICE, price);
        contentValues.put(DbHelper.TOTAL_AMOUNT, totalAmount);
        contentValues.put(DbHelper.WORK_ITEM_ID, 0);
        contentValues.put(DbHelper.PAID_AMOUNT, paidAmount);
        contentValues.put(DbHelper.BILL_TYPE, billType);
        long id = db.insert(DbHelper.ORDER_ITEM_TABLE, null, contentValues);
        return id;
    }

    public long insertDataToOrderSettlementTable(int orderDetailId, int paymentModeId, double amountPaid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.ORDER_DETAIL_ID, orderDetailId);
        contentValues.put(DbHelper.PAYMENT_MODE_ID, paymentModeId);
        contentValues.put(DbHelper.AMOUNT_PAID, amountPaid);
        long id = db.insert(DbHelper.ORDER_SETTLEMENT_TABLE, null, contentValues);
        return id;
    }

    public long insertDataToReprintTable(int orderDetailId, String orderInfoObject) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.ORDER_DETAIL_ID, orderDetailId);
        contentValues.put(DbHelper.ORDER_INFO_OBJECT, orderInfoObject);
        long id = db.insert(DbHelper.REPRINT_TABLE, null, contentValues);
        return id;
    }

    public long insertDataToDayCloseEventTable(String eventDate, String eventStatus, int startOrderId, int endOrderId, String eventTime, String createdBy, String cancelledBy) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.EVENT_DATE, eventDate);
        contentValues.put(DbHelper.EVENT_STATUS, eventStatus);
        contentValues.put(DbHelper.START_ORDER_ID, startOrderId);
        contentValues.put(DbHelper.END_ORDER_ID, endOrderId);
        contentValues.put(DbHelper.EVENT_TIME, eventTime);
        contentValues.put(DbHelper.CREATED_BY, createdBy);
        contentValues.put(DbHelper.CANCELLED_BY, cancelledBy);
        long id = db.insert(DbHelper.DAY_CLOSE_EVENT_TABLE, null, contentValues);
        return id;
    }

    public long insertDataToOrdersSummaryTable(int orderEventId, int totalOrders, double totalOrdersAmount) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.ORDER_EVENT_ID, orderEventId);
        contentValues.put(DbHelper.TOTAL_ORDERS, totalOrders);
        contentValues.put(DbHelper.TOTAL_ORDERS_AMOUNT, totalOrdersAmount);
        long id = db.insert(DbHelper.ORDERS_SUMMARY_TABLE, null, contentValues);
        return id;
    }

    public long insertDataToOrderItemSummaryTable(int orderEventId, int productId, String productName, int productQuantity, double orderItemsTotalAmount) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.ORDER_EVENT_ID, orderEventId);
        contentValues.put(DbHelper.PRODUCT_ID, productId);
        contentValues.put(DbHelper.PRODUCT_NAME, productName);
        contentValues.put(DbHelper.PRODUCT_QUANTITY, productQuantity);
        contentValues.put(DbHelper.ORDER_ITEMS_TOTAL_AMOUNT, orderItemsTotalAmount);
        long id = db.insert(DbHelper.ORDER_ITEMS_SUMMARY_TABLE, null, contentValues);
        return id;
    }

    public long insertDataToConnectionLogTable(String currentDate,String deviceName,String connectionTime,String disconnectionTime) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.CURRENT_DATE, currentDate);
        contentValues.put(DbHelper.DEVICE_NAME, deviceName);
        contentValues.put(DbHelper.CONNECTION_TIME, connectionTime);
        contentValues.put(DbHelper.DISCONNECTION_TIME, disconnectionTime);
        long id = db.insert(DbHelper.CONNECTION_LOG_TABLE, null, contentValues);
        return id;
    }

    public String getDataFromReprintTable(int orderDetailId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String orderInfoObject = "";
        String[] columns = {dbHelper.ORDER_INFO_OBJECT};
        String whereClause = dbHelper.ORDER_DETAIL_ID + " = " + orderDetailId;
        Cursor cursor = db.query(dbHelper.REPRINT_TABLE, columns, whereClause, null, null, null, null);
        while (cursor.moveToNext()) {
            orderInfoObject = cursor.getString(cursor.getColumnIndex(dbHelper.ORDER_INFO_OBJECT));
        }

        return orderInfoObject;

    }

    public List<ConnectionLog> getDataFromConnectionLogTable(String currentDate) {
        List<ConnectionLog> connectionLogList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {dbHelper.CURRENT_DATE,dbHelper.DEVICE_NAME,dbHelper.CONNECTION_TIME,dbHelper.DISCONNECTION_TIME};
        String whereClause = DbHelper.CURRENT_DATE + " = " + "'" + AppUtils.getCurrentBusinessDate() + "'";
        Cursor cursor = db.query(dbHelper.CONNECTION_LOG_TABLE, columns, whereClause, null, null, null, null);
        while (cursor.moveToNext()) {
            ConnectionLog connectionLog = new ConnectionLog();
            connectionLog.setCurrentDate(cursor.getString(cursor.getColumnIndex(dbHelper.CURRENT_DATE)));
            connectionLog.setDeviceName(cursor.getString(cursor.getColumnIndex(dbHelper.DEVICE_NAME)));
            connectionLog.setConnectionTime(cursor.getString(cursor.getColumnIndex(dbHelper.CONNECTION_TIME)));
            connectionLog.setDisconnectionTime(cursor.getString(cursor.getColumnIndex(dbHelper.DISCONNECTION_TIME)));
            connectionLogList.add(connectionLog);
        }
        return connectionLogList;

    }

    public void updateDataCounterTable() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = db.query(DbHelper.COUNTER_TABLE, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String whereClause = null;
            if (cursor.getString(cursor.getColumnIndex(DbHelper.COUNTER_NAME)).equals(CounterEnum.G_ORDER_ID.toString())) {
                int generatedOrderIdCount = cursor.getInt(cursor.getColumnIndex(DbHelper.COUNTER_VALUE)) + 1;
                contentValues.put(DbHelper.COUNTER_VALUE, generatedOrderIdCount);
                whereClause = DbHelper.COUNTER_ID + " = " + 1;
            } else if (cursor.getString(cursor.getColumnIndex(DbHelper.COUNTER_NAME)).equals(CounterEnum.TOKEN_ID.toString())) {
                int tokenCount = cursor.getInt(cursor.getColumnIndex(DbHelper.COUNTER_VALUE)) + 1;
                contentValues.put(DbHelper.COUNTER_VALUE, tokenCount);
                whereClause = DbHelper.COUNTER_ID + " = " + 2;
            } else if (cursor.getString(cursor.getColumnIndex(DbHelper.COUNTER_NAME)).equals(CounterEnum.WORK_ITEM_ID.toString())) {
                int workItemId = cursor.getInt(cursor.getColumnIndex(DbHelper.COUNTER_VALUE)) + 1;
                contentValues.put(DbHelper.COUNTER_VALUE, workItemId);
                whereClause = DbHelper.COUNTER_ID + " = " + 3;
            }

            db.update(DbHelper.COUNTER_TABLE, contentValues, whereClause, null);
        }
        db.close();

    }

    public void updateWorkItemId() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = db.query(DbHelper.COUNTER_TABLE, null, null, null, null, null, null);
        String whereClause = null;
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(DbHelper.COUNTER_NAME)).equals(CounterEnum.WORK_ITEM_ID.toString())) {
                int workItemId = cursor.getInt(cursor.getColumnIndex(DbHelper.COUNTER_VALUE)) + 1;
                contentValues.put(DbHelper.COUNTER_VALUE, workItemId);
                whereClause = DbHelper.COUNTER_ID + " = " + 3;
            }
        }
        db.update(DbHelper.COUNTER_TABLE, contentValues, whereClause, null);

    }


    public Map<String, Integer> getDataFromCounterTable() {
        Map<String, Integer> counterNameValueMap = new HashMap<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {DbHelper.COUNTER_NAME, DbHelper.COUNTER_VALUE};
        Cursor cursor = db.query(DbHelper.COUNTER_TABLE, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(DbHelper.COUNTER_NAME));
            int value = cursor.getInt(cursor.getColumnIndex(DbHelper.COUNTER_VALUE));
            counterNameValueMap.put(name, value);
        }
        return counterNameValueMap;
    }

    public void resetMonkCounterToOne() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String whereClause = DbHelper.COUNTER_ID + " = " + 2;
        contentValues.put(DbHelper.COUNTER_VALUE, 0);
        db.update(DbHelper.COUNTER_TABLE, contentValues, whereClause, null);
    }

    public ArrayList<OrderDetail> getJobQueueDetails(int endOrderId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<OrderDetail> orderDetailArrayList = new ArrayList<>();
        int orderDetailId = -1;
        String whereClause = DbHelper.ORDER_ID + " > " + endOrderId + " AND " + "orderStatus <> " + "'" + OrderStatus.SETTLED.toString() + "' AND " + "orderStatus <> " + "'" + OrderStatus.CANCELLED.toString() + "'";
        Cursor cursor = db.query(DbHelper.ORDER_DETAIL_TABLE, null, whereClause, null, null, null, null);
        while (cursor.moveToNext()) {

            orderDetailId = cursor.getInt(cursor.getColumnIndex(DbHelper.ORDER_ID));
            OrderDetail orderDetail = new OrderDetail(cursor.getInt(cursor.getColumnIndex(DbHelper.ORDER_ID)),
                    cursor.getInt(cursor.getColumnIndex(DbHelper.GENERATED_ORDER_ID)), cursor.getInt(cursor.getColumnIndex(DbHelper.TOKEN))
                    , cursor.getInt(cursor.getColumnIndex(DbHelper.EMPLOYEE_ID)), cursor.getInt(cursor.getColumnIndex(DbHelper.UNIT_ID)), OrderStatus.valueOf(cursor.getString(cursor.getColumnIndex(DbHelper.ORDER_STATUS))),
                    cursor.getString(cursor.getColumnIndex(DbHelper.BILL_GENERATION_TIME)), cursor.getDouble(cursor.getColumnIndex(DbHelper.TOTAL_AMOUNT)), getOrderItem(orderDetailId));
            orderDetailArrayList.add(orderDetail);
        }
        return orderDetailArrayList;
    }

    public void updateOrderStatus(int OrderId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.ORDER_STATUS, OrderStatus.SETTLED.toString());
        String whereClause = DbHelper.ORDER_ID + " = " + OrderId;
        db.update(DbHelper.ORDER_DETAIL_TABLE, contentValues, whereClause, null);
    }

    public ArrayList<OrderItem> getOrderItem(int orderDetailId) {
        ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = DbHelper.ORDER_DETAIL_ID + "=" + orderDetailId;
        Cursor cursor = db.query(DbHelper.ORDER_ITEM_TABLE, null, whereClause, null, null, null, null);

        while (cursor.moveToNext()) {
            OrderItem orderItem = new OrderItem(cursor.getInt(cursor.getColumnIndex(DbHelper.ORDER_ITEM_ID)),
                    cursor.getInt(cursor.getColumnIndex(DbHelper.PRODUCT_ID)),
                    cursor.getString(cursor.getColumnIndex(DbHelper.PRODUCT_NAME)),
                    cursor.getInt(cursor.getColumnIndex(DbHelper.PRODUCT_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(DbHelper.QUANTITY)),
                    cursor.getString(cursor.getColumnIndex(DbHelper.ORDER_ITEM_STATUS)),
                    cursor.getDouble(cursor.getColumnIndex(DbHelper.PRICE)),
                    cursor.getDouble(cursor.getColumnIndex(DbHelper.TOTAL_AMOUNT)),
                    cursor.getString(cursor.getColumnIndex(DbHelper.BILL_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(DbHelper.WORK_ITEM_ID)),
                    cursor.getString(cursor.getColumnIndex(DbHelper.MONK_ID))
            );
            orderItemArrayList.add(orderItem);
        }
        return orderItemArrayList;
    }

    public void updateOrderItemStatus(int orderItemId, String status) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.ORDER_ITEM_STATUS, status);
        String whereClause = DbHelper.ORDER_ITEM_ID + " = " + orderItemId;
        db.update(DbHelper.ORDER_ITEM_TABLE, contentValues, whereClause, null);
    }

    public void updateWorkItemIdAtOrderItemLabel(int orderItemId, int workItemId, String monkName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.WORK_ITEM_ID, workItemId);
        contentValues.put(DbHelper.MONK_ID, monkName);
        String whereClause = DbHelper.ORDER_ITEM_ID + " = " + orderItemId;
        db.update(DbHelper.ORDER_ITEM_TABLE, contentValues, whereClause, null);
    }

    public void updateOrderStatusToCancelled(int orderId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.ORDER_STATUS, OrderStatus.CANCELLED.toString());
        String whereClause = DbHelper.ORDER_ID + " = " + orderId;
        db.update(DbHelper.ORDER_DETAIL_TABLE, contentValues, whereClause, null);
    }

    public void updateCancellationTime(int orderId, String cancellationTime) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.BILL_CANCELLATION_TIME, cancellationTime);
        String whereClause = DbHelper.ORDER_ID + " = " + orderId;
        db.update(DbHelper.ORDER_DETAIL_TABLE, contentValues, whereClause, null);
    }

    public void updateSettlementTime(int orderId, String settlementTime) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.BILL_SETTLEMENT_TIME, settlementTime);
        String whereClause = DbHelper.ORDER_ID + " = " + orderId;
        db.update(DbHelper.ORDER_DETAIL_TABLE, contentValues, whereClause, null);
    }

    public void updateCompletionTime(int orderId, String completionTime) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.BILL_COMPLETION_TIME, completionTime);
        String whereClause = DbHelper.ORDER_ID + " = " + orderId;
        db.update(DbHelper.ORDER_DETAIL_TABLE, contentValues, whereClause, null);
    }

    public Pair<Integer, Integer> getOrderIdsOfCurrentBusinessDate(int previousEndOrderId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {DbHelper.ORDER_ID};
        int startOrderId = 0;
        int lastOrderId = 0;
        String whereClause = DbHelper.CURRENT_BUSINESS_DATE + " = " + "'" + AppUtils.getCurrentBusinessDate() + "'" + " AND " + "orderStatus <> " + "'" + OrderStatus.CANCELLED.toString() + "'" + " AND " + dbHelper.ORDER_ID + " > " + previousEndOrderId;
        String orderBy = DbHelper.ORDER_ID + " ASC";
        Cursor cursor = db.query(DbHelper.ORDER_DETAIL_TABLE, columns, whereClause, null, null, null, orderBy);

        if (cursor != null && cursor.moveToFirst()) {
            startOrderId = cursor.getInt(cursor.getColumnIndex(DbHelper.ORDER_ID));
        }
        if (cursor != null && cursor.moveToLast()) {
            lastOrderId = cursor.getInt(cursor.getColumnIndex(DbHelper.ORDER_ID));
        }
        return new Pair<>(startOrderId, lastOrderId);
    }


    public int getTotalAmountOfaBusinessDate(int previousEndOrderId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {DbHelper.TOTAL_AMOUNT};
        int totalAmount = 0;
        String whereClause = DbHelper.CURRENT_BUSINESS_DATE + " = " + "'" + AppUtils.getCurrentBusinessDate() + "'" + " AND " + "orderStatus <> " + "'" + OrderStatus.CANCELLED.toString() + "'" + " AND " + dbHelper.ORDER_ID + " > " + previousEndOrderId;
        Cursor cursor = db.query(DbHelper.ORDER_DETAIL_TABLE, columns, whereClause, null, null, null, null);
        while (cursor.moveToNext()) {

            totalAmount = totalAmount + cursor.getInt(cursor.getColumnIndex(DbHelper.TOTAL_AMOUNT));
        }
        return totalAmount;
    }

    public Map<String, Pair<String, Pair<Integer, Double>>> getProductWiseDetails(List<OrderDetail> orderDetailList) {

        Map<String, List<OrderItem>> map = getProductIdMap(orderDetailList);
        Map<String, Pair<String, Pair<Integer, Double>>> returnMap = new HashMap<>();
        for (String key : map.keySet()) {
            returnMap.put(key, getItemQuantityAndPrice(map.get(key)));
        }
        return returnMap;
    }


    public Map<String, List<OrderItem>> getProductIdMap(List<OrderDetail> orderDetailList) {
        Map<String, List<OrderItem>> returnMap = new HashMap<>();
        for (OrderDetail order : orderDetailList) {
            for (OrderItem item : order.getOrderItems()) {
                String key = generateKey(item);
                List<OrderItem> orderItems = returnMap.get(key);
                if (orderItems == null) {
                    orderItems = new ArrayList<>();
                }
                orderItems.add(item);
                returnMap.put(key, orderItems);
            }
        }
        return returnMap;
    }

    public Pair<String, Pair<Integer, Double>> getItemQuantityAndPrice(List<OrderItem> orderItemList) {
        String productName = null;
        int quantity = 0;
        double price = 0.00d;
        double value = 0.00d;
        for (OrderItem orderItem : orderItemList) {
            productName = orderItem.getProductName();
            price = orderItem.getPrice();
            quantity += orderItem.getQuantity();
        }
        value = price * quantity;
        return new Pair<>(productName, new Pair<>(quantity, value));

    }


    private static String generateKey(OrderItem item) {
        return item.getProductId() + "#" + item.getDimension();
    }

    public int getProductIdFromKey(String key) {
        String[] productIdProductDimensionArray = key.split("#");
        int productId = Integer.valueOf(productIdProductDimensionArray[0]);
        return productId;
    }

    public List<OrderDetail> getListOfOrderDetailOfCurrentBusinessDate(int previousEndOrderId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<OrderDetail> orderDetailArrayList = new ArrayList<>();
        String whereClause = DbHelper.CURRENT_BUSINESS_DATE + " = " + "'" + AppUtils.getCurrentBusinessDate() + "'" + " AND " + "orderStatus <> " + "'" + OrderStatus.CANCELLED.toString() + "'"  + " AND " + dbHelper.ORDER_ID + " > " + previousEndOrderId;
        Cursor cursor = db.query(DbHelper.ORDER_DETAIL_TABLE, null, whereClause, null, null, null, null);
        while (cursor.moveToNext()) {
            OrderDetail orderDetail = new OrderDetail();
            int orderId = cursor.getInt(cursor.getColumnIndex(DbHelper.ORDER_ID));
            orderDetail.setOrderItems(getOrdersItemListOfEachOrder(orderId));
            orderDetailArrayList.add(orderDetail);
        }
        return orderDetailArrayList;
    }

    private List<OrderItem> getOrdersItemListOfEachOrder(int orderId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<OrderItem> orderItemArrayList = new ArrayList<>();
        String whereClause = DbHelper.ORDER_DETAIL_ID + " = " + orderId;
        Cursor cursor = db.query(DbHelper.ORDER_ITEM_TABLE, null, whereClause, null, null, null, null);
        while (cursor.moveToNext()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cursor.getInt(cursor.getColumnIndex(DbHelper.PRODUCT_ID)));
            orderItem.setProductName(cursor.getString(cursor.getColumnIndex(DbHelper.PRODUCT_NAME)));
            orderItem.setQuantity(cursor.getInt(cursor.getColumnIndex(DbHelper.QUANTITY)));
            orderItem.setPrice(cursor.getDouble(cursor.getColumnIndex(DbHelper.PRICE)));
            orderItem.setStatus(cursor.getString(cursor.getColumnIndex(DbHelper.ORDER_ITEM_STATUS)));
            //orderItem.setDimension(cursor.getString(cursor.getColumnIndex(DbHelper)));
            orderItemArrayList.add(orderItem);
        }
        return orderItemArrayList;
    }

    public int getEndOrderIdOfLastDayClose() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {DbHelper.END_ORDER_ID};
        int endOrderId = 0;
        String whereClause = DbHelper.EVENT_DATE + " = '" + AppUtils.getPreviousBusinessDate() + "' AND " + DbHelper.EVENT_STATUS + " <> '" + OrderStatus.CANCELLED.toString() + "'";
        Cursor cursor = db.query(DbHelper.DAY_CLOSE_EVENT_TABLE, columns, whereClause, null, null, null, null);
        while (cursor.moveToNext()) {
            endOrderId = cursor.getInt(cursor.getColumnIndex(DbHelper.END_ORDER_ID));
        }
        return endOrderId;
    }

    public int getEndOrderIdOfCurrentDayClose() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {DbHelper.END_ORDER_ID};
        int endOrderId = 0;
        String whereClause = DbHelper.EVENT_DATE + " = '" + AppUtils.getCurrentBusinessDate() + "' AND " + DbHelper.EVENT_STATUS + " <> '" + OrderStatus.CANCELLED.toString() + "'";
        Cursor cursor = db.query(DbHelper.DAY_CLOSE_EVENT_TABLE, columns, whereClause, null, null, null, null);
        while (cursor.moveToNext()) {
            endOrderId = cursor.getInt(cursor.getColumnIndex(DbHelper.END_ORDER_ID));
        }
        return endOrderId;
    }

    public void updateEventStatusToCancelled() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String whereClause = DbHelper.EVENT_DATE + " = '" + AppUtils.getCurrentBusinessDate() + "'";
        contentValues.put(DbHelper.EVENT_STATUS, OrderStatus.CANCELLED.toString());
        db.update(DbHelper.DAY_CLOSE_EVENT_TABLE, contentValues, whereClause, null);

    }

    static class DbHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "orderdatabase.db";
        private static final int DATABASE_VERSION = 60;

        public static final String ORDER_DETAIL_TABLE = "orderDetail";
        public static final String ORDER_ITEM_TABLE = "orderItem";
        public static final String ORDER_REPRINT_DETAIL = "orderReprintDetail";
        public static final String ORDER_SETTLEMENT_TABLE = "orderSettlementDetail";
        public static final String COUNTER_TABLE = "counter_table";
        public static final String REPRINT_TABLE = "reprint_table";
        public static final String DAY_CLOSE_EVENT_TABLE = "day_close_event_table";
        public static final String ORDERS_SUMMARY_TABLE = "orders_summary";
        public static final String ORDER_ITEMS_SUMMARY_TABLE = "order_items_summary";
        public static final String CONNECTION_LOG_TABLE = "connection_log_table";

        /* OrderDetail table columns*/
        public static final String ORDER_ID = "orderId";
        public static final String GENERATED_ORDER_ID = "generatedOrderId";
        public static final String TOKEN = "token";
        public static final String EMPLOYEE_ID = "empId";
        public static final String CUSTOMER_NAME = "customerName";
        public static final String ORDER_STATUS = "orderStatus";
        public static final String SETTLEMENT_TYPE = "settlementType";
        public static final String UNIT_ID = "unitId";
        public static final String BILL_GENERATION_TIME = "billGenerationTime";
        public static final String BILL_CANCELLATION_TIME = "billCancellationTime";
        public static final String BILL_COMPLETION_TIME = "billCompletionTime";
        public static final String BILL_SETTLEMENT_TIME = "billSettlementTime";
        public static final String TAXABLE_AMOUNT = "taxableAmount";
        public static final String NET_PRICE_VAT_PERCENTAGE = "netPriceVatPercent";
        public static final String NET_PRICE_VAT_AMOUNT = "netPriceVatAmount";
        public static final String MRP_VAT_PERCENTAGE = "mrpVatPercent";
        public static final String MRP_VAT_AMOUNT = "mrpVatAmount";
        public static final String SERVICE_TAX_PERCENTAGE = "serviceTaxPercent";
        public static final String SERVICE_TAX_AMOUNT = "serviceTaxAmount";
        public static final String SBCESS_PERCENTAGE = "sbCessPercent";
        public static final String SBCESS_AMOUNT = "sbCessAmount";
        public static final String KKCESS_PERCENTAGE = "kkCessPercent";
        public static final String KKCESS_AMOUNT = "kkCessAmount";
        public static final String SURCHARGE_TAX_PERCENTAGE = "surchargeTaxPercent";
        public static final String SURCHARGE_TAX_AMOUNT = "surchargeTaxAmount";
        public static final String SETTLED_AMOUNT = "settledAmount";
        public static final String PRINT_COUNT = "printCount";
        public static final String CURRENT_BUSINESS_DATE = "current_business_date";

    /*ORDER ITEM TABLE COLUMNS */

        public static final String ORDER_ITEM_ID = "orderItemId";
        public static final String PRODUCT_ID = "productId";
        public static final String PRODUCT_NAME = "productName";
        public static final String QUANTITY = "quantity";
        public static final String PRICE = "price";
        public static final String WORK_ITEM_ID = "work_item_id";
        public static final String MONK_ID = "monk_id";
        public static final String TOTAL_AMOUNT = "totalAmount";
        public static final String PAID_AMOUNT = "paidAmount";
        public static final String BILL_TYPE = "billType";
        public static final String ORDER_ITEM_STATUS = "orderItemStatus";
        public static final String PRODUCT_TYPE = "productType";


        /*ORDER REPRINT DETAIL TABLE COLUMNS */
        public static final String ORDER_PRINT_ID = "orderPrintId";
        public static final String ORDER_DETAIL_ID = "orderDetailId";
        public static final String PRINT_REASON = "printReason";
        public static final String REPRINT_TIME = "reprintTime";


    /* ORDER SETTLEMENT DETAIL TABLE COLUMNS */

        public static final String SETTLEMENT_ID = "settlementId";
        public static final String PAYMENT_MODE_ID = "paymentModeId";
        public static final String AMOUNT_PAID = "amountPaid";
        public static final String ROUND_OFF_AMOUNT = "roundOffAmount";


        //counter table id and value.
        public static final String COUNTER_NAME = "counterName";
        public static final String COUNTER_VALUE = "counterValue";
        public static final String COUNTER_ID = "counterId";

        //reprint Table
        public static final String ORDER_INFO_OBJECT = "orderInfoObject";
        public static final String REPRINT_ID = "reprintId";

        //DayClose Table
        public static final String EVENT_ID = "event_id";
        public static final String EVENT_DATE = "enevt_date";
        public static final String EVENT_STATUS = "event_status";
        public static final String START_ORDER_ID = "start_order_id";
        public static final String END_ORDER_ID = "end_order_id";
        public static final String EVENT_TIME = "event_time";
        public static final String CREATED_BY = "created_by";
        public static final String CANCELLED_BY = "cancelled_by";

        //ORDERS_AT_DAYCLOSE_TABLE
        public static final String ORDER_ITEMS_INFO_ID = "order_items_info_id";
        public static final String TOTAL_ORDERS = "total_orders";
        public static final String TOTAL_ORDERS_AMOUNT = "total_orders_amount";
        public static final String ORDER_EVENT_ID = "order_event_id";

        //order_items_at_day_close
        public static final String ORDERS_INFO_ID = "order_info_id";
        public static final String ORDER_ITEMS_TOTAL_AMOUNT = "order_items_total_amount";
        public static final String PRODUCT_QUANTITY = "product_quantity";

        //pi connection log
        public static final String LOG_ID = "log_id";
        public static final String CURRENT_DATE = "current_date";
        public static final String DEVICE_NAME = "device_name";
        public static final String CONNECTION_TIME = "connection_time";
        public static final String DISCONNECTION_TIME = "disconnection_time";


        public static final String CREATE_ORDER_DETAIL_TABLE = "CREATE TABLE "
                + ORDER_DETAIL_TABLE + "(" + ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GENERATED_ORDER_ID + " INTEGER, " + TOKEN + " INTEGER, "
                + EMPLOYEE_ID + " INTEGER, " + CUSTOMER_NAME + " TEXT, "
                + ORDER_STATUS + " TEXT, " + SETTLEMENT_TYPE + " TEXT, " + UNIT_ID + " INTEGER, "
                + BILL_GENERATION_TIME + " TEXT, " + BILL_CANCELLATION_TIME + " TEXT, "
                + BILL_SETTLEMENT_TIME + " TEXT, " + BILL_COMPLETION_TIME + " TEXT, "
                + TOTAL_AMOUNT + " REAL, "
                + TAXABLE_AMOUNT + " REAL, " + NET_PRICE_VAT_PERCENTAGE + " REAL, "
                + NET_PRICE_VAT_AMOUNT + " REAL, " + MRP_VAT_PERCENTAGE + " REAL, "
                + MRP_VAT_AMOUNT + " REAL, " + SERVICE_TAX_PERCENTAGE + " REAL, "
                + SERVICE_TAX_AMOUNT + " REAL, " + SBCESS_PERCENTAGE + " REAL, "
                + SBCESS_AMOUNT + " REAL, " + KKCESS_PERCENTAGE + " REAL, "
                + KKCESS_AMOUNT + " REAL, " + SURCHARGE_TAX_PERCENTAGE + " REAL, "
                + SURCHARGE_TAX_AMOUNT + " REAL, " + ROUND_OFF_AMOUNT + " REAL, "
                + SETTLED_AMOUNT + " REAL, " + PRINT_COUNT + " INTEGER, "
                + CURRENT_BUSINESS_DATE + " TEXT "
                + ")";


        public static final String CREATE_ORDER_ITEM_TABLE = "CREATE TABLE "
                + ORDER_ITEM_TABLE + "(" + ORDER_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_DETAIL_ID + " INTEGER, " + PRODUCT_ID + " INTEGER, "
                + PRODUCT_NAME + " TEXT, " + PRODUCT_TYPE + " INTEGER, "
                + QUANTITY + " INTEGER, "
                + ORDER_ITEM_STATUS + " TEXT, "
                + PRICE + " REAL, " + TOTAL_AMOUNT + " REAL, "
                + WORK_ITEM_ID + " TEXT, "
                + MONK_ID + " TEXT, "
                + PAID_AMOUNT + " REAL, " + BILL_TYPE + " TEXT, "
                + "FOREIGN KEY(" + ORDER_DETAIL_ID + ") REFERENCES "
                + ORDER_DETAIL_TABLE + "(orderId) "
                + ")";

        public static final String CREATE_RE_PRINT_TABLE = "CREATE TABLE "
                + ORDER_REPRINT_DETAIL + "(" + ORDER_PRINT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_DETAIL_ID + " INTEGER, " + PRINT_REASON + " TEXT, "
                + REPRINT_TIME + " NUMERIC, "
                + "FOREIGN KEY(" + ORDER_DETAIL_ID + ") REFERENCES "
                + ORDER_DETAIL_TABLE + "(orderId) "
                + ")";

        public static final String CREATE_ORDER_SETTLEMENT_TABLE = "CREATE TABLE "
                + ORDER_SETTLEMENT_TABLE + "(" + SETTLEMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_DETAIL_ID + " INTEGER, " + PAYMENT_MODE_ID
                + " INTEGER, " + AMOUNT_PAID + " REAL, "
                + "FOREIGN KEY(" + ORDER_DETAIL_ID + ") REFERENCES "
                + ORDER_DETAIL_TABLE + "(orderId) "
                + ")";

        public static final String CREATE_COUNTER_TABLE = "CREATE TABLE "
                + COUNTER_TABLE + "(" + COUNTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COUNTER_NAME + " TEXT, "
                + COUNTER_VALUE + " INTEGER " + ")";

        public static final String CREATE_REPRINT_TABLE = "CREATE TABLE "
                + REPRINT_TABLE + "(" + REPRINT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_DETAIL_ID + " INTEGER, "
                + ORDER_INFO_OBJECT + " TEXT " + ")";

        public static final String CREATE_DAY_CLOSE_EVENT_TABLE = "CREATE TABLE "
                + DAY_CLOSE_EVENT_TABLE + "(" + EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EVENT_DATE + " TEXT, " + EVENT_STATUS + " TEXT, "
                + START_ORDER_ID + " INTEGER, " + END_ORDER_ID + " INTEGER, "
                + EVENT_TIME + " TEXT, " + CREATED_BY + " TEXT, "
                + CANCELLED_BY + " TEXT " + ")";


        public static final String CREATE_ORDERS_SUMMARY_TABLE = "CREATE TABLE "
                + ORDERS_SUMMARY_TABLE + "(" + ORDERS_INFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_EVENT_ID + " INTEGER, "
                + TOTAL_ORDERS + " INTEGER, "
                + TOTAL_ORDERS_AMOUNT + " REAL, "
                + "FOREIGN KEY(" + ORDER_EVENT_ID + ") REFERENCES "
                + DAY_CLOSE_EVENT_TABLE + "(event_id) "
                + ")";

        public static final String CREATE_ORDER_ITEMS_SUMMARY_TABLE = "CREATE TABLE "
                + ORDER_ITEMS_SUMMARY_TABLE + "(" + ORDER_ITEMS_INFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_EVENT_ID + " INTEGER, "
                + PRODUCT_ID + " INTEGER, " + PRODUCT_NAME + " TEXT, "
                + PRODUCT_QUANTITY + " INTEGER, "
                + ORDER_ITEMS_TOTAL_AMOUNT + " REAL, "
                + "FOREIGN KEY(" + ORDER_EVENT_ID + ") REFERENCES "
                + DAY_CLOSE_EVENT_TABLE + "(event_id) "
                + ")";


        public static final String CREATE_CONNECTION_LOG_TABLE = "CREATE TABLE "
                + CONNECTION_LOG_TABLE + "(" + LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CURRENT_DATE + " TEXT, " + DEVICE_NAME
                + " TEXT, " + CONNECTION_TIME + " TEXT, "
                + DISCONNECTION_TIME + " TEXT "
                + ")";

        private DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            if (!db.isReadOnly()) {
                db.execSQL("PRAGMA foreign_keys=ON;");
            }
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_ORDER_DETAIL_TABLE);
            db.execSQL(CREATE_ORDER_ITEM_TABLE);
            db.execSQL(CREATE_ORDER_SETTLEMENT_TABLE);
            db.execSQL(CREATE_RE_PRINT_TABLE);
            db.execSQL(CREATE_COUNTER_TABLE);
            db.execSQL(CREATE_REPRINT_TABLE);
            db.execSQL(CREATE_DAY_CLOSE_EVENT_TABLE);
            db.execSQL(CREATE_ORDERS_SUMMARY_TABLE);
            db.execSQL(CREATE_ORDER_ITEMS_SUMMARY_TABLE);
            db.execSQL(CREATE_CONNECTION_LOG_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + ORDER_DETAIL_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + ORDER_ITEM_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + ORDER_REPRINT_DETAIL);
            db.execSQL("DROP TABLE IF EXISTS " + ORDER_SETTLEMENT_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + COUNTER_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + REPRINT_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + DAY_CLOSE_EVENT_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + ORDERS_SUMMARY_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + ORDER_ITEMS_SUMMARY_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + CONNECTION_LOG_TABLE);
            onCreate(db);
        }

    }


}
