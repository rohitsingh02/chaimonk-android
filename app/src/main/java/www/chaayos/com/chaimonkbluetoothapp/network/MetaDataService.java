package www.chaayos.com.chaimonkbluetoothapp.network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.TransactionMetadata;
import www.chaayos.com.chaimonkbluetoothapp.domain.model.new_model.Unit;

/**
 * Created by shikhar on 09-08-2016.
 */
public interface MetaDataService {

    @POST("master-service/rest/v1/unit-metadata/unit")
    Call<Unit> getUnitDetails(@Header("auth") String jwtToken,@Body int unitId);

    @POST("master-service/rest/v1/unit-metadata/metadata")
    Call<TransactionMetadata> getMetaDataDetails(@Header("auth") String jwtToken,@Body int unitId);



}
