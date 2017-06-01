package www.chaayos.com.chaimonkbluetoothapp.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import www.chaayos.com.chaimonkbluetoothapp.GlobalVariables;
import www.chaayos.com.chaimonkbluetoothapp.PreferenceManager;
import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.IdCodeName;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.ListData;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderItem;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Product;
import www.chaayos.com.chaimonkbluetoothapp.fragments.CategoryFragment;
import www.chaayos.com.chaimonkbluetoothapp.fragments.CreateOrderScreenFragment;
import www.chaayos.com.chaimonkbluetoothapp.fragments.SubCategoryFragment;

public class CreateOrderActivity extends AppCompatActivity implements CategoryFragment.OnCategoryFragmentClickedListener {

    private TextView idTv;
    private TextView nameTv;
    private TextView placeTv;
    public Stack<String> mFragmentStack;
    private GlobalVariables globalVariables;
    private OrderItem orderItem;
    private ArrayList<OrderItem> orderItemArrayList = new ArrayList<>();
    private Map<String, List<Product>> productCategoryMap = new HashMap<>();
    public OrderItem getOrderItem() {
        return orderItem;
    }
    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
        checkIfAlreadyPresent(orderItem);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalVariables = ((GlobalVariables) getApplicationContext());
        createCategoriesArrayList();
        globalVariables.setCategoryAndProductMap(productCategoryMap);
        setContentView(R.layout.activity_create_order);

        idTv = (TextView) findViewById(R.id.userIdTv);
        nameTv = (TextView) findViewById(R.id.userNameTv);
        placeTv= (TextView) findViewById(R.id.unitPlaceTv);

        idTv.setText("Id: " + PreferenceManager.getCredentials(CreateOrderActivity.this).getUserId());
        nameTv.setText("Name: " + PreferenceManager.getUserDetail(CreateOrderActivity.this).getUserName());
        placeTv.setText("Place: " + PreferenceManager.getCredentials(CreateOrderActivity.this).getUnitName());
        mFragmentStack = new Stack<>();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = new CategoryFragment();
        String tag = "CategoryFragment";
        mFragmentStack.add(tag);
        transaction.add(R.id.fragment_swap, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();


    }



    @Override
    public void onBackPressed() {
        Fragment fragment = getFragmentManager().findFragmentByTag(mFragmentStack.peek());
        if (fragment instanceof SubCategoryFragment) {
            removeFragment();
            super.onBackPressed();
        } else {
            finish();
        }
    }

    private void removeFragment() {
        mFragmentStack.pop();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment currentFragment = getFragmentManager().findFragmentByTag("SubCategoryFragment");
        transaction.hide(currentFragment);
        Fragment fragment = getFragmentManager().findFragmentByTag("CategoryFragment");

        transaction.show(fragment);


        transaction.commit();
    }

    public ArrayList<OrderItem> getOrderItemArrayList() {
        return orderItemArrayList;
    }


    public Map<String,List<Product>> createCategoriesArrayList() {
        if(globalVariables.getListDatas() != null ) {
            for (ListData listData : globalVariables.getListDatas()) {
                for (IdCodeName idCodeName : listData.getContent()) {
                    for (Product product : globalVariables.getUnit().getProducts()) {
                        if (product.getSubType() == idCodeName.getId()) {
                            addToMap(idCodeName, product);
                        }
                    }
                }
            }
        }
        return this.productCategoryMap;
    }

    private void addToMap(IdCodeName idCodeName, Product product) {
        if (productCategoryMap == null) {
            productCategoryMap = new HashMap<>();
        }
        List<Product> productList = productCategoryMap.get(idCodeName.getName());
        if (productList != null) {
            productList.add(product);
        } else {
            productList = new ArrayList<>();
            productList.add(product);
            productCategoryMap.put(idCodeName.getName(), productList);
        }

    }


    @Override
    public void onCategoryFragmentClicked(String key) {
        Fragment subCategoryFragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        String tag = "SubCategoryFragment";
        args.putString("KEY",key);
        subCategoryFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment currentFragment = getFragmentManager().findFragmentByTag(mFragmentStack.peek());
        transaction.hide(currentFragment);
        transaction.add(R.id.fragment_swap, subCategoryFragment, tag);
        mFragmentStack.add(tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    public void checkIfAlreadyPresent(OrderItem orderItem) {

        if (!orderItemArrayList.isEmpty() && orderItemArrayList.contains(orderItem)){
            int index=orderItemArrayList.indexOf(orderItem);
            OrderItem tempItem = orderItemArrayList.get(index);
            if((tempItem.getQuantity()+1) <= 14) {
                tempItem.setQuantity(tempItem.getQuantity() + 1);
                tempItem.setTotalAmount(tempItem.getPrice().multiply(new BigDecimal(tempItem.getQuantity())));
                orderItemArrayList.set(index, tempItem);
                CreateOrderScreenFragment fragment = (CreateOrderScreenFragment) getFragmentManager().findFragmentById(R.id.orderScreenFragment);
                fragment.createOrderInsideRecyclerView();
            }
        } else {
            orderItemArrayList.add(orderItem);
        }
    }

}
