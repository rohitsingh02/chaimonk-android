package www.chaayos.com.chaimonkbluetoothapp.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.activities.ChaiMonkActivity;
import www.chaayos.com.chaimonkbluetoothapp.activities.JobQueueContentPopUpActivity;
import www.chaayos.com.chaimonkbluetoothapp.data.model.OrderDetail;
import www.chaayos.com.chaimonkbluetoothapp.data.model.OrderItem;
import www.chaayos.com.chaimonkbluetoothapp.db.DatabaseAdapter;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.ChaiMonk;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderStatus;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.TaskState;
import www.chaayos.com.chaimonkbluetoothapp.management.service.ChaiMonkManager;
import www.chaayos.com.chaimonkbluetoothapp.utils.AppUtils;

/**
 * Created by rohitsingh on 05/07/16.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.OrderViewHolder> {
    private static final String TAG = "CustomAdapter";
    private List<OrderDetail> orderDetailArrayList;
    private Context context;
    private DatabaseAdapter databaseAdapter;
    private static int counter = 0;
    //private OrderDetail orderDetail;



    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nameTextView;
        TextView timeLeftTextView;
        TextView totalAmountTextView;
        TextView orderStatusTextView;
        ImageButton settleOrderButton;
        ImageButton cancelOrderButton;
        TextView tokenTv;

        public OrderViewHolder(final View itemView){
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            timeLeftTextView = (TextView) itemView.findViewById(R.id.timeLeftTextView);
            totalAmountTextView = (TextView) itemView.findViewById(R.id.totalAmountTextView);
            tokenTv = (TextView) itemView.findViewById(R.id.tokenTextView);
            settleOrderButton = (ImageButton) itemView.findViewById(R.id.dispatchButton);
            orderStatusTextView = (TextView) itemView.findViewById(R.id.orderStatus);
            cancelOrderButton = (ImageButton) itemView.findViewById(R.id.cancelOrderButton);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(v.getContext(),JobQueueContentPopUpActivity.class);
            OrderDetail orderDetail = orderDetailArrayList.get(getAdapterPosition());
            Gson gson = new Gson();
            intent.putExtra("ORDERARRAYLIST",gson.toJson(orderDetail));
            context.startActivity(intent);
        }
    }

    public CustomAdapter(List<OrderDetail> orderDetailArrayList, Context context){
       this.orderDetailArrayList = orderDetailArrayList;
        this.context = context;
        databaseAdapter = new DatabaseAdapter(context);
    }
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_queue_fragment_list_content,parent,false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {
        Boolean isTrue = false;
        Log.d(TAG, "Element " + position + " set.");
        holder.nameTextView.setText(orderDetailArrayList.get(position).getCustomerName());
        holder.timeLeftTextView.setText(String.valueOf(orderDetailArrayList.get(position).getGeneratedOrderId()));
        holder.totalAmountTextView.setText(String.valueOf(orderDetailArrayList.get(position).getTotalAmount()));
        holder.tokenTv.setText("Token : " + String.valueOf(orderDetailArrayList.get(position).getToken()));

        Set<OrderStatus> orderItemStatuses = new HashSet<>();
        for(OrderItem orderItem : orderDetailArrayList.get(position).getOrderItems()){
            if(orderItem!=null && orderItem.getStatus()!=null){
                orderItemStatuses.add(OrderStatus.valueOf(orderItem.getStatus()));
            }
        }

        if(orderItemStatuses.size()==1 && orderItemStatuses.contains(OrderStatus.SETTLED)){
            isTrue = true;
        }

        if(isTrue){
            holder.orderStatusTextView.setText("Status: " + OrderStatus.SETTLED.toString()) ;
            OrderDetail orderDetail = orderDetailArrayList.get(position);
            String timestamp =  AppUtils.getCurrentTimeStamp();
            orderDetail.setBillCompletionTime(timestamp);
            databaseAdapter.updateCompletionTime(orderDetail.getOrderId(),timestamp);
        }
        else{
            holder.orderStatusTextView.setText("Status: " + orderDetailArrayList.get(position).getOrderStatus()) ;
        }
        holder.settleOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDetail orderDetail = orderDetailArrayList.get(position);
                orderDetail.setOrderStatus(OrderStatus.SETTLED);
                String timestamp = AppUtils.getCurrentTimeStamp();
                orderDetail.setBillSettlementTime(timestamp);
                if(context instanceof  ChaiMonkActivity){
                    ((ChaiMonkActivity) context).syncOrder(orderDetail);
                }
                databaseAdapter.updateOrderStatus(orderDetail.getOrderId());
                databaseAdapter.updateSettlementTime(orderDetail.getOrderId(), timestamp);
                Snackbar.make(v, "Order with orderId: " + orderDetail.getGeneratedOrderId() + " has been dispatched successfully", Snackbar.LENGTH_LONG).show();
                orderDetailArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,orderDetailArrayList.size());
            }
        });

        if(context instanceof  ChaiMonkActivity){
            ChaiMonkManager chaiMonkManager = ((ChaiMonkActivity) context).getMonkManager();
            if(chaiMonkManager!=null && chaiMonkManager.getAllMonks() != null){
                for(ChaiMonk chaiMonk : ((ChaiMonkActivity) context).getMonkManager().getAllMonks()){
                    if(chaiMonk.getCurrentStatus().equals(TaskState.STARTED)
                            &&  chaiMonk.getOrderIdList().contains(orderDetailArrayList.get(position).getOrderId())){
                        holder.cancelOrderButton.setVisibility(View.GONE);
                    }
                }
            }
        }

        holder.cancelOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dayCloseAlertBox = new AlertDialog.Builder(context).setMessage("Are You Sure you want to cancel this order.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String timestamp = AppUtils.getCurrentTimeStamp();
                                databaseAdapter.updateOrderStatusToCancelled(orderDetailArrayList.get(position).getOrderId());
                                databaseAdapter.updateCancellationTime(orderDetailArrayList.get(position).getOrderId(), timestamp);
                                OrderDetail orderDetail = orderDetailArrayList.get(position);
                                orderDetail.setOrderStatus(OrderStatus.CANCELLED);
                                orderDetail.setBillCancellationTime(timestamp);
                                if(context instanceof  ChaiMonkActivity){
                                    ((ChaiMonkActivity) context).syncOrder(orderDetail);
                                }
                                orderDetailArrayList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,orderDetailArrayList.size());
                                dialog.dismiss();

                                if(context instanceof ChaiMonkActivity){
                                    ChaiMonkActivity chaiMonkActivity =(ChaiMonkActivity) context;
                                    chaiMonkActivity.setOrderDetailArrayList(orderDetailArrayList);
                                    if(orderDetailArrayList.size()==0){
                                       chaiMonkActivity.resetMonks();
                                       chaiMonkActivity.refreshAdapterView(chaiMonkActivity.getMonkManager().getAllMonks());
                                    }
                                }
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dayCloseAlertBox.show();

            }
        });

    }
    @Override
    public int getItemCount() {
        return orderDetailArrayList.size();
    }

}
