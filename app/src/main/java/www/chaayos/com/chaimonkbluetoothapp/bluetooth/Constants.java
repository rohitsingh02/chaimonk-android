package www.chaayos.com.chaimonkbluetoothapp.bluetooth;

/**
 * Created by rohitsingh on 17/07/16.
 */
public interface Constants {
    // Message types sent from the BluetoothService Handler
    int MESSAGE_STATE_CHANGE = 1;
    int MESSAGE_READ = 2;
    int MESSAGE_WRITE = 3;
    int MESSAGE_DEVICE_NAME = 4;
    int MESSAGE_TOAST = 5;
    int MESSAGE_COMPLETED = 6;

    enum ChaiMonkCommand {
        START, STOP, CLEAN, PAUSE, RESUME;
    }

    /*String CHAI_MONK_1 = "CHAI_MONK1";
    String CHAI_MONK_2 = "CHAI_MONK2";*/

    // Key names received from the BluetoothService Handler
    String DEVICE_NAME = "device_name";
    String TOAST = "toast";


}
