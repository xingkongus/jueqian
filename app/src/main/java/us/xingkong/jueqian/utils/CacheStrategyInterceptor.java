package us.xingkong.jueqian.utils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import us.xingkong.jueqian.JueQianAPP;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/14 02:11
 */

public class CacheStrategyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetUtils.isConnected(JueQianAPP.getAppContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();

        }
        Response response = chain.proceed(request);

        if (NetUtils.isConnected(JueQianAPP.getAppContext())) {
            int maxAge = 0 * 60;
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
        return response;
    }
}
