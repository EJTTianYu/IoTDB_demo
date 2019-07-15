package io.learn;

import java.io.IOException;
import java.net.MalformedURLException;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.MetricBuilder;
import org.kairosdb.client.builder.QueryBuilder;
import org.kairosdb.client.builder.TimeUnit;
import org.kairosdb.client.response.QueryResponse;

public class KairosdbTest {

  private static void insertKairos() throws MalformedURLException {
    HttpClient client = new HttpClient("http://127.0.0.1:8080");
    try {
      MetricBuilder builder = MetricBuilder.getInstance();
      builder.addMetric("ZT1")
          .addTag("machine_id", "1702")
          .addDataPoint(System.currentTimeMillis() - 10, 10)
          .addDataPoint(System.currentTimeMillis() - 5, 30.0);
//          .addDataPoint(System.currentTimeMillis(), "v0.8.0");
      client.pushMetrics(builder);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void queryKairos() throws MalformedURLException {
    HttpClient client = new HttpClient("http://192.168.130.7:8080");
    try {
      QueryBuilder builder = QueryBuilder.getInstance();
      builder.setStart(10, TimeUnit.MINUTES)
          .addMetric("ZT1")
          .addTag("machine_id", "1702");
      QueryResponse response = client.query(builder);
      System.out.println("query success");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws Exception {
    insertKairos();
//    queryKairos();
  }
}
