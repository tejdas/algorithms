package xxx.yyy;

import java.util.HashMap;
import java.util.Map;

public class JobTimesWithCoolingPeriod {

    public static void main(final String[] args) {
        final int[] jobs = {1, 2, 3, 4, 5};

        final int coolingPeriod = 4;
        final int totalTime = jobTimes(jobs, coolingPeriod);
        System.out.println("Total time: " + totalTime);
    }

    public static int jobTimes(final int[] jobs, final int coolingPeriod) {
        if (coolingPeriod < 0) {
            throw new IllegalArgumentException("CoolingPeriod must be positive");
        }
        int totalTime = 0;

        final Map<Integer, Integer> jobFinishTimeById = new HashMap<>();
        for (final int jobId : jobs) {
            if (jobFinishTimeById.containsKey(jobId)) {
                final int lastFinishTime = jobFinishTimeById.get(jobId);
                if (totalTime - lastFinishTime < coolingPeriod) {
                    final int sleepTime = (coolingPeriod - (totalTime-lastFinishTime));
                    totalTime += sleepTime;
                }
            }
            totalTime++;
            jobFinishTimeById.put(jobId,  totalTime);
        }
        return totalTime;
    }
}
