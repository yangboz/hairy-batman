package info.smartkit.hairy_batman.batch;

import info.smartkit.hairy_batman.domain.WxFoo;

import org.springframework.batch.item.ItemProcessor;

public class WxFooItemProcessor implements ItemProcessor<WxFoo, WxFoo>
{

    @Override
    public WxFoo process(final WxFoo wxFoo) throws Exception
    {
        final String code = wxFoo.getCode();
        // final String store = wxFoo.getStore().toUpperCase();
        final String store = wxFoo.getStore();
        final String manager = wxFoo.getManager();
        final String agency = wxFoo.getAgency();
        final String unit = wxFoo.getUnit();
        final String onSubscribe = wxFoo.getOnSubscribe();
        final String subscribe = wxFoo.getSubscribe();
        final String followSubscribe = wxFoo.getFollowSubscribe();
        final String onService = wxFoo.getOnService();
        final String service = wxFoo.getService();
        final String followService = wxFoo.getFollowService();

        final WxFoo transformedWxFoo =
            new WxFoo(code, store, manager, agency, unit, onSubscribe, subscribe, followSubscribe, onService, service,
                followService);

        System.out.println("Converting (" + wxFoo + ") into (" + transformedWxFoo + ")");
        // TODO:API call here.
        return transformedWxFoo;
    }

}
