package iotdb.data.generation;

import iotdb.jdbc.demo.IotdbDemo;

public class DataQuery {

  private static String querySQL = "select ZT31, ZT32, ZT33, ZT34, ZT35, ZT36, ZT37, ZT38, ZT39, ZT40, ZT41, ZT42, ZT10697, ZT10698, ZT10699, ZT10700, ZT10701, ZT10702, ZT10703, ZT10704, ZT10705, ZT10706, ZT10061, ZT10062, CY1 from root.*.1701 where time > 2019-08-26T00:00:00.000 and time < 2019-08-27T00:00:00.000 group by device";

  public static void main(String[] args) throws Exception {
    long startTime = System.currentTimeMillis();
    iotdb.jdbc.demo.IotdbDemo iotdbDemo = new iotdb.jdbc.demo.IotdbDemo();
    iotdbDemo.getConnection("/Users/tianyu/dwf3s/src/main/resources/con_info.properties");

    IotdbDemo.outputRs(iotdbDemo.getResultSet(querySQL), System.out);
    long endTime = System.currentTimeMillis();
    System.out.println(String.format("查询结束,总耗时为%s", endTime - startTime));

  }
}
