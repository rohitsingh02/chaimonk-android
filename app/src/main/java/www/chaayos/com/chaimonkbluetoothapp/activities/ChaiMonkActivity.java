package www.chaayos.com.chaimonkbluetoothapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.epson.epos2.discovery.DeviceInfo;
import com.google.gson.Gson;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.chaayos.com.chaimonkbluetoothapp.GlobalVariables;
import www.chaayos.com.chaimonkbluetoothapp.PreferenceManager;
import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.ResponseCallback;
import www.chaayos.com.chaimonkbluetoothapp.adapters.ChaiMonkCustomAdapter;
import www.chaayos.com.chaimonkbluetoothapp.bluetooth.BluetoothChatService;
import www.chaayos.com.chaimonkbluetoothapp.bluetooth.Constants;
import www.chaayos.com.chaimonkbluetoothapp.common.Cherror;
import www.chaayos.com.chaimonkbluetoothapp.common.CustomDialog;
import www.chaayos.com.chaimonkbluetoothapp.common.DayCloseCustomDialog;
import www.chaayos.com.chaimonkbluetoothapp.common.Monk2CustomDialog;
import www.chaayos.com.chaimonkbluetoothapp.data.model.OrderDetail;
import www.chaayos.com.chaimonkbluetoothapp.data.model.OrderItem;
import www.chaayos.com.chaimonkbluetoothapp.data.provider.OrderDataProvider;
import www.chaayos.com.chaimonkbluetoothapp.db.DatabaseAdapter;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.ChaiMonk;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.UserSessionDetail;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.ChaiMonkEnum;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.ListData;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderItemsSummary;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderStatus;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.PiCommunication;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Product;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.TaskState;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.TransactionMetadata;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Unit;
import www.chaayos.com.chaimonkbluetoothapp.fragments.ChaiMonkActivityJobQueueFragment;
import www.chaayos.com.chaimonkbluetoothapp.management.service.ChaiMonkManager;
import www.chaayos.com.chaimonkbluetoothapp.management.service.OrderManagementService;
import www.chaayos.com.chaimonkbluetoothapp.network.LoginController;
import www.chaayos.com.chaimonkbluetoothapp.network.RetrofitHelper;
import www.chaayos.com.chaimonkbluetoothapp.printer.EpsonHelper;
import www.chaayos.com.chaimonkbluetoothapp.printer.PrintFeature;
import www.chaayos.com.chaimonkbluetoothapp.utils.AppUtils;
import www.chaayos.com.chaimonkbluetoothapp.utils.UserUtils;


public class ChaiMonkActivity extends AppCompatActivity implements ResponseCallback<UserSessionDetail, Cherror>, EpsonHelper.PrintEventsCallback {
    private TextView idTextView;
    private TextView nameTextView;
    private TextView placeTextView;
    private ProgressDialog progressDialog;
    private int unitId;
    private Unit unit;
    private List<ListData> listListData;
    private UserSessionDetail userSessionDetail;
    private GlobalVariables globalVariables;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothChatService mBluetoothChatService1;
    private BluetoothChatService mBluetoothChatService2;
    private Map<UUID, BluetoothChatService> bluetoothChatServiceList = new HashMap<>();
    private String device1 = null;
    private String device2 = null;
    private Map<ChaiMonkEnum, UUID> chaiMonkMap = AppUtils.initMonkMap();
    private List<OrderDetail> orderDetailArrayList = new ArrayList<>();
    private DatabaseAdapter databaseAdapter;
    private RecyclerView recyclerView;
    private ChaiMonkCustomAdapter chaiMonkCustomAdapter;
    private List<ChaiMonk> chaiMonkViewContentList = new ArrayList<>();
    private OrderDataProvider orderDataProvider = new OrderDataProvider();
    private ChaiMonkManager monkManager;
    private PrintFeature mPrintFeature;
    private ProgressDialog mLoginDialog;
    private ArrayList<OrderItemsSummary> orderItemsSummaryList;
    private int totalOrdersCount = 0;
    private double totalAmountOfAllOrders = 0.0;
    private String dayClosedBy = null;
    private double subTotalAmount = 0.0;
    private OrderManagementService orderManagementService;
    private String jwtToken;
    private static int counter = 0;
    public OrderManagementService getOrderManagementService(){
        return this.orderManagementService;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chai_monk);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        idTextView = (TextView) findViewById(R.id.idTextField);
        nameTextView = (TextView) findViewById(R.id.nameTextField);
        placeTextView = (TextView) findViewById(R.id.placeTextField);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        orderDataProvider.setData();
        globalVariables = ((GlobalVariables) getApplicationContext());
        globalVariables.setCurrentBusinessDate(AppUtils.getCurrentBusinessDate());
        unitId = PreferenceManager.getUserDetail(ChaiMonkActivity.this).getUnitId();
        jwtToken = PreferenceManager.getJwtToken(ChaiMonkActivity.this);
        setUserDetails();
        getUnitData();
        getTransactionMetadata();
        recyclerView = (RecyclerView) findViewById(R.id.chaiMonkRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chaiMonkCustomAdapter = new ChaiMonkCustomAdapter(chaiMonkViewContentList, bluetoothChatServiceList);
        recyclerView.setAdapter(chaiMonkCustomAdapter);
        databaseAdapter = new DatabaseAdapter(this);
        mLoginDialog = new ProgressDialog(this);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        globalVariables.setContext(ChaiMonkActivity.this);

        if(databaseAdapter.getEndOrderIdOfLastDayClose() != 0){
            globalVariables.setEndOrderId(databaseAdapter.getEndOrderIdOfLastDayClose());
            PreferenceManager.saveEndOrderIdAtDayClose(ChaiMonkActivity.this,databaseAdapter.getEndOrderIdOfLastDayClose());
        }else {
            PreferenceManager.saveEndOrderIdAtDayClose(ChaiMonkActivity.this,databaseAdapter.getEndOrderIdOfCurrentDayClose());
        }


        if (!bluetoothAdapter.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(this, "Bluetooth Turned On", Toast.LENGTH_LONG).show();
            Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivityForResult(getVisible, 0);
        }

    }

    public void setUserDetails() {

        idTextView.setText("Id: " + PreferenceManager.getCredentials(ChaiMonkActivity.this).getUserId());
        nameTextView.setText("Name: " + PreferenceManager.getUserDetail(ChaiMonkActivity.this).getUserName());
        placeTextView.setText("Place: " + PreferenceManager.getCredentials(ChaiMonkActivity.this).getUnitName());
    }

    public void getUnitData() {


        RetrofitHelper.getMetaDataService().getUnitDetails(jwtToken, unitId).enqueue(new Callback<Unit>() {
            @Override
            public void onResponse(Call<Unit> call, Response<Unit> response) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if (response == null) {
                    finish();
                    return;
                }
                unit = response.body();
                if(unit!=null){
                    initiate(unit);
                }else{
                    unit = PreferenceManager.getUnitObject(ChaiMonkActivity.this);
                    if (unit == null) {
                        finish();
                    }else{
                        initiate(unit);
                    }
                }
            }

            @Override
            public void onFailure(Call<Unit> call, Throwable t) {

                if(progressDialog.isShowing())
                progressDialog.dismiss();
                if (t instanceof Exception) {
//                    Toast.makeText(getApplicationContext(), "Unit timeout", Toast.LENGTH_LONG).show();
                    unit = PreferenceManager.getUnitObject(ChaiMonkActivity.this);
                    if (unit == null) {
                        finish();
                    }else{
                        initiate(unit);
                    }
                }
            }
        });
    }

    private void initiate(Unit unitObject){
        PreferenceManager.saveUnitObject(ChaiMonkActivity.this,new Gson().toJson(unitObject));
        globalVariables.setUnit(unit);
        globalVariables.setChaiMonkManager(ChaiMonkManager.getInstance());

        Map<Integer, List<Integer>> categoryWiseProducts = createCategoryProductIdMap(unit.getProducts());
        globalVariables.setOrderManagementService(OrderManagementService.getInstance(categoryWiseProducts,jwtToken));
        orderManagementService = globalVariables.getOrderManagementService();
        monkManager = globalVariables.getChaiMonkManager();
        chaiMonkViewContentList = monkManager.getAllMonks();
        setGlobalVariables(globalVariables);
        notifyFragmentAboutStatusOfGlobalVariables();
        if (globalVariables.getmPrintFeature() != null) {
            mPrintFeature = globalVariables.getmPrintFeature();
        } else {
            mPrintFeature = new PrintFeature();
            mPrintFeature.setupPrinter(ChaiMonkActivity.this, ChaiMonkActivity.this);
        }
    }

    private Map<Integer, List<Integer>> createCategoryProductIdMap(List<Product> products) {
        Map<Integer, List<Integer>> categoryWiseProducts = new HashMap<>();
        for (Product product : products) {
            Integer category = product.getType();
            List<Integer> productsOfCategory = categoryWiseProducts.get(category);
            if (productsOfCategory == null) {
                productsOfCategory = new ArrayList<>();
            }
            productsOfCategory.add(product.getId());
            categoryWiseProducts.put(category, productsOfCategory);
        }
        return categoryWiseProducts;
    }

    public void setGlobalVariables(GlobalVariables globalVariables) {
        this.globalVariables = globalVariables;
    }

    public void syncOrder(OrderDetail orderDetail){
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);
        new JobSyncExecutor().execute(orderDetailList);
    }

    public void saveLastSyncedOrder(Integer lastSyncedOrder){
        if(lastSyncedOrder!=null){
            PreferenceManager.saveSyncedOrderId(ChaiMonkActivity.this,lastSyncedOrder);
        }
    }

    public void getTransactionMetadata() {
        RetrofitHelper.getMetaDataService().getMetaDataDetails(jwtToken,unitId).enqueue(new Callback<TransactionMetadata>() {
            @Override
            public void onResponse(Call<TransactionMetadata> call, Response<TransactionMetadata> response) {
                if (response == null) {
                    finish();
                    return;
                } else {
                    String transactionMetadataObject = new Gson().toJson(response.body());
                    PreferenceManager.saveTransactionMetadata(ChaiMonkActivity.this,transactionMetadataObject);
                    listListData = response.body().getCategories();
                    globalVariables.setListDatas(listListData);
                }

            }

            @Override
            public void onFailure(Call<TransactionMetadata> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    if (PreferenceManager.getTransactionMetadataObject(ChaiMonkActivity.this) != null) {
                        TransactionMetadata transactionMetadata = new Gson().fromJson(PreferenceManager.getTransactionMetadataObject(ChaiMonkActivity.this), TransactionMetadata.class);
                        listListData = transactionMetadata.getCategories();
                        globalVariables.setListDatas(listListData);
                    }
                }
            }
        });
    }

    private void goToPrinterSettingsScreen() {
        Intent intent = new Intent(getApplicationContext(), PrintersSettingActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        MenuItem item = menu.add("PrintFeature Setting");
        item.setIcon(R.mipmap.ic_printer);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                goToPrinterSettingsScreen();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    public void sendCommandToMonk(UUID chaiMonkUUID, Constants.ChaiMonkCommand command) {
        ChaiMonk monk = monkManager.getChaiMonk(chaiMonkUUID);
        if (monk != null) {
            monk.reset();
            monk.setCommand(command);
            monk.setCurrentStatus(TaskState.WAITING);
            bluetoothChatServiceList.get(monk.getUuid()).write(monk.toString().getBytes());
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.chai_monk1:
                CustomDialog customDialog = new CustomDialog();
                customDialog.showDialog(ChaiMonkActivity.this);
                break;
            case R.id.chai_monk2:
                Monk2CustomDialog customDialog1 = new Monk2CustomDialog();
                customDialog1.showDialog(ChaiMonkActivity.this);

                break;
            case R.id.action_settings:
                Intent intent = new Intent(ChaiMonkActivity.this, AdminPanelActivity.class);
                startActivity(intent);
                break;
            case R.id.day_close:
                DayCloseCustomDialog dayCloseCustomDialog = new DayCloseCustomDialog();
                dayCloseCustomDialog.showDialog(ChaiMonkActivity.this);

                break;
            case R.id.test_print:

                if (globalVariables.getmPrintFeature() != null) {
                    mPrintFeature = globalVariables.getmPrintFeature();
                    generateTestPrint();
                } else {
                    mPrintFeature = new PrintFeature();
                    mPrintFeature.setupPrinter(this, this);
                    generateTestPrint();
                }

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void generateTestPrint() {
        if (mPrintFeature.testPrint(null)) {
        } else {
            UserUtils.showAlertMessage(ChaiMonkActivity.this, "No printer attached. Please give IP manually. ");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            List<ChaiMonk> chaiMonks = new ArrayList<>();
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            Toast.makeText(getApplicationContext(), "Connected to " + msg.arg2, Toast.LENGTH_LONG).show();
                            databaseAdapter.insertDataToConnectionLogTable(AppUtils.getCurrentBusinessDate(),String.valueOf(msg.arg2),AppUtils.getCurrentTimeStamp(),null);
                            if (msg.arg2 == 0) {
                                chaiMonks.add(new ChaiMonk(chaiMonkMap.get(ChaiMonkEnum.CHAI_MONK1)
                                        , ChaiMonkEnum.CHAI_MONK1.toString(), TaskState.IDLE));
                                bluetoothChatServiceList.put(chaiMonkMap.get(ChaiMonkEnum.CHAI_MONK1), mBluetoothChatService1);

                            } else if (msg.arg2 == 1) {
                                chaiMonks.add(new ChaiMonk(AppUtils.initMonkMap().get(ChaiMonkEnum.CHAI_MONK2)
                                        , ChaiMonkEnum.CHAI_MONK2.toString(), TaskState.IDLE));
                                bluetoothChatServiceList.put(chaiMonkMap.get(ChaiMonkEnum.CHAI_MONK2), mBluetoothChatService2);
                            }
                            refreshAdapterView(chaiMonks);
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            if (device1 == null) {} else if (device2 == null) {}
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                            break;
                        case BluetoothChatService.STATE_NONE:
                            Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_LONG).show();
                            if (device1 == null) {
                                databaseAdapter.insertDataToConnectionLogTable(AppUtils.getCurrentBusinessDate(),ChaiMonkEnum.CHAI_MONK1.value(),null,AppUtils.getCurrentTimeStamp());
                                // monk1StatusTv.setText("Not Connected");
                            } else if (device2 == null) {
                                databaseAdapter.insertDataToConnectionLogTable(AppUtils.getCurrentBusinessDate(),ChaiMonkEnum.CHAI_MONK2.value(),null,AppUtils.getCurrentTimeStamp());
                                // monk2StatusTv.setText("Not Connected");
                            }
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    String writeMessage = new String(writeBuf);
                    Toast.makeText(getApplicationContext(), writeMessage + "Message writed ......", Toast.LENGTH_SHORT).show();
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    handleCommunication(readBuf,msg);
                    break;

                case Constants.MESSAGE_DEVICE_NAME:
                    Toast.makeText(getApplicationContext(), "Connected to ", Toast.LENGTH_SHORT).show();
                    if (msg.getData().getString(AppUtils.DEVICE_NAME).equals(ChaiMonkEnum.CHAI_MONK1)) {
                        Toast.makeText(getApplicationContext(), device1, Toast.LENGTH_LONG).show();
                    }
                    if (msg.getData().getString(AppUtils.DEVICE_NAME).equals(ChaiMonkEnum.CHAI_MONK2)) {
                        Toast.makeText(getApplicationContext(), device2, Toast.LENGTH_LONG).show();
                    }
                    break;

                case Constants.MESSAGE_TOAST:
                    if (msg.getData().getInt("TOAST") == 0) {
                        monkManager.removeMonk(chaiMonkMap.get(ChaiMonkEnum.CHAI_MONK1));
                      Toast.makeText(ChaiMonkActivity.this,"MONK 1 is disconnected. Please check!",Toast.LENGTH_LONG).show();
                    } else if (msg.getData().getInt("TOAST") == 1) {
                        Toast.makeText(ChaiMonkActivity.this,"MONK 2 is disconnected. Please check!",Toast.LENGTH_LONG).show();
                        monkManager.removeMonk(chaiMonkMap.get(ChaiMonkEnum.CHAI_MONK2));
                    }
                    else if (msg.getData().getInt("TOAST") == 2) {
                        monkManager.removeMonk(chaiMonkMap.get(ChaiMonkEnum.CHAI_MONK1));
                        Toast.makeText(ChaiMonkActivity.this,"Unable to connect MONK 1. Try again!",Toast.LENGTH_LONG).show();
                    } else if (msg.getData().getInt("TOAST") == 3) {
                        Toast.makeText(ChaiMonkActivity.this,"Unable to connect MONK 2. Try again!",Toast.LENGTH_LONG).show();
                        monkManager.removeMonk(chaiMonkMap.get(ChaiMonkEnum.CHAI_MONK2));
                    }
                    refreshAdapterView(monkManager.getAllMonks());
                    break;
                case Constants.MESSAGE_COMPLETED:
                    break;
            }
        }
    };

    private void handleCommunication(byte[] readBuf, Message msg){
        String readMessage = new String(readBuf, 0, msg.arg1);
        String[] piStatus = readMessage.split("#");
        PiCommunication piCommunication = new PiCommunication(piStatus[0], piStatus[1]);
        if (readMessage.length() > 0) {
            Toast.makeText(getApplicationContext(), readMessage + " Message Received...", Toast.LENGTH_SHORT).show();
            UUID uuid = UUID.fromString(piCommunication.getUuid());
            ChaiMonk chaiMonk = monkManager.getChaiMonk(uuid);
            if (chaiMonk != null) {
                chaiMonk.setCurrentStatus(piCommunication.getMonkStatus());
                switch (piCommunication.getMonkStatus()) {
                    case WAITING:
                        chaiMonk.setCurrentStatus(TaskState.STARTED);
                        break;

                    case COMPLETED:
                        settleOrderItems(chaiMonk.getOrderIdList(), chaiMonk.getWorkItemId());
                        counter = counter + chaiMonk.getOrderIdList().size();
                        if(counter > 10){
                            UserUtils.showAlertMessage(ChaiMonkActivity.this,"Please Check Milk and Water Level");
                            counter = 0;
                        }
                        break;

                    case PAUSE:
                        chaiMonk.setCurrentStatus(TaskState.RESUME);
                        break;

                    case ERROR:
                        AlertDialog dialog = buildAlert("MONK has encountered an error. Click OK to restart").create();
                        dialog.show();
                        chaiMonk.setCurrentStatus(TaskState.WAITING);
                        break;

                    case RESUME:
                        chaiMonk.setCurrentStatus(TaskState.STARTED);
                        break;

                    case CANCELLED:
                        chaiMonk.setCurrentStatus(TaskState.WAITING);
                        break;
                }
                monkManager.setChaiMonk(uuid, chaiMonk);
            }
            refreshAdapterView(monkManager.getAllMonks());
        }
    }

    private AlertDialog.Builder buildAlert(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder;
    }

    private void settleOrderItems(List<Integer> orderIdList, Integer workItem) {
        for (OrderDetail orderDetail : orderDetailArrayList) {
            if (orderIdList.contains(orderDetail.getOrderId())) {
                for (OrderItem item : orderDetail.getOrderItems()) {
                    if (item.getWorkItemId() == workItem) {
                        item.setStatus(OrderStatus.SETTLED.toString());
                        databaseAdapter.updateOrderItemStatus(item.getOrderItemId(), OrderStatus.SETTLED.toString());
                    }
                }
            }
        }
        notifyFragmentAboutOrderDetailChange(orderDetailArrayList);
    }

    public void refreshAdapterView(List<ChaiMonk> chaiMonks) {
        monkManager.setChaiMonks(chaiMonks);
        chaiMonkViewContentList = monkManager.getAllMonks();
        if( orderManagementService != null){
            orderManagementService.assignOrders(orderDetailArrayList, monkManager, databaseAdapter);
        }
        chaiMonkCustomAdapter = new ChaiMonkCustomAdapter(chaiMonkViewContentList, bluetoothChatServiceList);
        recyclerView.setAdapter(chaiMonkCustomAdapter);
        notifyFragmentAboutOrderDetailChange(databaseAdapter.getJobQueueDetails(globalVariables.getEndOrderId()));
    }

    @Override
    public void onResume() {
        super.onResume();

        orderDetailArrayList = databaseAdapter.getJobQueueDetails(globalVariables.getEndOrderId());
        monkManager = globalVariables.getChaiMonkManager();
        assignNewOrders();

    }

    public void connectToMonk(String monkName) {
        if (monkName.equalsIgnoreCase(ChaiMonkEnum.CHAI_MONK1.toString())) {
            mBluetoothChatService1 = new BluetoothChatService(this, mHandler);
            connectMonk(ChaiMonkEnum.CHAI_MONK1, mBluetoothChatService1);
        } else if (monkName.equalsIgnoreCase(ChaiMonkEnum.CHAI_MONK2.toString())) {
            mBluetoothChatService2 = new BluetoothChatService(getApplicationContext(), mHandler);
            connectMonk(ChaiMonkEnum.CHAI_MONK2,mBluetoothChatService2);
        }
    }

    private void connectMonk(ChaiMonkEnum chaiMonkEnum, BluetoothChatService mBluetoothChatService){
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mBluetoothDevice = device;
                if (mBluetoothDevice.getName().contains(chaiMonkEnum.toString())) {
                    Toast.makeText(this, device.getName() + " found in paired devices", Toast.LENGTH_LONG).show();
                    UUID uuid = chaiMonkMap.get(ChaiMonkEnum.fromValue(mBluetoothDevice.getName()));
                    mBluetoothChatService.connect(mBluetoothDevice, uuid);
                }
            }
        }
    }

    public void assignNewOrders() {
        if (monkManager != null) {
            chaiMonkViewContentList = monkManager.getAllMonks();
            if (chaiMonkViewContentList != null) {
                refreshAdapterView(chaiMonkViewContentList);
            }
        }
    }


    public void notifyFragmentAboutOrderDetailChange(List<OrderDetail> orderDetailList) {

        ChaiMonkActivityJobQueueFragment fragment = (ChaiMonkActivityJobQueueFragment) getSupportFragmentManager().findFragmentById(R.id.orderJobQueueFragment);
        fragment.onBeingNotifiedFromChaiMonkActivity(orderDetailList);
    }


    public void removeCancelledOrders(ChaiMonk chaiMonk) {
        List<Integer> orderIds = chaiMonk.getOrderIdList();
        int quantity = chaiMonk.getQuantity() != null ? chaiMonk.getQuantity() : 0;
        for (OrderDetail order : orderDetailArrayList) {
            if (orderIds.contains(order.getOrderId()) && order.getOrderStatus().equals(OrderStatus.CANCELLED)) {
                for (OrderItem orderItem : order.getOrderItems()) {
                    if (orderItem.getWorkItemId() == chaiMonk.getWorkItemId()) {
                        quantity -= orderItem.getQuantity();
                        chaiMonk.getOrderIdList().remove(order);
                    }
                }
            }
        }
        chaiMonk.setQuantity(quantity);
    }


    public void notifyFragmentAboutStatusOfGlobalVariables() {

        ChaiMonkActivityJobQueueFragment fragment = (ChaiMonkActivityJobQueueFragment) getSupportFragmentManager().findFragmentById(R.id.orderJobQueueFragment);
        fragment.enableStartButton();
    }

    public void authenticateDayCloseUser(int userId, String password) {
        userSessionDetail = createUserSession(userId, password);
        LoginController.attemptLogin(userSessionDetail, ChaiMonkActivity.this);
    }

    private UserSessionDetail createUserSession(int userId, String password) throws IllegalArgumentException {
        return UserUtils.createLoginCredentials(PreferenceManager.getUserDetail(ChaiMonkActivity.this).getUnitId(), userId, 1, "POS", "KETTLE_SERVICE", password);
    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void onResponse(UserSessionDetail response) {
        mLoginDialog.dismiss();
        onLoginSuccessful();
        dayClosedBy = response.getUserName();
    }

    private void onLoginSuccessful() {
        AlertDialog dialog2 = new AlertDialog.Builder(ChaiMonkActivity.this).setMessage("Are You Sure You Want to Day Close!!!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<OrderDetail> orderDetails = databaseAdapter.getJobQueueDetails(globalVariables.getEndOrderId());
                        new JobSyncExecutor().execute(orderDetails);
                        createDayCloseEvent();
                        showOrderSummaryActivity(ChaiMonkActivity.this);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendCommandToMonk(chaiMonkMap.get(ChaiMonkEnum.CHAI_MONK2), Constants.ChaiMonkCommand.STOP);
                    }
                }).create();

        dialog2.show();
    }

    @Override
    public void onError(Cherror error) {
        mLoginDialog.dismiss();
        UserUtils.showAlertMessage(ChaiMonkActivity.this, "Entered Username and Password are not correct");
    }


    @Override
    public void onDiscovery(DeviceInfo deviceInfo) {
        if (deviceInfo != null) {
            String msg = "Printer \"%1$s\" detected and attached. , deviceInfo.getDeviceName()";
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            globalVariables.setmPrintFeature(mPrintFeature);
        }
    }

    @Override
    public void onPrintSuccess() {

    }

    @Override
    public void onPrintFailure() {
    }

    public void createDayCloseEvent() {
        List<OrderDetail> orderDetailArrayList;
        Map<String, Pair<String, Pair<Integer, Double>>> orderItemNameQuantityMap;

        Pair<Integer, Integer> startEndOrderIdPair = databaseAdapter.getOrderIdsOfCurrentBusinessDate(PreferenceManager.getEndOrderId(ChaiMonkActivity.this));
        orderItemsSummaryList = new ArrayList<>();
        int startOrderId = startEndOrderIdPair.first;
        int endOrderId = startEndOrderIdPair.second;
        if (startOrderId != 0 && endOrderId != 0) {
            totalOrdersCount = endOrderId - startOrderId + 1;
        } else {
            totalOrdersCount = endOrderId - startOrderId;
        }
        totalAmountOfAllOrders = databaseAdapter.getTotalAmountOfaBusinessDate(PreferenceManager.getEndOrderId(ChaiMonkActivity.this));
        int eventId = (int) databaseAdapter.insertDataToDayCloseEventTable(AppUtils.getCurrentBusinessDate(), OrderStatus.INITIATED.value(), startOrderId, endOrderId, AppUtils.getCurrentTimeStamp().toString(), dayClosedBy, null);
        databaseAdapter.insertDataToOrdersSummaryTable(eventId, totalOrdersCount, totalAmountOfAllOrders);
        orderDetailArrayList = databaseAdapter.getListOfOrderDetailOfCurrentBusinessDate(PreferenceManager.getEndOrderId(ChaiMonkActivity.this));
        orderItemNameQuantityMap = databaseAdapter.getProductWiseDetails(orderDetailArrayList);
        for (String key : orderItemNameQuantityMap.keySet()) {
            OrderItemsSummary orderItemsSummary = new OrderItemsSummary();
            int orderId = databaseAdapter.getProductIdFromKey(key);
            String productName = orderItemNameQuantityMap.get(key).first;
            orderItemsSummary.setProductName(productName);
            int quantity = orderItemNameQuantityMap.get(key).second.first;
            orderItemsSummary.setProductQuantity(quantity);
            double totalAmount = orderItemNameQuantityMap.get(key).second.second;
            subTotalAmount = subTotalAmount + totalAmount;
            orderItemsSummary.setProductTotalAmount(totalAmount);
            orderItemsSummaryList.add(orderItemsSummary);
            databaseAdapter.insertDataToOrderItemSummaryTable(eventId, orderId, productName, quantity, totalAmount);
        }
    }

    @Override
    protected void onStop() {
        globalVariables.setCurrentBusinessDate(AppUtils.getCurrentBusinessDate());
        super.onStop();
    }

    public void showOrderSummaryActivity(Activity activity) {
        if(activity instanceof AdminPanelActivity){
            Intent intent = new Intent(this, OrderSummaryActivity.class);
            intent.putParcelableArrayListExtra("showOrderSummaryActivity", orderItemsSummaryList);
            intent.putExtra("totalOrdersCount", totalOrdersCount);
            intent.putExtra("totalAmountValue", totalAmountOfAllOrders);
            intent.putExtra("subTotalAmount", subTotalAmount);
            intent.putExtra("ADMINPANEL",true);
            subTotalAmount = 0.0;
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, OrderSummaryActivity.class);
            intent.putParcelableArrayListExtra("showOrderSummaryActivity", orderItemsSummaryList);
            intent.putExtra("totalOrdersCount", totalOrdersCount);
            intent.putExtra("totalAmountValue", totalAmountOfAllOrders);
            intent.putExtra("subTotalAmount", subTotalAmount);
            subTotalAmount = 0.0;
            startActivity(intent);
        }
    }

    public void showLoginDialog() {
        mLoginDialog.setMessage("Attempting Login.......");
        mLoginDialog.show();
    }


    public ChaiMonkManager getMonkManager() {
        return monkManager;
    }

    public void setOrderDetailArrayList(List<OrderDetail> orderDetailArrayList) {
        this.orderDetailArrayList = orderDetailArrayList;
    }

    public void resetMonks() {
        List<ChaiMonk> waitingMonks = monkManager.findFreeMonks().get(TaskState.WAITING);
        if (waitingMonks != null) {
            for (ChaiMonk monk : waitingMonks) {
                monk.reset();
            }
        }
    }

    public void cancelDayClose() {
        databaseAdapter.updateEventStatusToCancelled();
    }


    private class JobSyncExecutor extends AsyncTask<List<OrderDetail>, Integer, Integer> {
        @Override
        protected Integer doInBackground(List<OrderDetail>... params) {
            return getOrderManagementService().syncOrders(params[0]);
        }

        @Override
        protected void onPostExecute(Integer lastSyncedOrder) {
            saveLastSyncedOrder(lastSyncedOrder);
        }
    }
}
