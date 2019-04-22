package wang.study.concurrent.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterSample {
    static class Sample{
        volatile int count;
    }
    AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(Sample.class,"count");

    private Sample sample;
    public AtomicIntegerFieldUpdaterSample(){
        sample = new Sample();
    }

    /**
     * compare and set 用法示例
     */
    public void addOne(){
        int current = updater.get(sample);
        while(!updater.compareAndSet(sample,current,current+1)){
            System.out.println("add one fail "+current);
            current = updater.get(sample);
        }
    }

    /**
     * 直接增加
     */
    public void addOneDirect(){
        updater.addAndGet(sample,1);
    }

    static class Task implements Runnable{
        private AtomicIntegerFieldUpdaterSample updaterSample;
        public Task(AtomicIntegerFieldUpdaterSample updaterSample){
            this.updaterSample = updaterSample;
        }
        @Override
        public void run() {
            for(int i=0;i<1000;i++){
                updaterSample.addOne();
            }
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        AtomicIntegerFieldUpdaterSample sample = new AtomicIntegerFieldUpdaterSample();
        List<Future> futures = new ArrayList<>();
        for(int i=0;i<10;i++){
            futures.add(service.submit(new Task(sample)));
        }
        for(Future future : futures){
            future.get();
        }
        service.shutdown();
        System.out.println("result is "+sample.sample.count);
    }

}
