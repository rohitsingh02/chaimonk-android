package www.chaayos.com.chaimonkbluetoothapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

import www.chaayos.com.chaimonkbluetoothapp.GlobalVariables;
import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.db.DatabaseAdapter;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderItem;


public class MakePaymentActivity extends AppCompatActivity implements View.OnClickListener{

    private Button oneButton;
    private Button twoButton;
    private Button threeButton;
    private Button fourButton;
    private Button fiveButton;
    private Button sixButton;
    private Button sevenButton;
    private Button eightButton;
    private Button nineButton;
    private Button zeroButton;
    private Button cancelButton;
    private Button clearButton;
    private Button finishButton;
    private TextView amountReceived;
    private TextView balanceAmountTextView;
    private  int amountToPay = 0;
    private BigDecimal receiveAmount = new BigDecimal(0);
    private boolean isAppending = false;
    private int balanceAmount = 0;
    private GlobalVariables globalVariables;
    private ArrayList<OrderItem> orderItemArrayList;
    DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalVariables = (GlobalVariables) getApplicationContext();
        orderItemArrayList = getIntent().getParcelableArrayListExtra("parcelableOrderItem");
        globalVariables.setLatestOrderItemArrayList(orderItemArrayList);
        setContentView(R.layout.activity_make_payment);
        zeroButton = (Button) findViewById(R.id.button0);
        oneButton = (Button) findViewById(R.id.button1);
        twoButton = (Button) findViewById(R.id.button2);
        threeButton = (Button) findViewById(R.id.button3);
        fourButton = (Button) findViewById(R.id.button4);
        fiveButton = (Button)findViewById(R.id.button5);
        sixButton = (Button) findViewById(R.id.button6);
        sevenButton = (Button) findViewById(R.id.button7);
        eightButton = (Button) findViewById(R.id.button8);
        nineButton = (Button) findViewById(R.id.button9);
        cancelButton = (Button) findViewById(R.id.buttonCancel);
        clearButton = (Button) findViewById(R.id.buttonClear);
        amountReceived = (TextView) findViewById(R.id.amountReceived);
        balanceAmountTextView = (TextView) findViewById(R.id.balanceAmount);
        finishButton = (Button) findViewById(R.id.finishButton);
        databaseAdapter = new DatabaseAdapter(this);


        oneButton.setOnClickListener(this);
        twoButton.setOnClickListener(this);
        threeButton.setOnClickListener(this);
        fourButton.setOnClickListener(this);
        fiveButton.setOnClickListener(this);
        sixButton.setOnClickListener(this);
        sevenButton.setOnClickListener(this);
        eightButton.setOnClickListener(this);
        nineButton.setOnClickListener(this);
        zeroButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        finishButton.setOnClickListener(this);
        calculateBalance();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button0:
                if(!isAppending){
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText("0");
                    }

                }else{
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText(amountReceived.getText() + "0");
                    }

                }
                calculateBalance();
                break;
            case R.id.button1:
                if(!isAppending){
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText("1");
                        isAppending = true;
                    }
                }else{
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText(amountReceived.getText() + "1");
                    }
                }
                calculateBalance();
                break;
            case R.id.button2:
                if(!isAppending){
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText("2");
                        isAppending = true;
                    }
                }else{
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText(amountReceived.getText() + "2");
                    }
                }
                calculateBalance();
                break;
            case R.id.button3:
                if(!isAppending){
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText("3");
                        isAppending = true;
                    }

                }else{
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText(amountReceived.getText() + "3");
                    }

                }
                calculateBalance();
                break;
            case R.id.button4:
                if(!isAppending){
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText("4");
                        isAppending = true;
                    }

                }else{
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText(amountReceived.getText() + "4");
                    }

                }
                calculateBalance();
                break;
            case R.id.button5:
                if(!isAppending){
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText("5");
                        isAppending = true;
                    }

                }else{
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText(amountReceived.getText() + "5");
                    }

                }
                calculateBalance();
                break;
            case R.id.button6:
                if(!isAppending){
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText("6");
                        isAppending = true;
                    }
                }else{
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText(amountReceived.getText() + "6");
                    }

                }
                calculateBalance();
                break;
            case R.id.button7:
                if(!isAppending){
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText("7");
                        isAppending = true;
                    }
                }else{
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText(amountReceived.getText() + "7");
                    }
                }
                calculateBalance();
                break;
            case R.id.button8:
                if(!isAppending){
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText("8");
                        isAppending = true;
                    }
                }else{
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText(amountReceived.getText() + "8");
                    }
                }
                calculateBalance();
                break;
            case R.id.button9:
                if(!isAppending){
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText("9");
                        isAppending = true;
                    }

                }else{
                    if(amountReceived.getText().length() < 6){
                        amountReceived.setText(amountReceived.getText() + "9");
                    }

                }
                calculateBalance();
                break;
            case R.id.buttonCancel:int length = amountReceived.length();
                if(length >1){
                    String numberEntered = amountReceived.getText().toString().substring(0,amountReceived.length()-1);
                    amountReceived.setText(numberEntered);
                }else{
                    amountReceived.setText("0");
                    isAppending = false;
                }
                calculateBalance();
                break;
            case R.id.buttonClear:
                receiveAmount = new BigDecimal(0);
                calculateBalance();
                amountReceived.setText("" + receiveAmount);
                isAppending = false;
                calculateBalance();
                break;
            case R.id.finishButton:System.out.print(globalVariables.getOrderArrayList());
                globalVariables.setTaxListModel(null);
                Intent intent = new Intent(MakePaymentActivity.this,SettlementActivity.class);
                startActivity(intent);

                break;
        }
    }

    public ArrayList<OrderItem> getOrderItemArrayList() {
        return orderItemArrayList;
    }

    public void setAmountToPay(int amountToPay) {
        this.amountToPay = amountToPay;
    }

    public void calculateBalance(){

        balanceAmount =   amountToPay - Integer.parseInt(amountReceived.getText().toString());
        balanceAmountTextView.setText(String.valueOf(balanceAmount));
    }



}
