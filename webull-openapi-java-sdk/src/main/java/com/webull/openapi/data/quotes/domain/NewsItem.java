/*
 * Copyright 2022 Webull
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webull.openapi.data.quotes.domain;

import java.util.List;

/**
 * A single news item returned by news search / stock news list APIs (US only).
 */
public class NewsItem {

    private String newsId;
    private String title;
    private String summary;
    private String source;
    private String url;
    private String publishTime;
    private String sentiment;
    private List<String> relatedSymbols;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public List<String> getRelatedSymbols() {
        return relatedSymbols;
    }

    public void setRelatedSymbols(List<String> relatedSymbols) {
        this.relatedSymbols = relatedSymbols;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "newsId='" + newsId + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", source='" + source + '\'' +
                ", url='" + url + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", sentiment='" + sentiment + '\'' +
                ", relatedSymbols=" + relatedSymbols +
                '}';
    }
}
