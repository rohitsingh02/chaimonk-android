package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a list of WorkTasks. Represents all the tasks of one type in order. For example: all the HOT tasks in an order.
 */
public class WorkTaskCategory {

    private final ProductType mType;
    protected String generatedOrderId;
    protected String orderRemark;
    private List<WorkTask> workTasks = new ArrayList<WorkTask>();

    public WorkTaskCategory(ProductType mType) {
        this.mType = mType;
    }

    public String getGeneratedOrderId() {
        return generatedOrderId;
    }

    public void setGeneratedOrderId(String generatedOrderId) {
        this.generatedOrderId = generatedOrderId;
    }

    public ProductType getType() {
        return mType;
    }


    public List<WorkTask> getWorkTasks() {
        return workTasks;
    }

    public void setWorkTasks(List<WorkTask> mWorkTasks) {
        this.workTasks = mWorkTasks;
    }

    public void addWorkTask(WorkTask workTask) {
        workTasks.add(workTask);
    }


    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    @Override
    public String toString() {
        return "WorkTaskCategory: " + mType + " " + generatedOrderId + " " + workTasks;
    }
}
