package www.chaayos.com.chaimonkbluetoothapp.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import www.chaayos.com.chaimonkbluetoothapp.GlobalVariables;
import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.activities.CreateOrderActivity;
import www.chaayos.com.chaimonkbluetoothapp.activities.MakePaymentActivity;
import www.chaayos.com.chaimonkbluetoothapp.adapters.RecyclerViewCustomAdapter;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.TaxListModel;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderItem;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.PercentageDetail;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.TaxProfile;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.TransactionDetail;
import www.chaayos.com.chaimonkbluetoothapp.utils.CalculateTax;


public class CreateOrderScreenFragment extends Fragment implements View.OnClickListener,Serializable,RecyclerViewCustomAdapter.OrderItemChangedNotifier{

    protected RecyclerView mRecyclerview;
    protected RecyclerViewCustomAdapter mRecyclerViewCustomAdapter;
    private ArrayList<OrderItem> orderItemArrayList;
    private Button clearButton;
    private Button proceedButton;
    private CreateOrderActivity myActivity;
    private CalculateTax calculateTax;
    private GlobalVariables globalVariables;
    private ArrayList<TaxListModel> taxListModelArrayList ;
    protected PercentageDetail netPriceVatPercentageDetail;
    protected PercentageDetail mrpVatPercentageDetail;
    protected PercentageDetail surchargeOnTaxPercentageDetail;
    protected PercentageDetail serviceTaxPercentageDetail;
    protected PercentageDetail sbCessPercentageDetail;
    protected PercentageDetail kkCessPercentageDetail;
    private TransactionDetail transactionDetail;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        netPriceVatPercentageDetail = new PercentageDetail();
        mrpVatPercentageDetail = new PercentageDetail();
        surchargeOnTaxPercentageDetail = new PercentageDetail();
        serviceTaxPercentageDetail = new PercentageDetail();
        sbCessPercentageDetail = new PercentageDetail();
        kkCessPercentageDetail = new PercentageDetail();
        transactionDetail = new TransactionDetail();
        calculateTax = new CalculateTax();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_order_screen, container, false);
        myActivity = (CreateOrderActivity) getActivity();
        globalVariables = (GlobalVariables) getActivity().getApplicationContext();
        mRecyclerview = (RecyclerView) rootView.findViewById(R.id.orderListRecyclerView);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        proceedButton = (Button) rootView.findViewById(R.id.proceedButton);
        createTaxProfilesMap();
        proceedButton.setEnabled(false);
        proceedButton.setOnClickListener(this);
        clearButton = (Button) rootView.findViewById(R.id.btnClear);
        clearButton.setOnClickListener(this);


        createTransactionDetailObject();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.proceedButton:
                Intent intent = new Intent(getActivity().getApplicationContext(), MakePaymentActivity.class);
                if(orderItemArrayList!= null){
                   taxListModelArrayList = new ArrayList<>();
                    intent.putParcelableArrayListExtra("parcelableOrderItem",orderItemArrayList);
                    calculateTaxHelperMethod(orderItemArrayList);
                    globalVariables.setTaxListModel(taxListModelArrayList);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Kindly select item to Proceed",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnClear:
                if(orderItemArrayList != null){
                    orderItemArrayList.clear();
                    mRecyclerViewCustomAdapter = new RecyclerViewCustomAdapter(this,orderItemArrayList);
                    mRecyclerview.setAdapter(mRecyclerViewCustomAdapter);
                }
                break;
        }
    }
    public void createOrderInsideRecyclerView(){
        orderItemArrayList = myActivity.getOrderItemArrayList();
        mRecyclerViewCustomAdapter = new RecyclerViewCustomAdapter(this,orderItemArrayList);
        mRecyclerview.setAdapter(mRecyclerViewCustomAdapter);
        proceedButton.setEnabled(true);
    }

    private void calculateTaxHelperMethod(ArrayList<OrderItem> orderItemArrayList) {
        for(OrderItem orderItem: orderItemArrayList){
            callCalculateTax(orderItem);
        }
    }

    @Override
    public void itemChanged(ArrayList<OrderItem> updatedOrderItemArrayList) {
        orderItemArrayList = updatedOrderItemArrayList;
        if(updatedOrderItemArrayList.isEmpty()){
            proceedButton.setEnabled(false);
        }
    }

    public void  callCalculateTax(OrderItem orderItem){
        taxListModelArrayList.add(calculateTax.calculatePaidAmount(orderItem.getPrice(),orderItem.getQuantity(),orderItem.getBillType(),globalVariables));
    }

    public  void createTaxProfilesMap(){
        for(TaxProfile taxProfile : globalVariables.getUnit().getTaxProfiles()){

            switch (taxProfile.getType()) {
                case NET_PRICE_VAT:
                    netPriceVatPercentageDetail.setPercentage(taxProfile.getPercentage());
                    break;

                case MRP_VAT:
                {
                    mrpVatPercentageDetail.setPercentage(taxProfile.getPercentage());

                    break;
                }
                case SURCHARGE:
                {
                    surchargeOnTaxPercentageDetail.setPercentage(taxProfile.getPercentage());

                    break;
                }
                case SERVICE_TAX:
                {
                    serviceTaxPercentageDetail.setPercentage(taxProfile.getPercentage());
                    break;
                }
                case SB_CESS:
                {
                    sbCessPercentageDetail.setPercentage(taxProfile.getPercentage());
                    break;
                }
                case KK_CESS:

                    kkCessPercentageDetail.setPercentage(taxProfile.getPercentage());
                    break;

            }
        }
    }

    public  void createTransactionDetailObject(){
        transactionDetail.setNetPriceVat(netPriceVatPercentageDetail);
        transactionDetail.setMrpVat(mrpVatPercentageDetail);
        transactionDetail.setSurchargeOnTax(surchargeOnTaxPercentageDetail);
        transactionDetail.setServiceTax(serviceTaxPercentageDetail);
        transactionDetail.setSbCess(sbCessPercentageDetail);
        transactionDetail.setKkCess(kkCessPercentageDetail);
        globalVariables.setLatestTransactionDetail(transactionDetail);
    }


}
