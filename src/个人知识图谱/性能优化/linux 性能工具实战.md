### CPU性能工具
- uptime
    - 输出: 22:36:39 up  6:50,  1 user,  load average: 0.28, 0.21, 0.10
    - 过去 1 分钟、5 分钟、15 分钟的平均负载
- mpstat
    - mpstat -P ALL 5 #监控所有cpu，5秒输出一次    
    - 输出:
        10:44:53 PM  CPU    %usr   %nice    %sys %iowait    %irq   %soft  %steal  %guest  %gnice   %idle
        10:44:58 PM  all   99.20    0.00    0.60    0.00    0.00    0.20    0.00    0.00    0.00    0.00
        10:44:58 PM    0   99.20    0.00    0.60    0.00    0.00    0.20    0.00    0.00    0.00    0.00
- pidstat
    - 分进程CPU使用率
        - pidstat -u 5 1 
        - 输出:
            10:45:47 PM   UID       PID    %usr %system  %guest   %wait    %CPU   CPU  Command
            10:45:52 PM  1000     22982   87.65    0.20    0.00   11.95   87.85     0  stress
            10:45:52 PM  1000     24248    0.00    0.20    0.00    0.00    0.20     0  pidstat
    - 分进程上下文切换数据
        - pidstat -w   
        - 输出:
            1:13:05 AM   UID       PID   cswch/s nvcswch/s  Command
            01:13:05 AM     0         1      1.56      1.15  systemd
            01:13:05 AM     0         2      0.01      0.00  kthreadd
        - cswch   每秒自愿上下文切换次数
        - nvcswch 每秒非自愿上下文切换次数
        - 选项: -t 显示线程数据
    - 查看IO数据
        - -d 展示 I/O 统计数据        
    - 查看cpu使用率
        - pidstat 1 5
- vmstat 
    - vmstat 5
    - 输出
     procs -----------memory---------- ---swap-- -----io---- -system-- ------cpu-----
      r  b   swpd   free   buff  cache      si   so    bi   bo   in   cs  us sy id wa st
      0  0  13888 1176280 171164 1439620    0    0     58   819  101  265  4  1 94  0  0
      0  0  13888 1196448 171164 1439612    0    0     0     2   265  881  0  1 99  0  0
    - r   就绪队列长度
    - b   不可中断睡眠状态的进程数
    - cs  每秒上下文切换的次数
    - in  每秒中断次数
- top
- perf
    - 查找热点函数:　sudo perf top
        - 输出
        Samples: 37K of event 'cpu-clock:pppH', 4000 Hz, Event count (approx.): 5846519178 lost: 0/0 drop: 0/0
        Overhead  Shared Object                    Symbol
          55.66%  [kernel]                         [k] finish_task_switch                                                   ◆
          10.13%  libc-2.30.so                     [.] __sched_yield                                                        ▒
           8.22%  [kernel]                         [k] _raw_spin_unlock_irqrestore    
    - 采样&报告
        - sudo perf record 
        - sudo perf report
    - 参数: -g 开启调用关系的采样
    - 开启pref: 
        - 编辑/etc/sysctl.conf文件，增加 kernel.perf_event_paranoid = -1
        - 编辑 sudo sh -c 'echo -1 >/proc/sys/kernel/perf_event_paranoid'
- dstats
    - 新的性能工具，同时观察系统的 CPU、磁盘 I/O、网络以及内存使用情况    
- 查看中断次数
    - watch -d cat /proc/interrupts
    


### 压测工具
- stress
    - stress --cpu 1 --timeout 600 #模拟1个cpu使用率100%
    - stress -i 1 --timeout 600 #模拟 I/O 压力
    - stress -c 8 --timeout 600 #模拟多线程并发
- stress-ng
    - stress-ng -i 1 --hdd 1 --timeout 600 # --hdd表示读写临时文件
- sysstat 
- sysbench
    - sysbench --threads=10 --max-time=300 threads run
- ab
    - ab -c 10 -n 100 http://192.168.0.10:10000/ #并发测试
    
# next 07 | 案例篇：系统中出现大量不可中断进程和僵尸进程怎么办？（上）

### 问题
- docker中运行的程序，perf 监测时显示地址字符，而不是函数名
- execsnoop 不起作用