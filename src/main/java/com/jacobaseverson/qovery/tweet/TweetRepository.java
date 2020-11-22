package com.jacobaseverson.qovery.tweet;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface TweetRepository extends PagingAndSortingRepository<TweetEntity, String> { }
