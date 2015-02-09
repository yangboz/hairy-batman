package info.smartkit.hairy_batman;

import info.smartkit.hairy_batman.config.GlobalConsts;
import info.smartkit.hairy_batman.config.GlobalVariables;
import info.smartkit.hairy_batman.model.WxSubscriberExcelModel;
import info.smartkit.hairy_batman.schedule.ScheduledTasks;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.blogspot.na5cent.exom.ExOM;

//@ComponentScan
//@EnableAutoConfiguration
@SpringBootApplication
public class ScheduledApplication
{
    private static Logger LOG = LogManager.getLogger(Application.class);

    public static void main(String[] args) throws Throwable
    {
        GlobalVariables.appContext = SpringApplication.run(ScheduledTasks.class, args);
        //
        File excelFile = new ClassPathResource(GlobalConsts.RESOURCE_FILE_INPUT_XLS).getFile();
        final List<WxSubscriberExcelModel> rawItems =
            ExOM.mapFromExcel(excelFile).toObjectOf(WxSubscriberExcelModel.class).map();

        for (WxSubscriberExcelModel item : rawItems) {
            LOG.info("WxSubscriberModel:" + item.toString());
        }
        // JdbcTemplate Batch save.
        String sql = "INSERT INTO CUSTOMER " + "(CUST_ID, NAME, AGE) VALUES (?, ?, ?)";

        GlobalVariables.jdbcTempate.batchUpdate(sql, new BatchPreparedStatementSetter()
        {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException
            {
                WxSubscriberExcelModel subscriber = rawItems.get(i);
                ps.setLong(1, subscriber.getId());
                ps.setString(2, subscriber.getCode());
                ps.setString(3, subscriber.getStore());
                //
            }

            @Override
            public int getBatchSize()
            {
                return rawItems.size();
            }

        });
    }
}
