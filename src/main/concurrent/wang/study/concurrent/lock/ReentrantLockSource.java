package wang.study.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class ReentrantLockSource implements Lock , java.io.Serializable{

    private ReentrantLock lock;

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    static class Sync extends AbstractQueuedSynchronizerSample {

        final boolean nonfairTryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0){
                    // overflow
                    throw new Error("Maximum lock count exceeded");
                }
                setState(nextc);
                return true;
            }
            return false;
        }
    }

    static final class NonfairSync extends Sync{

        public final void lock() {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
            }
            else{
                acquire(1);
            }
        }

        protected boolean tryAcquire(int acquires){
            return this.nonfairTryAcquire(acquires);
        }

    }

    static final class FairSync extends Sync{
        public final void lock() {
            acquire(1);
        }

        protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (!hasQueuedPredecessors() && compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
    }


    private final Sync sync;

    public ReentrantLockSource(){
        this.sync = new NonfairSync();
    }


    @Override
    public void lock() {
        sync.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
