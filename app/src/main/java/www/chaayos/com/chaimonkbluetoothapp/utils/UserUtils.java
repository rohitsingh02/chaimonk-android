package www.chaayos.com.chaimonkbluetoothapp.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import www.chaayos.com.chaimonkbluetoothapp.domain.model.UnitBasicDetail;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.UserSessionDetail;

/**
 * Created by rohitsingh on 10/07/16.
 */
public class UserUtils {

    private static final String LOG_TAG = UserUtils.class.getSimpleName();

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public static Filter getUnitsFilter(final List<UnitBasicDetail> units, final ArrayAdapter<UnitBasicDetail>
            adapter, final List<UnitBasicDetail> suggestions) {
        Filter nameFilter = new Filter() {

            @Override
            public String convertResultToString(Object resultValue) {
                String str = ((UnitBasicDetail) (resultValue)).getName();
                return str;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    suggestions.clear();
                    for (UnitBasicDetail unit : units) {
                        if (unit.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            Log.i(LOG_TAG, "unit: " + unit.getName());
                            suggestions.add(unit);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ArrayList<UnitBasicDetail> filteredList =
                        (ArrayList<UnitBasicDetail>) results.values;
                adapter.clear();
                if (results != null && results.count > 0) {
                    adapter.addAll(filteredList);
                }
                adapter.notifyDataSetChanged();
            }
        };
        return nameFilter;
    }

    public static UserSessionDetail createLoginCredentials(int unitId, int userId,
                                                           int terminalId,String screenType,String application,String password) {
        UserSessionDetail user = new UserSessionDetail();
        user.setUserId(userId);
        user.setPassword(password);
        user.setUnitId(unitId);
        user.setTerminalId(terminalId);
        user.setScreenType(screenType);
        user.setApplication(application);
        return user;
    }


    public static void showAlertMessage(Context context, String message) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(300);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(message);
        dialog.setPositiveButton("Okay", null);
        dialog.show();
    }



}
