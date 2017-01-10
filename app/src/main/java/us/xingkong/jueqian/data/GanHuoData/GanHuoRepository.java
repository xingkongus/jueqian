package us.xingkong.jueqian.data.GanHuoData;

import rx.Observable;
import us.xingkong.jueqian.api.ApiClient;
import us.xingkong.jueqian.api.GanHuoService;
import us.xingkong.jueqian.bean.GanHuoBean.DataResults;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/10 11:16
 */

public class GanHuoRepository implements GanHuoSource {
    private final GanHuoService mGanHuoService;

    public GanHuoRepository() {
        mGanHuoService = ApiClient.getInstance().getGanHuoService();
    }

    @Override
    public Observable<DataResults> getDataResults(String type, int number, int page) {
        return mGanHuoService.getDataResults(type, number, page);
    }
}
