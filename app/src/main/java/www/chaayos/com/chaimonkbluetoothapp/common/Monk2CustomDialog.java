package www.chaayos.com.chaimonkbluetoothapp.common;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.Map;
import java.util.UUID;

import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.activities.ChaiMonkActivity;
import www.chaayos.com.chaimonkbluetoothapp.bluetooth.Constants;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.ChaiMonkEnum;
import www.chaayos.com.chaimonkbluetoothapp.utils.AppUtils;

/**
 * Created by rohitsingh on 30/07/16.
 */
public class Monk2CustomDialog {
    private Map<ChaiMonkEnum,UUID> chaiMonkMap = AppUtils.initMonkMap();
    public void showDialog(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog);


        Button connectButton = (Button) dialog.findViewById(R.id.connectButton);
        Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
        Button cleanButton = (Button) dialog.findViewById(R.id.cleanButton);
        final Button pauseResumeButton = (Button) dialog.findViewById(R.id.pauseResumeButton);


        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChaiMonkActivity) context).connectToMonk(ChaiMonkEnum.CHAI_MONK2.toString());
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChaiMonkActivity) context).sendCommandToMonk(chaiMonkMap.get(ChaiMonkEnum.CHAI_MONK2), Constants.ChaiMonkCommand.STOP);
                dialog.dismiss();
            }
        });

        cleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChaiMonkActivity)context).sendCommandToMonk(chaiMonkMap.get(ChaiMonkEnum.CHAI_MONK2), Constants.ChaiMonkCommand.CLEAN);
                dialog.dismiss();
            }
        });

        pauseResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pauseResumeButton.getText().equals("Pause")) {
                    ((ChaiMonkActivity) context).sendCommandToMonk(chaiMonkMap.get(ChaiMonkEnum.CHAI_MONK2), Constants.ChaiMonkCommand.PAUSE);
                    pauseResumeButton.setText("Resume");
                    dialog.dismiss();
                }else if(pauseResumeButton.getText().equals("Resume")){
                    ((ChaiMonkActivity) context).sendCommandToMonk(chaiMonkMap.get(ChaiMonkEnum.CHAI_MONK2), Constants.ChaiMonkCommand.RESUME);
                    pauseResumeButton.setText("Pause");
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

}
