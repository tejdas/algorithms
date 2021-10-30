package net.lc.greedy;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 630
 * Greedy
 * PriorityQueue
 */
public class CourseScheduleIII {
    static class Course implements Comparable<Course> {
        int duration;
        int endby;

        public Course(int duration, int endby) {
            this.duration = duration;
            this.endby = endby;
        }

        @Override
        public int compareTo(Course o) {
            if (this.endby == o.endby) {
                return Integer.compare(this.duration, o.duration);
            }
            return Integer.compare(this.endby, o.endby);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Course{");
            sb.append("duration=").append(duration);
            sb.append(", endby=").append(endby);
            sb.append('}');
            return sb.toString();
        }
    }

    public int scheduleCourse(int[][] courses) {

        if (courses == null || courses.length == 0) {
            return 0;
        }

        PriorityQueue<Course> pq = new PriorityQueue<>();
        PriorityQueue<Integer> dq = new PriorityQueue<>(Comparator.reverseOrder());

        for (int[] c : courses) {
            pq.add(new Course(c[0], c[1]));
        }

        int totalTime = 0;
        int courseCount = 0;

        while (!pq.isEmpty()) {
            Course course = pq.remove();

            if ((totalTime + course.duration) <= course.endby) {
                courseCount++;
                dq.add(course.duration);
                totalTime += course.duration;
                System.out.println("chose: " + course.toString());
            } else {
                /**
                 * current course cannot end before course endby time. See whether a previously selected course of higher duration can be bumped out
                 * to make room for current task.
                 */
                if (!dq.isEmpty()) {
                    int totalTimeMinusHigerDurationJob = totalTime - dq.peek().intValue();

                    if (course.duration < dq.peek().intValue() && (totalTimeMinusHigerDurationJob + course.duration) <= course.endby) {
                        Integer d = dq.remove();
                        totalTime -= d;
                        totalTime += course.duration;
                        dq.add(course.duration);

                        System.out.println("chose and replaced: duration" + d + "   with: " + course.toString());
                    }
                }
            }
        }
        return courseCount;
    }

    public static void main(String[] args) {
        int[][] input = {{7,17},{3,12},{10,20},{9,10},{5,20},{10,19},{4,18}};
        System.out.println(new CourseScheduleIII().scheduleCourse(input));
    }
}
