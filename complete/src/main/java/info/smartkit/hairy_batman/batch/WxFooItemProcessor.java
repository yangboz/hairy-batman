package info.smartkit.hairy_batman.batch;

import info.smartkit.hairy_batman.domain.WxSubscriber;
import info.smartkit.hairy_batman.query.SogouSearchQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

public class WxFooItemProcessor implements ItemProcessor<WxSubscriber, WxSubscriber>
{
    private static Logger LOG = LogManager.getLogger(WxFooItemProcessor.class);

    @Override
    public WxSubscriber process(final WxSubscriber wxFoo) throws Exception
    {
        //
        new SogouSearchQuery(wxFoo).parseWxOpenId();
        // System.out.println("ApiQuery converting..." + wxFoo);
        LOG.debug("SogouSearchQuery processing..." + wxFoo);
        // new KJsonApiQuery(wxFoo).query();
        // // System.out.println("ApiQuery converting..." + wxFoo);
        // LOG.debug("KJsonApiQuery processing..." + wxFoo);
        return wxFoo;
    }
}
