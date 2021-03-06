package smc.minjoon.accompanying.LockScreen.News;

/**
 * Created by skaqn on 2017-10-07.
 */

public class OpenGraphData {
    protected String domain;
    protected String url;
    protected String html;

    protected String title;
    protected String image;
    protected String description;

    public String getHtml() {
        return html;
    }

    public String getDomain() {
        return domain;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return html;
    }
}

