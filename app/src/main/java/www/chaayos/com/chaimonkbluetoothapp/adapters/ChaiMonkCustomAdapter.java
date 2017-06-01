package www.chaayos.com.chaimonkbluetoothapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.activities.ChaiMonkActivity;
import www.chaayos.com.chaimonkbluetoothapp.bluetooth.BluetoothChatService;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.ChaiMonk;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.TaskState;

/**
 * Created by rohitsingh on 23/07/16.
 */
public class ChaiMonkCustomAdapter extends RecyclerView.Adapter<ChaiMonkCustomAdapter.MonkItemsContentViewHolder> {
    private List<ChaiMonk> chaiMonkViewContentList = new ArrayList<>();
    private Context context;
    private Map<UUID,BluetoothChatService> bluetoothChatServiceArrayList;

    class MonkItemsContentViewHolder extends RecyclerView.ViewHolder {
        private TextView monkNumberTv;
        private TextView monkStatusTv;
        private TextView monkOrderItemNameTv;
        private TextView monkOrderItemNameQtyTv;
        private TextView monkOrderIds;
        private Button monkStartButton;
        private Button monkFreeButton;

        public MonkItemsContentViewHolder(View itemView) {
            super(itemView);
            monkNumberTv = (TextView) itemView.findViewById(R.id.monkNumberTv);
            monkStatusTv = (TextView) itemView.findViewById(R.id.monkStatusTv);
            monkOrderItemNameTv = (TextView) itemView.findViewById(R.id.monkOrderItemTv);
            monkOrderItemNameQtyTv = (TextView) itemView.findViewById(R.id.monkOrderItemQty);
            monkOrderIds = (TextView) itemView.findViewById(R.id.monkOrderIds);
            monkStartButton = (Button) itemView.findViewById(R.id.monkReadyBtn);
            monkFreeButton = (Button) itemView.findViewById(R.id.monkFreeBtn);
            context = itemView.getContext();
        }
    }

    public ChaiMonkCustomAdapter(List<ChaiMonk> chaiMonkViewContentList, Map<UUID,BluetoothChatService> bluetoothChatServiceArrayList) {
        this.chaiMonkViewContentList = chaiMonkViewContentList;
        this.bluetoothChatServiceArrayList = bluetoothChatServiceArrayList;
    }

    @Override
    public MonkItemsContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.monk_recyclerview_content, parent, false);
        return new MonkItemsContentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MonkItemsContentViewHolder holder, final int position) {
        final ChaiMonk chaiMonk = chaiMonkViewContentList.get(position);
        holder.monkNumberTv.setText(String.valueOf(chaiMonk.getMonkName()));
        holder.monkStatusTv.setText(chaiMonk.getCurrentStatus().toString());
        holder.monkOrderItemNameTv.setText(chaiMonk.getProductName()!=null ? chaiMonk.getProductName() : "");
        holder.monkOrderItemNameQtyTv.setText(chaiMonk.getQuantity()!=null ? ("x"+String.valueOf(chaiMonk.getQuantity())) : "");
        String orderIds = chaiMonk.getOrderIdList().size() > 0 ? Arrays.toString(chaiMonk.getOrderIdList().toArray()) : "";
        holder.monkOrderIds.setText(orderIds);

        if(chaiMonk.getCurrentStatus().equals(TaskState.COMPLETED)
                || chaiMonk.getCurrentStatus().equals(TaskState.CANCELLED)){
            holder.monkFreeButton.setVisibility(View.VISIBLE);
            holder.monkStartButton.setVisibility(View.INVISIBLE);
        } else if(chaiMonk.getCurrentStatus().equals(TaskState.STARTED)){
            holder.monkStartButton.setVisibility(View.INVISIBLE);
        } else if(chaiMonk.getCurrentStatus().equals(TaskState.WAITING)){
            holder.monkFreeButton.setVisibility(View.INVISIBLE);
            holder.monkStartButton.setVisibility(View.VISIBLE);
        }

        if(chaiMonk.getOrderIdList().size() == 0){
            holder.monkStartButton.setVisibility(View.INVISIBLE);
        }

        holder.monkStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(bluetoothChatServiceArrayList.size()>0){
                ((ChaiMonkActivity) context).removeCancelledOrders(chaiMonk);
                bluetoothChatServiceArrayList.get(chaiMonk.getUuid()).write(chaiMonk.toString().getBytes());
            }
            }
        });

        holder.monkFreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(context instanceof ChaiMonkActivity){
                chaiMonk.reset();
                ((ChaiMonkActivity) context).assignNewOrders();
            }
            }
        });
    }

    @Override
    public int getItemCount() {
        return chaiMonkViewContentList.size();
    }


}
