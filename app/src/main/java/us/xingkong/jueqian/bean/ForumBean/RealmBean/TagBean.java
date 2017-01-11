package us.xingkong.jueqian.bean.ForumBean.RealmBean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Garfield on 1/11/17.
 */

public class TagBean extends RealmObject{
    private String tag;

    @PrimaryKey
    private String tagId;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
}
