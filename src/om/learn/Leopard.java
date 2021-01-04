package om.learn;
public class Leopard extends Animal {
    public Leopard(String a, String b) {
        super(a, b);
    }

    @Override
    public void eat() {
        System.out.println("Leopard eat");
    }

    @Override
    public void run() {
        System.out.println("Leopard is the fastest runner");
        super.run();
        System.out.println("Leopard got tired");
    }

    @Override
    public void drink() {
        System.out.println("Leopard drink");
    }

    @Override
    public void sleep() {
        System.out.println("Leopard sleep");

    }
}
