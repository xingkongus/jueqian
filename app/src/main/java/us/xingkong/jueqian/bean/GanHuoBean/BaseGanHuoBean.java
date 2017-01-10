package us.xingkong.jueqian.bean.GanHuoBean;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/10 09:31
 */

public class BaseGanHuoBean {

    protected boolean error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "BaseGanHuoBean{" +
                "error=" + error +
                '}';
    }
}
