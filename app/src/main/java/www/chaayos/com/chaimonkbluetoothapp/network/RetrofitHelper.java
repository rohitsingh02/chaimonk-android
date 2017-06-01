package www.chaayos.com.chaimonkbluetoothapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import www.chaayos.com.chaimonkbluetoothapp.utils.AppUtils;

/**
 * Created by rohitsingh on 09/07/16.
 */
public class RetrofitHelper {

    private static final MediaType JSON = MediaType.parse("application/json; charset =utf-8");
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    private static Retrofit loginInstance;
    private static Retrofit metaDataInstance;
    private static Retrofit analyticsInstance;

    private static LoginService loginService;
    private static MetaDataService metaDataService;
    private static AnalyticsDataService analyticsDataService;


    public  static Retrofit getInstance(){
        if(loginInstance == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
            loginInstance = new Retrofit.Builder()
                    .baseUrl(AppUtils.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        return loginInstance;
    }
    public static LoginService getLoginService(){
        if(loginService == null){
            loginService = getInstance().create(LoginService.class);
        }
        return loginService;
    }

    public  static Retrofit getMetaDataInstance(){
        if(metaDataInstance == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(50, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();
            metaDataInstance = new Retrofit.Builder()
                    .baseUrl(AppUtils.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        return metaDataInstance;
    }

    public  static Retrofit getAnalyticsInstance(){
        if(analyticsInstance == null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(50, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();
            analyticsInstance = new Retrofit.Builder()
                    .baseUrl(AppUtils.ANALYTICS_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        return analyticsInstance;
    }

    public static MetaDataService getMetaDataService(){
        if(metaDataService == null){
            metaDataService = getMetaDataInstance().create(MetaDataService.class);
        }
        return metaDataService;
    }


    public static AnalyticsDataService getAnalyticsDataService(){
        if(analyticsDataService == null){
            analyticsDataService = getAnalyticsInstance().create(AnalyticsDataService.class);
        }
        return analyticsDataService;
    }
}
