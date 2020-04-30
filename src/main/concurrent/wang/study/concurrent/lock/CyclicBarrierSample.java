package wang.study.concurrent.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierSample {

    private CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    public void sample() throws BrokenBarrierException, InterruptedException {
        cyclicBarrier.await();
    }



}
