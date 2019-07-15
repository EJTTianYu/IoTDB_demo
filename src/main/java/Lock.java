import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Lock {

  // 定义两个资源
  final static ReentrantReadWriteLock mergeLock = new ReentrantReadWriteLock();
  final static ReentrantReadWriteLock fileLock = new ReentrantReadWriteLock();

  //  public static void main(String[] args) {
//    new myThread().start();
//    // 尝试同时获取，返回true则执行相应逻辑，只要一个返回false则是失败释放资源
//    while (!mergeLock.writeLock().tryLock() || !fileLock.writeLock().tryLock()) {
//      System.out.println(mergeLock.writeLock().isHeldByCurrentThread());
//      System.out.println(fileLock.writeLock().isHeldByCurrentThread());
//      if (mergeLock.writeLock().isHeldByCurrentThread()){
//        mergeLock.writeLock().unlock();
//      }
//      if(fileLock.writeLock().isHeldByCurrentThread()){
//        fileLock.writeLock().unlock();
//      }
//      // todo
//    }
//    mergeLock.writeLock().unlock();
//    fileLock.writeLock().unlock();
//  }
  public static void main(String[] args) {
    fileLock.writeLock().lock();
    fileLock.writeLock().lock();
    fileLock.writeLock().lock();
    System.out.println("test");
  }

  static class myThread extends Thread {

    @Override
    public void run() {
      System.out.println("获取锁");
      mergeLock.readLock().lock();
      System.out.println("获取锁1");
      fileLock.readLock().lock();
      System.out.println("get");
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("释放锁");
      mergeLock.readLock().unlock();
      fileLock.readLock().unlock();
      System.out.println("rel");
    }

  }

}
