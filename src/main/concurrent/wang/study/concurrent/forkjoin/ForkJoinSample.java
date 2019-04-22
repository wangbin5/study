package wang.study.concurrent.forkjoin;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSample {
    static class Task extends RecursiveTask<Integer>{
        private final int start;
        private final int end;
        public Task(int start,int end){
            this.start = start;
            this.end = end;
        }
        @Override
        protected Integer compute() {
            if(end-start>10){
                int middle = (end+start)/2;
                Task t1 = new Task(start,middle);
                Task t2 = new Task(middle+1,end);
                t1.fork();
                t2.fork();
                int sum = t1.join()+t2.join();
                return sum;
            }
            int sum = 0;
            for(int i=start;i<=end;i++){
                sum+=i;
            }
            return sum;
        }
    }

    public static void main(String[] args){
        long start = System.currentTimeMillis();
        Task task = new Task(0,100);
        int value = task.compute();
        System.out.println("calculate result "+value+",use time "+(System.currentTimeMillis()-start));
    }
}
