package wang.study.concurrent.lock;

import sun.misc.Unsafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class CountDownLatchSample {

    private CountDownLatch countDownLatch = new CountDownLatch(4);

    public void sample() throws InterruptedException {
        countDownLatch.await();
    }

    public void run(){
        countDownLatch.countDown();
    }




}
