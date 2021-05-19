package iotdb.data.generation;

import java.sql.ResultSet;

public class DataQuery {

  public static void main(String[] args) throws Exception {

    iotdb.jdbc.demo.IotdbDemo iotdbDemo = new iotdb.jdbc.demo.IotdbDemo();
    iotdbDemo.getConnection("/home/fit/tianYu/IoTDB_demo/src/main/resources/con_info.properties");

    for (int i = 0; i < iotdbDemo.getSelectCnt(); i++) {
      long startTime = System.currentTimeMillis();
      System.out.println(iotdbDemo.getQuerySQL());

      ResultSet queryResult = iotdbDemo.getResultSet(iotdbDemo.getQuerySQL());
      int rowCount = 0;
      while (queryResult.next()) {
        rowCount++;
      }
      long endTime = System.currentTimeMillis();
      System.out.println(String.format("查询结束,总耗时为%s", endTime - startTime));
      System.out.println(rowCount);
    }
  }
}
