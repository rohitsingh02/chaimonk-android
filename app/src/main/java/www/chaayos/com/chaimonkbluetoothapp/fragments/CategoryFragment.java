package www.chaayos.com.chaimonkbluetoothapp.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;
import java.util.Map;

import www.chaayos.com.chaimonkbluetoothapp.GlobalVariables;
import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Product;


public class CategoryFragment extends Fragment implements View.OnClickListener {
    private OnCategoryFragmentClickedListener onCategoryFragmentClickedListener;
    private FlowLayout flowLayout;
    private GlobalVariables globalVariables;
    private Map<String, List<Product>> categoryMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        globalVariables = ((GlobalVariables) getActivity().getApplicationContext());
        categoryMap = globalVariables.getCategoryAndProductMap();

        flowLayout = (FlowLayout) rootView.findViewById(R.id.categoryFlowLayout);

        Button button;
        int i = 0;
        for (String subCategory : categoryMap.keySet()) {
            button = new Button(getActivity().getApplicationContext());
            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.MATCH_PARENT,
                    FlowLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 10, 0);
            params.setWeight(1);
            button.setLayoutParams(params);
            button.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_button_print));
            button.setGravity(Gravity.FILL);
            button.setText(subCategory);
            button.setId(i);
            flowLayout.addView(button);
            button.setOnClickListener(this);
            i++;
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        onCategoryFragmentClickedListener.onCategoryFragmentClicked(b.getText().toString());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onCategoryFragmentClickedListener = (OnCategoryFragmentClickedListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public interface OnCategoryFragmentClickedListener {
        public void onCategoryFragmentClicked(String key);
    }

}
