package om.learn;

public class Employee extends Person implements Leader, Manager {

    @Override
    public void manage() {
        System.out.println("manage");

    }

    @Override
    public void delegate() {
        System.out.println("delegate");

    }

    @Override
    public void eat() {
        System.out.println("employee eat");
    }

    public static void main(String[] args) {
        Employee e = new Employee();
        e.manage();
        e.delegate();
        e.eat();

        Person p = new Employee();
        p.eat();

        Leader l = new Employee();
        l.delegate();

        Manager m = new Employee();
        m.manage();
    }
}
