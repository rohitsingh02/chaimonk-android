package www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model;

/**
 * Created by rohitsingh on 25/07/16.
 */
public enum DimensionEnum {

    Regular,
    Full,
    ChotiKetli,
    BadiKetli;

    public String value() {
        return name();
    }

    public static DimensionEnum fromValue(String v) {
        return valueOf(v);
    }

}
