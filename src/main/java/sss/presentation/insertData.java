package sss.presentation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.apache.calcite.adapter.jdbc.JdbcSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.commons.dbcp2.BasicDataSource;

public class insertData {

  /**
   * 用于插入数据进行展示
   */
  public static void main(String[] args) throws ClassNotFoundException, SQLException {

    Class.forName("org.apache.calcite.jdbc.Driver");
    Properties info = new Properties();
    info.setProperty("lex", "JAVA");
    Connection connection = DriverManager.getConnection("jdbc:calcite:", info);
    CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
    SchemaPlus rootSchema = calciteConnection.getRootSchema();

    /**
     * 此处为不同数据源的实际操作部分，BasicDataSource为Apache DBCP的连接池
     */
    Class.forName("org.postgresql.Driver");
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setUrl("jdbc:postgresql://192.168.10.18:5432/dataway");
    dataSource.setUsername("postgres");
    dataSource.setPassword("S4eXv&d$");
    dataSource.addConnectionProperty("remarks", "true");
    dataSource.addConnectionProperty("useInformationSchema", "true");
    /**
     * 此处创建Schema的时候，param1指定父模式，param5的schema为数据库中的模式，需要匹配，param2暴露出来的name用作下面的rootSchema的add方法
     */
    Schema schema = JdbcSchema
        .create(rootSchema, "plt_omf_procedure", dataSource, null, "plt_omf_procedure");

    rootSchema.add("plt_omf_procedure", schema);

    Statement statement = calciteConnection.createStatement();

    for (int i = 1; i < 1000; i++) {
      statement.addBatch(
          "insert into plt_omf_procedure.sssTest values ('" + i + "','name" + i + "','description" + i
              + "')");
    }
    statement.executeBatch();

    statement.close();
    connection.close();
  }
}
