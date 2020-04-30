package wang.study.concurrent.lock;

import java.util.concurrent.Semaphore;

public class ToolsSample {
    private Semaphore semaphore = new Semaphore(10);

    public void usage() throws InterruptedException {
        semaphore.acquire(5);
    }
}
