package om.learn;
import java.util.ArrayList;
import java.util.List;

public class ZooDriver {

    public static void main(String[] args) {

        Animal b = new GIraffe("Tej", "Das");
        Animal d = new Leopard("Aryaman", "Das");

        List<Animal> zoo = new ArrayList<>();

        zoo.add(b);
        zoo.add(d);

        for (Animal animal : zoo) {
            System.out.println(animal.getClass().getName());
            animal.eat();
            animal.run();
            animal.sleep();

        }

        GIraffe g = GIraffe.createGiraffe(10);
        GIraffe g2 = GIraffe.createGiraffe(20);

        System.out.println(";;;;;;;;;;;;;");
        g.look(d);
    }

}
