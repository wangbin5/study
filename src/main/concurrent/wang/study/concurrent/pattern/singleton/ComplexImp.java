package wang.study.concurrent.pattern.singleton;

/**
 * 利用jvm类初始化机制实现按需创建
 */
public class ComplexImp {
    public ComplexImp(){
        System.out.println("init single");
    }
    private static class Holder{
        private static ComplexImp single = new ComplexImp();
    }
    public static ComplexImp getInstance(){
        return Holder.single;
    }
}
