package www.chaayos.com.chaimonkbluetoothapp.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import www.chaayos.com.chaimonkbluetoothapp.bluetooth.Constants;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.PiCommunication;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.TaskState;

/**
 * Created by shikhar on 17-07-2016.
 */
public class ChaiMonk {

    protected UUID uuid;
    protected String monkName;
    protected TaskState currentStatus;
    protected String productName;
    protected Integer productId;
    protected Integer workItemId;
    protected Integer quantity;
    protected String dimension;
    protected List<Integer> orderIdList;
    protected Constants.ChaiMonkCommand command;
    protected PiCommunication piCommunication;

    public ChaiMonk() {}

    public ChaiMonk(UUID uuid, String monkName, TaskState currentStatus) {
        this.uuid = uuid;
        this.monkName = monkName;
        this.currentStatus = currentStatus;
    }

    public String getMonkName() {
        return monkName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public TaskState getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(TaskState currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<Integer> getOrderIdList() {
        if(this.orderIdList==null){
            this.orderIdList = new ArrayList<>();
        }
        return this.orderIdList;
    }


    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public Constants.ChaiMonkCommand getCommand() {
        return command;
    }

    public void setCommand(Constants.ChaiMonkCommand command) {
        this.command = command;
    }

    public Integer getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(Integer workItemId) {
        this.workItemId = workItemId;
    }


    public PiCommunication getPiCommunication() {
        return piCommunication;
    }

    public void setPiCommunication(PiCommunication piCommunication) {
        this.piCommunication = piCommunication;
    }


    @Override
    public String toString() {
        return command + "#" +workItemId + "#" + productId +"#"+ productName +
                "#" + dimension + "#" + quantity + "#" + currentStatus ;
    }

    public void reset() {
        this.setWorkItemId(null);
        this.setCommand(null);
        this.setDimension(null);
        this.setProductId(null);
        this.setQuantity(null);
        this.setProductId(null);
        this.setProductName(null);
        this.getOrderIdList().clear();
        this.setCurrentStatus(TaskState.IDLE);
    }


}
