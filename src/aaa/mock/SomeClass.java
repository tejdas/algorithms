package aaa.mock;

public class SomeClass {
    static class ClassB {
        public void foo() {
            // making some HTTP calls or gRPC calls
        }

        public String getTicketFromZenDesk() {
            // call ZenDesk GET API and get ticket info
            return "";
        }
    }

    static class ClassA {
        ClassB inner = new ClassB();
        public void bar() {
            inner.foo();
            //
            //
            //
            inner.foo();

            String str = inner.getTicketFromZenDesk();
            /*
           if (true) {// if (JsonParse(str) == error) {
                // do this
            } else {
                // do that
            }
            */
        }
    }

    public static void main(String[] args) {
        ClassA a = new ClassA();
        a.bar();
        // assert that ClassB.foo() got called twice
    }
}
