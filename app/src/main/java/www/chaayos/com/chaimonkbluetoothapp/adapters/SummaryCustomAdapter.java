package www.chaayos.com.chaimonkbluetoothapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.OrderItemsSummary;

/**
 * Created by rohitsingh on 04/08/16.
 */
public class SummaryCustomAdapter extends RecyclerView.Adapter<SummaryCustomAdapter.SummaryViewHolder> {
  private Context context;
  private List<OrderItemsSummary> orderItemsSummaryList;
    class SummaryViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productQuantity;
        TextView totalAmount;


        public SummaryViewHolder(final View itemView){
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productQuantity = (TextView) itemView.findViewById(R.id.productQuantity);
            totalAmount = (TextView) itemView.findViewById(R.id.productTotalAmount);
        }

    }

    public SummaryCustomAdapter(List<OrderItemsSummary> orderItemsSummaryList ,Context context){
        this.orderItemsSummaryList = orderItemsSummaryList;
        this.context = context;

    }

    @Override
    public SummaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_summary_rvcontent,parent,false);
        return new SummaryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SummaryViewHolder holder, int position) {

        holder.productName.setText(orderItemsSummaryList.get(position).getProductName());
        holder.productQuantity.setText(String.valueOf(orderItemsSummaryList.get(position).getProductQuantity()));
        holder.totalAmount.setText(String.valueOf(orderItemsSummaryList.get(position).getProductTotalAmount()));
    }

    @Override
    public int getItemCount() {
        return orderItemsSummaryList.size();
    }


}
