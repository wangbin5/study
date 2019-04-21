### 一、 java 同步集合  
- 同步容器(Synchronized)注意事项容器
    - Collections.synchronizedList/Map 方法创建
    - 组合操作需要注意竞态条件问题
    - 用迭代器遍历容器时，要加锁   
- Concurrent： lock-free 高吞吐量
    - ConcurrentHashMap/ConcurrentSkipListMap
    - 遍历时弱一致性
- Blocking：   
    - LinkedBlockingQueue/ArrayBlockingQueue
    - SynchronousQueue
    - LinkedTransferQueue
    - PriorityBlockingQueue
    - DelayQueue
    - LinkedBlockingDeque
    - 基于锁，等待性操作
    - 无边界：put操作可以立刻返回
    - LinkedBlockingQueue 的吞吐量一般优于 ArrayBlockingQUeue，原因是锁粒度更细
    - 使用无界队列时，需要充分考虑是否存在导致OOM的隐患
- CopyOnWrite
    - ArrayList
    - Set/SkipListSet 

### 二、 java 锁


### 三、CAS （compare-and-swap）机制  
- lock-free 机制的基础
- 实现
    - Unsafe提供的能力，CPU提供的特殊指令
    - 轻量级实现
- 假设： 竞争是短暂的
- 问题： A->B->A 的问题
    - 版本号（stamp） 解决 
- AtomicInteger
- AtomicLongFieldUpdater
- VarHandle java 9.0

### 四、  AQS（AbstractQueuedSynchronizer）： 基础同步操作的抽象
- 数据
    - state(volatile): 标示状态
    - fifo 等待线程队列
- 方法
    - 基于cas的基础操作方法
    - 子类实现acquire/release方法
    - tryAcquire
    - acquireQueued