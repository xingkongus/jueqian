package us.xingkong.jueqian.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import us.xingkong.jueqian.base.Constants;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/10 10:34
 */

public class ApiClient {

    private static OkHttpClient okHttpClient;
    private static RealSService sRealSService;

    private static class ApiClientHolder {
        public static final ApiClient INSTANCE = new ApiClient();
    }

    public static ApiClient getInstance() {
        return ApiClientHolder.INSTANCE;
    }

    private ApiClient() {
        okHttpClient = new OkHttpClient();
    }

    public RealSService getRealSService() {
        if (sRealSService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Constants.REALS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            sRealSService = retrofit.create(RealSService.class);
        }
        return sRealSService;
    }

}
