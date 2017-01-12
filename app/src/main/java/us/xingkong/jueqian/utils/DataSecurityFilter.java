package us.xingkong.jueqian.utils;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;
import us.xingkong.jueqian.bean.RealSBean.DataResults;
import us.xingkong.jueqian.bean.RealSBean.Results;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/12 15:17
 */

public class DataSecurityFilter implements Func1<DataResults, DataResults> {

    private static DataSecurityFilter INSTANCE = new DataSecurityFilter();

    private DataSecurityFilter() {
    }

    public static DataSecurityFilter getInstance() {
        return INSTANCE;
    }

    @Override
    public DataResults call(DataResults data) {
        List<Results> delList = new ArrayList();
        for (Results results : data.getResults()) {
            if (results.getType().equals("福利")
                    || results.getType().equals("休息视频")) {
                delList.add(results);
            }
        }
        data.getResults().removeAll(delList);
        return data;
    }
}
