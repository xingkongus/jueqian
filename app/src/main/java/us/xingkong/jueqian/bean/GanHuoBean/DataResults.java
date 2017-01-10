package us.xingkong.jueqian.bean.GanHuoBean;

import java.util.List;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/10 10:05
 */

public class DataResults extends BaseGanHuoBean {
    /**
       "error": false,
       "results": [{"_id": "5872234c421aa93161103dc7","createdAt": "2017-01-08T19:32:28.973Z","desc": "Lint \u662f\u4f18\u79c0\u7a0b\u5e8f\u5458\u4e4b\u53cb\uff0c\u68c0\u6d4b\u6f5c\u5728\u95ee\u9898\uff0c\u6027\u80fd\u4f18\u5316\uff0c\u53bb\u9664\u65e0\u7528\u8d44\u6e90\u5fc5\u5907\uff01","publishedAt": "2017-01-09T11:46:59.821Z","source": "web","type": "Android","url": "http://blog.csdn.net/u011240877/article/details/54141714","used": true,"who": "Shixin Zhang"}
     */
    private List<Results> results;

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
