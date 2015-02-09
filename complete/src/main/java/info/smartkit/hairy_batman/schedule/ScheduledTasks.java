/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * All rights reserved.
 */
package info.smartkit.hairy_batman.schedule;

import info.smartkit.hairy_batman.config.GlobalConsts;
import info.smartkit.hairy_batman.model.WxSubscriberExcelModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.blogspot.na5cent.exom.ExOM;

/**
 * @see http://spring.io/guides/gs/scheduling-tasks/
 * @author yangboz
 */
@EnableScheduling
public class ScheduledTasks
{
    private static Logger LOG = LogManager.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 60000)
    public void reportCurrentTime() throws Throwable
    {
        LOG.info("The time is now " + dateFormat.format(new Date()));
        File excelFile = new ClassPathResource(GlobalConsts.RESOURCE_FILE_INPUT_XLS).getFile();
        List<WxSubscriberExcelModel> items =
            ExOM.mapFromExcel(excelFile).toObjectOf(WxSubscriberExcelModel.class).map();

        for (WxSubscriberExcelModel item : items) {
            LOG.info("WxSubscriberModel:" + item.toString());
        }
        /*
         * // Check the openId storage results: LOG.info("GlobalVariables.wxFooListWithOpenId(size):" +
         * GlobalVariables.wxFooListWithOpenId.size()); // Spring-batch reading CSV testing. List<WxComplexSubscriber>
         * batch_results = GlobalVariables.appContext.getBean(JdbcTemplate.class).query( "SELECT " +
         * GlobalConsts.QUERY_COLUMNS_NAME + " FROM " + GlobalConsts.QUERY_TABLE_NAME, new
         * RowMapper<WxComplexSubscriber>() {
         * @Override public WxComplexSubscriber mapRow(ResultSet rs, int row) throws SQLException { WxComplexSubscriber
         * subscriber = new WxComplexSubscriber(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs
         * .getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs .getString(10),
         * rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14)); return subscriber; } }); for
         * (WxComplexSubscriber wxFoo : batch_results) { // System.out.println("Found <" + wxFoo +
         * "> in the database."); LOG.info("Found <" + wxFoo + "> in the database."); } // Final file reporting // new
         * FileReporter(GlobalConsts.REPORT_FILE_OUTPUT_FULL, batch_results, // FileReporter.REPORTER_TYPE.R_T_FULL,
         * FileReporter.REPORTER_FILE_TYPE.EXCEL).write();
         */
    }
}
