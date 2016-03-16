package eccrm.base.im.web;

import eccrm.base.im.domain.News;
import eccrm.base.im.domain.NewsReceiver;

import java.util.List;

/**
 * @author Michael
 */
public class NewsTemp {
    private News news;
    private List<NewsReceiver> receivers;

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public List<NewsReceiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<NewsReceiver> receivers) {
        this.receivers = receivers;
    }
}
