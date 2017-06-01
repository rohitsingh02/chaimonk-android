package www.chaayos.com.chaimonkbluetoothapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.chaayos.com.chaimonkbluetoothapp.PreferenceManager;
import www.chaayos.com.chaimonkbluetoothapp.R;
import www.chaayos.com.chaimonkbluetoothapp.ResponseCallback;
import www.chaayos.com.chaimonkbluetoothapp.common.Cherror;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.LoginCredentials;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.UnitBasicDetail;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.UnitBasicDetailList;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.UserSessionDetail;
import www.chaayos.com.chaimonkbluetoothapp.network.LoginController;
import www.chaayos.com.chaimonkbluetoothapp.network.RetrofitHelper;
import www.chaayos.com.chaimonkbluetoothapp.utils.UserUtils;

public class LoginActivity extends AppCompatActivity implements ResponseCallback<UserSessionDetail,Cherror> {

    private EditText userIdEditText;
    private EditText passcodeEditText;
    private final List<UnitBasicDetail> mSuggestions = new ArrayList<>();
    private Button loginButton;
    private List<UnitBasicDetail> mUnits = new ArrayList<>();
    private AutoCompleteTextView mUnitsActv;
    private UnitBasicDetail mSelectedUnit;
    private UserSessionDetail mSession;
    private LoginCredentials mSavedLoginCredentials;
    private ProgressBar mProgressBar;
    private UnitBasicDetailList unitBasicDetailList;
    int defaultUnitId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userIdEditText = (EditText) findViewById(R.id.userIdEditText);
        passcodeEditText = (EditText) findViewById(R.id.passcodeEditText);
        mUnitsActv = (AutoCompleteTextView) findViewById(R.id.instant_auto_complete);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_chai_monk_login);
        mProgressBar.setVisibility(View.GONE);
        loginButton = (Button) findViewById(R.id.btn_login);
        unitBasicDetailList = new UnitBasicDetailList();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSessionDetail detail;
                if(isNetworkAvailable()){
                    populateUnitsList();
                    try {
                        mProgressBar.setVisibility(View.VISIBLE);
                        detail = createUserSession();
                        LoginController.attemptLogin(detail, LoginActivity.this);
                    }catch (IllegalArgumentException e){
                        UserUtils.showAlertMessage(LoginActivity.this,e.getMessage());
                    }
                }else{
                    Snackbar.make(v,"Not Connected to internet",Snackbar.LENGTH_LONG).show();
                }

            }
        });

         if(isNetworkAvailable()){
             populateUnitsList();
         }else{
             Toast.makeText(LoginActivity.this,"You are not Connected to Internet",Toast.LENGTH_LONG).show();
             unitBasicDetailList = new Gson().fromJson(PreferenceManager.getUnitsListObject(LoginActivity.this),UnitBasicDetailList.class);
             mUnits = unitBasicDetailList.getUnitBasicDetailList();
             helperMethod();
         }

        populateSavedCredentials();

    }
    private void populateSavedCredentials (){
        mSavedLoginCredentials = PreferenceManager.getCredentials(LoginActivity.this);
        if(!TextUtils.isEmpty(mSavedLoginCredentials.getUserId())){
            userIdEditText.setText("" + mSavedLoginCredentials.getUserId());
            passcodeEditText.setText(mSavedLoginCredentials.getPassword());
            defaultUnitId = Integer.parseInt(mSavedLoginCredentials.getUnitId());
        }
    }

    private void onLoginSuccessful(){
       PreferenceManager.saveDetailsInPreferences(LoginActivity.this,mSession);
        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setUserId(""+mSession.getUserId());
        loginCredentials.setPassword(passcodeEditText.getText().toString());
        loginCredentials.setUnitId("" + mSession.getUnitId());
        loginCredentials.setUnitName(mSelectedUnit.getName());
        loginCredentials.setJwtToken(mSession.getJwtToken());
        PreferenceManager.saveCredentials(LoginActivity.this, loginCredentials);
        UserUtils.hideKeyboard(LoginActivity.this);
        mProgressBar.setVisibility(View.GONE);
        Intent intent = new Intent(getApplicationContext(),ChaiMonkActivity.class);
        startActivity(intent);

    }

    private int getSelectedUnitId(){
        if(mSelectedUnit != null){
            if(mUnitsActv.getText().toString().trim().equalsIgnoreCase(mSelectedUnit.getName())){
                return  mSelectedUnit.getId();
            }else {
                mProgressBar.setVisibility(View.GONE);
                throw new IllegalArgumentException("Please Select Units from the list only.Do not edit Unit....");
            }
        }else {
            mProgressBar.setVisibility(View.GONE);
            return defaultUnitId;
//            throw new IllegalArgumentException("Please select a Unit for login");
        }
    }

  private void populateUnitsList(){
      RetrofitHelper.getLoginService().getUnits().enqueue(new Callback<List<UnitBasicDetail>>() {
          @Override
          public void onResponse(Call<List<UnitBasicDetail>> call, Response<List<UnitBasicDetail>> response) {
              mUnitsActv.setEnabled(true);
              if(response.body() == null){
                  return;
              }
              mUnits = response.body();
              if(mSavedLoginCredentials != null && !TextUtils.isEmpty(mSavedLoginCredentials.getUnitId())){
                  UnitBasicDetail temp = new UnitBasicDetail();
                  temp.setId(Integer.valueOf(mSavedLoginCredentials.getUnitId()));
                  int index = mUnits.indexOf(temp);
                  if(index != -1){
                      mSelectedUnit = mUnits.get(index);
                      UserUtils.hideKeyboard(LoginActivity.this);
                  }
              }
              mUnitsActv.setAdapter(new UnitsListAdapter(LoginActivity.this,0));
              mUnitsActv.setThreshold(0);
              mUnitsActv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                  @Override
                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      mSelectedUnit = mSuggestions.get(position);
                      UserUtils.hideKeyboard(LoginActivity.this);
                  }
              });
              unitBasicDetailList.setUnitBasicDetailList(mUnits);
              PreferenceManager.saveUnitsListObject(LoginActivity.this,new Gson().toJson(unitBasicDetailList));
              helperMethod();
          }

          @Override
          public void onFailure(Call<List<UnitBasicDetail>> call, Throwable t) {
              mProgressBar.setVisibility(View.GONE);
//              unitBasicDetailList = new UnitBasicDetailList();
              unitBasicDetailList = new Gson().fromJson(PreferenceManager.getUnitsListObject(LoginActivity.this),UnitBasicDetailList.class);
              mUnits = unitBasicDetailList.getUnitBasicDetailList();
              helperMethod();
          }
      });
  }

    private void helperMethod(){
        if(mSavedLoginCredentials != null && !TextUtils.isEmpty(mSavedLoginCredentials.getUnitId())){
            UnitBasicDetail temp = new UnitBasicDetail();
            temp.setId(Integer.valueOf(mSavedLoginCredentials.getUnitId()));
            int index = mUnits.indexOf(temp);
            if(index != -1){
                mSelectedUnit = mUnits.get(index);
                UserUtils.hideKeyboard(LoginActivity.this);
            }
        }
        mUnitsActv.setAdapter(new UnitsListAdapter(LoginActivity.this,0));
        mUnitsActv.setThreshold(0);
        mUnitsActv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedUnit = mSuggestions.get(position);
                UserUtils.hideKeyboard(LoginActivity.this);
            }
        });

        loginButton.setEnabled(true);
    }

    private int getUserId() throws IllegalArgumentException {
        String entered = userIdEditText.getText().toString();
        if (!TextUtils.isEmpty(entered)) {
            int userId = Integer.valueOf(entered);
            if (TextUtils.isDigitsOnly(entered)) {
                return userId;
            } else {
                throw new IllegalArgumentException("");
            }
        } else {
            if (TextUtils.isEmpty(passcodeEditText.getText().toString())) {
                mProgressBar.setVisibility(View.GONE);
                throw new IllegalArgumentException("Please fill user id and password");
            } else {
                mProgressBar.setVisibility(View.GONE);
                throw new IllegalArgumentException("please fill userid");
            }
        }

    }

    private String getPassword() throws IllegalArgumentException {
        String password = passcodeEditText.getText().toString();
        if (!TextUtils.isEmpty(password)) {
            return password;
        } else {
            mProgressBar.setVisibility(View.GONE);
            throw new IllegalArgumentException("please fill password");
        }
    }

    @Override
    public void beforeRequest() {
        loginButton.setEnabled(false);
    }

    @Override
    public void onResponse(UserSessionDetail response) {
       mSession = response;
       loginButton.setEnabled(true);
       onLoginSuccessful();
    }

    @Override
    public void onError(Cherror error) {
        loginButton.setEnabled(true);
        mProgressBar.setVisibility(View.GONE);
        Intent intent = new Intent(getApplicationContext(),ChaiMonkActivity.class);
        startActivity(intent);

    }





    class UnitsListAdapter extends ArrayAdapter<UnitBasicDetail> {
        final Filter filter = UserUtils.getUnitsFilter(mUnits, this, mSuggestions);

        public UnitsListAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView tv = new TextView(LoginActivity.this);
            tv.setPadding(20, 20, 20, 20);
            tv.setTextColor(0xff727272);
            tv.setTextSize(18);
            tv.setText(mSuggestions.get(position).getName());
            return tv;
        }

        @Override
        public Filter getFilter() {
            return filter;
        }
    }

    private UserSessionDetail createUserSession() throws IllegalArgumentException {

        return UserUtils.createLoginCredentials(getSelectedUnitId(),getUserId(),1,"POS","KETTLE_SERVICE",getPassword());

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
