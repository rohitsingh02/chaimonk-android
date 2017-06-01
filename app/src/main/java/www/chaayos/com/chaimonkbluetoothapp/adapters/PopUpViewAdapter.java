package www.chaayos.com.chaimonkbluetoothapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.data.model.OrderItem;


/**
 * Created by rohitsingh on 23/07/16.
 */
public class PopUpViewAdapter extends RecyclerView.Adapter<PopUpViewAdapter.OrderItemContentViewHolder>  {
    private List<OrderItem> orderItemArrayList = new ArrayList<>();
    private Context context;

    class OrderItemContentViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTv;
        TextView itemQuantityTv;
        TextView totalAmountTv;
        TextView orderItemStatus;

        public OrderItemContentViewHolder(View itemView) {
            super(itemView);
            itemNameTv = (TextView) itemView.findViewById(R.id.itemNameTv);
            itemQuantityTv = (TextView) itemView.findViewById(R.id.itemQuantityTv);
            totalAmountTv = (TextView) itemView.findViewById(R.id.amountTv);
            orderItemStatus = (TextView) itemView.findViewById(R.id.order_item_status);
            context = itemView.getContext();
        }
    }


   public PopUpViewAdapter(List<OrderItem> mOrderItemList){
        this.orderItemArrayList = mOrderItemList;
    }

    @Override
    public PopUpViewAdapter.OrderItemContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_up_activity_list_content, parent, false);
        return new OrderItemContentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PopUpViewAdapter.OrderItemContentViewHolder holder, int position) {
        holder.itemNameTv.setText(orderItemArrayList.get(position).getProductName());
        holder.itemQuantityTv.setText("x" +String.valueOf(orderItemArrayList.get(position).getQuantity()));
        holder.orderItemStatus.setText("Status: " + orderItemArrayList.get(position).getStatus());
        holder.totalAmountTv.setText(String.valueOf(orderItemArrayList.get(position).getPrice()*orderItemArrayList.get(position).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return orderItemArrayList.size();
    }
}
