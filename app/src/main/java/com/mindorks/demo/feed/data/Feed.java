package com.mindorks.demo.feed.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by janisharali on 24/08/16.
 */
public class Feed {

    @SerializedName("catergory")
    @Expose
    private String heading;

    @SerializedName("data")
    @Expose
    private List<Info> infoList;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public List<Info> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<Info> infoList) {
        this.infoList = infoList;
    }
}
