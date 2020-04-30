package wang.study.concurrent.lock;

import java.util.concurrent.locks.*;

public class LockSample {

    private ReentrantLock reentrantLock;

    private ReadWriteLock readWriteLock;

    private StampedLock stampedLock;

    public void lockSample() throws InterruptedException {
        reentrantLock.lockInterruptibly();

        reentrantLock.lock();
        try{
            //业务代码
        }
        finally{
            reentrantLock.unlock();
        }
    }

    public void readWriteLockSample(){
        Lock lock = readWriteLock.readLock();
        lock.lock();
        try{
            //业务代码
        }
        finally{
            lock.unlock();
        }
    }

    public void stampedLockSample(){
        long stamp = stampedLock.tryOptimisticRead();
        //业务代码
        if(!stampedLock.validate(stamp)){
            //乐观读失败，有其他线程更改了版本号
            stamp = stampedLock.readLock();
            try{
                //业务代码
            }
            finally{
                stampedLock.unlockRead(stamp);
            }
        }
    }



}
