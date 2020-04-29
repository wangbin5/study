package wang.study.concurrent.lock;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import sun.misc.Unsafe;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.LockSupport;

public class AbstractQueuedSynchronizerSample {



    static final class Node {
        static final Node SHARED = new Node();

        static final Node EXCLUSIVE = null;
        static final int CANCELLED =  1;
        static final int SIGNAL    = -1;
        static final int CONDITION = -2;
        static final int PROPAGATE = -3;

        volatile Node prev;

        volatile Node next;

        volatile Thread thread;

        Node nextWaiter;

        volatile int waitStatus;

        final Node predecessor() throws NullPointerException {
            Node p = prev;
            if (p == null)
                throw new NullPointerException();
            else
                return p;
        }

        public Node(Thread thread, Node mode) {
            this.thread = thread;
            this.nextWaiter = mode;
        }

        public Node() {

        }
    }

    private transient volatile Node head;

    private transient volatile Node tail;

    private volatile int state;

    public void lock(){

    }

    protected final boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    public final void acquire(int arg) {
        if(tryAcquire(arg)){
            return;
        }
        Node node = addWaiter(Node.EXCLUSIVE);
        if (acquireQueued(node, arg)) {
            selfInterrupt();
        }
    }

    final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                if (shouldParkAfterFailedAcquire(p, node)){
                    interrupted = parkAndCheckInterrupt();
                }
            }
        } finally {
            if (failed){
                cancelAcquire(node);
            }
        }
    }

    private void cancelAcquire(Node node) {
        if (node == null){
            return;
        }
        node.thread = null;

        // Skip cancelled predecessors
        Node pred = node.prev;
        while (pred.waitStatus > 0){
            node.prev = pred.prev;
            pred = pred.prev;
        }

        Node predNext = pred.next;
        node.waitStatus = Node.CANCELLED;

        if (node == tail && compareAndSetTail(node, pred)) {
            compareAndSetNext(pred, predNext, null);
        } else {
            // If successor needs signal, try to set pred's next-link
            // so it will get one. Otherwise wake it up to propagate.
            int ws;
            if (pred != head){
                ws = pred.waitStatus;
                if(ws == Node.SIGNAL ||(ws <= 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL)) && pred.thread != null){
                    Node next = node.next;
                    if (next != null && next.waitStatus <= 0){
                        compareAndSetNext(pred, predNext, next);
                    }

                }
                else {
                    unparkSuccessor(node);
                }
            } else {
                unparkSuccessor(node);
            }

            node.next = node; // help GC
        }
    }

    private void unparkSuccessor(Node node) {

        int ws = node.waitStatus;
        if (ws < 0) {
            compareAndSetWaitStatus(node, ws, 0);
        }

        /*
         * Thread to unpark is held in successor, which is normally
         * just the next node.  But if cancelled or apparently null,
         * traverse backwards from tail to find the actual
         * non-cancelled successor.
         */
        Node s = node.next;
        if (s == null || s.waitStatus > 0) {
            s = null;
            for (Node t = tail; t != null && t != node; t = t.prev){
                if (t.waitStatus <= 0){
                    s = t;
                }
            }
        }
        if (s != null){
            LockSupport.unpark(s.thread);
        }

    }


    private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        return Thread.interrupted();
    }

    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        int ws = pred.waitStatus;
        if (ws == Node.SIGNAL) {
            return true;
        }
        if (ws > 0) {
            do {
                node.prev = pred.prev;
                pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }


    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.prev = null;
    }

    private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);
        enq(node);
        return node;
    }

    private Node enq(final Node node) {
        for (;;) {
            Node t = tail;
            if (t == null) { // Must initialize
                if (compareAndSetHead(new Node())){
                    tail = head;
                }
            } else {
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return t;
                }
            }
        }
    }


    public final boolean hasQueuedPredecessors() {
        // The correctness of this depends on head being initialized
        // before tail and on head.next being accurate if the current
        // thread is first in queue.
        Node t = tail; // Read fields in reverse initialization order
        Node h = head;
        if(h == t){
            return false;
        }
        Node s = h.next;
        return (s == null || s.thread != Thread.currentThread());
    }

    protected boolean tryAcquire(int arg){
        return true;
    }

    private transient Thread exclusiveOwnerThread;

    protected final void setExclusiveOwnerThread(Thread thread) {
        exclusiveOwnerThread = thread;
    }

    protected final Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }
    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }

    private final boolean compareAndSetHead(Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }
    private static final boolean compareAndSetWaitStatus(Node node,int expect,int update) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset,expect, update);
    }
    private static final boolean compareAndSetNext(Node node,Node expect,Node update) {
        return unsafe.compareAndSwapObject(node, nextOffset, expect, update);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private static final long nextOffset;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizerSample.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizerSample.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizerSample.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizerSample.Node.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset
                    (AbstractQueuedSynchronizerSample.Node.class.getDeclaredField("next"));

        } catch (Exception ex) { throw new Error(ex); }
    }

    static void selfInterrupt() {
        Thread.currentThread().interrupt();
    }
}
