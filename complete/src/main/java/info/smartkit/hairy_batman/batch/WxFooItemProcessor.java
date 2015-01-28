package info.smartkit.hairy_batman.batch;

import info.smartkit.hairy_batman.domain.WxFoo;

import org.springframework.batch.item.ItemProcessor;

public class WxFooItemProcessor implements ItemProcessor<WxFoo, WxFoo>
{

    @Override
    public WxFoo process(final WxFoo wxFoo) throws Exception
    {
        final String code = wxFoo.getCode().toUpperCase();
        final String store = wxFoo.getStore().toUpperCase();

        final WxFoo transformedWxFoo = new WxFoo(code, store, "", "", "", false, "", -1, false, "", -1);

        System.out.println("Converting (" + wxFoo + ") into (" + transformedWxFoo + ")");

        return transformedWxFoo;
    }

}
