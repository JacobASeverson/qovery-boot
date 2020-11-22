package com.jacobaseverson.qovery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class TweetControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createTweet() throws Exception {
        String message = "This is a tweet";

        mockMvc.perform(
                post("/tweets")
                        .content("{\"message\": \"" + message + "\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(content().string(containsString(message)));
    }

    @SqlGroup({
            @Sql("/setup.sql"),
            @Sql("/tweet-list.sql")
    })
    @Test
    public void listTweets() throws Exception {
        mockMvc.perform(
                get("/tweets")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @SqlGroup({
            @Sql("/setup.sql"),
            @Sql("/tweet.sql")
    })
    @Test
    public void getTweet() throws Exception {
        mockMvc.perform(
                get("/tweets/tweet-id-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("This is a tweet")));
    }

    @SqlGroup({
            @Sql("/setup.sql"),
            @Sql("/tweet.sql")
    })
    @Test
    public void deleteTweet() throws Exception {
        mockMvc.perform(
                delete("/tweets/tweet-id-1"))
                .andExpect(status().isOk());

        mockMvc.perform(
                get("/tweets")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @SqlGroup({
            @Sql("/setup.sql"),
            @Sql("/tweet.sql")
    })
    @Test
    public void addLike() throws Exception {
        mockMvc.perform(post("/tweets/tweet-id-1/likes"))
                .andExpect(status().isOk());

        mockMvc.perform(
                get("/tweets/tweet-id-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.likes").isNotEmpty());
    }

    @SqlGroup({
            @Sql("/setup.sql"),
            @Sql("/tweet-with-like.sql")
    })
    @Test
    public void removeLike() throws Exception {
        mockMvc.perform(delete("/tweets/tweet-id-1/likes"))
                .andExpect(status().isOk());

        mockMvc.perform(
                get("/tweets/tweet-id-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.likes").isEmpty());
    }

}
