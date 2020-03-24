- 查看java 进程pid
    - jps -lv 
- JConsole 工具
    - 用途
        - 监控JVM中内存、线程、类、CPU占用率
        - 查看年轻代、老年代、metaspace等占用空间
        - 查看GC时间
        - 可以监控远程jvm
    - 启动方式： 命令行执行jconsole
    - 可支持插件，如： JTop
    - 载入插件方法： jconsole -pluginpath path/plugin.jar

- VisualVm
    - 用途：
        - 监控JVM中内存、线程、类、CPU占用率
        - 实时显示线程的状态
        - CPU采样： 线程执行时间
        - 内存采用：对象内存数据占用
        - profile： 方法占用时间 
    -  加载dump文件需要区分core dump 、heap dump、thread dump
        - core dump： 关于cpu的
        - heap dump： 关于内存的
    
- Jmap    
    - 查看内存使用情况：jmap -heap pid
    - 查看jvm堆内对象： jmap -histo [pid]
    - dump java 内存： jmap -dump:format=b,file=文件名 [pid]
        - :format=b 可以不使用
    - dump 结果是heap dump
    
- jstack 工具
    - 查看Java堆栈信息：jstack pid
- jstats 命令
    - jstat -options 查看支持的选项
    - 命令语法： jstat  -option vmid interval count
        - option: 
            - gcutil/gc 查看gc情况
            - class 类加载器
            - compiler JIT
            - printcompilation 编译统计
        - vmid  : 虚拟机进程号
        - interval : 间隔时间
        - count    : 打印次数
    - 输出结果： 文本
        - S0/S1    : 第一/二个存活区   
        - E/O/M    : 年轻代/老年代/元数据去
        - CCS      : Compressed Class Space 空闲率
        - YGC/YGCT : yong GC 次数/用时  
        - FGC/FGCT : full GC 次数/用时    
        - GCT      : 整个gc所用的时间
        - 后缀C/U  : C 容量大小， U 已使用大小， 无： 使用率
    - 参考资料： 
        - http://www.cnblogs.com/kongzhongqijing/articles/3625574.html
        - https://yq.aliyun.com/articles/62538
        
- 检查GC日志格式 
    - user时间：用户态下代码CPU使用时间
    - sys时间 ：系统态下代码CPU使用时间
    - real时间：GC开始到结束之间的时间
    - ![image](images/gc_info.jpg)    
    - 参考地址： https://blog.csdn.net/iverson2010112228/article/details/82885976
    
- java 启动命令
    - -XX:+HeapDumpOnOutOfMemoryError 内存用尽异常时，dump heap
    - -XX:HeapDumpPath=/disk2/dumps 指定dump文件路径
    - -XX:+PrintGCDetails     输出gc日志
    - -XX:+PrintGCDateStamps  输出gc执行的时间戳
    - -Xloggc:file 输出日志到文件


