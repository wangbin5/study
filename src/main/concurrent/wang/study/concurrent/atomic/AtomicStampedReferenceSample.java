package wang.study.concurrent.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceSample {
    private AtomicStampedReference<Integer> reference = new AtomicStampedReference<>(1,1);

    public void addValue(int value){
        int[] stamp = new int[1];
        int last = reference.get(stamp);

        while(!reference.compareAndSet(last,last+value,stamp[0],stamp[0]+1)){
            last = reference.get(stamp);
        }
    }
}
