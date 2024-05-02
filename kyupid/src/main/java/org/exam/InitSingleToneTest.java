package org.exam;

public class InitSingleToneTest {
    public static void main(String[] args) {
        Foo.getInstance();
    }
}

class Foo {
    private Foo() {
        System.out.println("foo");
    }

    private static class SingletonHolder {
        private static final Foo INSTANCE = new Foo();

        static {
            Bar.regist();
        }
    }

    public static Foo getInstance() {
        return SingletonHolder.INSTANCE;
    }
}

class Bar {
    static void regist() {
        System.out.println("regist");
    }
}