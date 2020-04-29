package wang.study.concurrent.collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueSample {
    BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);
}
