package wang.study.concurrent.pattern.singleton;

/**
 * 类加载时初始化实例
 * 实现简单
 */
public class SingleImp {
    private static final SingleImp single = new SingleImp();
    public SingleImp(){
        System.out.println("init single");
    }

    public static SingleImp getInstance(){
        return single;
    }
}
