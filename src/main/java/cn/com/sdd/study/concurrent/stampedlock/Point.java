package cn.com.sdd.study.concurrent.stampedlock;

import java.util.concurrent.locks.StampedLock;

/**
 * @author suidd
 * @name Point
 * @description StampedLock三种模式的使用方法
 * @date 2020/5/25 10:34
 * Version 1.0
 **/
public class Point {
    private double x, y;
    private final StampedLock sl = new StampedLock();

    void move(double deltaX, double deltaY) {
        // 获取写锁，返回一个版本号（戳）
        long stamp = sl.writeLock();
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            // 释放写锁，需要传入上面获取的版本号
            sl.unlockWrite(stamp);
        }
    }

    double distanceFromOrigin() {
        // 乐观读
        long stamp = sl.tryOptimisticRead();
        double currentX = x, currentY = y;
        // 验证版本号是否有变化
        if (!sl.validate(stamp)) {
            // 版本号变了，乐观读转悲观读
            stamp = sl.readLock();
            try {
                // 重新读取x、y的值
                currentX = x;
                currentY = y;
            } finally {
                // 释放写锁，需要传入上面获取的版本号
                sl.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    void moveIfAtOrigin(double newX, double newY) {
        // 获取悲观读锁
        long stamp = sl.readLock();
        try {
            while (newX == 0.0 && newY == 0.0) {
                // 转为写锁
                long wStamp = sl.tryConvertToWriteLock(stamp);
                // 转换成功
                if (wStamp != 0L) {
                    stamp = wStamp;
                    x = newX;
                    y = newY;
                    break;
                } else {
                    // 转换失败
                    sl.unlockRead(stamp);
                    // 获取写锁
                    stamp = sl.writeLock();
                }
            }
        } finally {
            // 释放锁
            sl.unlock(stamp);
        }
    }
}