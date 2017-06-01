package www.chaayos.com.chaimonkbluetoothapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import www.chaayos.com.chaimonkbluetoothapp.domain.model.LoginCredentials;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.UserSessionDetail;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Unit;


/**
 * Created by rohitsingh on 10/07/16.
 */
public class PreferenceManager {
    public static final Gson JSON_CONVERTER = new Gson();
    public static final String PREFERENCE_NAME = "chai_monk_preferences";
    public static final String PREFERENCE_NAME_CREDENTIALS = "chaimonk!@#";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_UNIT_ID = "unitId";
    public static final String KEY_TERMINAL_ID = "terminalId";
    public static final String KEY_SESSION_ID = "sessionKeyId";
    public static final String KEY_CREDENTIAL_ID = "credential_id";
    public static final String KEY_CREDENTIAL_PASSWORD = "credential_password";
    public static final String KEY_SELECTED_UNIT_ID = "selected_unit_id";
    public static final String KEY_SELECTED_UNIT_NAME = "selected_unit_name";
    public static final String KEY_SELECTED_USERNAME = "selected_username";
    public static final String KEY_UNIT_OBJECT = "unit_object";
    public static final String JWT_TOKEN = "jwt_token";
    public static final String LAST_SYNCED_ORDER_ID = "last_synced_order";
    public static final String UNITS_LIST = "units_list";
    public static final String END_ID = "end_id";
    public static final String LAST_ORDER_DETAIL_ID = "last_order_detail_id";
    public static final String KEY_TRANSACTION_METADATA_OBJECT = "transaction_metadata_object";

    public static void saveDetailsInPreferences(Activity activity, UserSessionDetail detail) {
        SharedPreferences preferences = activity.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_USER_ID, detail.getUserId());
        editor.putInt(KEY_UNIT_ID, detail.getUnitId());
        editor.putString(KEY_SELECTED_USERNAME,detail.getUser().getName());
        editor.putString(KEY_SESSION_ID, detail.getSessionKeyId());
        editor.apply();
    }

    public static UserSessionDetail getUserDetail(Activity activity) {
        UserSessionDetail detail = new UserSessionDetail();
        SharedPreferences pref = activity.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        detail.setUnitId(pref.getInt(KEY_UNIT_ID, -1));
        detail.setUserId(pref.getInt(KEY_USER_ID, -1));
        detail.setTerminalId(pref.getInt(KEY_TERMINAL_ID, -1));
        detail.setUserName(pref.getString(KEY_SELECTED_USERNAME,""));
        detail.setSessionKeyId(pref.getString(KEY_SESSION_ID, ""));
        return detail;
    }

    public static void saveCredentials(Activity activity, LoginCredentials loginCredentials) {
        SharedPreferences preferences = activity.getSharedPreferences(PREFERENCE_NAME_CREDENTIALS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_CREDENTIAL_ID, loginCredentials.getUserId());
        editor.putString(KEY_CREDENTIAL_PASSWORD, loginCredentials.getPassword());
        editor.putString(KEY_SELECTED_UNIT_ID, loginCredentials.getUnitId());
        editor.putString(KEY_SELECTED_UNIT_NAME, loginCredentials.getUnitName());
        editor.putString(JWT_TOKEN, loginCredentials.getJwtToken());
        editor.apply();
    }

    public static LoginCredentials getCredentials(Activity activity) {
        LoginCredentials loginCredentials = new LoginCredentials();
        SharedPreferences pref = activity.getSharedPreferences(PREFERENCE_NAME_CREDENTIALS, Activity.MODE_PRIVATE);
        loginCredentials.setUserId(pref.getString(KEY_CREDENTIAL_ID, ""));
        loginCredentials.setPassword( pref.getString(KEY_CREDENTIAL_PASSWORD, ""));
        loginCredentials.setUnitId( pref.getString(KEY_SELECTED_UNIT_ID, ""));
        loginCredentials.setUnitName( pref.getString(KEY_SELECTED_UNIT_NAME, ""));
        return loginCredentials;
    }

    public static void saveUnitsListObject(Activity activity, String unitsList) {
        SharedPreferences preferences = activity.getSharedPreferences(PREFERENCE_NAME_CREDENTIALS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(UNITS_LIST, unitsList);
        editor.apply();
    }

    public static String getUnitsListObject(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences(PREFERENCE_NAME_CREDENTIALS, Activity.MODE_PRIVATE);
        return pref.getString(UNITS_LIST, "");
    }

    public static void saveSyncedOrderId(Activity activity,Integer orderId) {
        SharedPreferences preferences = activity.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LAST_SYNCED_ORDER_ID, orderId.toString());
        editor.apply();
    }



    public static Unit getUnitObject(Activity activity) {

        SharedPreferences pref = activity.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        String unitObject = pref.getString(KEY_UNIT_OBJECT,null);
        return unitObject!=null ? JSON_CONVERTER.fromJson(unitObject,Unit.class) : null;
    }

    public static void  saveUnitObject(Activity activity,String unitObject) {
        SharedPreferences preferences = activity.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_UNIT_OBJECT, unitObject);
        editor.apply();
    }

    public static String getTransactionMetadataObject(Activity activity) {

        SharedPreferences pref = activity.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        String transactionMetadataObject = pref.getString(KEY_TRANSACTION_METADATA_OBJECT,null);
        return transactionMetadataObject;
    }

    public static void saveTransactionMetadata(Activity activity,String transactionMetadataObject) {
        SharedPreferences preferences = activity.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_TRANSACTION_METADATA_OBJECT, transactionMetadataObject);
        editor.apply();
    }


    public static void saveEndOrderIdAtDayClose(Activity activity,int endId) {
        SharedPreferences preferences = activity.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(END_ID, endId);
        editor.apply();
    }

    public static int getEndOrderId(Activity activity) {

        SharedPreferences pref = activity.getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
        int endOrderId = pref.getInt(END_ID,-1);
        return endOrderId;
    }

    public static String getJwtToken(Activity activity) {

        SharedPreferences pref = activity.getSharedPreferences(PREFERENCE_NAME_CREDENTIALS, Activity.MODE_PRIVATE);
        String jwtToken = pref.getString(JWT_TOKEN,null);
        return jwtToken;
    }

}
