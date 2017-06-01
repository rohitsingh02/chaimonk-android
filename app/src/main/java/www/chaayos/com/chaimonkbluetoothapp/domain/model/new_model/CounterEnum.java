package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

/**
 * Created by rohitsingh on 24/07/16.
 */
public enum CounterEnum {
    G_ORDER_ID,
    TOKEN_ID,
    WORK_ITEM_ID;

    public String value() {
        return name();
    }

    public static CounterEnum fromValue(String v) {
        return valueOf(v);
    }
}
