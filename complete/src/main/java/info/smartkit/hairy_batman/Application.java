package info.smartkit.hairy_batman;

import info.smartkit.hairy_batman.config.GlobalConsts;
import info.smartkit.hairy_batman.config.GlobalVariables;
import info.smartkit.hairy_batman.domain.WxComplexSubscriber;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

//@ComponentScan
//@EnableAutoConfiguration
@SpringBootApplication
public class Application
{
    private static Logger LOG = LogManager.getLogger(Application.class);

    public static void main(String[] args)
    {
        // GlobalVariables.appContext = SpringApplication.run(ScheduledTasks.class, args);
        //
        GlobalVariables.appContext = SpringApplication.run(Application.class, args);
        // Check the openId storage results:
        LOG.info("GlobalVariables.wxFooListWithOpenId(size):" + GlobalVariables.wxFooListWithOpenId.size());
        // Spring-batch reading CSV testing.
        List<WxComplexSubscriber> batch_results =
            GlobalVariables.appContext.getBean(JdbcTemplate.class).query(
                "SELECT " + GlobalConsts.QUERY_COLUMNS_NAME + " FROM " + GlobalConsts.QUERY_TABLE_NAME_BASIC
                    + " WHERE openId!='null'", new RowMapper<WxComplexSubscriber>()
                {
                    @Override
                    public WxComplexSubscriber mapRow(ResultSet rs, int row) throws SQLException
                    {
                        WxComplexSubscriber subscriber =
                            new WxComplexSubscriber(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs
                                .getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs
                                .getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14));
                        return subscriber;
                    }
                });

        // for (WxComplexSubscriber wxFoo : batch_results) {
        // // System.out.println("Found <" + wxFoo + "> in the database.");
        // LOG.info("Found <" + wxFoo + "> in the database.");
        // }
        // Final file reporting
        // new FileReporter(GlobalConsts.REPORT_FILE_OUTPUT_FULL, batch_results,
        // FileReporter.REPORTER_TYPE.R_T_FULL, FileReporter.REPORTER_FILE_TYPE.EXCEL).write();
    }
}
