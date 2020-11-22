package com.jacobaseverson.qovery.like;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Like {

    private String id;

    private Date createdAt;

    @JsonCreator
    public Like(@JsonProperty("id") String id,
                @JsonProperty("createdAt") Date createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

}
