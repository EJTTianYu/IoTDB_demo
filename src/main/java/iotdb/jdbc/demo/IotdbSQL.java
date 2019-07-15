package iotdb.jdbc.demo;

public class IotdbSQL {

  //创建storage group的语句
  public static final String createSG = "SET STORAGE GROUP TO root.demo";

  //查看storage group的语句
  public static final String showSG = "SHOW STORAGE GROUP";

  //创建时间序列的语句，不同的数据类型有不同的编码方式，这里仅以INT32为例
  public static final String createTs = "CREATE TIMESERIES root.demo.s0 WITH DATATYPE=INT32,ENCODING=RLE;";

  //创建时间序列的语句，不同的数据类型有不同的编码方式，这里仅以INT32为例
  public static final String createTsMul = "CREATE TIMESERIES root.demo0.s%s WITH DATATYPE=INT32,ENCODING=RLE;";

  //查看时间序列的语句
  public static final String showTs = "SHOW TIMESERIES root.demo";

  //插入语句,注意插入的数据类型要和时间序列的数据类型相匹配
  public static final String insertSql = "insert into root.demo(timestamp,s0) values(%s,%s);";

  //全量查询语句
  public static final String selectAllSql = "select * from root.demo";

  //精确点查询语句
  public static final String preciseSql = "select s0 from root.demo where time=1";

  //时间范围查询
  public static final String intervalSql = "select s0 from root.demo where time<2";

  //聚合查询
  public static final String aggregationSql = "select count(s0) from root.demo";

  //最近时间点查询
  public static final String maxTimeSql = "select max_time(s0) from root.demo";
}
