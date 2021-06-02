package net.lc.greedy;

import java.util.PriorityQueue;

/**
 * 630
 * Greedy
 * PriorityQueue
 */
public class CourseScheduleIII {
    static class Course implements Comparable<Course> {
        int duration;
        int latestStartTime;
        int endby;

        public Course(int duration, int endby) {
            this.duration = duration;
            this.endby = endby;
            this.latestStartTime = endby - duration;
        }

        @Override
        public int compareTo(Course o) {
            if (this.endby == o.endby) {
                return Integer.compare(this.duration, o.duration);
            }
            return Integer.compare(this.endby, o.endby);
        }
    }

    static class Duration implements Comparable<Duration> {
        int duration;

        public Duration(int duration) {
            this.duration = duration;
        }

        @Override
        public int compareTo(Duration o) {
            return Integer.compare(o.duration, this.duration);
        }
    }

    public int scheduleCourse(int[][] courses) {

        if (courses == null || courses.length == 0) {
            return 0;
        }

        PriorityQueue<Course> pq = new PriorityQueue<>();
        PriorityQueue<Duration> dq = new PriorityQueue<>();

        for (int[] c : courses) {
            pq.add(new Course(c[0], c[1]));
        }

        int totalTime = 0;
        int count = 0;

        while (!pq.isEmpty()) {
            Course course = pq.remove();

            if (course.latestStartTime >= totalTime) {
                count++;
                dq.add(new Duration(course.duration));
                totalTime += course.duration;
            } else {
                /**
                 * current course cannot be started before totalTime so far. See whether a previously selected course of higher duration can be bumped out
                 * to make room for current task.
                 */
                if (!dq.isEmpty() && (course.latestStartTime >= totalTime - dq.peek().duration) && (course.duration < dq
                    .peek().duration)) {
                    Duration d = dq.remove();
                    totalTime -= d.duration;
                    totalTime += course.duration;
                    dq.add(new Duration(course.duration));
                }
            }
        }
        return count;
    }
}
