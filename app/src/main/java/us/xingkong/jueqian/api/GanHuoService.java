package us.xingkong.jueqian.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import us.xingkong.jueqian.bean.GanHuoBean.DataResults;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/10 10:35
 */

public interface GanHuoService {
    @GET("data/{type}/{number}/{page}")
    Observable<DataResults> getDataResults(
            @Path("type") String type,
            @Path("number") int number,
            @Path("page") int page
    );
}
