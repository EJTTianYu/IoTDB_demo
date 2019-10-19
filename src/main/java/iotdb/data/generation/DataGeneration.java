package iotdb.data.generation;

import iotdb.jdbc.demo.IotdbDemo;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.junit.Test;

public class DataGeneration {

  public static final String ENCODING = "PLAIN";

  public static final String createSG = "SET STORAGE GROUP TO root.group_9";

  public static final String createTs = "CREATE TIMESERIES root.group_9.%s WITH DATATYPE=%s,ENCODING=%s;";

  public static final String insertSql = "insert into root.group_9.%s(timestamp,%s) values(%s,%s);";

  public static List<String> DATATYPE = new ArrayList<>(
      Arrays.asList(new String[]{"BOOLEAN", "INT32", "INT64", "FLOAT", "DOUBLE", "TEXT"}));

  public static void main(String[] args) throws Exception {
    iotdb.jdbc.demo.IotdbDemo iotdbDemo = new iotdb.jdbc.demo.IotdbDemo();
    iotdbDemo.getConnection("/Users/tianyu/dwf3s/src/main/resources/con_info.properties");

    iotdbDemo.getResultSet(createSG);
    int deviceCount = 0;
    for (String dtype : DATATYPE) {
      iotdbDemo
          .getResultSet(
              String.format(createTs, "d" + deviceCount + ".s_" + dtype, dtype, ENCODING));
      for (int i = 0; i < 10; i++) {
        String value = null;
        switch (dtype) {
          case "BOOLEAN":
            value = "true";
            break;
          case "INT32":
            value = "1";
            break;
          case "INT64":
            value = "2";
            break;
          case "FLOAT":
            value = "1.1";
            break;
          case "DOUBLE":
            value = "2.11";
            break;
          case "TEXT":
            value = "version_test";
            break;
        }

        if (!dtype.equals("TEXT")) {
          iotdbDemo.getResultSet(
              String.format(insertSql, "d" + deviceCount, "s_" + dtype, i + 1, value));
        } else {
          iotdbDemo.getResultSet(
              String
                  .format(insertSql, "d" + deviceCount, "s_" + dtype, i + 1, "\"" + value + "\""));
        }
      }
      deviceCount += 1;
    }
    if (iotdbDemo.getIotdbConn() != null) {
      iotdbDemo.getResultSet("flush");
      iotdbDemo.getIotdbConn().close();
    }
  }
}
