package com.jacobaseverson.qovery.like;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import com.jacobaseverson.qovery.tweet.TweetEntity;

@Entity(name = "tweet_like") // like is reserved
public class LikeEntity {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    private TweetEntity tweet;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public LikeEntity() { }

    public LikeEntity(String id, TweetEntity tweet, Date createdAt) {
        this.id = id;
        this.tweet = tweet;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

}
