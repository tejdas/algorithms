package algo.graph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class RealEstateMatch {
    public static void main(String[] args) throws IOException {

        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));

        String[] array = bfr.readLine().trim().split(" ");

        numClients = Integer.parseInt(array[0]);
        numHouses = Integer.parseInt(array[1]);

        clients = new Client[numClients + 1];
        houses = new House[numHouses + 1];

        for (int i = 1; i <= numClients; i++) {
            array = bfr.readLine().trim().split(" ");
            long a = Long.parseLong(array[0]);
            long mp = Long.parseLong(array[1]);

            clients[i] = new Client(a, mp, i);
        }

        for (int i = 1; i <= numHouses; i++) {
            array = bfr.readLine().trim().split(" ");
            long a = Long.parseLong(array[0]);
            long mp = Long.parseLong(array[1]);

            houses[i] = new House(a, mp, i);
        }

        computeMatch();
        // printMatch();

        int count = houseCount();
        System.out.println(count);

        printMatch();

        bfr.close();
    }

    private static void printMatch() {
        for (int i = 1; i <= numClients; i++) {
            Client c = clients[i];
            if (!c.houseMatches.isEmpty()) {
                System.out.println("ClientId: " + i + "   houses: " + Arrays.toString(c.houseMatches.toArray()));
            }
        }

        for (int i = 1; i <= numHouses; i++) {
            House c = houses[i];
            if (!c.clientMatches.isEmpty()) {
                System.out.println("HouseId: " + i + "   clients: " + Arrays.toString(c.clientMatches.toArray()));
            }
        }
    }

    static class Client implements Comparable<Client> {
        public Client(long minArea, long maxPrice, int index) {
            super();
            this.minArea = minArea;
            this.maxPrice = maxPrice;
            this.index = index;
        }

        long minArea;
        long maxPrice;
        int index;

        Set<Integer> houseMatches = new HashSet<>();

        @Override
        public int compareTo(Client o) {
            return Integer.compare(this.houseMatches.size(), o.houseMatches.size());
        }
    }

    static class House implements Comparable<House> {
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (area ^ (area >>> 32));
            result = prime * result + index;
            result = prime * result + (int) (price ^ (price >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            House other = (House) obj;
            if (area != other.area)
                return false;
            if (index != other.index)
                return false;
            if (price != other.price)
                return false;
            return true;
        }

        public House(long area, long price, int index) {
            super();
            this.area = area;
            this.price = price;
            this.index = index;
        }

        Set<Integer> clientMatches = new HashSet<>();

        long area;
        long price;
        int index;

        @Override
        public int compareTo(House o) {
            return Integer.compare(this.clientMatches.size(), o.clientMatches.size());
        }
    }

    static Client[] clients = null;
    static House[] houses = null;

    static int numClients = -1;
    static int numHouses = -1;

    static void computeMatch() {
        for (int i = 1; i <= numClients; i++) {
            for (int j = 1; j <= numHouses; j++) {
                if (houses[j].area > clients[i].minArea && houses[j].price <= clients[i].maxPrice) {
                    houses[j].clientMatches.add(i);
                    clients[i].houseMatches.add(j);
                }
            }
        }
    }

    static int houseCount() {
        PriorityQueue<House> pq1 = new PriorityQueue<>();
        PriorityQueue<House> pq = new PriorityQueue<>();

        for (int i = 1; i <= numHouses; i++) {
            if (!houses[i].clientMatches.isEmpty())
                pq1.add(houses[i]);
        }

        while (!pq1.isEmpty()) {
            House h = pq1.remove();
            System.out.print(h.clientMatches.size() + " ");
            pq.add(h);
        }
        System.out.println();

        Set<Integer> soldHouses = new HashSet<>();

        int clientRemaining = numClients;

        while (!pq.isEmpty() && clientRemaining > 0) {
            House h = pq.remove();
            if (h.clientMatches.isEmpty()) {
                continue;
            }

            soldHouses.add(h.index);

            Client[] array = new Client[h.clientMatches.size()];

            int index = 0;
            for (int cli : h.clientMatches) {
                array[index++] = clients[cli];
            }

            Arrays.sort(array);

            Client chosenClient = array[0];
            clientRemaining--;

            for (Client cli : array) {
                cli.houseMatches.remove(h.index);
            }

            Set<Integer> affectedHouses = new HashSet<>();

            for (int interestedHouse : chosenClient.houseMatches) {
                houses[interestedHouse].clientMatches.remove(chosenClient.index);
                affectedHouses.add(interestedHouse);
            }

            chosenClient.houseMatches.clear();
            h.clientMatches.clear();

            for (int hindex : affectedHouses) {
                pq.remove(houses[hindex]);

                if (houses[hindex].clientMatches.size() > 0)
                    pq.add(houses[hindex]);
            }
        }
        return soldHouses.size();
    }
}

