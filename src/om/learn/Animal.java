package om.learn;

public abstract class Animal {

    public Animal() {

    }
    public Animal(String a, String b) {
    }
    public void eat() {
        System.out.println("animal eat");
    }

    public void run() {
        System.out.println("animal run");
    }

    protected void drink() {
        System.out.println("animal drink");
    }

    public abstract void sleep();
}
