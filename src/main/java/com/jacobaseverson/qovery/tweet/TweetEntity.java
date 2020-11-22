package com.jacobaseverson.qovery.tweet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

import com.jacobaseverson.qovery.like.LikeEntity;

@Entity(name = "tweet")
public class TweetEntity {

    @Id
    private String id;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    private String message;

    @OneToMany(mappedBy = "tweet", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LikeEntity> likes;

    public TweetEntity() { }

    public TweetEntity(String id,
                       Date createdAt,
                       String message,
                       Set<LikeEntity> likes) {
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

    public Set<LikeEntity> getLikes() {
        return likes;
    }

    public void addLike(LikeEntity likeEntity) {
        likes.add(likeEntity);
    }

    public void removeLike() {
        likes.forEach(likeEntity -> {
            likes.remove(likeEntity);
            return;
        });
    }

}
