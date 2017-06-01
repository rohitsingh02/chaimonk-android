package www.chaayos.com.chaimonkbluetoothapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import www.chaayos.com.chaimonkbluetoothapp.GlobalVariables;
import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.activities.MakePaymentActivity;
import www.chaayos.com.chaimonkbluetoothapp.adapters.OrderSummaryCustomAdapter;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.TaxListModel;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderItem;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.TransactionDetail;
import www.chaayos.com.chaimonkbluetoothapp.utils.AppUtils;

public class OrderSummaryFragment extends Fragment implements View.OnClickListener{
    protected RecyclerView mRecyclerview;
    protected OrderSummaryCustomAdapter mOrderSummaryCustomAdapter;
    private ArrayList<OrderItem> orderItemArrayList;
    private TextView vatOnMRPTextView;
    private TextView vatOnNetPriceTextView;
    private TextView surchargeOnVatTextView;
    private TextView serviceTaxTextView;
    private TextView sBC;
    private TextView kKC;
    private TextView subTotalTextView;
    private TextView totalTextView;
    private TextView vatOnMRPTv;
    private TextView vatOnNetPriceTv;
    private TextView surchargeOnVatTv;
    private TextView serviceTaxTv;
    private TextView sBCTv;
    private TextView kKCTv;
    private BigDecimal subTotal = BigDecimal.ZERO;
    private BigDecimal taxableAmount = BigDecimal.ZERO;
    private BigDecimal vatOnMRP = BigDecimal.ZERO;
    private BigDecimal vatOnNetPrice = BigDecimal.ZERO;
    private BigDecimal surchargeOnVat = BigDecimal.ZERO;
    private BigDecimal serviceTax = BigDecimal.ZERO;
    private BigDecimal kkCess = BigDecimal.ZERO;
    private BigDecimal sbCess = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;
    private Button editOrder;
    private GlobalVariables globalVariables;
    private TransactionDetail latestTransactionDetail;
    private ArrayList<TaxListModel> taxListModelArrayList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalVariables = (GlobalVariables) getActivity().getApplicationContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_order_summary, container, false);

        vatOnMRPTextView  = (TextView) rootView.findViewById(R.id.vatOnMrp);
        vatOnNetPriceTextView = (TextView) rootView.findViewById(R.id.vatOnNet);
        surchargeOnVatTextView = (TextView) rootView.findViewById(R.id.surVat);
        serviceTaxTextView = (TextView) rootView.findViewById(R.id.serviceTax);
        sBC = (TextView) rootView.findViewById(R.id.sbc);
        kKC = (TextView) rootView.findViewById(R.id.kkc);

        vatOnMRPTv  = (TextView) rootView.findViewById(R.id.vatOnMrpTv);
        vatOnNetPriceTv = (TextView) rootView.findViewById(R.id.vatOnNetTv);
        surchargeOnVatTv = (TextView) rootView.findViewById(R.id.surVatTv);
        serviceTaxTv = (TextView) rootView.findViewById(R.id.serviceTaxTv);
        sBCTv = (TextView) rootView.findViewById(R.id.sbcTv);
        kKCTv = (TextView) rootView.findViewById(R.id.kkcTv);
        subTotalTextView = (TextView) rootView.findViewById(R.id.subTotal);
        totalTextView = (TextView) rootView.findViewById(R.id.GTotal);
        mRecyclerview = (RecyclerView) rootView.findViewById(R.id.orderListRecycle);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        MakePaymentActivity activity = (MakePaymentActivity) getActivity();
        orderItemArrayList = activity.getOrderItemArrayList();
        editOrder = (Button) rootView.findViewById(R.id.editOrder);
        editOrder.setOnClickListener(this);
        mOrderSummaryCustomAdapter = new OrderSummaryCustomAdapter(orderItemArrayList);
        mRecyclerview.setAdapter(mOrderSummaryCustomAdapter);
        taxListModelArrayList = globalVariables.getTaxListModel();
        TransactionDetail transactionObject = globalVariables.getLatestTransactionDetail();
        latestTransactionDetail = new TransactionDetail();
        calculateTaxAtOrderLevel();
        getTotalAmount();
        subTotalTextView.setText( String.valueOf(subTotal));
        vatOnMRPTv.setText(String.valueOf(AppUtils.VAT_ON_MRP + String.valueOf(transactionObject.getMrpVat().getPercentage())));
        vatOnNetPriceTv.setText(String.valueOf(AppUtils.VAT_ON_NETPRICE + String.valueOf(transactionObject.getNetPriceVat().getPercentage())));
        surchargeOnVatTv.setText(String.valueOf(AppUtils.SURCHARGE_ON_VAT + String.valueOf(transactionObject.getSurchargeOnTax().getPercentage())));
        serviceTaxTv.setText(String.valueOf(AppUtils.SERVICE_TAX + String.valueOf(transactionObject.getServiceTax().getPercentage())));
        kKCTv.setText(String.valueOf(AppUtils.KK_CESS + String.valueOf(transactionObject.getKkCess().getPercentage())));
        sBCTv.setText(String.valueOf(AppUtils.SB_CESS + String.valueOf(transactionObject.getSbCess().getPercentage())));
        vatOnMRPTextView.setText(String.valueOf(vatOnMRP));
        vatOnNetPriceTextView.setText(String.valueOf(vatOnNetPrice));
        surchargeOnVatTextView.setText(String.valueOf(surchargeOnVat));
        serviceTaxTextView.setText(String.valueOf(serviceTax));
        kKC.setText(String.valueOf(kkCess));
        sBC.setText(String.valueOf(sbCess));
        totalTextView.setText(String.valueOf(total));
        ((MakePaymentActivity) getActivity()).setAmountToPay(total.intValue());
        fillTransactionDetailObject();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        getActivity().finish();
    }

    public void calculateTaxAtOrderLevel(){
        for(TaxListModel taxListModels: taxListModelArrayList){
            subTotal = AppUtils.getCeilingValue(subTotal.add(taxListModels.getSubTotal()));
            vatOnMRP = AppUtils.getCeilingValue(vatOnMRP.add(taxListModels.getVatOnMrp()));
            vatOnNetPrice = AppUtils.getCeilingValue(vatOnNetPrice.add(taxListModels.getVatOnNetPrice()));
            surchargeOnVat = AppUtils.getCeilingValue(surchargeOnVat.add(taxListModels.getSurchargeOnVat()));
            serviceTax = AppUtils.getCeilingValue(serviceTax.add(taxListModels.getServiceTax()));
            kkCess = AppUtils.getCeilingValue(kkCess.add(taxListModels.getKkCess()));
            sbCess = AppUtils.getCeilingValue(sbCess.add(taxListModels.getSbCess()));
        }
    }

    public void getTotalAmount(){
        total = subTotal.add(vatOnMRP).add(vatOnNetPrice).add(surchargeOnVat)
                .add(serviceTax).add(kkCess).add(sbCess).setScale(0,RoundingMode.HALF_UP);
    }

    public void fillTransactionDetailObject(){
        TransactionDetail transactionDetail = globalVariables.getLatestTransactionDetail();
        BigDecimal roundedOffValue = total.setScale(0,RoundingMode.HALF_UP);

        transactionDetail.getNetPriceVat().setValue(vatOnNetPrice);
        transactionDetail.getMrpVat().setValue(vatOnMRP);
        transactionDetail.getServiceTax().setValue(serviceTax);
        transactionDetail.getSurchargeOnTax().setValue(surchargeOnVat);
        transactionDetail.getKkCess().setValue(kkCess);
        transactionDetail.getSbCess().setValue(sbCess);
        transactionDetail.setTotalAmount(total);
        transactionDetail.setTaxableAmount(subTotal);
        transactionDetail.setRoundOffValue(roundedOffValue.subtract(total));
    }

}
