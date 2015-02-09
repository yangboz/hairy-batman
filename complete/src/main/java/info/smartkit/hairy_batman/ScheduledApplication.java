package info.smartkit.hairy_batman;

import info.smartkit.hairy_batman.config.GlobalConsts;
import info.smartkit.hairy_batman.config.GlobalVariables;
import info.smartkit.hairy_batman.model.WxSubscriberExcelModel;
import info.smartkit.hairy_batman.schedule.ScheduledTasks;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

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
        List<WxSubscriberExcelModel> items =
            ExOM.mapFromExcel(excelFile).toObjectOf(WxSubscriberExcelModel.class).map();

        for (WxSubscriberExcelModel item : items) {
            LOG.info("WxSubscriberModel:" + item.toString());
        }
    }
}
