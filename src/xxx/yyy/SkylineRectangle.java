package xxx.yyy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SkylineRectangle {

    static class Building {
        public Building(final int width, final int height) {
            this.width = width;
            this.height = height;
        }
        public int width;
        public int height;
    }

    static int maxRectangleArea = 0;
    static int height = 0;
    static int width = 0;

    /*
     * Map of Height -> Width
     */
    static Map<Integer, Integer> maxWidth = new HashMap<>();

    static void processBuilding(final List<Building> buildings) {
        for (final Building building : buildings) {
            final Set<Integer> keys = maxWidth.keySet();
            for (final Integer key : keys) {

                if (building.height < key) {
                    maxWidth.remove(key);
                } else {
                    int curWidth = maxWidth.get(key);
                    curWidth += building.width;
                    maxWidth.put(key,  curWidth);
                    final int area = curWidth * key;
                    if (area > maxRectangleArea) {
                        maxRectangleArea = area;
                        height = key;
                        width = curWidth;
                    }
                }
            }

            if (!maxWidth.containsKey(building.height)) {
                maxWidth.put(building.height,  building.width);
                if (building.height * building.width > maxRectangleArea) {
                    maxRectangleArea = building.height * building.width;
                    height = building.height;
                    width = building.width;
                }
            }
        }

        System.out.println("height: " + height + " width: " + width + " maxArea: " + maxRectangleArea);
    }

    public static void main(final String[] args) {
        final List<Building> buildings = new ArrayList<>();
        buildings.add(new Building(1, 1));
        buildings.add(new Building(2, 3));
        buildings.add(new Building(3, 4));
        buildings.add(new Building(1, 3));
        buildings.add(new Building(1, 6));
        buildings.add(new Building(1, 3));
        buildings.add(new Building(1, 2));
        buildings.add(new Building(1,1));

        processBuilding(buildings);
    }

}
