package course.p14.p1;

/**
 * @author 微信公众号：微观技术
 */
public class SingleonLock {

    private static SingleonLock doubleLock;

    private SingleonLock() {
    }

    // 双重校验锁
    public static SingleonLock getInstance() {
        if (doubleLock == null) {
            synchronized (SingleonLock.class) {
                if (doubleLock == null) {
                    doubleLock = new SingleonLock();
                }
            }
        }
        return doubleLock;
    }
}




