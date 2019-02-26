### 1、 分布式锁
- 实现要点
    - 使用redis的如果键值不存在，就设置，否则返回失败的功能
    - 需要设置锁的超时时间，避免因故无法释放锁导致的阻断
    - 申请锁的时候，设置值和超时时间的命令要顺序执行，这里使用redis提供的单条语句保证在执行过程中，不会有其他命令插入。
    - 申请时，将唯一标示作为值记录在redis，释放锁的时候需要比较
    - 释放锁需要判读锁里的值，是否是申请时提供的唯一标示，避免释放当前锁在超时后，其他事务申请到的锁
    - 释放锁时，判断和释放操作也要在顺序执行，这里使用lua脚本保证在执行过程中，不会有其他命令插入
- 分布式锁的目的
    - efficiency： 避免重复计算，如： 计算报表值
    - correctness：保证正确，如： 账号金额处理
<pre>
public class RedisTool {
    private static final Long RELEASE_SUCCESS = 1L;
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }
    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }
}  
</pre>

### 2、分布式锁-Redlock 算法
- 原理
    - 同时对多个redis实例加锁
    - 成功获取锁的条件是过半数实例加锁成功
- 好处： 提高可用性

### 3、延时队列
- zset          设置延时消息，执行时间作为score
- zrangebyscore 获取消息
- zrem          删除消息，删除成功的才执行
- 优化： lua脚本将获取消息、删除消息做原子化处理

### 4、 位图
- 数据类型       string         
- setbit/getbit  执行位读写操作
- bitcount/bitops 计算1、0位的数量
- bitfield key get/set/incrby i/ulength startindex
    - 一次最多处理64位
    - 一条指令可以多个操作（get、set...)
    - i 有符号数
    - u 无符号数，做多操作63位
    - length 操作位长度
    - startindex 操作开始位
    - incrby 如果发生溢出，会将溢出位丢弃
    - overflow + sat 溢出后保留最大值
    - overflow + fail 溢出后失败

### 5、HyperLogLog 不精确去重（准确率99%）
- 业务含义与set类似
- pfadd   ：增加数据
- pfcount : 统计数值
- pfmerge ：合并多个pf计数器
- http://content.research.neustar.biz/blog/hll.html
- 实现原理
    - maxbits： 从右开始，连续0出现的个数
    - 桶： 对每个添加的数，计算maxbits，如果比桶内值大，则将桶值设置成maxbits，共添加K次
    - 多个桶使用调和平均数（倒数的平均）生成最终值
    - 使用pow（2，maxbits）值来估算K值

### 6、 Bloom Filter
- 4.0 之后以插件的方式提供
- bf.reserve key error_rate initial_size     显示创建布隆过滤器
    - error_rate 错误率
    - initial_size 初始大小
- bf.add/bf.madd 添加元素
- bf.exists      判断元素是否存在
- 不提供删除元素的方法
- java lib： JReBloom
- 原理
    - 多个hash函数，将key值计算在多个位上，如果每个位都是1，认为key值存在

### 7、 限流
- 数据类型： zset ，以时间戳作为value和score值
- 操作顺序：
    1. 增加记录 zadd key time time
    2. 溢出时间范围外的记录 zremrangebyscore key 0 time-period
    3. 获取行为数量 zcard key
    4. 设置过期时间 expire key period+1

### 8、 漏斗限流
- 模块： Redis-Cell
- 操作： cl.throttle key capacity operation period quota
- 参数
    - capacity 漏斗容量
    - operation 最大操作数
    - period 时间范围
    - quota 每个操作
- 返回值
    1. 是否允许
    2. 漏斗容量
    3. 剩余空间
    4. 被拒后，等待重试时间
    5. 漏斗全空需要的时间
        
### 9、队列： 阻塞读
- blpop/brpop list1 list2 list3 timeout
    - list1/list2/list3 等待读取的集合
    - timeout 超时时间，0 立即返回
- 可以选择读取多个集合
- 读取顺序与命令执行集合顺序一致，返回第一个有数据的集合中的值
- 如果读取集合全部没有值，请求连接阻塞，知道有集合有值。
- 多个读取连接按照阻塞时间排序，优先通知阻塞时间最长的连接。
    
### 10、 Geo Hash
- Geo Hash 编码：将二维的经纬度数据映射到一维整数
    - 有损压缩
    - http://www.cnblogs.com/LBSer/p/3310455.html
    - http://openlocation.org/geohash/geohash-js/
    - 算法：
        - 步骤1 ： 水平、垂直方向划线平分，将空间划分成四等分
            - 编码： 左上 00  右上 01 左下10 右下 11
        - 步骤2： 选择节点经纬度所在的区域，使用该区域的编码
        - 继续步骤1、2，直到到达编码设置的最大位数
        - 步骤3： 将编码进行base32编码得到Geo Hash 编码
    - 编码后，将区划问题转化成字符串前缀匹配的问题
- zset 实现，score 是Geo Hash的52位整数值
- 命令
    - geoadd key （经度 纬度 值）*
        - 增加一个或多个值
    - geodist key value1 value2 unit
        - 计算两个值间的距离
        - unit 距离单位
    - geopos key value
        - 获取值得经纬度坐标
    - geohash key value
        - 获取值得经纬度编码值
    - georadiusbymember key value 20 km count 3 desc
        - 20 km： 距离范围
        - count 3： 结果集最大个数
        - asc 排序方式
        - withcoord 显示经纬度坐标
        - withdist  显示距离
        - withhash  显示geo hash 编码值
    - georadius key value 经度 纬度 20km count 3 asc
        - 参数同 georadiusbymember
        
### 11、 scan
- 通过游标分步进行，不会阻塞线程
- 提供limit参数，控制每次请求返回的最大记录数
- 服务端不需要保存游标状态
- 返回记录可能重复，需要客户端去重。
- 遍历过程中，修改的数据是否能遍历到，是不确定的。
- 判断遍历结束的标志是游标值是否为0.
- 遍历顺序是采用高位进位加法计算的
    - 左边相加，向右进位
    - 解决扩容、缩容时，避免遍历重复和遗漏
- scan index match key* count length
    - index 游标值
    - key*  匹配规则
    - length 一次scan 遍历的槽位数
    - 返回值第一个数据时下次访问时的游标值