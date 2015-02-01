package info.smartkit.hairy_batman.batch;

import info.smartkit.hairy_batman.domain.WxFoo;
import info.smartkit.hairy_batman.query.SogouSearchQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

public class WxFooItemProcessor implements ItemProcessor<WxFoo, WxFoo>
{
    private static Logger LOG = LogManager.getLogger(WxFooItemProcessor.class);

    @Override
    public WxFoo process(final WxFoo wxFoo) throws Exception
    {
        //
        new SogouSearchQuery(wxFoo).parse();
        // System.out.println("ApiQuery converting..." + wxFoo);
        LOG.debug("SogouSearchQuery processing..." + wxFoo);
        // new KJsonApiQuery(wxFoo).query();
        // // System.out.println("ApiQuery converting..." + wxFoo);
        // LOG.debug("KJsonApiQuery processing..." + wxFoo);
        return wxFoo;
    }
}
