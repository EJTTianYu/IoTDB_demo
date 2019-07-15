package iotdb.jdbc.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.junit.Test;

public class IotdbDemo {

  private Connection iotdbConn;
  private static String urlTem = "jdbc:iotdb://%s:%s/";

  public void getConnection(String configPath)
      throws IOException, ClassNotFoundException, SQLException {
    InputStream config = new FileInputStream(new File(configPath));
    Properties properties = new Properties();
    properties.load(config);
    Class.forName("org.apache.iotdb.jdbc.IoTDBDriver");
    String url = String
        .format(urlTem, properties.getProperty("IP"), properties.getProperty("PORT"));
    iotdbConn = DriverManager
        .getConnection(url, (String) properties.get("USER"), (String) properties.get("PASS"));
    System.out.println(String.format("连接成功,JDBC的地址为%s", url));
  }

  /**
   * 执行传入的sql语句，如果有结果集，则返回，否则返回null
   */
  public ResultSet getResultSet(String sql) throws SQLException {
    Statement statement = iotdbConn.createStatement();
    ResultSet resultSet = null;
    boolean hasResultSet = statement.execute(sql);
    if (hasResultSet) {
      resultSet = statement.getResultSet();
    }
    return resultSet;
  }

  /**
   * 如果传入的ResultSet不为空,输出ResultSet
   */
  public static void outputRs(ResultSet resultSet, PrintStream out) throws SQLException {
    if (resultSet != null) {
      out.println("--------------------------");
      final ResultSetMetaData metaData = resultSet.getMetaData();
      final int columnCount = metaData.getColumnCount();
      for (int i = 0; i < columnCount; i++) {
        out.print(metaData.getColumnLabel(i + 1) + " ");
      }
      out.println();
      while (resultSet.next()) {
        for (int i = 1; ; i++) {
          out.print(resultSet.getString(i));
          if (i < columnCount) {
            out.print(", ");
          } else {
            out.println();
            break;
          }
        }
      }
      out.println("--------------------------");
    }
  }


  public static void main(String[] args) throws Exception {
    IotdbDemo iotdbDemo = new IotdbDemo();
    iotdbDemo.getConnection("/Users/tianyu/dwf3s/src/main/resources/con_info.properties");
    outputRs(iotdbDemo.getResultSet(IotdbSQL.createSG), System.out);
    outputRs(iotdbDemo.getResultSet(IotdbSQL.showSG), System.out);
    outputRs(iotdbDemo.getResultSet(IotdbSQL.createTs), System.out);
    outputRs(iotdbDemo.getResultSet(IotdbSQL.showTs), System.out);
    outputRs(iotdbDemo.getResultSet(String.format(IotdbSQL.insertSql, 1, 1)), System.out);
    outputRs(iotdbDemo.getResultSet(IotdbSQL.selectAllSql), System.out);
    outputRs(iotdbDemo.getResultSet(IotdbSQL.preciseSql), System.out);
    outputRs(iotdbDemo.getResultSet(IotdbSQL.intervalSql), System.out);
    outputRs(iotdbDemo.getResultSet(IotdbSQL.aggregationSql), System.out);
    outputRs(iotdbDemo.getResultSet(IotdbSQL.maxTimeSql), System.out);

    if (iotdbDemo.iotdbConn != null) {
      iotdbDemo.iotdbConn.close();
    }
  }
}
