package vn.iostar.tuan11.Model;

import java.io.Serializable;

public class Video1Model implements Serializable {
    private String title;
    private String url;

    public Video1Model() {
    }

    public Video1Model(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
