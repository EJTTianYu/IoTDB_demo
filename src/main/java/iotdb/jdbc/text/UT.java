package iotdb.jdbc.text;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class UT {

  private static AtomicInteger threadCnt = new AtomicInteger(0);


  public static void main(String[] args) {
    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
        .setNameFormat("tcp-pool-%d").build();
    ExecutorService kairosdbSenderThreadPool = new ThreadPoolExecutor(128, 1024,
        8L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(4096), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    ExecutorService ikrSenderThreadPool = new ThreadPoolExecutor(128, 1024,
        8L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(4096), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    while (true){
      FutureTask<Boolean> kairosSendTask = new FutureTask<Boolean>((Callable<Boolean>) () -> {
        try {
          return true;
        } catch (Exception e) {
          e.printStackTrace();
          return false;
        }
      });
      FutureTask<Boolean> ikrSendTask = new FutureTask<Boolean>((Callable<Boolean>) () -> {
        try {
          return true;
        } catch (Exception e) {
          e.printStackTrace();
          return false;
        }
      });
      try {
        kairosdbSenderThreadPool.submit(new Thread(kairosSendTask));
      } catch (Exception e) {
        e.printStackTrace();
      }
      try {
        ikrSenderThreadPool.submit(new Thread(ikrSendTask));
      } catch (Exception e) {
        e.printStackTrace();
      }
      try {
        if (kairosSendTask.get() && ikrSendTask.get()) {
          threadCnt.getAndIncrement();
          if (threadCnt.get() % 50 == 0) {
            System.out.println("数据写入成功,写入tag为:{}");
            threadCnt.set(0);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
