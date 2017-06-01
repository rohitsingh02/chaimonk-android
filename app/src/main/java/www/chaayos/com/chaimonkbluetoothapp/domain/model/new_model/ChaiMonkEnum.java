package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

/**
 * Created by rohitsingh on 23/07/16.
 */
public enum ChaiMonkEnum {

    CHAI_MONK1,
    CHAI_MONK2,
    CHAI_MONK3,
    CHAI_MONK4,
    CHAI_MONK5,
    CHAI_MONK6;

    public String value() {
        return name();
    }

    public static ChaiMonkEnum fromValue(String v) {
        return valueOf(v);
    }

}
