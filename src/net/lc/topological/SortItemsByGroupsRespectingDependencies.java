package net.lc.topological;

import java.util.*;

/**
 * BFS topological sort
 * 1203
 */
public class SortItemsByGroupsRespectingDependencies {
    static class Group {
        int id;
        int indegree = 0;
        Set<Integer> items = new HashSet<>();
        Set<Integer> successors = new HashSet<>();
        Set<Integer> predecessors = new HashSet<>();

        public Group(int id) {
            this.id = id;
        }
    }

    private final Map<Integer, Group> groups = new HashMap<>();
    private final Map<Integer, Integer> itemToGroupMap = new HashMap<>();
    private int m;
    private int n;
    private int[] group;
    private List<List<Integer>> beforeItems;
    private int[] resultArray;
    private int pos = 0;

    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        this.n = n;
        this.m = m;
        this.group = group;
        this.beforeItems = beforeItems;
        this.groups.clear();
        this.itemToGroupMap.clear();
        pos = 0;
        resultArray = new int[n];

        buildGroups();
        boolean flag = topoSortGroup();
        if (!flag) return new int[0];
        return resultArray;
    }

    private void buildGroups() {
        int unassignedGrouplabel = m;
        for (int i = 0; i < group.length; i++) {
            int g = group[i];

            Group group;
            if (g == -1) {
                group = new Group(unassignedGrouplabel++);
                groups.put(group.id, group);
            } else {
                if (groups.containsKey(g)) {
                    group = groups.get(g);
                } else {
                    group = new Group(g);
                    groups.put(group.id, group);
                }
            }

            group.items.add(i);
            itemToGroupMap.put(i, group.id);
        }

        for (Group group : groups.values()) {
            for (int it : group.items) {
                List<Integer> beforeList = beforeItems.get(it);
                for (int bi : beforeList) {
                    int befGroupId = itemToGroupMap.get(bi);
                    if (group.id != befGroupId && !group.predecessors.contains(befGroupId)) {
                        Group beforeGroup = groups.get(befGroupId);
                        group.predecessors.add(befGroupId);
                        beforeGroup.successors.add(group.id);
                        group.indegree++;
                    }
                }
            }
        }
    }

    private boolean topoSortGroup() {

        int groupsWithPositiveIndegree = groups.size();

        Queue<Integer> queue = new LinkedList<>();
        for (Group group : groups.values()) {
            if (group.indegree == 0) {
                groupsWithPositiveIndegree--;
                queue.add(group.id);
            }
        }

        while (!queue.isEmpty()) {
            int groupId = queue.remove();
            Group group = groups.get(groupId);
            if (group.items.size() == 1) {
                resultArray[pos++] = group.items.iterator().next();
            } else {
                boolean flag = processGroup(group);
                if (!flag) return false;
            }

            for (int succId : group.successors) {
                Group succGroup = groups.get(succId);
                succGroup.indegree--;
                if (succGroup.indegree == 0) {
                    groupsWithPositiveIndegree--;
                    queue.add(succId);
                }
            }
        }

        if (groupsWithPositiveIndegree > 0) return false;
        return true;
    }

    static class Item {
        int id;
        int indegree = 0;
        Set<Integer> successors = new HashSet<>();
        Set<Integer> predecessors = new HashSet<>();

        public Item(int id) {
            this.id = id;
        }
    }
    private boolean processGroup(Group group) {
        Map<Integer, Item> items = new HashMap<>();

        for (int i : group.items) {
            Item item = new Item(i);
            items.put(i, item);
            List<Integer> biList = beforeItems.get(i);
            for (int bi : biList) {
                if (group.items.contains(bi)) {
                    item.predecessors.add(bi);
                }
            }
        }

        int itemsWithPositiveIndegree = items.size();
        Queue<Integer> queue = new LinkedList<>();

        for (Item item : items.values()) {
            item.indegree = item.predecessors.size();
            if (item.indegree == 0) {
                itemsWithPositiveIndegree--;
                queue.add(item.id);
            }

            for (int pred : item.predecessors) {
                Item predItem = items.get(pred);
                predItem.successors.add(item.id);
            }
        }

        while (!queue.isEmpty()) {
            int itemId = queue.remove();
            Item item = items.get(itemId);
            resultArray[pos++] = itemId;

            for (int succId : item.successors) {
                Item succItem = items.get(succId);
                succItem.indegree--;
                if (succItem.indegree == 0) {
                    itemsWithPositiveIndegree--;
                    queue.add(succId);
                }
            }
        }

        if (itemsWithPositiveIndegree > 0) return false;
        return true;
    }

    public static void main(String[] args) {
        {
            int n = 8;
            int m = 2;
            int[] group = { -1, -1, 1, 0, 0, 1, 0, -1 };
            List<List<Integer>> beforeItems = new ArrayList<>();
            beforeItems.add(Collections.emptyList());
            beforeItems.add(Arrays.asList(6));
            beforeItems.add(Arrays.asList(5));
            beforeItems.add(Arrays.asList(6));
            beforeItems.add(Arrays.asList(3, 6));
            beforeItems.add(Collections.emptyList());
            beforeItems.add(Collections.emptyList());
            beforeItems.add(Collections.emptyList());

            int[] result = new SortItemsByGroupsRespectingDependencies().sortItems(n, m, group, beforeItems);
            System.out.println(Arrays.toString(result));
        }
        {
            int n = 8;
            int m = 2;
            int[] group = { -1, -1, 1, 0, 0, 1, 0, -1 };
            List<List<Integer>> beforeItems = new ArrayList<>();
            beforeItems.add(Collections.emptyList());
            beforeItems.add(Arrays.asList(6));
            beforeItems.add(Arrays.asList(5));
            beforeItems.add(Arrays.asList(6));
            beforeItems.add(Arrays.asList(3));
            beforeItems.add(Collections.emptyList());
            beforeItems.add(Arrays.asList(4));
            beforeItems.add(Collections.emptyList());

            int[] result = new SortItemsByGroupsRespectingDependencies().sortItems(n, m, group, beforeItems);
            System.out.println(Arrays.toString(result));
        }
    }
}
