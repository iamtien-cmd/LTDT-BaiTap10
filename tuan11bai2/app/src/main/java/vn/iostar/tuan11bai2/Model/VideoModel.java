package vn.iostar.tuan11bai2.Model;

import java.io.Serializable;
import java.util.List;

public class VideoModel implements Serializable {
    private int id;
    private String title;
    private String description;
    private String url;

    public class MessageVideoModel implements Serializable {
        private boolean success;
        private String message;


        private List<VideoModel> result;

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public List<VideoModel> getResult() { return result; }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

