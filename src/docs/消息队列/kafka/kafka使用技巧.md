#### 消息队列监控指标
- 消息堆积
    - 会影响上下游整个调用链的时效性
    - 消息堆积过多会造成磁盘爆满
    - 监控 Lag指标
        - Lag = HW -consumerOffset
    - 事务消息监控
        - Lag = LSO -consumerOffset  
        - LSO LSO = 事务中第一条消息的位置
- 同步失效分区
    - 监控失效副本数

#### 高级应用
- 死信队列
- 延时队列
    - 应用场景
        - 用户下单后30分钟进行支付
        - 订单完成1小时后通知用户评价
        - 手机远程遥控家里的只能设备在指定时间工作
    - 方案
        - 先将消息投递到kafka内部主题
        - 通过自定义任务拉取内部主题中的消息，满足条件的消息发送到真实主题
            - 内部队列可以是多个，按照延时时长进行划分
            - 比如： 5s 10s 30s 1min 2min ....
        - 消费者订阅的还是真实主题 
        
#### kafka常用脚本
- 主题管理： kafka-topics.sh
- 生产者：   kafka-console-producer.sh
- 消费者：   kafka-console-consumer.sh
- 消费组管理 kafka-consumer-group.sh