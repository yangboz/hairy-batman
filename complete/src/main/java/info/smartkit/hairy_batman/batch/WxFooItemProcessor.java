package info.smartkit.hairy_batman.batch;

import info.smartkit.hairy_batman.domain.WxSubscriber;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

public class WxFooItemProcessor implements ItemProcessor<WxSubscriber, WxSubscriber>
{
    private static Logger LOG = LogManager.getLogger(WxFooItemProcessor.class);

    @Override
    public WxSubscriber process(final WxSubscriber wxFoo) throws Exception
    {
        LOG.info("WxFooItemProcessor processing..." + wxFoo);
        // new SogouSearchQuery(wxFoo).parseWxOpenId();
        // LOG.debug("SogouSearchQuery processing..." + wxFoo);
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
