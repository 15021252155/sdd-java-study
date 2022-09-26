package cn.com.sdd.study.seq;

/**
 * @ClassName LoaderLazy
 * @Author suidd
 * @Description 类的初始化&实例化顺序
 * 从大流程来说，类肯定是先初始化，再实例化的，这里得出第一个顺序：
 * 静态域 --> 实例域 --> 构造函数。另外要符合任何子类的动作都会触发父类：父类 --> 子类。所以得出原则：【先静态后实例；先父类后子类】
 *
 * 而且同一个域的顺序可以分成两步： 创建-->赋值
 * 对于静态域，其先经过链接创建静态变量，赋default值；再到初始化阶段给静态变量赋assign值和执行静态代码块。
 * 同理于实例域，也是分成创建和赋值两个部分，不同的只是加入构造函数（形参和代码块）：先创建实例变量和构造函数形参以default值，然后对变量和形参赋assign值和执行实例代码块，最后执行构造函数的代码。
 * @Date 21:05 2021/10/23
 * @Version 1.0
 **/
public class LoaderLazy {
    {
        System.out.println("Parent Instance Code");
    }
    private PrintTmp p1 = new PrintTmp("Parent Instance Member");

    static {
        System.out.println("Parent Static Code");
    }
    private static PrintTmp p2 = new PrintTmp("Parent Static Member");


    public LoaderLazy() {
        System.out.println("Parent Constuctor");
    }

    public static void main(String[] args) {
        SubLoaderLazy test = new SubLoaderLazy();
        /*
        控制台输出：
        Parent Static Code
        Parent Static Member
        Sub Static Code
        Sub Static Member
        Parent Instance Code
        Parent Instance Member
        Parent Constuctor
        Sub Instance Code
        Sub Instance Member
        Sub Constuctor
        */

        //=======================================================================
        /*
        顺序：
        父类静态代码块（按static声明的先后顺序）--》子类静态代码块（按static声明的先后顺序）--》
        父类实例代码块（按声明的先后顺序）--》父类构造函数 --》
        子类实例代码块（按声明的先后顺序）--》子类构造函数
        */
    }
}


class SubLoaderLazy extends LoaderLazy {
    {
        System.out.println("Sub Instance Code");
    }
    private PrintTmp p1 = new PrintTmp("Sub Instance Member");

    static {
        System.out.println("Sub Static Code");
    }
    private static PrintTmp p2 = new PrintTmp("Sub Static Member");


    public SubLoaderLazy() {
        System.out.println("Sub Constuctor");
    }
}


class PrintTmp {
    public PrintTmp(String strOut) {
        System.out.println(strOut);
    }
}

