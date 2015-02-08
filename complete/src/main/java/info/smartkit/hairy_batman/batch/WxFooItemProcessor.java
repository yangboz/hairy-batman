package info.smartkit.hairy_batman.batch;

import java.sql.ResultSet;
import java.sql.SQLException;

import info.smartkit.hairy_batman.config.GlobalConsts;
import info.smartkit.hairy_batman.config.GlobalVariables;
import info.smartkit.hairy_batman.domain.WxComplexSubscriber;
import info.smartkit.hairy_batman.domain.WxSimpleSubscriber;
import info.smartkit.hairy_batman.query.SogouSearchQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.RowCallbackHandler;

public class WxFooItemProcessor implements ItemProcessor<WxSimpleSubscriber, WxSimpleSubscriber>
{
    private static Logger LOG = LogManager.getLogger(WxFooItemProcessor.class);

    @Override
    public WxSimpleSubscriber process(final WxSimpleSubscriber wxFoo) throws Exception
    {
        LOG.info("WxFooItemProcessor processing..." + wxFoo);
//        TODO:读取excel， 读取db        4s店基础信息表
//        根据 code，分析OpenId
//        如果有差异，更新差异，去除不要的，新增新加的 （Thead Sleep 10s）
//        如果没有差异，继续
//        GlobalVariables.jdbcTempate.execute(sql);
        // LOG.debug("SogouSearchQuery processing..." + wxFoo);
        if ( wxFoo!=null && wxFoo.getSubscribeId()!= "NULL") {
        	String dbOpenId;
			try {
				dbOpenId = (String)  GlobalVariables.jdbcTempate.queryForObject( "SELECT openId FROM "+GlobalConsts.QUERY_TABLE_NAME+" WHERE code= ?", new Object[] {wxFoo.getCode()}, java.lang.String.class);
			} catch (Exception e) {
				 LOG.info("get dbOpenId is NULL,goto SogouSearchQuery");
				new SogouSearchQuery(new WxComplexSubscriber(wxFoo.getId(), wxFoo.getCode(), wxFoo.getStore(),
	                    wxFoo.getAgency(), wxFoo.getUnit(), wxFoo.getOnSubscribe(), wxFoo.getSubscribeId(), null, null, null,
	                    null, null, null, null)).parseWxOpenId();
			}	
        } else {
            LOG.warn("wxFoo.getSubscribeId() is NULL.");
        }
        // new KJsonApiQuery(wxFoo).query();
        // LOG.debug("KJsonApiQuery processing..." + wxFoo);
        // TODO:JobLauncher start here.
        // TaskletStep taskletStep1 = new TaskletStep("step_parseWxOpenId");
        // Tasklet tasklet_parseWxOpenId = new TaskParseWxOpenId();
        // taskletStep1.setTasklet(tasklet_parseWxOpenId);
        // //
        // SimpleJob simpleJob = new SimpleJob("simpleJob");
        // simpleJob.addStep(taskletStep1);
        // SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        // // JobLauncher jobLauncher = GlobalVariables.appContext.getBean(JobLauncher.class);
        // JobParameters jobParameters = new JobParametersBuilder().addDate("date", new Date()).toJobParameters();
        // // jobLauncher.run(simpleJob, jobParameters);
        // simpleJobLauncher.run(simpleJob, jobParameters);
        //
        return wxFoo;
    }
}