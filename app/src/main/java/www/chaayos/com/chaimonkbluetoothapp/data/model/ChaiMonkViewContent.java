package www.chaayos.com.chaimonkbluetoothapp.data.model;

/**
 * Created by rohitsingh on 23/07/16.
 */
public class ChaiMonkViewContent {

    private int monkNumberTv;
    private String monkStatusTv;
    private String monkOrderItemNameTv;
    private int monkOrderItemQtyTv;
    private String monkOrderIds;

    public ChaiMonkViewContent(int monkNumberTv, String monkStatusTv, String monkOrderItemNameTv, int monkOrderItemQtyTv, String monkOrderIds) {
        this.monkNumberTv = monkNumberTv;
        this.monkStatusTv = monkStatusTv;
        this.monkOrderItemNameTv = monkOrderItemNameTv;
        this.monkOrderItemQtyTv = monkOrderItemQtyTv;
        this.monkOrderIds = monkOrderIds;
    }

    public int getMonkNumberTv() {
        return monkNumberTv;
    }

    public void setMonkNumberTv(int monkNumberTv) {
        this.monkNumberTv = monkNumberTv;
    }

    public String getMonkStatusTv() {
        return monkStatusTv;
    }

    public void setMonkStatusTv(String monkStatusTv) {
        this.monkStatusTv = monkStatusTv;
    }

    public String getMonkOrderItemNameTv() {
        return monkOrderItemNameTv;
    }

    public void setMonkOrderItemNameTv(String monkOrderItemNameTv) {
        this.monkOrderItemNameTv = monkOrderItemNameTv;
    }

    public int getMonkOrderItemQtyTv() {
        return monkOrderItemQtyTv;
    }

    public void setMonkOrderItemQtyTv(int monkOrderItemQtyTv) {
        this.monkOrderItemQtyTv = monkOrderItemQtyTv;
    }

    public String getMonkOrderIds() {
        return monkOrderIds;
    }

    public void setMonkOrderIds(String monkOrderIds) {
        this.monkOrderIds = monkOrderIds;
    }
}
