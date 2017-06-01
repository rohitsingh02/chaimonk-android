package www.chaayos.com.chaimonkbluetoothapp.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.ChaiMonkEnum;

/**
 * Created by rohitsingh on 09/07/16.
 */
public class AppUtils {
    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Asia/Kolkata");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public  final static String BASE_URL = "http://dev.kettle.chaayos.com:9595/";
    public  final static String ANALYTICS_URL = "http://dev.kettle.chaayos.com:9595/";
    public  final static int DEBIT = 0;
    public  final static String TOAST = "toast";
    public  final static String DEVICE_NAME = "device";
    public  final static String VAT_ON_MRP = "VAT on MRP @";
    public  final static String VAT_ON_NETPRICE = "VAT on Net Price @";
    public  final static String SURCHARGE_ON_VAT = "Surcharge on VAT @";
    public  final static String SERVICE_TAX = "Service Tax @";
    public  final static String SB_CESS = "S.B. Cess @";
    public  final static String KK_CESS = "K.K. Cess @";

    public static Map<ChaiMonkEnum,UUID> initMonkMap(){
        Map<ChaiMonkEnum,UUID> monkNameUUIDMap = new HashMap<>();
        monkNameUUIDMap.put(ChaiMonkEnum.CHAI_MONK1,UUID.fromString("2d64189d-5a2c-4511-a074-77f199fd0834"));
        monkNameUUIDMap.put(ChaiMonkEnum.CHAI_MONK2,UUID.fromString("1e0ca4ea-299d-4335-93eb-27fcfe7fa848"));
        monkNameUUIDMap.put(ChaiMonkEnum.CHAI_MONK3,UUID.fromString("2d64189d-5a2c-4511-a074-77f199fd0835"));
        monkNameUUIDMap.put(ChaiMonkEnum.CHAI_MONK4,UUID.fromString("2d64189d-5a2c-4511-a074-77f199fd0836"));
        monkNameUUIDMap.put(ChaiMonkEnum.CHAI_MONK5,UUID.fromString("2d64189d-5a2c-4511-a074-77f199fd0837"));
        monkNameUUIDMap.put(ChaiMonkEnum.CHAI_MONK6,UUID.fromString("2d64189d-5a2c-4511-a074-77f199fd0838"));
        return monkNameUUIDMap;
    }

    public static String getCurrentBusinessDate() {
        Calendar calendar = Calendar.getInstance(TIME_ZONE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
        if(hour < 5){
            calendar.add(Calendar.DATE, -1);
        }
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        return DATE_FORMAT.format(calendar.getTime());
    }

    public static String getCurrentTimeStamp() {
        Calendar calendar = Calendar.getInstance(TIME_ZONE);
        return TIMESTAMP_FORMAT.format(calendar.getTime());
    }

    public static String getPreviousBusinessDate(){
        Calendar calendar = Calendar.getInstance(TIME_ZONE);
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return DATE_FORMAT.format(calendar.getTime());
    }

    public static BigDecimal getCeilingValue(BigDecimal value){
        return value.setScale(2, RoundingMode.CEILING);
    }

    public static void main(String[] args) {
        System.out.print(getCurrentBusinessDate());
    }


}

