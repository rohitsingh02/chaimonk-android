package www.chaayos.com.chaimonkbluetoothapp;

/**
 * Created by rohitsingh on 10/07/16.
 */
public interface ResponseCallback<R,E> {
    void beforeRequest();
    void onResponse(R response);
    void onError(E error);
}
