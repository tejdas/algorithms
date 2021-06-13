package net.lc;

import java.util.*;

public class Twitter {
    /**
     * Person -> Set of all followers
     */
    private final Map<Integer, Set<Integer>> followersMap = new HashMap<>();

    /**
     * Person -> Set of all people that person follows
     */
    private final Map<Integer, Set<Integer>> followingMap = new HashMap<>();

    private int gSeqId = 0;

    static class Tweet implements Comparable<Tweet> {
        int seqId;
        int personId;
        int tweetId;

        public Tweet(int personId, int tweetId, int seqId) {
            this.personId = personId;
            this.tweetId = tweetId;
            this.seqId = seqId;
        }

        @Override
        public int compareTo(Tweet o) {
            return Integer.compare(o.seqId, this.seqId);
        }
    }

    /**
     * global tweet list
     */
    List<Tweet> allTweets = new ArrayList<>();

    /**
     * person -> TimelineList
     */
    private final Map<Integer, LinkedList<Tweet>> timeLineMap = new HashMap<>();

    /**
     * person -> list of tweets from person
     */
    private final Map<Integer, LinkedList<Tweet>> tweetsMap = new HashMap<>();

    public Twitter() {
    }

    private void addTweetToList(LinkedList<Tweet> list, Tweet tweet) {
        list.addLast(tweet);
        if (list.size() > 10) {
            list.removeFirst();
        }
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(userId, tweetId, gSeqId);
        gSeqId++;
        allTweets.add(tweet);

        LinkedList<Tweet> selfTweets = tweetsMap.computeIfAbsent(userId, k -> new LinkedList<>());
        addTweetToList(selfTweets, tweet);

        LinkedList<Tweet> selfTimeline = timeLineMap.computeIfAbsent(userId, k -> new LinkedList<>());
        addTweetToList(selfTimeline, tweet);

        Set<Integer> followers = followersMap.get(userId);
        if (followers != null) {
            followers.forEach(follower -> {
                LinkedList<Tweet> timeline = timeLineMap.computeIfAbsent(follower, k -> new LinkedList<>());
                addTweetToList(timeline, tweet);
            });
        }
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed.
     * Each item in the news feed must be posted by users who the user
     * followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {

        LinkedList<Tweet> timeline = timeLineMap.computeIfAbsent(userId, k -> new LinkedList<>());

        int size = timeline.size();
        if (size == 0) return new ArrayList<>();
        List<Integer> result = new ArrayList<>();

        Iterator<Tweet> it = timeline.descendingIterator();
        while (it.hasNext()) {
            result.add(it.next().tweetId);
        }
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
         * add all the tweets of followee in follower's timeline and store latest (upto 10)
         */
        LinkedList<Tweet> timeline = timeLineMap.computeIfAbsent(followerId, k -> new LinkedList<>());
        LinkedList<Tweet> followeeTweets = tweetsMap.get(followeeId);
        if (followeeTweets == null) return;

        PriorityQueue<Tweet> pq = new PriorityQueue<>(timeline);
        pq.addAll(followeeTweets);

        timeline.clear();
        while (timeline.size() < 10 && !pq.isEmpty()) {
            timeline.addFirst(pq.remove());
        }
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        Set<Integer> followers = followersMap.get(followeeId);
        Set<Integer> following = followingMap.get(followerId);
        if (followers == null || following == null) return;

        if (!followers.contains(followerId)) {
            return;
        }

        followers.remove(followerId);
        following.remove(followeeId);

        /**
         * remove followeeId tweets from followers timeline
         */

        PriorityQueue<Tweet> pq = new PriorityQueue<>();

        LinkedList<Tweet> timeline = timeLineMap.computeIfAbsent(followerId, k -> new LinkedList<>());
        if (timeline.isEmpty()) return;
        timeline.forEach(t -> {
           if (t.personId != followeeId) {
               pq.add(t);
           }
        });

        if (pq.size() == timeline.size()) return;

        int earliestSeqId = timeline.getFirst().seqId;

        /*
        if (!pq.isEmpty()) {
            earliestSeqId = pq.peek().seqId;
        } else {
            earliestSeqId = timeline.getFirst().seqId;
        }

         */

        if (pq.size() < 10) {
            int tweetsToAdd = 10 - pq.size();

            int nextSeqIdToAdd = earliestSeqId;

            while (tweetsToAdd > 0) {
                nextSeqIdToAdd--;
                if (nextSeqIdToAdd < 0) break;

                Tweet t = allTweets.get(nextSeqIdToAdd);

                if (following.contains(t.personId) || t.personId == followerId) {
                    pq.add(t);
                    tweetsToAdd--;
                }
            }
        }

        timeline.clear();
        while (!pq.isEmpty()) {
            timeline.addFirst(pq.remove());
        }
    }

    public static void main(String[] args) {

        {

            Twitter twitter2 = new Twitter();
            twitter2.postTweet(1, 5); // User 1 posts a new tweet (id = 5).
            twitter2.getNewsFeed(1);  // User 1's news feed should return a list with 1 tweet id -> [5]. return [5]
            twitter2.follow(1, 2);    // User 1 follows user 2.
            twitter2.postTweet(2, 6); // User 2 posts a new tweet (id = 6).

            List<Integer> res = twitter2.getNewsFeed(1);
            System.out.println(Arrays.toString(res.toArray()));

            twitter2.unfollow(1, 2);  // User 1 unfollows user 2.

            res = twitter2.getNewsFeed(1);
            System.out.println(Arrays.toString(res.toArray()));
        }

/*
        {
            Twitter twitter2 = new Twitter();
            twitter2.postTweet(1, 1);
            twitter2.getNewsFeed(1);
            twitter2.follow(2, 1);
            List<Integer> res = twitter2.getNewsFeed(2);
            System.out.println(Arrays.toString(res.toArray()));

            twitter2.unfollow(2, 1);
            res = twitter2.getNewsFeed(2);
            System.out.println(Arrays.toString(res.toArray()));
        }
*/

/*
        {
            Twitter twitter2 = new Twitter();
            twitter2.postTweet(1, 4);
            twitter2.postTweet(2, 5);

            twitter2.unfollow(1, 2);
            twitter2.follow(1, 2);


            List<Integer> res = twitter2.getNewsFeed(1);
            System.out.println(Arrays.toString(res.toArray()));
        }

 */

        {
            Twitter twitter2 = new Twitter();

            //[1,5],[2,3],[1,101],[2,13],[2,10]   ,[1,2],[1,94],[2,505],[1,333],[2,22],    [1,11],[1,205],[2,203],[1,201],[2,213],   [1,200],[2,202],[1,204],[2,208],[2,233],[1,222],[2,211],

            twitter2.postTweet(1, 5);
            twitter2.postTweet(2, 3);
            twitter2.postTweet(1, 101);
            twitter2.postTweet(2, 13);
            twitter2.postTweet(2, 10);

            twitter2.postTweet(1,2);
            twitter2.postTweet(1,94);
            twitter2.postTweet(2,505);
            twitter2.postTweet(1,333);
            twitter2.postTweet(2,22);


            twitter2.postTweet(1,11);
            twitter2.postTweet(1,205);
            twitter2.postTweet(2,203);
            twitter2.postTweet(1,201);
            twitter2.postTweet(2,213);

            twitter2.postTweet(1,200);
            twitter2.postTweet(2,202);
            twitter2.postTweet(1,204);
            twitter2.postTweet(2,208);
            twitter2.postTweet(2,233);

            twitter2.postTweet(1,222);
            twitter2.postTweet(2,211);

            //","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet"
            //
            // ,"getNewsFeed","follow","getNewsFeed","unfollow","getNewsFeed"

            // [1],[1,2],[1],[1,2],[1]]

            List<Integer> res = twitter2.getNewsFeed(1);
            System.out.println(Arrays.toString(res.toArray()));

            twitter2.follow(1, 2);

            res = twitter2.getNewsFeed(1);
            System.out.println(Arrays.toString(res.toArray()));

            twitter2.unfollow(1, 2);

            res = twitter2.getNewsFeed(1);
            System.out.println(Arrays.toString(res.toArray()));
        }
    }


}
