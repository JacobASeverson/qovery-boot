package com.jacobaseverson.qovery.tweet;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jacobaseverson.qovery.like.Like;

public class Tweet {

    private final String id;

    private final Date createdAt;

    private final String message;

    private final List<Like> likes;

    @JsonCreator
    public Tweet(@JsonProperty("id") String id,
                 @JsonProperty("createdAt") Date createdAt,
                 @JsonProperty("message") String message,
                 @JsonProperty("likes") List<Like> likes) {
        this.id = id;
        this.createdAt = createdAt;
        this.message = message;
        this.likes = likes;
    }

    public String getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getMessage() {
        return message;
    }

    public List<Like> getLikes() {
        return likes;
    }

}
