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
import info.smartkit.hairy_batman.domain.WxSubscriber;
import info.smartkit.hairy_batman.plain.WxBar;
import info.smartkit.hairy_batman.plain.WxKJson;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Abstract query class for CSV reading and parsing then querying.
 * 
 * @author yangboz
 */
public class KJsonApiQuery
{
    private static Logger LOG = LogManager.getLogger(KJsonApiQuery.class);

    protected WxSubscriber wxFoo;

    MultiValueMap<String, String> parameters;

    public KJsonApiQuery()
    {

    }

    public KJsonApiQuery(WxSubscriber wxFoo)
    {
        this.wxFoo = wxFoo;
        this.parameters = new LinkedMultiValueMap<String, String>();
        this.parameters.add("urls", wxFoo.getArticleUrl());
        // "http://mp.weixin.qq.com/s?__biz=MjM5ODE4MTUzMg==&mid=202895379&idx=1&sn=a46187dd2e3fc704b72277dbf863f356&3rd=MzA3MDU4NTYzMw==&scene=6#rd");
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
        WxBar api_query_resutls = restTemplate.postForObject(GlobalConsts.KJSON_API_URI, this.parameters, WxBar.class);
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
        this.wxFoo.setArticleReadNum(wxKJson.getRead());
        this.wxFoo.setArticleLikeNum(wxKJson.getLike());
        Long likeRate = (long) ((float) likeNum / readNum * 100);
        this.wxFoo.setArticleLikeRate(likeRate.toString() + "%");
        //
        this.wxFoo.setMoniterTime(GlobalVariables.now());
        //
        GlobalVariables.wxFooList.add(this.wxFoo);
        //
        // System.out.println("GlobalVariables.wxFooList:" + GlobalVariables.wxFooList.toString());
        LOG.info("GlobalVariables.wxFooList:" + GlobalVariables.wxFooList.toString());
    }

}
