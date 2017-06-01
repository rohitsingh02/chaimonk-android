package www.chaayos.com.chaimonkbluetoothapp.management.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import www.chaayos.com.chaimonkbluetoothapp.bluetooth.Constants;
import www.chaayos.com.chaimonkbluetoothapp.data.model.OrderItem;
import www.chaayos.com.chaimonkbluetoothapp.db.DatabaseAdapter;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.ChaiMonk;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.CounterEnum;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.TaskState;

/**
 * Created by shikhar on 18-07-2016.
 */
public class ChaiMonkManager {
    private static ChaiMonkManager instance = null;
    private Map<UUID,ChaiMonk> chaiMonks = new HashMap<>();

    private ChaiMonkManager(){}

    public static ChaiMonkManager getInstance(){
        if(instance==null){
            instance = new ChaiMonkManager();
        }
        return instance;
    }


    public void removeMonk(UUID uuid){
        if(instance.chaiMonks.get(uuid)!=null){
            instance.chaiMonks.remove(uuid);
        }
    }

    public Map<TaskState,List<ChaiMonk>> findFreeMonks(){
        Map<TaskState,List<ChaiMonk>> freeMonks = new HashMap<>();
        for(ChaiMonk chaiMonk : instance.chaiMonks.values()){
            List<ChaiMonk> monksOfType = freeMonks.get(chaiMonk.getCurrentStatus());
            if (monksOfType == null) {
                monksOfType = new ArrayList<>();
            }
            monksOfType.add(chaiMonk);
            freeMonks.put(chaiMonk.getCurrentStatus(),monksOfType);
        }
        return freeMonks;
    }

    public ChaiMonk getChaiMonk(UUID uuid){
        return instance.chaiMonks.get(uuid);
    }

    public static void setChaiMonks(List<ChaiMonk> chaiMonkList){
        for(ChaiMonk chaiMonk : chaiMonkList){
            instance.chaiMonks.put(chaiMonk.getUuid(),chaiMonk);
        }
    }

    public ChaiMonk assignToMonk(Map<TaskState, List<ChaiMonk>> monks, OrderItem orderItem, Integer orderId, DatabaseAdapter databaseAdapter) {
        ChaiMonk assignedMonk = null;
        if(monks.get(TaskState.WAITING)!=null){
            for(ChaiMonk monk : monks.get(TaskState.WAITING)){
                int threshold = monk.getQuantity() + orderItem.getQuantity();
                if(threshold <= 14
                        && monk.getProductId().equals(orderItem.getProductId())){
                    assignedMonk = monk;
                    if(!assignedMonk.getOrderIdList().contains(orderId)){
                        orderItem.setWorkItemId(monk.getWorkItemId());
                        monk.getOrderIdList().add(orderId);
                        monk.setQuantity(threshold);
                    }
                    break;
                }
            }
        }

        if(assignedMonk==null){
            assignedMonk = monks.get(TaskState.IDLE)!=null ? monks.get(TaskState.IDLE).get(0):null;
            if(assignedMonk != null){
                Integer workItem = databaseAdapter.getDataFromCounterTable().get(CounterEnum.WORK_ITEM_ID.toString());
                orderItem.setWorkItemId(workItem);
                assignedMonk.setWorkItemId(workItem);
                assignedMonk.setQuantity(orderItem.getQuantity() >= 14 ? 14 : orderItem.getQuantity());
                assignedMonk.setProductId(orderItem.getProductId());
                assignedMonk.setProductName(orderItem.getProductName());
                assignedMonk.setCurrentStatus(TaskState.WAITING);
                assignedMonk.setDimension(orderItem.getDimension()!=null ? orderItem.getDimension() : "Regular");
                assignedMonk.getOrderIdList().add(orderId);
                assignedMonk.setCommand(Constants.ChaiMonkCommand.START);
                databaseAdapter.updateWorkItemId(); // update counter of work item id label in database
            }
        }

        if(assignedMonk!=null){
            instance.chaiMonks.put(assignedMonk.getUuid(),assignedMonk);
        }
        return assignedMonk;
    }

    public List<ChaiMonk> getAllMonks() {
        return new ArrayList<>(instance.chaiMonks.values());
    }

    public void setChaiMonk(UUID uuid, ChaiMonk chaiMonk) {
        instance.chaiMonks.put(uuid,chaiMonk);
    }
}
