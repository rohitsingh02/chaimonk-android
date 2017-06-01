package www.chaayos.com.chaimonkbluetoothapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.adapters.SummaryCustomAdapter;
import www.chaayos.com.chaimonkbluetoothapp.db.DatabaseAdapter;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderItemsSummary;

public class OrderSummaryActivity extends AppCompatActivity {
    private TextView totalOrders;
    private TextView totalOrdersAmount;
    private Button cancelButton;
    private Button proceedButton;
    private RecyclerView recyclerView;
    protected SummaryCustomAdapter mAdapter;
    private List<OrderItemsSummary> orderItemsSummaryList;
    private int totalOrdersCount = 0;
    private double totalAmountValue = 0.0;
    private TextView subTotalAmount;
    DatabaseAdapter databaseAdapter;
    private double subTotalAmountValue = 0.0;
    private Button closeButton;
    private RelativeLayout cancelSubmitRv;
    private boolean isCalledFromAdminPanelActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        orderItemsSummaryList = getIntent().getParcelableArrayListExtra("showOrderSummaryActivity");
        totalOrdersCount = getIntent().getIntExtra("totalOrdersCount",-1);
        totalAmountValue = getIntent().getDoubleExtra("totalAmountValue",-1.0);
        subTotalAmountValue = getIntent().getDoubleExtra("subTotalAmount",-1.0);
        isCalledFromAdminPanelActivity = getIntent().getBooleanExtra("ADMINPANEL",false);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width * 0.8),(int)(height * 0.8));
        databaseAdapter = new DatabaseAdapter(OrderSummaryActivity.this);
        totalOrders = (TextView) findViewById(R.id.totalOrders);
        totalOrdersAmount = (TextView) findViewById(R.id.ordersTotalAmount);
        subTotalAmount = (TextView) findViewById(R.id.subtotalAmount);
        totalOrders.setText("Total Orders: " + String.valueOf(totalOrdersCount));
        totalOrdersAmount.setText("TotalAmount : Rs " +String.valueOf(totalAmountValue));
        subTotalAmount.setText("Rs " + subTotalAmountValue);
        proceedButton = (Button) findViewById(R.id.proceedButton);
        cancelButton = (Button) findViewById(R.id.cButton);
        recyclerView = (RecyclerView) findViewById(R.id.ordersSummaryRv);
        closeButton = (Button) findViewById(R.id.clButton);
        cancelSubmitRv = (RelativeLayout) findViewById(R.id.cancelSubmitRv);
        if(isCalledFromAdminPanelActivity){
            showCloseButton();
        }else {
            closeButton.setVisibility(View.INVISIBLE);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SummaryCustomAdapter(orderItemsSummaryList,this);
        recyclerView.setAdapter(mAdapter);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(OrderSummaryActivity.this,"Day close completed successfully",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(OrderSummaryActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
 private void showCloseButton(){

     cancelSubmitRv.setVisibility(View.INVISIBLE);
     closeButton.setVisibility(View.VISIBLE);

 }


}
