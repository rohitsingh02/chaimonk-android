package www.chaayos.com.chaimonkbluetoothapp.data.model;

/**
 * Created by rohitsingh on 23/08/16.
 */
public class ConnectionLog {
    private String currentDate;
    private String deviceName;
    private String connectionTime;
    private String disconnectionTime;

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(String connectionTime) {
        this.connectionTime = connectionTime;
    }

    public String getDisconnectionTime() {
        return disconnectionTime;
    }

    public void setDisconnectionTime(String disconnectionTime) {
        this.disconnectionTime = disconnectionTime;
    }

    @Override
    public String toString() {
        return "CurrentDate: " + getCurrentDate() + ", DeviceName: " + getDeviceName() + ", ConnectionTime: " + getConnectionTime() + ", DisconnectTime: " + getDisconnectionTime();
    }
}
