package cn.com.sdd.study.concurrent.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @author suidd
 * @name ZkLocker
 * @description zookeeper分布式锁
 * 原生API实现更易于理解zookeeper实现分布式锁的逻辑，但是难免保证没有什么问题，比如不是重入锁，不支持读写锁等
 * @date 2020/5/27 13:12
 * Version 1.0
 **/
@Component
@Slf4j
public class ZkLocker implements Locker {
    @Override
    public void lock(String key, Runnable command) {
        ZkLockerWatcher watcher = ZkLockerWatcher.conn(key);
        try {
            if (watcher.getLock()) {
                command.run();
            }
        } finally {
            watcher.releaseLock();
        }
    }

    /**
     * @param
     * @author suidd
     * @description //zk监听器内部类
     * @date 2020/5/27 13:16
     * @return change notes
     **/
    private static class ZkLockerWatcher implements Watcher {
        public static final String connAddr = "127.0.0.1:2181";
        public static final int timeout = 6000;
        public static final String LOCKER_ROOT = "/locker";
        private static final byte[] EMPTY_BYTE = "".getBytes();
        ZooKeeper zooKeeper;
        String parentLockPath;
        String childLockPath;
        Thread thread;

        public static ZkLockerWatcher conn(String key) {
            ZkLockerWatcher watcher = new ZkLockerWatcher();
            try {
                ZooKeeper zooKeeper = watcher.zooKeeper = new ZooKeeper(connAddr, timeout, watcher);
                watcher.thread = Thread.currentThread();
                // 阻塞等待连接建立完毕
                LockSupport.park();
                // 根节点如果不存在，就创建一个（并发问题，如果两个线程同时检测不存在，两个同时去创建必须有一个会失败）
                if (zooKeeper.exists(LOCKER_ROOT, false) == null) {
                    try {
                        zooKeeper.create(LOCKER_ROOT, EMPTY_BYTE, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    } catch (KeeperException e) {
                        // 如果节点已存在，则创建失败，这里捕获异常，并不阻挡程序正常运行
                        log.info("创建节点 {} 失败", LOCKER_ROOT);
                    }
                    // 当前加锁的节点是否存在
                    watcher.parentLockPath = LOCKER_ROOT.concat("/").concat(key);
                    if (zooKeeper.exists(watcher.parentLockPath, false) == null) {
                        try {
                            zooKeeper.create(watcher.parentLockPath, EMPTY_BYTE, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                        } catch (KeeperException e) {
                            // 如果节点已存在，则创建失败，这里捕获异常，并不阻挡程序正常运行
                            log.info("创建节点 {} 失败", watcher.parentLockPath);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("conn to zk error", e);
                throw new RuntimeException("conn to zk error");
            }
            return watcher;
        }


        /**
         * @param
         * @return change notes
         * @author suidd
         * @description //获取锁
         * @date 2020/5/27 13:33
         **/
        public boolean getLock() {
            try {
                this.childLockPath = zooKeeper.create(parentLockPath.concat("/"), EMPTY_BYTE, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                // 检查自己是不是最小的节点，是则获取成功，不是则监听上一个节点
                return getLockOrWatchLast();
            } catch (Exception e) {
                log.error("get lock error", e);
                throw new RuntimeException("get lock error");
            } finally {
//                System.out.println("getLock: " + childLockPath);
            }
        }

        /**
         * @param
         * @return change notes
         * @author suidd
         * @description //释放锁
         * @date 2020/5/27 13:38
         **/
        public void releaseLock() {
            try {
                if (childLockPath != null) {
                    // 释放锁，删除节点
                    zooKeeper.delete(childLockPath, -1);
                }
                // 最后一个释放的删除锁节点
                List<String> children = zooKeeper.getChildren(parentLockPath, false);
                if (children.isEmpty()) {
                    try {
                        zooKeeper.delete(parentLockPath, -1);
                    } catch (KeeperException e) {
                        // 如果删除之前又新加了一个子节点，会删除失败
                        log.info("删除节点 {} 失败", parentLockPath);
                    }
                }
                // 关闭zk连接
                if (zooKeeper != null) {
                    zooKeeper.close();
                }
            } catch (Exception e) {
                log.error("release lock error", e);
                throw new RuntimeException("release lock error");
            } finally {
//                System.out.println("releaseLock: " + childLockPath);
            }
        }

        /**
         * @param
         * @return change notes
         * @author suidd
         * @description //获取锁或监听上一个节点
         * @date 2020/5/27 13:39
         **/
        private boolean getLockOrWatchLast() throws KeeperException, InterruptedException {
            List<String> children = zooKeeper.getChildren(parentLockPath, false);
            // 必须要排序一下，这里取出来的顺序可能是乱的
            Collections.sort(children);
            // 如果当前节点是第一个子节点，则获取锁成功
            String currentPath = parentLockPath.concat("/").concat(children.get(0));
            if (currentPath.equals(childLockPath)) {
                return true;
            }

            // 如果不是第一个子节点，就监听前一个节点
            String last = "";
            for (String child : children) {
                if ((parentLockPath.concat("/").concat(child)).equals(childLockPath)) {
                    break;
                }
                last = child;
            }

            if (zooKeeper.exists(parentLockPath.concat("/").concat(last), true) != null) {
                this.thread = Thread.currentThread();
                // 阻塞当前线程
                LockSupport.park();
                // 唤醒之后重新检测自己是不是最小的节点，因为有可能上一个节点断线了
                return getLockOrWatchLast();
            } else {
                // 如果上一个节点不存在，说明还没来得及监听就释放了，重新检查一次
                return getLockOrWatchLast();
            }
        }

        @Override
        public void process(WatchedEvent watchedEvent) {
            if (this.thread != null) {
                // 唤醒阻塞的线程（这是在监听线程，跟获取锁的线程不是同一个线程）
                LockSupport.unpark(this.thread);
                this.thread = null;
            }
        }
    }
}