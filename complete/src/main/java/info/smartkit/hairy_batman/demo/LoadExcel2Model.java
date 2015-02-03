/*
 * code https://github.com/jittagornp/excel-object-mapping
 */
package info.smartkit.hairy_batman.demo;

import info.smartkit.hairy_batman.config.GlobalConsts;
import info.smartkit.hairy_batman.model.WxSubscriberExcelModel;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import com.blogspot.na5cent.exom.ExOM;

/**
 * @author redcrow
 */
public class LoadExcel2Model
{

    private static Logger LOG = LogManager.getLogger(LoadExcel2Model.class);

    public static void main(String[] args) throws Throwable
    {
        // File excelFile = new
        // File("/Users/yangboz/Documents/Git/hairy-batman/complete/src/main/resources/excel.xlsx");
        // List<ExcelModel> items = ExOM.mapFromExcel(excelFile).toObjectOf(ExcelModel.class).map();
        //
        // for (ExcelModel item : items) {
        // LOG.info("first name --> {}", item.getFistName());
        // LOG.info("last name --> {}", item.getLastName());
        // LOG.info("age --> {}", item.getAge());
        // LOG.info("birth date --> {}", item.getBirthdate());
        // LOG.info("");
        // }
        File excelFile = new ClassPathResource(GlobalConsts.CSV_RESOURCE_FILE_INPUT_XLS).getFile();
        List<WxSubscriberExcelModel> items = ExOM.mapFromExcel(excelFile).toObjectOf(WxSubscriberExcelModel.class).map();

        for (WxSubscriberExcelModel item : items) {
            LOG.info("WxSubscriberModel:" + item.toString());
        }

    }
}
