package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

/**
 * orderId
 * ItemId
 * billingServerTime,
 * unitId,
 * type
 * employeeId
 * productId,
 * itemQuantity,
 * dimension,
 * orderSource,
 * timeToAcknowledge,
 * timeToStart
 * timeToProcess,
 * timeToRemove,
 * timeToDispatch
 * timeToProcessByMachine = timeToProcessByMachine + timeToDispacth,
 * cooktop station
 * stationEventsForOrder
 */
public class WorkTask extends BaseEntity {

    protected int taskId;//orderItemId
    protected String orderId;//generatedOrderId
    protected OrderItem orderItem;
    protected boolean combo;
    protected long orderCreationTime;
    protected long taskCreationTime;
    protected String customerName;
    protected TaskState state;
    protected String type;
    protected int productId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public WorkTask() {
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }


    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public boolean isCombo() {
        return combo;
    }

    public void setCombo(boolean mCombo) {
        this.combo = mCombo;
    }

    public long getOrderCreationTime() {
        return orderCreationTime;
    }

    public void setOrderCreationTime(long orderCreationTime) {
        this.orderCreationTime = orderCreationTime;
    }

    public long getTaskCreationTime() {
        return taskCreationTime;
    }

    public void setTaskCreationTime(long taskCreationTime) {
        this.taskCreationTime = taskCreationTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof WorkTask) {
            return (((WorkTask) obj).getTaskId() == this.getTaskId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Work task: " + taskId + " " + orderItem.getProductName();
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
