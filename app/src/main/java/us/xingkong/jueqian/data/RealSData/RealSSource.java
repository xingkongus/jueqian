package us.xingkong.jueqian.data.RealSData;

import rx.Observable;
import us.xingkong.jueqian.bean.RealSBean.DataResults;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/10 10:22
 */

public interface RealSSource {
    Observable<DataResults> getDataResults(String type, int number, int page);
}
