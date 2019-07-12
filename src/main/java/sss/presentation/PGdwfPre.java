package sss.presentation;

import cn.edu.thu.dwf3s.DWF3sFactory;
import cn.edu.thu.dwf3s.dataset.bean.DataSet;
import cn.edu.thu.dwf3s.dataset.impl.DataSetReader;
import cn.edu.thu.dwf3s.dataset.impl.DataSetWriter;
import cn.edu.thu.dwf3s.datasource.bean.DataSource;
import cn.edu.thu.dwf3s.datasource.bean.DataSourceType;
import cn.edu.thu.dwf3s.utils.Utils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.rmi.CORBA.Util;

public class PGdwfPre {

  /**
   * 建立一个PG的数据源
   *
   * @return 数据源实体
   */
  public static DataSource generateDataSource() {
    DataSource dataSource = new DataSource("testPGDataSource", "3s SDK单元测试",
        "192.168.10.18", 5432,
        "postgres", "S4eXv&d$", "dataway.plt_omf_procedure", DataSourceType.POSTGRESQL, null, null,
        null,
        null);
    return dataSource;
  }

  /**
   * 建立一个数据集
   *
   * @return 数据集实例
   */
  public static DataSet generateDataSet(DataSource dataSource) {
    DataSet dataSet = new DataSet(dataSource, "testPostgreSqlDataSet", "3s SDK单元测试",
        "select ? from sssTest", null, null, "select * from sssTest", null);
    return dataSet;
  }

  /**
   * 建立一个读取器
   */
  public static DataSetReader generateDataSetReader(DataSource dataSource)
      throws ClassNotFoundException {
    return DWF3sFactory.getDataSetReader(dataSource);
  }

  /**
   * 建立一个写入器
   */
  public static DataSetWriter generateDataSetWriter(DataSource dataSource)
      throws ClassNotFoundException {
    return DWF3sFactory.getDataSetWriter(dataSource);
  }

  /**
   * 用于展示idea的读取数据集功能
   */
  public static void readDataTest()
      throws ClassNotFoundException, InterruptedException, SQLException, IOException {
    DataSource dataSource = generateDataSource();
    DataSet dataSet = generateDataSet(dataSource);
    DataSetReader dataSetReader = generateDataSetReader(dataSource);
    List<Map<String, Object>> data = dataSetReader.readDataSet(dataSet, new String[]{"*"}, 1, 10);
    Utils.show(data);
  }

  /**
   * 用于展示sss的插入数据集功能
   */
  public static void insertDataTest()
      throws ClassNotFoundException, SQLException, IOException, InterruptedException {
    DataSource dataSource = generateDataSource();
    DataSetWriter dataSetWriter = generateDataSetWriter(dataSource);
    List<Map<String, Object>> data = new ArrayList<>();
    for (int i=1;i<10;i++) {
      Map<String, Object> dataRow = new HashMap<>();
      dataRow.put("id", "idInsert"+i);
//      dataRow.put("name","nameInsert"+i);
//      dataRow.put("description","descriptionInsert"+i);
      data.add(dataRow);
    }
    dataSetWriter.insertData(data,dataSource,"sssTest");
  }

  /**
   * 用于展示sss的更新数据集功能，需要指定主键
   */
  public static void updateDataTest(){
    DataSource dataSource=generateDataSource();

  }
  public static void main(String[] args) throws Exception {
//    readDataTest();
//    insertDataTest();
    System.out.println(System.getenv("DEPLOY_HOME"));
  }

}
