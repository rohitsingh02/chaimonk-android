package www.chaayos.com.chaimonkbluetoothapp.common;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.activities.ChaiMonkActivity;

/**
 * Created by rohitsingh on 29/07/16.
 */
public class CustomDayClose {

    public void showDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.username_password_alert);

        final EditText userName = (EditText) dialog.findViewById(R.id.usernameEt);
        final EditText password = (EditText) dialog.findViewById(R.id.passwordEt);
        Button cancelButton = (Button) dialog.findViewById(R.id.cnclBtn);
        final Button submitButton = (Button) dialog.findViewById(R.id.submitBtn);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userName.getText().toString();
                String passwrd = password.getText().toString();
                ((ChaiMonkActivity)context).showLoginDialog();
                ((ChaiMonkActivity)context).authenticateDayCloseUser(Integer.valueOf(username),passwrd);
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }









}
