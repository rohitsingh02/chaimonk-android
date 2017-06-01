package www.chaayos.com.chaimonkbluetoothapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import www.chaayos.com.chaimonkbluetoothapp.GlobalVariables;
import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.adapters.PopUpViewAdapter;
import www.chaayos.com.chaimonkbluetoothapp.data.model.OrderDetail;
import www.chaayos.com.chaimonkbluetoothapp.db.DatabaseAdapter;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderInfo;
import www.chaayos.com.chaimonkbluetoothapp.printer.PrintFeature;
import www.chaayos.com.chaimonkbluetoothapp.utils.UserUtils;


public class JobQueueContentPopUpActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PopUpViewAdapter popUpViewAdapter;
    private Button cancelButton;
    private Button reprint;
    private PrintFeature mPrintFeature;
    private DatabaseAdapter databaseAdapter;
    private GlobalVariables globalVariables;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_queue_content_pop_up);
        cancelButton = (Button)findViewById(R.id.cancelBtn);
        reprint = (Button) findViewById(R.id.reptintButton);
        Intent intent = getIntent();
        Gson gson = new Gson();
        String jsonString = intent.getStringExtra("ORDERARRAYLIST");
        globalVariables = (GlobalVariables)getApplicationContext();
        databaseAdapter = new DatabaseAdapter(this);
        final OrderDetail orderDetail = gson.fromJson(jsonString, OrderDetail.class);
        recyclerView= (RecyclerView) findViewById(R.id.order_item_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(JobQueueContentPopUpActivity.this));
        popUpViewAdapter = new PopUpViewAdapter(orderDetail.getOrderItems());
        recyclerView.setAdapter(popUpViewAdapter);
        mPrintFeature = globalVariables.getmPrintFeature();
        if(mPrintFeature == null){
            mPrintFeature = new PrintFeature();
            mPrintFeature.setupPrinter((ChaiMonkActivity)globalVariables.getContext(),(ChaiMonkActivity)globalVariables.getContext());
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width * 0.6),(int)(height * 0.6));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateReprint(orderDetail.getOrderId());

            }
        });
    }



    public void generateReprint(int orderId){

       Gson gson = new Gson();
       OrderInfo orderInfo =  gson.fromJson(databaseAdapter.getDataFromReprintTable(orderId),OrderInfo.class);

        if(mPrintFeature != null) {
            if (mPrintFeature.printBill(null, orderInfo, globalVariables.getUnit(), true)) {
                finish();

            } else {
                UserUtils.showAlertMessage(JobQueueContentPopUpActivity.this, "No printer attached. Please give IP manually. ");
            }
        }
    }

}


