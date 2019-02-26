
### 字符串（string）
- set/get
- setnx key value 当key 不存在时，设置
- setrange/getrange 获取字符串的一部分
- mset/mget 批量设置
- append 追加值
- setex/psetex 设置超时值（秒、毫秒）
- INCR/incrby/incrbyfloat/decr/decrby 自增、自减
    
### 哈希表（hash）
- hset/hget key field value 设置、获取值
- hmset/hmget 批量操作
- hdel key field 删除列
- hgetall 获取全部字段值
- hsetnx 字段不存在时，设置值
- hkeys/hvalues 
- hexists field是否存在
- hincrby/hincrbyfloat key field increment 自增
    
### 列表（list）
- lpush/lpop key value 将value放到列表头部
- rpush/rpop key value 将value放到列表尾部
- lpushx/rpushx 列表存在时，才push
- lindex key index 获取列表中下标是idnex的值
- lrange key start stop  获取范围内的值
- lset key index value 设置index位置的值
- lrem key count value 删除值得value的x个项（count>0 从头删，count<0 从尾删，count=0 删除全部）
- blpop/brpop key1 key2 timeout ： 阻塞读
- llen 获取列表长度
    
### 集合（set）
- sadd/srem 添加、删除元素
- spop 删除并返回随机的元素
- smembers 返回集合所有的值
- srandmember key count 返回随机count个值，默认1个
- scard key 返回集合元素数
- sismember 判断是否集合成员
- sdiff key1 key2 返回集合的差异
- SDIFFSTORE dest key1 key2 将集合key1、key2的差异存放到dest
- SINTER/SINTERSTORE 返回集合的交集
- SUNION/SUNIONSTORE 返回集合的并集

### 有序集合（zset）
- zadd key score member 增加元素，可以一次增加多个
- zrange key start stop withscores 返回排名在start、stop直接的元素
- ZREVRANGE 返回倒序的start、stop个元素
- ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset n] 分数在min、max直接，跳过offset后，取n个元素
- zrem 删除元素
- zrank 查询元素排名
- zrevrank 降序排名
- zscore 查询元素的分数
- zcard 查询集合数量
- zcount key min max 分数在min、max直接元素个数
- zincrby 