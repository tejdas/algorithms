package om.learn;

public class GIraffe extends Animal {
    public GIraffe(String a, String b) {
        super(a, b);
        System.out.println("constructing giraffe");
    }

    private GIraffe(int height) {
        System.out.println("private giraffe constructor");
    }

    public static GIraffe createGiraffe(int height) {
        GIraffe g = new GIraffe(height);
        return g;
    }

    @Override
    public void eat() {
        System.out.println("giraffe eat");
    }

    @Override
    public void run() {
        System.out.println("giraffe cannot run");
    }

    @Override
    public void sleep() {
        System.out.println("giraffe sleeps");

    }

    public void look(Animal a) {
        a.drink();
        a.sleep();
    }
}
