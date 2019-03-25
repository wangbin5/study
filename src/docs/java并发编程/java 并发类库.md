### 一、 java 同步集合     
- Concurrent： lock-free 高吞吐量
    - 遍历时弱一致性
- Blocking：   
    - 基于锁，等待性操作
    - 无边界：put操作可以立刻返回
    - LinkedBlockingQueue 的吞吐量一般优于 ArrayBlockingQUeue，原因是锁粒度更细
- CopyOnWrite


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

### 九、  AQS（AbstractQueuedSynchronizer）： 基础同步操作的抽象
- 数据
    - state(volatile): 标示状态
    - fifo 等待线程队列
- 方法
    - 基于cas的基础操作方法
    - 子类实现acquire/release方法
    - tryAcquire
    - acquireQueued