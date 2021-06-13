package net.lc;

import java.util.*;

public class Twitter2 {
    /**
     * Person -> Set of all followers
     */
    private final Map<Integer, Set<Integer>> followersMap = new HashMap<>();

    /**
     * Person -> Set of all people that person follows
     */
    private final Map<Integer, Set<Integer>> followingMap = new HashMap<>();

    private static int gSeqId = 0;

    static class Tweet implements Comparable<Tweet> {
        int seqId;
        int personId;
        int tweetId;

        public Tweet(int personId, int tweetId) {
            this.personId = personId;
            this.tweetId = tweetId;
            this.seqId = gSeqId++;
        }

        @Override
        public int compareTo(Tweet o) {
            return Integer.compare(o.seqId, this.seqId);
        }
    }

    /**
     * person -> TimelineList
     */
    private final Map<Integer, PriorityQueue<Tweet>> timeLineMap = new HashMap<>();

    /**
     * person -> list of tweets from person
     */
    private final Map<Integer, List<Tweet>> tweetsMap = new HashMap<>();

    public Twitter2() {
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(userId, tweetId);

        List<Tweet> selfTweets = tweetsMap.computeIfAbsent(userId, k -> new ArrayList<>());
        selfTweets.add(tweet);

        PriorityQueue<Tweet> selfTimeline = timeLineMap.computeIfAbsent(userId, k -> new PriorityQueue<>());
        selfTimeline.add(tweet);

        Set<Integer> followers = followersMap.get(userId);
        if (followers != null) {
            followers.forEach(follower -> {
                PriorityQueue<Tweet> timeline = timeLineMap.computeIfAbsent(follower, k -> new PriorityQueue<>());
                timeline.add(tweet);
            });
        }
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed.
     * Each item in the news feed must be posted by users who the user
     * followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {

        Set<Integer> following = followingMap.get(userId);

        List<Integer> result = new ArrayList<>();

        PriorityQueue<Tweet> timeline = timeLineMap.computeIfAbsent(userId, k -> new PriorityQueue<>());
        int size = timeline.size();
        if (size == 0) return result;

        int count = 0;

        List<Tweet> topTweets = new ArrayList<>();
        while (count < 10 && !timeline.isEmpty()) {
            Tweet tweet = timeline.remove();
            int tweetedUserId = tweet.personId;

            if (tweetedUserId == userId) {
                result.add(tweet.tweetId);
                count++;
                topTweets.add(tweet);
            } else if (following != null && following.contains(tweetedUserId)) {
                result.add(tweet.tweetId);
                count++;
                topTweets.add(tweet);
            }
        }

        timeline.addAll(topTweets);
        return result;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        Set<Integer> followers = followersMap.computeIfAbsent(followeeId, k -> new HashSet<>());
        if (followers.contains(followerId)) return;

        followers.add(followerId);

        Set<Integer> following = followingMap.computeIfAbsent(followerId, k -> new HashSet<>());
        following.add(followeeId);

        /**
         * add all the tweets of followee in follower's timeline
         */
        PriorityQueue<Tweet> timeline = timeLineMap.computeIfAbsent(followerId, k -> new PriorityQueue<>());
        List<Tweet> followeeTweets = tweetsMap.get(followeeId);
        if (followeeTweets != null) {
            timeline.addAll(followeeTweets);
        }
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        Set<Integer> followers = followersMap.get(followeeId);
        if (followers != null) {
            followers.remove(followerId);
        }

        Set<Integer> following = followingMap.get(followerId);
        if (following != null) {
            following.remove(followeeId);
        }
    }

    public static void main(String[] args) {
        Twitter2 twitter2 = new Twitter2();
        twitter2.postTweet(1, 5); // User 1 posts a new tweet (id = 5).
        twitter2.getNewsFeed(1);  // User 1's news feed should return a list with 1 tweet id -> [5]. return [5]
        twitter2.follow(1, 2);    // User 1 follows user 2.
        twitter2.postTweet(2, 6); // User 2 posts a new tweet (id = 6).
        twitter2.getNewsFeed(1);  // User 1's news feed should return a list with 2 tweet ids -> [6, 5]. Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
        twitter2.unfollow(1, 2);  // User 1 unfollows user 2.
        twitter2.getNewsFeed(1);  // User 1's news feed should return a list with 1 tweet id -> [5], since user 1 is no longer following user 2.
    }
}
