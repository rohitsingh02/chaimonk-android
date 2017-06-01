package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

/**
 * Created by rohitsingh on 29/07/16.
 */
public class PiCommunication {
    private String uuid;
    private TaskState monkStatus;

    public PiCommunication(String uuid, String monkStatus) {
        this.uuid = uuid;
        this.monkStatus = TaskState.valueOf(monkStatus);
    }

    public TaskState getMonkStatus() {
        return monkStatus;
    }

    public void setMonkStatus(TaskState monkStatus) {
        this.monkStatus = monkStatus;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
