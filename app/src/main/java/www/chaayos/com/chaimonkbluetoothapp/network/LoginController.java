package www.chaayos.com.chaimonkbluetoothapp.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.chaayos.com.chaimonkbluetoothapp.ResponseCallback;
import www.chaayos.com.chaimonkbluetoothapp.common.Cherror;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.UserSessionDetail;

/**
 * Created by rohitsingh on 10/07/16.
 */
public class LoginController {

    public static void attemptLogin(UserSessionDetail userDetails,final ResponseCallback<UserSessionDetail, Cherror> responseCallback){
        LoginService service = RetrofitHelper.getLoginService();
        Call<UserSessionDetail> call = service.login(userDetails);
        call.enqueue(new Callback<UserSessionDetail>() {
            @Override
            public void onResponse(Call<UserSessionDetail> call, Response<UserSessionDetail> response) {
                if(response!=null){
                    UserSessionDetail session = response.body();
                    if(session!=null && (session.getUser() == null || session.getSessionKeyId() == null)){
                        responseCallback.onError(new Cherror());
                    }else {
                        responseCallback.onResponse(session);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserSessionDetail> call, Throwable t) {
             responseCallback.onError(new Cherror());
            }
        });
    }
}
