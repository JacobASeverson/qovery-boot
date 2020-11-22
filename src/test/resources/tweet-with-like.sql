INSERT INTO tweet(id, created_at, message)
VALUES
    ('tweet-id-1', '2020-11-22T16:27:53.322+00:00', 'This is a tweet');

INSERT INTO tweet_like(id, tweet_id, created_at)
VALUES
    ('tweet-like-id-1', 'tweet-id-1', '2020-11-22T16:27:53.322+00:00');