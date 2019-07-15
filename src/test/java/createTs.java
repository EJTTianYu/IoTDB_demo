import iotdb.jdbc.demo.IotdbDemo;
import iotdb.jdbc.demo.IotdbSQL;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class createTs {

  private static AtomicInteger cnt = new AtomicInteger();

  private static int times = 30;
  private static CountDownLatch countDownLatch = new CountDownLatch(times);

  private static Runnable task = () -> {
    IotdbDemo iotdbDemo = new IotdbDemo();
    try {
      iotdbDemo.getConnection("/Users/tianyu/dwf3s/src/main/resources/con_info.properties");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      while (true) {
        IotdbDemo
            .outputRs(
                iotdbDemo.getResultSet(String.format(IotdbSQL.createTsMul, cnt.incrementAndGet())),
                System.out);
      }
    } catch (Exception e) {
    }
    countDownLatch.countDown();
  };

  public static void main(String[] args) throws Exception {
    long startTime = System.currentTimeMillis();
    Thread[] threads = new Thread[times];
    IotdbDemo iotdbDemo = new IotdbDemo();
    try {
      iotdbDemo.getConnection("/Users/tianyu/dwf3s/src/main/resources/con_info.properties");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
//    try {
//      for (int i = 0; i < 50; i++) {
//        IotdbDemo.outputRs(iotdbDemo.getResultSet(IotdbSQL.createSG + i), System.out);
//      }
//      IotdbDemo.outputRs(iotdbDemo.getResultSet(IotdbSQL.createSG), System.out);
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Thread(task);
    }
    for (int i = 0; i < threads.length; i++) {
      threads[i].start();
    }
    countDownLatch.await();
    System.out.println(String.format("成功创建的时间序列数量:%s", cnt.get()));
    System.out.println(String
        .format("创建时间序列总共耗时:%s，当前时间为：%s", (System.currentTimeMillis() - startTime), new Date()));
//      System.exit(0);
  }
}
