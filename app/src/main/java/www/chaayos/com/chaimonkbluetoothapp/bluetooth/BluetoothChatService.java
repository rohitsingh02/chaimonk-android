package www.chaayos.com.chaimonkbluetoothapp.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.ChaiMonkEnum;
import www.chaayos.com.chaimonkbluetoothapp.utils.AppUtils;

/**
 * Created by rohitsingh on 17/07/16.
 */
public class BluetoothChatService {
    private static final String TAG = BluetoothChatService.class.getName();
    private static final String NAME_SECURE = "CHAI_MOMK";
    private static final int CHAI_MONK1 = 0;
    private static final int CHAI_MONK2 = 1;
    private final BluetoothAdapter mAdapter;
    private final Handler mHandler;
    private AcceptThread mSecureAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private String deviceName;
    private int mState;
    private UUID uuid;


    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device


    public BluetoothChatService(Context context, Handler mHandler) {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        //this.sendConnectedDeviceName = (SendConnectedDeviceName)context;
        this.mHandler = mHandler;

    }
    private  synchronized  void setState(int state,int deviceName){
        Log.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;

        mHandler.obtainMessage(Constants.MESSAGE_STATE_CHANGE, state, deviceName).sendToTarget();
    }

    /**
     * Return the current connection state.
     */
    public synchronized int getState() {
        return mState;
    }

    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume()
     */
  /*  public synchronized void start() {
        Log.d(TAG, "start");

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        // Start the thread to listen on a BluetoothServerSocket
        if (mSecureAcceptThread == null) {
            mSecureAcceptThread = new AcceptThread();
            mSecureAcceptThread.start();
        }
        setState(STATE_LISTEN,5);
    }
*/
    /**
     * Start the ConnectThread to initiate a connection to a remote device.
     *
     * @param device The BluetoothDevice to connect
     *
     */
    public synchronized void connect(BluetoothDevice device,UUID uuid) {
        Log.d(TAG, "connect to: " + device);
        this.uuid = uuid;
        deviceName = device.getName();
        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        try{
            mConnectThread = new ConnectThread(device,uuid,deviceName);
            mConnectThread.start();
            setState(STATE_CONNECTING,3);
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG,e.getMessage());
        }

    }

    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     *
     * @param socket The BluetoothSocket on which the connection was made
     * @param device The BluetoothDevice that has been connected
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice
            device) {
        Log.d(TAG, "connected");


        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
        //sendConnectedDeviceName.connectedDeviceName(device.getName());
        // Send the name of the connected device back to the UI Activity
        Message msg = mHandler.obtainMessage(Constants.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(AppUtils.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        if(device.getName().equals("CHAI_MONK1")){
            setState(STATE_CONNECTED,0);
        }else if(device.getName().equals("CHAI_MONK2")){
            setState(STATE_CONNECTED,1);
        }

    }

    /**
     * Stop all threads
     */
    public synchronized void stop() {
        Log.d(TAG, "stop");

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        if (mSecureAcceptThread != null) {
            mSecureAcceptThread.cancel();
            mSecureAcceptThread = null;
        }

        setState(STATE_NONE,4);
    }
    /**
     * Write to the ConnectedThread in an unsynchronized manner
     *
     * @param out The bytes to write
     * @see ConnectedThread#write(byte[])
     */
    public void write(byte[] out) {
        // Create temporary object



            try{
                ConnectedThread r;
                // Synchronize a copy of the ConnectedThread
                synchronized (this) {
                    if (mState != STATE_CONNECTED) return;
                    r = mConnectedThread;
                }
                r.write(out);

            }catch (Exception e){
                Log.d(TAG,e.getMessage());
            }


    }

    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private void connectionFailed() {

      //  setState(STATE_LISTEN,5);
        this.stop();
        Message msg = mHandler.obtainMessage(Constants.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        if(deviceName.equals(ChaiMonkEnum.CHAI_MONK1.toString())){
            bundle.putInt("TOAST",2);
        }else if(deviceName.equals(ChaiMonkEnum.CHAI_MONK2.toString())){
            bundle.putInt("TOAST", 3);
        }
        msg.setData(bundle);
        mHandler.sendMessage(msg);


    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    private void connectionLost(String deviceName) {
        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(Constants.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        if(deviceName.equals(ChaiMonkEnum.CHAI_MONK1.toString())){
            bundle.putInt("TOAST",0);
        }else if(deviceName.equals(ChaiMonkEnum.CHAI_MONK2.toString())){
            bundle.putInt("TOAST", 1);
        }
        msg.setData(bundle);
        mHandler.sendMessage(msg);



        // Start the service over to restart listening mode
        //BluetoothChatService.this.start();
    }

    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    private class AcceptThread extends Thread {
        BluetoothServerSocket serverSocket = null;
        private String mSocketType;

        public AcceptThread() {

        }

        public void run() {
            Log.d(TAG, "Socket Type: " + mSocketType +
                    "BEGIN mAcceptThread" + this);
            setName("AcceptThread" + mSocketType);
            BluetoothSocket socket = null;
            // Listen to the server socket if we're not connected
            try{

                    serverSocket = mAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE,uuid);
                    socket = serverSocket.accept();
                    if(socket != null){
                        String address = socket.getRemoteDevice().getAddress();
                        connected(socket,socket.getRemoteDevice());
                    }

            }catch (IOException e){
                Log.e(TAG,e.getMessage() + "Accept Failed");
            }
            Log.i(TAG, "END mAcceptThread, socket Type: " + mSocketType);

        }

        public void cancel() {
            Log.d(TAG, "Socket Type" + mSocketType + "cancel " + this);
            try {
                serverSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Socket Type" + mSocketType + "close() of server failed", e);
            }
        }
    }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private UUID tempUuid;


        public ConnectThread(BluetoothDevice device, UUID uuidToTry,String deviceName) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            tempUuid = uuidToTry;

            try {

                tmp = device.createRfcommSocketToServiceRecord(uuidToTry);

            } catch (IOException e) {
                Log.e(TAG, "Socket Type: " + "create() failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectThread SocketType:" );
            setName("ConnectThread" );
            // Always cancel discovery because it will slow down a connection
            mAdapter.cancelDiscovery();
            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();

            } catch (Exception e) {
                    // Close the socket
                    connectionFailed();
                try{
                    mmSocket.close();
                }catch (IOException ex){
                    Log.e(TAG,"Unable to close socket" + ex.getMessage());
                }
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (BluetoothChatService.this) {
                mConnectThread = null;
            }

            // Start the connected thread
            connected(mmSocket, mmDevice);


        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect " +  " socket failed", e);
            }
        }
    }

    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    private class ConnectedThread extends Thread {
        private BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "create ConnectedThread: " );

            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[256];
            int bytes;

            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);

                    // Send the obtained bytes to the UI Activity
                    mHandler.obtainMessage(Constants.MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    Log.e(TAG, "disconnect"+deviceName ,e );
                    connectionLost(deviceName);
                    break;
                }
            }
        }

        /**
         * Write to the connected OutStream.
         * <p/>
         * buffer The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);

                // Share the sent message back to the UI Activity
                mHandler.obtainMessage(Constants.MESSAGE_WRITE, -1, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }


}
