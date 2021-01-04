package net.lc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;

/**
 * https://leetcode.com/problems/web-crawler-multithreaded/
 * 19/20 tests pass. Last one TLE
 */
class WebCrawlerMT {
    interface HtmlParser {
        public List<String> getUrls(String url);
    }

    private String hostname(String url) {
        return url.split("/")[2];
    }

    public List<String> crawl(String startUrl, final HtmlParser htmlParser) {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        ConcurrentSkipListSet<String> seen = new ConcurrentSkipListSet<>();

        queue.offer(startUrl);
        seen.add(startUrl);

        final List<String> result = new ArrayList<>();
        final String host = hostname(startUrl);
        int nThreads = 8;
        final CountDownLatch latch = new CountDownLatch(nThreads);

        final Runnable worker = new Runnable() {
            public void run() {
                try {
                    while (true) {
                        final String url = queue.poll();
                        if (url == null) {
                            break;
                        }
                        result.add(url);
                        for (String nextUrl : htmlParser.getUrls(url)) {
                            if (seen.add(nextUrl) && host.equals(hostname(nextUrl))) {
                                queue.offer(nextUrl);
                            }
                        }
                    }
                } finally {
                    latch.countDown();
                }
            }
        };

        for (int i = 0; i < nThreads; i++) {
            new Thread(worker).start();
        }

        try {
            latch.await();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return result;
    }
}
