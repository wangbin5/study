### Redis 其他操作
- pipeline： 管道命令
    - 客户端将管道内的命令一次性发给服务端
    - 服务端执行完全部命令后将结果一次性发送给客户端
- redis事务： 将操作队列化
    - multi     开始事务
    - 读写命令 命令添加到队列，不执行立刻返回
    - exec     执行队列里的命令
    - 不具备原子性，仅满足事务隔离性中的串行化要求
- watch
    - 实例代码
    <pre>
        while true:
            do_watch
            commands
            multi
            send_commands
            try
                exec
                break
            catch WatchError:
                continue
        end
    </pre>
    - 原理： redis监控关键变量的值，如果在watch之后被修改了，就会抛出异常
    - redis禁止在multi 和exec 直接执行watch 命令
    
### java客户端 Jedis
- Jedis 连接池： JedisPool
    - Jedis不是线程安全的
- Tedis 是另一个 redis 的 java 客户端。
    - 目标是打造一个可在生产环境直接使用的高可用 Redis 解决方案。
    - https://github.com/justified/tedis
    - 单实例： tedis-atomic
    - 高可用： tedis-group
