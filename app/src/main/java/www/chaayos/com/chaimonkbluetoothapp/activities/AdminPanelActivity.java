package www.chaayos.com.chaimonkbluetoothapp.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import www.chaayos.com.chaimonkbluetoothapp.GlobalVariables;
import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.db.DatabaseAdapter;
import www.chaayos.com.chaimonkbluetoothapp.utils.AppUtils;

public class AdminPanelActivity extends AppCompatActivity {

    private Button resetCounterButton;
    private DatabaseAdapter databaseAdapter;
    private TextView logTv;
    private Button showLogBtn;
    private Button showOrderSummary;
    private GlobalVariables globalVariables;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        resetCounterButton = (Button) findViewById(R.id.resetCounter);
        logTv = (TextView) findViewById(R.id.logTv);
        showLogBtn = (Button) findViewById(R.id.showLogButton);
        showOrderSummary = (Button) findViewById(R.id.showSummary);
        databaseAdapter = new DatabaseAdapter(this);
        globalVariables = ((GlobalVariables) getApplicationContext());
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width * 0.8),(int)(height * 0.7));


        resetCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseAdapter.resetMonkCounterToOne();
                Snackbar.make(v,"Token Reset Successfully!!",Snackbar.LENGTH_LONG).show();

            }
        });
        showLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logTv.setText(new Gson().toJson(databaseAdapter.getDataFromConnectionLogTable(AppUtils.getCurrentBusinessDate())));

            }
        });

        showOrderSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChaiMonkActivity)globalVariables.getContext()).createDayCloseEvent();
                ((ChaiMonkActivity)globalVariables.getContext()).showOrderSummaryActivity(AdminPanelActivity.this);
            }
        });
    }
}
