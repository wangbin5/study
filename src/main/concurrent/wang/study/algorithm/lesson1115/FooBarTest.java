package wang.study.algorithm.lesson1115;


import org.testng.annotations.Test;

public class FooBarTest {
    private FooBar fooBar = new FooBar(100);



    @Test
    public void run() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    fooBar.foo(new Runnable() {
                        @Override
                        public void run() {
                            System.out.print("foo");
                        }
                    });
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        });
        t1.start();
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fooBar.bar(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("bar");
                        }
                    });
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        });
        t2.start();
        t1.join();
        t2.join();

    }


}