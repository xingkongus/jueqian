package us.xingkong.jueqian.data;

import rx.Observable;
import us.xingkong.jueqian.bean.GanHuoBean.DataResults;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/10 10:22
 */

public interface GanHuoSource {
    Observable<DataResults> getDataResults(String type, int number, int page);
}
