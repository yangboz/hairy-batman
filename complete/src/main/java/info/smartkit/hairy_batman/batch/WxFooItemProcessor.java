package info.smartkit.hairy_batman.batch;

import info.smartkit.hairy_batman.domain.WxFoo;
import info.smartkit.hairy_batman.query.ApiQuery;

import org.springframework.batch.item.ItemProcessor;

public class WxFooItemProcessor implements ItemProcessor<WxFoo, WxFoo>
{

    @Override
    public WxFoo process(final WxFoo wxFoo) throws Exception
    {
        // final String code = wxFoo.getCode();
        // // final String store = wxFoo.getStore().toUpperCase();
        // final String store = wxFoo.getStore();
        // final String manager = wxFoo.getManager();
        // final String agency = wxFoo.getAgency();
        // final String unit = wxFoo.getUnit();
        // final String onSubscribe = wxFoo.getOnSubscribe();
        // final String subscribeId = wxFoo.getSubscribeId();
        // final String articleTime = wxFoo.getArticleTime();
        // final String articleUrl = wxFoo.getArticleUrl();
        // final String articleTitle = wxFoo.getArticleTitle();
        // final String articleReadNum = wxFoo.getArticleReadNum();
        // final String articleLikeNum = wxFoo.getArticleLikeNum();
        // final String articleLikeRate = wxFoo.getArticleLikeRate();
        // final String moniterTime = wxFoo.getMoniterTime();

        // final WxFoo transformedWxFoo =
        // new WxFoo(code, store, manager, agency, unit, onSubscribe, subscribeId, articleTime, articleUrl,
        // articleTitle, articleReadNum, articleLikeNum, articleLikeRate, moniterTime);
        new ApiQuery(wxFoo).query();
        // new ApiQuery(wxFoo).parse();
        //
        System.out.println("ApiQuery converting..." + wxFoo);
        return wxFoo;
    }
}
