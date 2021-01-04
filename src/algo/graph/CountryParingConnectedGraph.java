package algo.graph;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public class CountryParingConnectedGraph {
    public static void main(String[] args) throws Exception {
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = bfr.readLine().split(" ");
        int N = Integer.parseInt(temp[0]);
        int I = Integer.parseInt(temp[1]);

        Set<Integer> seen = new HashSet<>();

        Map<String, Set<Integer>> countryMap = new HashMap<>();


        for (int i = 0; i < I; i++) {
            temp = bfr.readLine().split(" ");
            int a = Integer.parseInt(temp[0]);
            int b = Integer.parseInt(temp[1]);

            if (a < 0 || a >= N || b < 0 || b >= N) {
                continue;
            }

            if (seen.contains(a) && seen.contains(b)) {

                Set<Integer> aSet = null;
                Set<Integer> bSet = null;
                String aKey = null;
                String bKey = null;
                for (Entry<String, Set<Integer>> entry : countryMap.entrySet()) {

                    if (entry.getValue().contains(a)) {
                        aSet = entry.getValue();
                        aKey = entry.getKey();
                    }
                    if (entry.getValue().contains(b)) {
                        bSet = entry.getValue();
                        bKey = entry.getKey();
                    }

                    if (aSet != null && bSet != null) {
                        break;
                    }
                }

                if (aKey.equals(bKey)) {
                    // duplicate
                } else {
                    if (aSet.size() >= bSet.size()) {
                        aSet.addAll(bSet);
                        countryMap.remove(bKey);
                    } else {
                        bSet.addAll(aSet);
                        countryMap.remove(aKey);
                    }
                }
            } else if (seen.contains(a)) {
                for (Entry<String, Set<Integer>> entry : countryMap.entrySet()) {
                    if (entry.getValue().contains(a)) {
                        entry.getValue().add(b);
                        break;
                    }
                }
                seen.add(b);
            } else if (seen.contains(b)) {
                for (Entry<String, Set<Integer>> entry : countryMap.entrySet()) {
                    if (entry.getValue().contains(b)) {
                        entry.getValue().add(a);
                        break;
                    }
                }
                seen.add(a);
            } else {
                Set<Integer> set = new HashSet<>();
                set.add(a);
                set.add(b);
                countryMap.put(UUID.randomUUID().toString(), set);
                seen.addAll(set);
            }
        }

        long combinations = 0;


        List<Long> personsByCountry = new ArrayList<>();
        for (Set<Integer> country : countryMap.values()) {
            // for (int i : country) {
            // System.out.print(i + " ");
            // }
            // System.out.println();
            personsByCountry.add((long) country.size());
        }


        long unseen = N - seen.size();
        // System.out.println("unseen: " + unseen);

        for (int i = 0; i < personsByCountry.size() - 1; i++) {
            for (int j = i + 1; j < personsByCountry.size(); j++) {
                combinations += (personsByCountry.get(i) * personsByCountry.get(j));
            }
        }

        // System.out.println("combinations1: " + combinations);
        if (unseen > 0) {
            combinations += (unseen * (unseen - 1) / 2);
            // System.out.println("combinations2: " + combinations);

            for (long val : personsByCountry) {
                combinations += unseen * val;
                // System.out.println("combinations3: " + combinations);
            }
        }

        // Compute the final answer - the number of combinations

        System.out.println(combinations);

        bfr.close();
    }
}
