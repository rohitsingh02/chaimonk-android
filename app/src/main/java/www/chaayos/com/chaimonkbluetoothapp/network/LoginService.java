package www.chaayos.com.chaimonkbluetoothapp.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.UnitBasicDetail;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.UserSessionDetail;

/**
 * Created by rohitsingh on 08/07/16.
 */
public interface LoginService {
    @GET("master-service/rest/v1/unit-metadata/all-units?category=CHAI_MONK")
    Call<List<UnitBasicDetail>> getUnits();

    @POST("master-service/rest/v1/users/login")
    Call<UserSessionDetail> login(@Body UserSessionDetail session);
}
