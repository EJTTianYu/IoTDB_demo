package iotdb.data.generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataGeneration {

  private static final List<String> createSG = new ArrayList<>(
      Arrays.asList("SET STORAGE GROUP TO root.group_11", "SET STORAGE GROUP TO root.group_12"));

  private static final List<String> createTs = new ArrayList<>(Arrays
      .asList("CREATE TIMESERIES root.group_11.%s WITH DATATYPE=%s,ENCODING=%s",
          "CREATE TIMESERIES root.group_12.%s WITH DATATYPE=%s,ENCODING=%s,COMPRESSOR=SNAPPY"));

  private static final List<String> insertSql = new ArrayList<>(Arrays
      .asList("insert into root.group_11.%s(timestamp,%s) values(%s,%s)",
          "insert into root.group_12.%s(timestamp,%s) values(%s,%s)"));

  private static List<String> DATATYPE = new ArrayList<>(
      Arrays.asList("BOOLEAN", "INT32", "INT64", "FLOAT", "DOUBLE", "TEXT"));

  private static List<String> BOOLEAN_ENCODING = new ArrayList<>(Arrays.asList("PLAIN", "RLE"));

  private static List<String> INT_ENCODING = new ArrayList<>(
      Arrays.asList("PLAIN", "RLE", "TS_2DIFF"));

  private static List<String> FLOAT_ENCODING = new ArrayList<>(
      Arrays.asList("PLAIN", "RLE", "TS_2DIFF", "GORILLA"));

  private static List<String> TEXT_ENCODING = new ArrayList<>(Arrays.asList("PLAIN"));


  public static void main(String[] args) throws Exception {
    iotdb.jdbc.demo.IotdbDemo iotdbDemo = new iotdb.jdbc.demo.IotdbDemo();
    iotdbDemo.getConnection("/Users/tianyu/dwf3s/src/main/resources/con_info.properties");

    for (String createSGString : createSG) {
      iotdbDemo.getResultSet(createSGString);
    }
    int deviceCount = 0;
    for (String dtype : DATATYPE) {
      switch (dtype) {
        case "BOOLEAN":
          for (String boolean_enc : BOOLEAN_ENCODING) {
            for (int i = 0; i < createTs.size(); i++) {
              iotdbDemo.getResultSet(
                  String.format(createTs.get(i),
                      "d" + deviceCount + ".s_" + dtype + "e_" + boolean_enc,
                      dtype, boolean_enc));
              for (int j = 0; j < 10000; j++) {
                String value = "true";
                iotdbDemo.getResultSet(String.format(insertSql.get(i), "d" + deviceCount,
                    "s_" + dtype + "e_" + boolean_enc, j + 1, value));
              }
            }
          }
          deviceCount++;
          break;
        case "INT32":
        case "INT64":
          for (String int_enc : INT_ENCODING) {
            for (int i = 0; i < createTs.size(); i++) {
              iotdbDemo.getResultSet(String.format(createTs.get(i),
                  "d" + deviceCount + ".s_" + dtype + "e_" + int_enc,
                  dtype, int_enc));
              for (int j = 0; j < 10000; j++) {
                int value = 1;
                iotdbDemo.getResultSet(String.format(insertSql.get(i), "d" + deviceCount,
                    "s_" + dtype + "e_" + int_enc, j + 1, value));
              }
            }
          }
          deviceCount++;
          break;
        case "FLOAT":
        case "DOUBLE":
          for (String double_enc : FLOAT_ENCODING) {
            for (int i = 0; i < createTs.size(); i++) {
              String create_ts = String.format(createTs.get(i),
                  "d" + deviceCount + ".s_" + dtype + "e_" + double_enc,
                  dtype, double_enc);
              if (double_enc.equals("RLE") || double_enc.equals("TS_2DIFF")) {
                create_ts = create_ts.concat(",MAX_POINT_NUMBER=3");
              }
              iotdbDemo.getResultSet(create_ts);
              for (int j = 0; j < 10000; j++) {
                double value = 1.1;
                iotdbDemo.getResultSet(String.format(insertSql.get(i), "d" + deviceCount,
                    "s_" + dtype + "e_" + double_enc, j + 1, value));
              }
            }
          }
          deviceCount++;
          break;
        case "TEXT":
          for (String text_enc : TEXT_ENCODING) {
            for (int i = 0; i < createTs.size(); i++) {
              iotdbDemo.getResultSet(String.format(createTs.get(i),
                  "d" + deviceCount + ".s_" + dtype + "e_" + text_enc,
                  dtype, text_enc));
              for (int j = 0; j < 10000; j++) {
                String value = "version_test";
                iotdbDemo.getResultSet(String.format(insertSql.get(i), "d" + deviceCount,
                    "s_" + dtype + "e_" + text_enc, j + 1, "\"" + value + "\""));
              }
            }
          }
          deviceCount++;
          break;
      }
    }
    if (iotdbDemo.getIotdbConn() != null) {
      iotdbDemo.getResultSet("flush");
      iotdbDemo.getIotdbConn().close();
    }
  }
}