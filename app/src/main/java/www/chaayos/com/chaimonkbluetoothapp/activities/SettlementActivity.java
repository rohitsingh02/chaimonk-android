package www.chaayos.com.chaimonkbluetoothapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.chaayos.com.chaimonkbluetoothapp.GlobalVariables;
import www.chaayos.com.chaimonkbluetoothapp.PreferenceManager;
import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.db.DatabaseAdapter;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.CounterEnum;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Order;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderInfo;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderItem;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderStatus;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.PaymentMode;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Settlement;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.SettlementType;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.TransactionDetail;
import www.chaayos.com.chaimonkbluetoothapp.utils.AppUtils;
import www.chaayos.com.chaimonkbluetoothapp.printer.PrintFeature;
import www.chaayos.com.chaimonkbluetoothapp.utils.UserUtils;


public class SettlementActivity extends AppCompatActivity implements View.OnClickListener{


    private Button cashButton;
    private Button submitButton;
    private Button cancelButton;
    private TextView amountToPay;
    private TextView receivedAmount;
    private EditText cashEditText;
    private PrintFeature mPrintFeature;
    private ProgressDialog mPrintDialog;
    private DatabaseAdapter databaseAdapter;
    private GlobalVariables globalVariables;
    private EditText customerNameEt;
    private List<OrderItem> orderItemArrayList = new ArrayList<>();
    private int token = 1;
    private int generatedOrderId = 1000;
    private int workItemId = 100;
    private OrderInfo orderInfo;
    private Order order;
    private List<Settlement> settlementsList = new ArrayList<>();
    private PaymentMode paymentMode;
    private Settlement settlement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);
        globalVariables = (GlobalVariables) getApplicationContext();
        databaseAdapter = new DatabaseAdapter(SettlementActivity.this);
        order = new Order();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.7));
        order = new Order();
        orderInfo = new OrderInfo();
        orderItemArrayList = globalVariables.getLatestOrderItemArrayList();
        mPrintFeature = globalVariables.getmPrintFeature();
        if (mPrintFeature == null) {
            mPrintFeature = new PrintFeature();
            mPrintFeature.setupPrinter((ChaiMonkActivity)globalVariables.getContext(), (ChaiMonkActivity)globalVariables.getContext());
        }
        paymentMode = new PaymentMode();
        settlement = new Settlement();
        amountToPay = (TextView) findViewById(R.id.amountToPay);
        receivedAmount = (TextView) findViewById(R.id.receivedAmount);
        submitButton = (Button) findViewById(R.id.submitButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cashEditText = (EditText) findViewById(R.id.cashEditText);
        customerNameEt = (EditText) findViewById(R.id.customerNameEt);
        mPrintDialog = new ProgressDialog(SettlementActivity.this);
        cashButton = (Button) findViewById(R.id.cashGoButton);
        cancelButton.setOnClickListener(this);
        cashButton.setOnClickListener(this);
        createOrderObject();
        amountToPay.setText("Rs " + String.valueOf(globalVariables.getLatestTransactionDetail().getTotalAmount()));
        receivedAmount.setText("Rs " + String.valueOf(globalVariables.getLatestTransactionDetail().getTotalAmount()));

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cashEditText.getText().length() != 0) {

                    if (databaseAdapter.getDataFromCounterTable().size() == 0) {
                        databaseAdapter.insertDataToCounterTable(CounterEnum.G_ORDER_ID.toString(), generatedOrderId);
                        databaseAdapter.insertDataToCounterTable(CounterEnum.TOKEN_ID.toString(), token);
                        databaseAdapter.insertDataToCounterTable(CounterEnum.WORK_ITEM_ID.toString(), workItemId);
                        orderInfo.setToken(token);
                        order.setGenerateOrderId(String.valueOf(generatedOrderId));
                    } else {
                        databaseAdapter.updateDataCounterTable();
                        Map<String, Integer> counternameValueMap = databaseAdapter.getDataFromCounterTable();
                        generatedOrderId = counternameValueMap.get(CounterEnum.G_ORDER_ID.toString()).intValue();
                        token = counternameValueMap.get(CounterEnum.TOKEN_ID.toString()).intValue();
                        workItemId = counternameValueMap.get(CounterEnum.WORK_ITEM_ID.toString()).intValue();
                        orderInfo.setToken(token);
                        order.setGenerateOrderId(String.valueOf(generatedOrderId));
                    }
                    TransactionDetail transactionDetail = globalVariables.getLatestTransactionDetail();
                    int orderDetailId = (int) databaseAdapter.insertDataToOrderDetailTable(generatedOrderId, token, PreferenceManager.getUserDetail(SettlementActivity.this).getUserId(), customerNameEt.getText().toString(),
                            OrderStatus.INITIATED.value(), AppUtils.DEBIT, PreferenceManager.getUserDetail(SettlementActivity.this).getUnitId(), AppUtils.getCurrentTimeStamp(),  transactionDetail, AppUtils.getCurrentBusinessDate());

                    for (OrderItem orderItem : orderItemArrayList) {
                        databaseAdapter.insertDataToOrderItemTable(orderItem.getProductId(),
                                orderDetailId, orderItem.getProductName(),orderItem.getProductType(),
                                orderItem.getQuantity(), orderItem.getOrderItemStatus(),
                                orderItem.getPrice().doubleValue(),
                                orderItem.getTotalAmount().doubleValue(),
                                workItemId,
                                transactionDetail.getTotalAmount().doubleValue(),
                                orderItem.getBillType().value()
                        );
                    }
                    databaseAdapter.insertDataToOrderSettlementTable(orderDetailId, AppUtils.DEBIT, transactionDetail.getTotalAmount().doubleValue());
                    createOrderInfoObject();
                    generatePrint();
                    databaseAdapter.insertDataToReprintTable(orderDetailId, new Gson().toJson(orderInfo));
                    Intent intent = new Intent(SettlementActivity.this, ChaiMonkActivity.class);
                    startActivity(intent);
                } else {
                    UserUtils.showAlertMessage(SettlementActivity.this, "Please Enter an Amount to Proceed");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelButton:
                finish();
                break;

            case R.id.cashGoButton:

                cashEditText.setText(String.valueOf(globalVariables.getLatestTransactionDetail().getTotalAmount()));
                if (cashEditText.getText().length() == 0) {
                    UserUtils.showAlertMessage(this, "Please Enter an Amount to Proceed");
                } else {
                    int receiveAmount = Integer.valueOf(cashEditText.getText().toString()) - globalVariables.getLatestTransactionDetail().getTotalAmount().intValue();
                    receivedAmount.setText("Rs " + String.valueOf(receiveAmount));
                    this.submitButton.setEnabled(true);
                    paymentMode.setDescription("Cash");
                    settlement.setModeDetail(paymentMode);
                    settlementsList.add(settlement);
                    order.setSettlements(settlementsList);
                }
                break;

        }
    }

    private void generatePrint() {

        if (mPrintFeature.printBill(null, orderInfo, globalVariables.getUnit(), false)) {
           // ((ChaiMonkActivity)globalVariables.getContext()).showPrintDialog();
        } else {
           // UserUtils.showAlertMessage(SettlementActivity.this, "No printer attached. Please give IP manually.");
        }

    }

    public void createOrderObject() {
        order.setUnitId(Integer.valueOf(PreferenceManager.getCredentials(SettlementActivity.this).getUnitId()));
        order.setUnitName(PreferenceManager.getCredentials(SettlementActivity.this).getUnitName());
        order.setEmployeeId(Integer.valueOf(PreferenceManager.getCredentials(SettlementActivity.this).getUserId()));
        order.setTotalAmount(globalVariables.getLatestTransactionDetail().getTotalAmount());
        order.setOrders(orderItemArrayList);
        order.setTransactionDetail(globalVariables.getLatestTransactionDetail());
        order.setSettlementType(SettlementType.DEBIT);
        order.setBillCreationTime(AppUtils.getCurrentTimeStamp());
    }

    public void createOrderInfoObject() {
        orderInfo.setOrder(order);
    }


}
