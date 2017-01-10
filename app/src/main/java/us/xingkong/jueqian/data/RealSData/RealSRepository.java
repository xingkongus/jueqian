package us.xingkong.jueqian.data.RealSData;

import rx.Observable;
import us.xingkong.jueqian.api.ApiClient;
import us.xingkong.jueqian.api.RealSService;
import us.xingkong.jueqian.bean.RealSBean.DataResults;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/10 11:16
 */

public class RealSRepository implements RealSSource {
    private final RealSService mRealSService;

    public RealSRepository() {
        mRealSService = ApiClient.getInstance().getRealSService();
    }

    @Override
    public Observable<DataResults> getDataResults(String type, int number, int page) {
        return mRealSService.getDataResults(type, number, page);
    }
}
