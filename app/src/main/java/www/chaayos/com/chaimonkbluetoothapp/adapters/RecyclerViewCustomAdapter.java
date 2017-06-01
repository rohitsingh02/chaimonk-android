package www.chaayos.com.chaimonkbluetoothapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderItem;

/**
 * Created by rohitsingh on 05/07/16.
 */
public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.OrderItemViewHolder>  {
    private ArrayList<OrderItem> mOrderItemList;
    private Context context;
    private OrderItemChangedNotifier orderItemChangedNotifier ;

    class OrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemQuantityTextView;
        TextView totalAmountTextView;
        ImageButton addImageButton;
        ImageButton subImageButton;
        ImageButton deleteItemButton;


        public OrderItemViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = (TextView) itemView.findViewById(R.id.itemName);
            itemQuantityTextView = (TextView) itemView.findViewById(R.id.itemQuantity);
            totalAmountTextView = (TextView) itemView.findViewById(R.id.amount);
            addImageButton = (ImageButton) itemView.findViewById(R.id.addImageButton);
            subImageButton = (ImageButton) itemView.findViewById(R.id.subImageButton);
            deleteItemButton = (ImageButton) itemView.findViewById(R.id.deleteItemButton);
            context = itemView.getContext();
        }
    }

    public RecyclerViewCustomAdapter(OrderItemChangedNotifier orderItemChanged,ArrayList<OrderItem> orderItemList){
        orderItemChangedNotifier = orderItemChanged;
        mOrderItemList = orderItemList;

    }
    @Override
    public RecyclerViewCustomAdapter.OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_order_recyclerview_content,parent,false);
        return new OrderItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewCustomAdapter.OrderItemViewHolder holder, final int position) {
        Log.d("RecyclerViewCustom", "Element " + position + " set.");
        holder.itemNameTextView.setText(mOrderItemList.get(position).getProductName());
        holder.itemQuantityTextView.setText("" + mOrderItemList.get(position).getQuantity());
        holder.totalAmountTextView.setText("" + mOrderItemList.get(position).getTotalAmount());
        holder.addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = mOrderItemList.get(position).getQuantity()+ 1;
                if(quantity < 15) {
                    mOrderItemList.get(position).setQuantity(quantity);
                    holder.itemQuantityTextView.setText(String.valueOf(mOrderItemList.get(position).getQuantity()));
                    System.out.print(mOrderItemList.get(position).getPrice());
                    holder.totalAmountTextView.setText(mOrderItemList.get(position).getPrice().multiply(new BigDecimal(mOrderItemList.get(position).getQuantity())).toString());
                    mOrderItemList.get(position).setTotalAmount(mOrderItemList.get(position).getPrice().multiply(new BigDecimal(mOrderItemList.get(position).getQuantity())));
                }
            }
        });

        holder.subImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOrderItemList.get(position).getQuantity() > 1){
                   mOrderItemList.get(position).setQuantity(mOrderItemList.get(position).getQuantity() - 1);
                    mOrderItemList.get(position).setTotalAmount(mOrderItemList.get(position).getPrice().multiply(new BigDecimal(mOrderItemList.get(position).getQuantity())));

                    holder.itemQuantityTextView.setText(String.valueOf(mOrderItemList.get(position).getQuantity()));
                   holder.totalAmountTextView.setText(String.valueOf(mOrderItemList.get(position).getPrice().multiply(new BigDecimal(mOrderItemList.get(position).getQuantity()))));
                }else{

                   holder.itemQuantityTextView.setText(String.valueOf(mOrderItemList.get(position).getQuantity()));
                }
            }
        });
        holder.deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mOrderItemList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mOrderItemList.size());
                orderItemChangedNotifier.itemChanged(mOrderItemList);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrderItemList.size();
    }



    public interface OrderItemChangedNotifier{
         void itemChanged(ArrayList<OrderItem> updatedOrderItemList);
    }

}
