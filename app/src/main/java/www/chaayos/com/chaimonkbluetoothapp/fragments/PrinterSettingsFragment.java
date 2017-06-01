package www.chaayos.com.chaimonkbluetoothapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.discovery.DeviceInfo;

import java.util.ArrayList;

import www.chaayos.com.chaimonkbluetoothapp.GlobalVariables;
import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.ResponseCallback;
import www.chaayos.com.chaimonkbluetoothapp.activities.ChaiMonkActivity;
import www.chaayos.com.chaimonkbluetoothapp.printer.EpsonHelper;
import www.chaayos.com.chaimonkbluetoothapp.printer.PrintFeature;
import www.chaayos.com.chaimonkbluetoothapp.utils.UserUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrinterSettingsFragment extends Fragment implements
        ResponseCallback<DeviceInfo, String>, EpsonHelper.PrintEventsCallback {

    public static final String PREFERENCE_IP = "printer_ip";
    private String mSavedIp;
    private ArrayList<DeviceInfo> mPrinterList = new ArrayList<>();
    private ListView mListView;
    private PrintersListAdapter mAdapter;
    private Button mRestartButton;
    private EditText mIPInputEt;
    private Button mIPSaveButton;
    private PrintFeature mPrintFeature;
    private SharedPreferences mPreferences;
    private GlobalVariables globalVariables;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_printer_settings,container,false);
        mListView = (ListView) rootView.findViewById(R.id.lv_printers);
        mIPInputEt = (EditText) rootView.findViewById(R.id.et_printer_manual_enter);
        mIPSaveButton = (Button) rootView.findViewById(R.id.button_printer_manual_save);
        globalVariables = (GlobalVariables)getActivity().getApplicationContext();
        mPreferences = getActivity().getSharedPreferences("printer", Context.MODE_PRIVATE);
        mAdapter = new PrintersListAdapter();
        mListView.setAdapter(mAdapter);

        mRestartButton = (Button) rootView.findViewById(R.id.button_printer_settings_restart);
        mRestartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrintFeature.restartDiscovery();
            }
        });
        mSavedIp = mPreferences.getString(PREFERENCE_IP, "");
        mIPInputEt.setText(mSavedIp);
        mIPSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mIPInputEt.getText().toString())){
                    mSavedIp = mIPInputEt.getText().toString();
                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.putString(PREFERENCE_IP, mSavedIp);
                    editor.apply();
                    Intent intent = new Intent(getActivity(), ChaiMonkActivity.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(), "Assigned IP: " + mSavedIp, Toast.LENGTH_SHORT).show();
                    if(globalVariables.getmPrintFeature() != null){
                        mPrintFeature = globalVariables.getmPrintFeature();
                    }else{
                        mPrintFeature = new PrintFeature();
                        mPrintFeature.setupPrinter((ChaiMonkActivity)globalVariables.getContext(),(ChaiMonkActivity)globalVariables.getContext());
                    }

                }else {
                    UserUtils.showAlertMessage(getActivity(),"Please Enter an Ip address to continue");
                }
            }
        });


        return  rootView;
    }

    @Override
    public void beforeRequest() {

    }

    @Override
    public void onResponse(DeviceInfo response) {

        mPrinterList.add(response);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onDiscovery(DeviceInfo deviceInfo) {

        String msg = "Printer \"%1$s\" detected and attached. ,"+ deviceInfo.getDeviceName();
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        globalVariables.setmPrintFeature(mPrintFeature);
    }

    @Override
    public void onPrintSuccess() {

    }

    @Override
    public void onPrintFailure() {

    }

    class PrintersListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mPrinterList.size();
        }

        @Override
        public Object getItem(int position) {
            return mPrinterList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.printers_list_row, parent,
                    false);
            TextView tv = (TextView) view.findViewById(R.id.tv_printer_name);
            tv.setText(mPrinterList.get(position).getDeviceName());
            return view;
        }
    }
}
