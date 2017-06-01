package www.chaayos.com.chaimonkbluetoothapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import www.chaayos.com.chaimonkbluetoothapp.GlobalVariables;
import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.activities.CreateOrderActivity;
import www.chaayos.com.chaimonkbluetoothapp.adapters.CustomAdapter;
import www.chaayos.com.chaimonkbluetoothapp.data.model.OrderDetail;
import www.chaayos.com.chaimonkbluetoothapp.db.DatabaseAdapter;


public class ChaiMonkActivityJobQueueFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected CustomAdapter mAdapter;
    private Button startOrderButton;
    private DatabaseAdapter databaseAdapter;
    private List<OrderDetail> orderDetailArrayList = new ArrayList<>();
    private GlobalVariables globalVariables;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseAdapter = new DatabaseAdapter(getContext());
        globalVariables = (GlobalVariables)getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chai_monk_activity_job_queue, container, false);
        createOrderQueue();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mainRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        startOrderButton = (Button) rootView.findViewById(R.id.btnStartOrder);
        startOrderButton.setEnabled(false);
        startOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateOrderActivity.class);
                startActivity(intent);
            }
        });
        if (orderDetailArrayList != null) {
            mAdapter = new CustomAdapter(orderDetailArrayList, getContext());
            mRecyclerView.setAdapter(mAdapter);
        }
        return rootView;
    }


    public void createOrderQueue() {
        orderDetailArrayList = databaseAdapter.getJobQueueDetails(globalVariables.getEndOrderId());
    }


    @Override
    public void onResume() {
        super.onResume();

        orderDetailArrayList = databaseAdapter.getJobQueueDetails(globalVariables.getEndOrderId());
        if (orderDetailArrayList != null) {
            mAdapter = new CustomAdapter(orderDetailArrayList, getContext());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void onBeingNotifiedFromChaiMonkActivity(List<OrderDetail> orderDetailList) {
        this.orderDetailArrayList = orderDetailList;
        mAdapter = new CustomAdapter(orderDetailArrayList, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    public void enableStartButton() {
        startOrderButton.setEnabled(true);
    }

}
