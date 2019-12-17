#### 类加载器
- 类加载器层级（java 9 以前）
    - 启动类加载器 （无java对应对象）
    - 扩展累加载器
    - 应用类加载器
- 类加载器层级（java 9 以后）    
    - 启动类加载器 （无java对应对象）
    - 平台类加载器
- 类的唯一性： 类加载器实例+类全名
- 其他类加载器： ClassLoader子类
- 双亲委派模型

#### 链接
- 验证：验证字节码是否合规
- 准备：为静态字段分配内存
- 解析（非必需）：将符号引用解析成实际引用 

#### 初始化
- 标记为常量（final修饰）的类字段复制
- 执行 clinit 方法（加锁保证执行一次）
- 执行初始化的时机： 第一次使用类前
    - 当虚拟机启动时，初始化用户指定的主类 
    - new指令新建目标类实例时
    - 调用静态方法/静态字段时
    - 子类初始化触发父类初始化
    - 定义default方法的接口，在实现类初始化前
    - 反射API对某个类进行反射时
    - 初次调用MethodHandler实例时

#### 反射方法调用
- MethodAccessor
    - 委派实现 DelegatingMethodAccessorImpl 
    - 本地实现 NativeMethodAccessorImpl
    - 动态生成字节码的实现： 直接invoke指令调用目标方法 
        - 第一次生成需要较长时间
        - 运行速度快
        - 适合多次运行时使用
- 反射执行方法开销
    - invoke 是变长参数，需要将参数存到数组
    - 基本数据类型需要装箱、拆箱操作
- invokedynamic（非java语言）
    - duck typing： 只要方法签名一致，就能够调用；不考虑调用者类型
    - 方法句柄（MethodHandler)
        - 唯一性由参数及返回值类型决定
        - 不考虑方法名
        - MethodHandles.Lookup 类创建
        - 权限在创建时间检查
            - 可以访问 Lookup 创建位置所在的类的私有方法
        - invokeExact 要求参数完全匹配，不执行类型转换，如： String -> Object 
        - invoke      参数通过转换后匹配
        - 方法柯里化： g(y) --> f(4,y)
    - invokedynamic调用过程
        - 第一次执行invokedynamic，会使用启动方法创建调用点（CallSite），并绑定
        - 之后的访问直接调用绑定调用点链接的方法句柄
