package www.chaayos.com.chaimonkbluetoothapp.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import www.chaayos.com.chaimonkbluetoothapp.data.model.OrderDetail;

/**
 * Created by shikhar on 09-08-2016.
 */
public interface AnalyticsDataService {

    @POST("kettle-analytics/rest/v1/chai-monk/sync-orders")
    Call<Integer> syncOrders(@Header("auth") String jwtToken, @Body List<OrderDetail> orderDetails);

}
