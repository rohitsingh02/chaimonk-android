package www.chaayos.com.chaimonkbluetoothapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderItem;

/**
 * Created by rohitsingh on 06/07/16.
 */
public class OrderSummaryCustomAdapter extends RecyclerView.Adapter<OrderSummaryCustomAdapter.OrderItemViewHolder> {
    private ArrayList<OrderItem> mOrderItemList;
    Context context;
    private BigDecimal subTotalAmount;
    public BigDecimal getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(BigDecimal subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemQuantityTextView;
        TextView totalAmountTextView;

        public OrderItemViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = (TextView) itemView.findViewById(R.id.itemName);
            itemQuantityTextView = (TextView) itemView.findViewById(R.id.itemQuantity);
            totalAmountTextView = (TextView) itemView.findViewById(R.id.amount);
            context = itemView.getContext();
        }
    }

    public OrderSummaryCustomAdapter(ArrayList<OrderItem> mOrderItemList) {
        this.mOrderItemList = mOrderItemList;
        this.setSubTotalAmount(calculateSubTotalAmount());
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.summary_recycleview_content, parent, false);
        return new OrderItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderItemViewHolder holder, int position) {
        holder.itemNameTextView.setText(mOrderItemList.get(position).getProductName());
        holder.itemQuantityTextView.setText(String.valueOf("x" + mOrderItemList.get(position).getQuantity()));
        BigDecimal amount = mOrderItemList.get(position).getPrice().multiply(BigDecimal.valueOf(mOrderItemList.get(position).getQuantity()));
        holder.totalAmountTextView.setText("Rs " +String.valueOf(amount.setScale(2,BigDecimal.ROUND_HALF_UP)));
    }
    @Override
    public int getItemCount() {
        return mOrderItemList.size();
    }
    public BigDecimal calculateSubTotalAmount() {
        BigDecimal count =  new BigDecimal(0);
        for (int i = 0; i < mOrderItemList.size(); i++) {
            count =  mOrderItemList.get(i).getTotalAmount().multiply(new BigDecimal(mOrderItemList.get(i).getQuantity())).add(count);
        }
        return count;
    }
}
