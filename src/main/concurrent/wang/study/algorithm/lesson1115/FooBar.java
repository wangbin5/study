package wang.study.algorithm.lesson1115;

import org.apache.catalina.loader.ResourceEntry;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FooBar {
    private ReentrantLock lock;

    private Condition foo;
    private Condition bar;

    private int n;

    private int count = 0;

    public FooBar(int n) {
        this.n = n;
        lock = new ReentrantLock();
        foo = lock.newCondition();
        bar = lock.newCondition();
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            try{
                lock.lock();
                if(count%2 == 1 ){
                    foo.await();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                count++;
                bar.signal();
            }
            finally{
                lock.unlock();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            try{
                lock.lock();
                if(count%2 == 0 ){
                    bar.await();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                count--;
                foo.signal();
            }
            finally{
                lock.unlock();
            }
        }
    }
}
