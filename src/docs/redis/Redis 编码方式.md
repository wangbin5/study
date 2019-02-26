### 对象头
<pre>
    {
        type         数据类型
        encoding     编码类型
        lru          最后访问时间戳
        refcount     引用数
        ptr          指向真实数据指针
    }
</pre>

### 字符串-SDS
- 分配时len和capacity大小一样
- <pre>
    {
        capacity        容量
        len             字符串长度
        flags           标志位
        byte[] content  字节数组
    }
</pre>

- raw 编码类型
    - 需要分配两次内存，一次分配对象头，一次分配数据
    - 对象头和数据不会连续存放
- embedstr 编码类型
    - 只需要分配一次内存，对象头和数据连续存储
    - 最大44个字节： 
        - 对象头（16字节）+3字节（容量）+1字节（NULL结尾）+内容 = 64字节

### 字典
#### 字典值对象结构
<pre>
    {
        数据类型(type)
        编码方式(encoding)
        数据指针
        虚拟内存
        其它信息
    }
</pre>

#### 字典数据结构
<pre>
    字典：
    {
        哈希表[2]
        rehash记录的下标
        字典类型（提供通用方法）
    }
    哈希表：
    {
        字典项链表数组  -- hash值 mod 数组大小 = 数组下标
        数组大小
    }
    字典项：
    {
        键
        值
        下一个元素 （hash冲突的时候使用）
    }
</pre> 

#### 渐进式rehash：
1. 就是把拷贝节点数据的过程平摊到后续的操作中，而不是一次性拷贝
2. 查询： 先查h[0]，无查询结果再查 h[1]
3. redis的全部写入操作到h[1]上执行
4. 每个操作（查找元素、删除元素） 迁移一个key值
    - 长时间没有请求，会有定时任务触发
5. rehash时，数据修改、删除操作到h[1]执行
6. key迁移完成后,释放h[0]占用的内存，并将h[0]指针设置到h[1]



### 压缩列表-ziplist
- 数组的数据结构存储
- 如果是hash结构，key和value会占两个entry
- 如果是set结构， value和score会占两个entry
<pre>
    {
        zlbytes 列表占用的字节数
        zltail  最后一个entry的偏移量
        zllen   列表中entry的数量
        entry列表
        zlend   结束标示
    }
</pre>

### 数字集合 -intset
- 数组的数据结构
- 数据编码类型可以扩张，16-->32-->64,但是不能回缩
<pre>
    {
        encoding  数值的编码类型，16、32、64位
        length    元素个数
        value数组
    }
</pre>

### linklist
- 缺点：链表附加空间较多，且会加剧内存碎片化
<pre>
    node {
        prev
        next
        value
    }
    list {
        head
        tail
        length
    }
</pre>


### quicklist--> ziplist+linkedlist
- 默认单个ziplist 8kb
- 默认压缩深度为0（不压缩），压缩深度是指首尾各x个节点不压缩
- ziplist 节点可以压缩（LZF算法）
<pre>
    ziplist_compressed{
        size
        compressed_data : byte[]
    }
    quickListNode {
        prev
        next    : quickListNode 类型
        zl      : ziplist 实例
        size
        count
        encoding
    }
    quickList{
        head
        tail
        count
        nodes
        compressDepth
    }
</pre>

### skiplist 跳跃列表
- zset = hash + skiplist
- skiplist 提供排序功能
<pre>
    node {
        value
        score
        node[] forwards  //多层指针，指向同层下一个节点
        node backward    //回溯指针，指向最下层上一个节点
    }
    zset {
        node header    //表头节点
        maxlevel       //最高层
        hash ht        //键值对
    }
</pre>
- 查找算法
<pre>
    当前节点 = 表头节点    
1)  for 节点 ： 当前节点的多层指针
        if 节点值 < 查找值
            当前节点 = 节点
            重复步骤1）
        else if 节点值 = 查找值：
            查找结束，返回
        else
            continue;
        end
    end 
</pre>
- 随机层数： 节点以pow(2,-n) 概率被分配到第n层。
- 插入算法
    - 查找节点所在位置，并记录查找路径
        - 查找路径应该是每层一个节点，顺序由最高层到最底层
    - 计算随机层数
    - 对查找路径上的每个节点，更新多层指针到插入节点
    - 同时更新插入节点的多层指针到查找路径节点的多层指针的原值。
- 删除算法
    - 查找节点所在位置，并记录查找路径
    - 对查找路径上的每个节点，更新多层指针到删除节点的多层指针的值
    - 删除节点
- 更新算法
    - 先删除节点
    - 修改节点值
    - 插入节点
- 算法特点
    - 没有平衡性要求，如：二叉树、红黑树
    - 层数采用概率方式随机确定，更偏向实践。