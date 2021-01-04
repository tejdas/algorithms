package aaa.bbb;

public class FooNook {

    static class Person {
        String firstName;
        String lastName;
        Person next;

        public Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }


        public String printName() {
            final StringBuilder sb = new StringBuilder("Person{");
            sb.append("firstName='").append(firstName).append('\'');
            sb.append(", lastName='").append(lastName).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        Person tej = new Person("Tej", "Das");
        //System.out.println(tej);

        Person ary = new Person("Aryaman", "Das");
        //System.out.println(ary);

        Person sar = new Person("Sarada", "Subha");
        //System.out.println(sar);

        ary.next = sar;

        tej.next = ary;

        printNames(tej);

        Person som = new Person("Ayusman", "Das");
        sar.next = som;

        printNames(tej);

    }

    private static void printNames(Person obj) {
        while (obj != null) {
            System.out.println(obj);
            System.out.println(obj.printName());
            obj = obj.next;
        }
    }
}
