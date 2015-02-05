package info.smartkit.hairy_batman.batch;

import info.smartkit.hairy_batman.domain.WxComplexSubscriber;
import info.smartkit.hairy_batman.domain.WxSimpleSubscriber;
import info.smartkit.hairy_batman.query.SogouSearchQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

public class WxFooItemProcessor implements ItemProcessor<WxSimpleSubscriber, WxSimpleSubscriber>
{
    private static Logger LOG = LogManager.getLogger(WxFooItemProcessor.class);

    @Override
    public WxSimpleSubscriber process(final WxSimpleSubscriber wxFoo) throws Exception
    {
        LOG.info("WxFooItemProcessor processing..." + wxFoo);
        // LOG.debug("SogouSearchQuery processing..." + wxFoo);
        new SogouSearchQuery(new WxComplexSubscriber(wxFoo.getId(), wxFoo.getCode(), wxFoo.getStore(),
            wxFoo.getAgency(), wxFoo.getUnit(), wxFoo.getOnSubscribe(), wxFoo.getSubscribeId(), null, null, null, null,
            null, null, null)).parseWxOpenId();
        ;
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
