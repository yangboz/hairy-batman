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
package info.smartkit.hairy_batman.query;

import info.smartkit.hairy_batman.config.GlobalConsts;
import info.smartkit.hairy_batman.config.GlobalVariables;
import info.smartkit.hairy_batman.domain.WxComplexSubscriber;
import info.smartkit.hairy_batman.plain.WxBar;
import info.smartkit.hairy_batman.plain.WxKJson;
import info.smartkit.hairy_batman.reports.FileReporter;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Abstract query class for CSV reading and parsing then querying.
 * 
 * @author yangboz
 */
public class KJsonApiQuery
{
    private static Logger LOG = LogManager.getLogger(KJsonApiQuery.class);

    protected ArrayList<WxComplexSubscriber> subscribers;

    protected WxComplexSubscriber queriedSubscriber;

    // MultiValueMap<String, String> parameters;

    protected ArrayList<WxComplexSubscriber> queriedSubscribers = new ArrayList<WxComplexSubscriber>();

    public KJsonApiQuery()
    {

    }

    public KJsonApiQuery(ArrayList<WxComplexSubscriber> subscribers)
    {
        this.subscribers = subscribers;
    }

    private LinkedMultiValueMap<String, String> getParameters()
    {
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        // long numbOfBundle = this.subscribers.size() % GlobalConsts.KJSON_API_PPQ;
        // String[] urlsBundle = new String[] {subscribers.get(0).getArticleUrl(), subscribers.get(1).getArticleUrl()};
        // this.parameters.add("urls", urlsBundle.toString());
        parameters.add("urls", this.subscribers.remove(0).getArticleUrl());
        //
        // for (long i = 0; i < numbOfBundle; i++)
        // {
        // for(long j=0;j<GlobalConsts.KJSON_API_PPQ;j++)
        // {
        // this.parameters.add("urls", this.singleSubscriber.getArticleUrl());
        // }
        // this.parameters.add("urls", this.singleSubscriber.getArticleUrl());
        // //
        // "http://mp.weixin.qq.com/s?__biz=MjM5ODE4MTUzMg==&mid=202895379&idx=1&sn=a46187dd2e3fc704b72277dbf863f356&3rd=MzA3MDU4NTYzMw==&scene=6#rd");
        // }
        return parameters;
    }

    public void query()
    {
        // KJSON API testing using RestTemplate.
        RestTemplate restTemplate = new RestTemplate();
        // restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // @see:
        // http://stackoverflow.com/questions/22329368/spring-android-rest-template-parse-json-data-with-content-type-text-html
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_HTML));
        restTemplate.getMessageConverters().add(converter);
        // Spring batch for CSV reading.
        //
        WxBar api_query_resutls =
            restTemplate.postForObject(GlobalConsts.KJSON_API_URI, this.getParameters(), WxBar.class);
        // WxBar returns = restTemplate.getForObject(GlobalConsts.KJSON_API_URI, WxBar.class);
        ArrayList<WxKJson> api_query_resutls_data = api_query_resutls.getData();
        // System.out.println("ApiQuery result data:  " + api_query_resutls_data);
        LOG.info("ApiQuery result data:  " + api_query_resutls_data);
        WxKJson wxKJson = api_query_resutls_data.get(0);
        // System.out.println("Parsed ApiQuery results,articleReadNum:" + wxKJson.getRead() + ",articleLikeNum: "
        // + wxKJson.getLike());
        LOG.info("Parsed ApiQuery results,articleReadNum:" + wxKJson.getRead() + ",articleLikeNum: "
            + wxKJson.getLike());
        //
        Long readNum = Long.parseLong(wxKJson.getRead());
        Long likeNum = Long.parseLong(wxKJson.getLike());
        this.queriedSubscriber.setArticleReadNum(wxKJson.getRead());
        this.queriedSubscriber.setArticleLikeNum(wxKJson.getLike());
        Long likeRate = (long) ((float) likeNum / readNum * 100);
        this.queriedSubscriber.setArticleLikeRate(likeRate.toString() + "%");
        //
        this.queriedSubscriber.setMoniterTime(GlobalVariables.now());
        //
        GlobalVariables.wxFooListWithOpenIdArticleReadLike.add(this.queriedSubscriber);
        //
        LOG.info("GlobalVariables.wxFooListWithOpenIdArticleReadLike(size):"
            + GlobalVariables.wxFooListWithOpenIdArticleReadLike.size() + ",raw: "
            + GlobalVariables.wxFooListWithOpenIdArticleReadLike.toString());
        //
        if (this.subscribers.size() > 0) {
            // this.query();
        } else {
            // File reporting...
            new FileReporter(GlobalConsts.REPORT_FILE_OUTPUT_OPENID_ARITICLE_READ_LIKE,
                GlobalVariables.wxFooListWithOpenIdArticleReadLike,
                FileReporter.REPORTER_TYPE.R_T_OPENID_ARTICLE_READ_LIKE, FileReporter.REPORTER_FILE_TYPE.EXCEL).write();
        }
    }

}
