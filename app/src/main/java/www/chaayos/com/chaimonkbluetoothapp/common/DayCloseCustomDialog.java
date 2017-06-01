package www.chaayos.com.chaimonkbluetoothapp.common;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.activities.ChaiMonkActivity;
import www.chaayos.com.chaimonkbluetoothapp.utils.AppUtils;

/**
 * Created by rohitsingh on 05/08/16.
 */
public class DayCloseCustomDialog {
    View v;
    public void showDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.day_close_custom_dialog);


        Button cancelDayClose = (Button) dialog.findViewById(R.id.cancelDayclose);
        Button dayClose = (Button) dialog.findViewById(R.id.dayClose);



        dayClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDayClose customDayClose = new CustomDayClose();
                customDayClose.showDialog(context);
                dialog.dismiss();
            }
        });

        cancelDayClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog cancelDay = new AlertDialog.Builder(context).setMessage("Are You Sure you want to cancel Day Close For " + AppUtils.getCurrentBusinessDate())
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((ChaiMonkActivity) context).cancelDayClose();
                                Toast.makeText(context, "Day Close has been Cancelled Successfully", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                cancelDay.show();
            }

        });

        dialog.show();


}
}
