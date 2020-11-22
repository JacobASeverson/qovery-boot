package com.jacobaseverson.qovery.tweet;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.jacobaseverson.qovery.like.Like;
import com.jacobaseverson.qovery.like.LikeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/tweets")
@RestController
public class TweetController {

    private final TweetRepository tweetRepository;

    @Autowired
    public TweetController(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @GetMapping
    public List<Tweet> list() {
        return tweetRepository.findAll(PageRequest.of(0, 50)).getContent().stream()
                .map(tweetEntity ->
                        new Tweet(tweetEntity.getId(), tweetEntity.getCreatedAt(), tweetEntity.getMessage(),
                                toLikes(tweetEntity.getLikes()))
                )
                .collect(Collectors.toList());
    }

    @PostMapping
    public Tweet create(@RequestBody Tweet tweet) {
        TweetEntity tweetEntity = tweetRepository.save(toTweetEntity(tweet));
        return toTweet(tweetEntity);
    }

    @GetMapping("/{id}")
    public Tweet get(@PathVariable String id) {
        return tweetRepository.findById(id)
                .map(this::toTweet)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        tweetRepository.deleteById(id);
    }

    @GetMapping("/{id}/likes")
    public List<Like> list(@PathVariable String id) {
        return tweetRepository.findById(id)
                .map(tweetEntity -> toLikes(tweetEntity.getLikes()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/likes")
    public Like plusOne(@PathVariable String id) {
        Optional<TweetEntity> optionalTweet = tweetRepository.findById(id);

        if (optionalTweet.isPresent()) {
            TweetEntity tweet = optionalTweet.get();
            LikeEntity like = new LikeEntity(UUID.randomUUID().toString(), tweet, new Date());
            tweet.addLike(like);
            tweetRepository.save(tweet);

            return toLike(like);

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/likes")
    public void minusOne(@PathVariable String id) {
        tweetRepository.findById(id).ifPresent(tweetEntity -> {
            tweetEntity.removeLike();
            tweetRepository.save(tweetEntity);
        });
    }

    private List<Tweet> toTweets(Set<TweetEntity> tweetEntities) {
        return tweetEntities.stream()
                .map(this::toTweet)
                .collect(Collectors.toList());
    }

    private Tweet toTweet(TweetEntity tweetEntity) {
        return  new Tweet(tweetEntity.getId(), tweetEntity.getCreatedAt(), tweetEntity.getMessage(),
                toLikes(tweetEntity.getLikes()));
    }

    private TweetEntity toTweetEntity(Tweet tweet) {
        return new TweetEntity(UUID.randomUUID().toString(), new Date(), tweet.getMessage(), new HashSet<>());
    }

    private List<Like> toLikes(Set<LikeEntity> likeEntities) {
        return likeEntities.stream()
                .map(this::toLike)
                .collect(Collectors.toList());
    }

    private Like toLike(LikeEntity likeEntity) {
        return new Like(likeEntity.getId(), likeEntity.getCreatedAt());
    }

}
