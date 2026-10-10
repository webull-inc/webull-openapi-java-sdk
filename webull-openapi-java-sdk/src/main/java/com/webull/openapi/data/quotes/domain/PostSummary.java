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

/**
 * Summary information for a community post list.
 *
 * @author Webull
 */
public class PostSummary {

    private String postId;
    private String text;
    private Long publishTime;
    private Long thumbs;
    private Long comments;
    private Long rank;
    private PublishUser publishUser;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Long publishTime) {
        this.publishTime = publishTime;
    }

    public Long getThumbs() {
        return thumbs;
    }

    public void setThumbs(Long thumbs) {
        this.thumbs = thumbs;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public PublishUser getPublishUser() {
        return publishUser;
    }

    public void setPublishUser(PublishUser publishUser) {
        this.publishUser = publishUser;
    }

    @Override
    public String toString() {
        return "PostSummary{" +
                "postId='" + postId + '\'' +
                ", text='" + text + '\'' +
                ", publishTime=" + publishTime +
                ", thumbs=" + thumbs +
                ", comments=" + comments +
                ", rank=" + rank +
                ", publishUser=" + publishUser +
                '}';
    }
}
