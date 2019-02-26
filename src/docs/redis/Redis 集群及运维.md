### Redis 数据备份
#### RDB
- 优点： 单文件，速度快，性能好
- 缺点： 两次备份之间的数据会丢失
- 备份命令
    - SAVE     同步生成备份文件，会阻断主线程
    - BGSAVE   后台进程生成备份文件
    - LASTSAVE 查看生成结果
- 配置
<pre>
    # RDB文件名
    dbfilename dump.rdb
    # 文件目录
    dir ./
    # 保存点设置，可多个
    # 第一个参数表示间隔时间，第二个参数标示修改数超过1000，开启备份
    save 60 1000
</pre>
- bgsave 的原理： fork + copy on write
    - 在Linux程序中，fork（）会产生一个和父进程完全相同的子进程，但子进程在此后多会exec系统调用，出于效率考虑，linux中引入了“写时复制“技术，也就是只有进程空间的各段的内容要发生变化时，才会将父进程的内容复制一份给子进程。
- 参考网址： 
<pre>
    http://www.cnblogs.com/biyeymyhjob/archive/2012/07/20/2601655.html 
</pre>
    
#### AOF
- 变更命令写入文件，会阻塞主线程
- AOF 重写： 压缩AOF日志，只保留最后状态
- 优点：可靠性好，数据不会丢失
- 缺点：备份文件大，恢复速度慢
- 配置：
<pre>
    #开启aof日志
    appendonly yes
    #日志存放目录
    dir ./
    #日志存放文件名
    appendfilename "appendonly.aof"
    #日志写文件的时间
    # always   每个命令写一次
    # no       从不写
    # everysec 每秒写一次，推荐
    appendfsync everysec
</pre>
- 参考网址
<pre>
  https://segmentfault.com/a/1190000002906345
</pre>

### TODO RDB+AOF 
- 支持版本： 
- 原理+使用范围


### Reids 集群
- 主从同步
    - 全量同步： RDB，ASF
    - 增量同步的是指令流，同步指令存在环结构里，如果同步过慢，环内空间不够，就需要全量同步了。
    - 指令： wait  nodenumber waittime
        - 等待nodenumber个从节点同步完成后，才返回
        - 等待时间超过waittime，导致超时错误
    - 哨兵（sentinel）：监控主从节点的健康状态
        - 客户端从sentinel里获取主节点地址
        - 建立连接时，从sentinel里读取主节点地址
        - 捕获ReadOnlyError时，从sentinel里获取主节点地址，关闭连接，重连服务
- Codis
    - 代理模式
    - 缺点： 
        1. 操作受限制，比如重命名key值
        2. 不是官方方案
    
- Redis Cluster 集群
    - 官方方案
    - 对key取hash，然后分配在16384个槽位上
    - 客户端向错误的节点发送请求后，节点返回特殊消息，指导客户端访问正确的节点
    - 迁移工具： redis-trib
    - gossip 协议广播自己的状态
    - cluster不支持事务

### Redis 运维
- info 指令 --> 可视化运维界面
    - 每秒执行指令数
    - 客户端连接数
    - 内存占用大小
    - 复制积压缓冲区大小
- 过期策略
    - 定时删除+惰性删除
    - 定时删除算法
        1. 随机选20个key
        2. 删除选中key中过期的
        3. 如果删除key数量超过选中的1/4，重复执行步骤1
        4. 删除key值不足选中的1/4，或者超时
        5. 删除结束
    - 大批key过期删除算法耗时过长，会导致卡顿，甚至链接超时异常
        - 设置过期时间时，加个随机数
    - 从节点不会执行过期扫描操作
- 实际使用内存超过最大设置值时的操作
    - volatile-lru： 使用lru算法，淘汰设置过期时间的key
        - 算法： 
            1. 每个key记录最后访问时间的时间戳
            2. 采样n个key，淘汰采样中时间戳最旧的
            3. 如果淘汰后，内存还是不足，执行步骤2
            4. 结束
    - volatile-ttl： 比较key剩余寿命ttl，ttl越小越优先被淘汰
    - volatile-random： 最忌淘汰设置过期时间的key
    - noeviction：   禁止写操作
    - allkeys-lru/allkeys-random: 雷系volatile-xx，参与方是全部的key
- 懒惰删除：后台线程异步删除数据，执行命令后，主线程不能访问删除的数据  
    - unlink key
    - flushdb async
- 安全
    - 危险操作重命名 rename command1 newCommand1
    - bind 内网地址
    - 开启用户名、密码机制
    - 普通用户启动redis
    - 禁止Lua脚本中包含用户输入内容
    - 使用ssl代理软件： spiped