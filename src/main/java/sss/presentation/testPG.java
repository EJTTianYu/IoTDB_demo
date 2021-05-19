package sss.presentation;

import cn.edu.thu.dwf3s.dataset.PGDataSetHandler;
import cn.edu.thu.dwf3s.dataset.bean.DataSet;
import cn.edu.thu.dwf3s.datasource.PGDataSourceHandler;
import cn.edu.thu.dwf3s.datasource.bean.DataSource;
import cn.edu.thu.dwf3s.datasource.bean.DataSourceType;

public class testPG {

  public static void main(String[] args) throws Exception {
    String path="/Users/tianyu/git_project/application.properties";
    PGDataSetHandler pgDataSetHandler=new PGDataSetHandler(path);
//    PGDataSourceHandler pgDataSourceHandler=new PGDataSourceHandler(path);
    DataSource dataSource=new DataSource("testSQLSerDataSource", "3s SQLSer单元测试",
        "192.168.130.30", 1433,
        "gouwang", "gouwang", "iotdb.dbo", DataSourceType.SQLSERVER, null, null, null, null);
//    pgDataSourceHandler.registerDataSource(dataSource);
    DataSet dataSet=new DataSet(dataSource,"testSQLSerDataSet", "3s SQLSer单元测试",
        "select * from dwf_origin", null, "CSV", "select * from dwf_origin", null);
    pgDataSetHandler.registerDataSet(dataSet);
  }
}
