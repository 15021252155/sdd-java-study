package cn.com.sdd.study.other;

/**
 * @author suidd
 * @name TestDemo1
 * @description 递归算法测试Demo1
 * @date 2020/5/9 11:01
 * Version 1.0
 **/
public class TestDemo1 {
    public static int i = 1; //汉诺塔移动记录步骤数

    public static void main(String[] args) {
        //求n!的递归算法
        System.out.println(factorial(5));
        //求2个数的最大公约数
        System.out.println(gcd(9, 15));
        Hanio(4, "C", "B", "A");
        //build
        build(10, 5, 15);
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //求n!的递归算法
     * @date 2020/5/9 11:03
     **/
    static int factorial(int n) {
        if (n == 1)
            return (1);
        else
            return (factorial(n - 1) * n);
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //求2个数的最大公约数
     * @date 2020/5/9 11:03
     **/
    static int gcd(int a, int b) {
        return a % b == 0 ? b : gcd(b, a % b);
    }

    /**
     * @param
     * @return change notes
     * @author suidd
     * @description 打印移动步骤
     * i表示进行到的步数,将编号为n的盘子由from柱移动到to柱(目标柱)
     * @date 2020/5/9 11:35
     **/
    static void move(int n, String from, String to) {
        System.out.println(String.format("第%d步:将%d号盘子%s---->%s", i++, n, from, to));
    }


    /**
     * @param n         表示要将多少个"圆盘"从起始柱子移动至目标柱子
     * @param start_pos 表示起始柱子
     * @param tran_pos  表示过渡柱子
     * @param end_pos   表示目标柱子
     * @return change notes
     * @author suidd
     * @description //汉诺塔递归函数
     * <p>
     * 我们首先需要一点思维：解决n块盘子从C移动到A，那么我只需要先把n-1块盘子从C移到B，然后把最下面的第n块盘子从C移到A，最后把n-1块盘子从B移到A（这就完成了）。
     * <p>
     * 等等，那么如何把n-1块盘子从C移到B？
     * <p>
     * 很好，这说明你已经开始递归入门了。
     * <p>
     * 同样这样去想：解决n-1块盘子从C移动到B，那么我只需要先把n-2块盘子从C移动到A，然后把倒数第二块盘子从C移到B，最后把n-2块盘子从A移到B（这就完成了）。
     * <p>
     * 这就是递归的“递”！
     * <p>
     * 那么“归”呢？n==1的时候？
     * @date 2020/5/9 11:35
     **/
    static void Hanio(int n, String start_pos, String tran_pos, String end_pos) {
        if (n == 1) {
            //很明显,当n==1的时候,我们只需要直接将圆盘从起始柱子移至目标柱子即可.
            move(n, start_pos, end_pos);
        } else {
            //递归处理,一开始的时候,先将n-1个盘子从起始柱子移至过渡柱上
            Hanio(n - 1, start_pos, end_pos, tran_pos);
            //然后把第n块盘子（即最下面的盘子）从起始柱子移到目标柱子
            move(n, start_pos, end_pos);
            //最后把n-1块盘子从过渡柱子移到目标柱子
            Hanio(n - 1, tran_pos, start_pos, end_pos);
        }
    }


    /**
     * @param
     * @return change notes
     * @author suidd
     * @description //用二分思想建立二叉树
     * @date 2020/5/9 13:56
     **/
    static void build(int root, int left, int right) {
        if (left == right)
            return;
        int mid = (left + right) / 2;
        build(root * 2, left, mid);
        build(root * 2 + 1, mid + 1, right);
    }
}