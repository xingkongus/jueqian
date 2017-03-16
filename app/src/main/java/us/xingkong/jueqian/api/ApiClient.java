package us.xingkong.jueqian.api;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.base.Constants;
import us.xingkong.jueqian.utils.CacheStrategyInterceptor;

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
        setOkhttpAndCache();
    }

    private void setOkhttpAndCache() {
        File httpCacheDirectory = new File(JueQianAPP.getAppContext().getExternalCacheDir().getAbsolutePath(), "responses");
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        CacheStrategyInterceptor cacheStrategyInterceptor = new CacheStrategyInterceptor();

        okHttpClient = new OkHttpClient
                .Builder()
                .cache(cache)
                .addInterceptor(cacheStrategyInterceptor)
                .addNetworkInterceptor(cacheStrategyInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(7, TimeUnit.SECONDS)
                .build();
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
