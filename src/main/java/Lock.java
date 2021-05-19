import iotdb.jdbc.demo.IotdbDemo;
import iotdb.jdbc.demo.IotdbSQL;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.PriorityQueue;

//13490
public class Lock {

  private static Connection iotdbConn;
  private static String urlTem = "jdbc:iotdb://%s:%s/";

  public static void getConnection()
      throws IOException, ClassNotFoundException, SQLException {
    Class.forName("org.apache.iotdb.jdbc.IoTDBDriver");
    String url = String
        .format(urlTem, "192.168.130.9", "6667");
    iotdbConn = DriverManager
        .getConnection(url, "root", "root");
    System.out.println(String.format("连接成功,JDBC的地址为%s", url));
  }

  /**
   * 执行传入的sql语句，如果有结果集，则返回，否则返回null 733502111 191974827
   */
  public static ResultSet getResultSet(String sql) throws SQLException {
    long st = System.nanoTime();
    int count = 0;
    Statement statement = iotdbConn.createStatement();
    ResultSet resultSet = null;
    boolean hasResultSet = statement.execute(sql);
    if (hasResultSet) {
      resultSet = statement.getResultSet();
      while (resultSet.next()) {
        count++;
      }
    }
    long en = System.nanoTime();
    long interval = en - st;
    System.out.println("查询时间: " + interval + ",查询行数: " + count);
    return resultSet;
  }

  public static void insertSeq(int start, int end, boolean seq) throws Exception {
    Statement statement = iotdbConn.createStatement();
    int count = 0;
    for (int i = start; i <= end; i++) {
      if (seq) {
        statement.execute(String
            .format("insert into root.demo.d0(timestamp,s0) values(%s,%s)", i, i));
        statement.execute(String
            .format("insert into root.demo.d1(timestamp,s0) values(%s,%s)", i, i));
        count++;
//        if (count % 11 == 0) {
//          Thread.sleep(5000);
//        }
      } else {
        statement.execute(String
            .format("insert into root.demo(timestamp,s0,s1,s2,s3,s4) values(%s,%s,%s,%s,%s)", i - 1,
                i - 1, i - 1, i - 1, i - 1));
      }
    }
    statement.execute("flush");
    statement.close();
  }

  public static void count(int cnt) throws SQLException {
    ResultSet resultSet = getResultSet("select count(s0) from root.demo");
    if (Integer.parseInt(resultSet.getString(1)) != cnt) {
      System.out.println("error count");
    }
  }

  public static void insertData() throws Exception {
    String dir = "/Users/tianyu/PycharmProjects/data_vis_final/dynamic_comp/res/no_comp/after/";
    Statement statement = iotdbConn.createStatement();
    // 处理 ingestion rate
    BufferedReader reader = new BufferedReader(new FileReader(dir.concat("ingestion.csv")));
    reader.readLine();
    String line = null;
    while ((line = reader.readLine()) != null) {
      String item[] = line.split(",");
      statement.execute(String
          .format("insert into root.no_comp(timestamp, ingestion_rate) values(%s,%s)",
              item[item.length - 2],
              item[item.length - 1]));
    }
    // 处理 cpu
    BufferedReader reader1 = new BufferedReader(new FileReader(dir.concat("cpu.csv")));
    reader1.readLine();
    String line1 = null;
    while ((line1 = reader1.readLine()) != null) {
      String item[] = line1.split(",");
      statement.execute(String
          .format("insert into root.no_comp(timestamp, cpu_idle, cpu_use) values(%s,%s,%s)",
              item[item.length - 3],
              item[item.length - 2], item[item.length - 1]));
    }
    // 处理 iostat
    BufferedReader reader2 = new BufferedReader(new FileReader(dir.concat("iostat.csv")));
    reader2.readLine();
    String line2 = null;
    while ((line2 = reader2.readLine()) != null) {
      String item[] = line2.split(",");
      statement.execute(String
          .format("insert into root.no_comp(timestamp, iostat) values(%s,%s)",
              item[item.length - 2],
              item[item.length - 1]));
    }
  }

  public static void main(String[] args) throws Exception {
    getConnection();
//    insertSeq(0, 799, true);
//    insertData();
    getResultSet("select s_0 from root.group_0.d_0");
  }

//  public static void main(String[] args) throws Exception {
//    getConnection();
//    int total = 0;
//    int miss = 0;
//    String file = "/Users/tianyu/PycharmProjects/data_vis_final/dynamic_comp/res/zc_sql/query/log-info-2021-05-06.0.log";
//    Statement statement = iotdbConn.createStatement();
//    BufferedReader reader = new BufferedReader(new FileReader(file));
//    String line = null;
//    while ((line = reader.readLine()) != null) {
//      String query = line.substring(line.indexOf("SELECT"));
////      System.out.println(query);
//
//      try {
//        total++;
//        statement.execute(query);
//      } catch (Exception e) {
//        //
//        System.out.println(query);
//        miss++;
//      }
//    }
//    System.out.println(miss + "," + total);
//  }
}
//550695037,654928503,619368783,559537774  596132524.25

// no-limit
//7585828797, 8061106286, 8670627848, 7748530273 均值 8016523301      wa=0
//7034796508, 6898124180, 6943365595, 6893896480 均值 6942545690.75   wa=0

//3508471766, 3977554597, 3936704918, 3427649312 均值 3712595148.25   wa=1
//2359126905, 2434701916, 2380004023, 2544495922 均值 2429582191.5    wa=2
//1495530573, 1608150694, 1510505738, 1499852056 均值 1528509765.25   wa=3

// limiter_8, 合并不够充分的情况
//5816804122, 5546513816, 5743881913, 5737917237 均值 5711279272      wa_1
//5667674491, 5624712419, 5538500053, 5604475606 均值 5608840642.25   wa_2
//5820113018, 5697035259, 5905394171, 5595030805 均值 5754393313.25   wa_3

// limiter_16, 合并不够充分的情况
//4785560974, 4818291086, 4723527943, 4768877746 均值 4774064437.25   wa_1
//5075396100, 5386635236, 5136347610, 5067278904 均值 5166414462.5    wa_2
//5261094487, 5613011111, 5323100994, 5265314271 均值 5365630215.75   wa_3

// limiter_32, 合并不够充分的情况
//3603501792, 3499940932, 3604890885, 3519707048 均值 3557010164.25   wa_1
//3671415941, 3452987878, 3822882761, 3700916033 均值 3662050653.25   wa_2
//3680003223, 3643375524, 3827545200, 3485254781 均值 3659044682      wa_3

// limiter_64, 合并不够充分的情况
//3384201709, 3287779750, 3185698079, 3400110383 均值 3314447480.25   wa_1
//2162781801, 2238695422, 2262596437, 2232967933 均值 2224260398.25   wa_2
//1741788417, 1805375003, 1739128577, 1704389934 均值 1747670482.75   wa_3

// wa_1_hitter_limiter_8
//7578613962, 7312578235, 6792035245 冷时间序列集
//4541433351, 4457942998, 4480655861 热点合并时序集

// wa_1_hitter_limiter_8
//6884474972, 6968963973, 6928111526, 6905915320 均值 6921866447.75 冷时间序列集
//3918731055, 4589253485, 4362194624, 4188779504 均值 4264739667    热点合并时序集

// wa_2_hitter_limiter_8
//7087624717, 7154818348, 7126528172, 7067056702 均值 7109006984.75 冷时间序列集
//2843319567, 2825380650, 2836002768, 2858507322 均值 2840802576.75 热点合并时序集

// wa_3_hitter_limiter_8
//6290034107, 6301246070, 7143268586, 6267897797 均值 6500611640   冷时间序列集
//2897476332, 2868196204, 2905008314, 2893011620 均值 2890923117.5 热点合并时序集


