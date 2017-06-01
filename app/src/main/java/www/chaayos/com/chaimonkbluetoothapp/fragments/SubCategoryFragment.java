package www.chaayos.com.chaimonkbluetoothapp.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import org.apmem.tools.layouts.FlowLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import www.chaayos.com.chaimonkbluetoothapp.GlobalVariables;
import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.activities.CreateOrderActivity;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.BillType;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.DimensionEnum;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderStatus;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Product;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.ProductPrice;
import www.chaayos.com.chaimonkbluetoothapp.management.service.OrderItemManagementService;


public class SubCategoryFragment extends Fragment implements View.OnClickListener {
    private FlowLayout flowLayout;
    private ImageButton backButton;
    private GlobalVariables globalVariables;
    private List<Product> productList = new ArrayList<>();
    private Product product;
    int productId;
    String productName;
    private int quantity;
    int type;
    int subType;
    private BigDecimal price;
    private BigDecimal totalAmount;
    private BigDecimal amount;
    private String dimension;
    BillType billType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sub_category, container, false);
        globalVariables = (GlobalVariables) getActivity().getApplicationContext();
        Bundle bundle = getArguments();
        String key = bundle.getString("KEY");
        System.out.print(key);
        getProducts(key);
        backButton = (ImageButton) rootView.findViewById(R.id.cancelFragmentButton);
        flowLayout = (FlowLayout) rootView.findViewById(R.id.subCategoryFlowLayout);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        for (int i = 0; i < productList.size(); i++) {
            Button button = new Button(getActivity().getApplicationContext());
            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.MATCH_PARENT,
                    FlowLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 8);
            button.setLayoutParams(params);
            button.setGravity(Gravity.CENTER_HORIZONTAL);
            button.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_button_print));


            button.setText(productList.get(i).getName());
            button.setId(i);
            flowLayout.addView(button);
            button.setOnClickListener(this);
        }
        return rootView;
    }

    private void getProducts(String key) {
        productList.addAll(globalVariables.getCategoryAndProductMap().get(key));
        System.out.print(productList);

    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        fillOrderItem(b.getText().toString());
    }

    private void fillOrderItem(String key) {
        for (Product prod : productList) {
            if (prod.getName().equalsIgnoreCase(key)) {
                product = prod;
                productId = product.getId();
                productName = product.getName();
                quantity = 1;
                billType = product.getBillType();
                type = product.getType();
                subType = product.getSubType();

                for (ProductPrice productPrice : product.getPrices()) {
                    if (productPrice.getDimension().equalsIgnoreCase("Regular") || productPrice.getDimension().equalsIgnoreCase("None")) {
                        price = productPrice.getPrice();
                        System.out.print(price);
                    }
                }
                totalAmount = price.multiply(new BigDecimal(quantity));
                amount = totalAmount;
                dimension = DimensionEnum.Regular.value();

                createOrderItem(product, quantity, price, totalAmount, amount, dimension);


            }
        }
    }

    public void createOrderItem(Product product, int quantity, BigDecimal price, BigDecimal totalAmount, BigDecimal amount, String dimension) {

        OrderItemManagementService managementService = new OrderItemManagementService();
        CreateOrderActivity createOrderActivity = (CreateOrderActivity) getActivity();
        createOrderActivity.setOrderItem(managementService.createOrderItem(product, quantity, price, totalAmount, amount, dimension,
                product.getType() != 5 ? OrderStatus.SETTLED.toString():OrderStatus.INITIATED.toString()));
        CreateOrderScreenFragment fragment = (CreateOrderScreenFragment) getFragmentManager().findFragmentById(R.id.orderScreenFragment);
        fragment.createOrderInsideRecyclerView();

    }

}
