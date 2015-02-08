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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.PreparedStatementSetter;
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
        //
        for (long j = 0; j < GlobalConsts.KJSON_API_PPQ; j++) {
            if (this.subscribers.size() >= 1) {
                this.queriedSubscriber = this.subscribers.remove(0);
                LOG.info("this.queriedSubscriber: " + this.queriedSubscriber.toString());
                parameters.add("urls", this.queriedSubscriber.getArticleUrl() + "\\n");
            } else {
                break;
            }
        }
        // this.queriedSubscriber = this.subscribers.remove(0);
        // LOG.info("this.queriedSubscriber: " + this.queriedSubscriber.toString());
        // parameters.add("urls", this.queriedSubscriber.getArticleUrl());
        // "http://mp.weixin.qq.com/s?__biz=MjM5ODE4MTUzMg==&mid=202895379&idx=1&sn=a46187dd2e3fc704b72277dbf863f356&3rd=MzA3MDU4NTYzMw==&scene=6#rd");
        return parameters;
    }

    private Long readNum;

    private Long likeNum;

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
        if (api_query_resutls.getData().size() <= 0)// FIXME: null JSON exception handler here.
            return;
        // WxBar returns = restTemplate.getForObject(GlobalConsts.KJSON_API_URI, WxBar.class);
        ArrayList<WxKJson> api_query_resutls_data = api_query_resutls.getData();
        // System.out.println("ApiQuery result data:  " + api_query_resutls_data);
        LOG.info("ApiQuery result data:  " + api_query_resutls_data.toString());
        WxKJson wxKJson = api_query_resutls_data.get(0);
        // System.out.println("Parsed ApiQuery results,articleReadNum:" + wxKJson.getRead() + ",articleLikeNum: "
        // + wxKJson.getLike());
        LOG.info("Parsed ApiQuery results,articleReadNum:" + wxKJson.getRead() + ",articleLikeNum: "
            + wxKJson.getLike());
        //
        this.readNum = Long.parseLong(wxKJson.getRead());
        this.likeNum = Long.parseLong(wxKJson.getLike());
        this.queriedSubscriber.setArticleReadNum(wxKJson.getRead());
        this.queriedSubscriber.setArticleLikeNum(wxKJson.getLike());
        double likeRate = (double) likeNum / readNum * 100;
        java.math.BigDecimal bigLikeRate = new java.math.BigDecimal(likeRate);
        String bigLikeRateStr =
            bigLikeRate.setScale(GlobalConsts.DEFINITION_PRECISION, java.math.BigDecimal.ROUND_HALF_UP).doubleValue()
                + "%";
        this.queriedSubscriber.setArticleLikeRate(bigLikeRateStr);
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
            this.query();
        } else {
            // File reporting...
            new FileReporter(GlobalConsts.REPORT_FILE_OUTPUT_OPENID_ARITICLE_READ_LIKE,
                GlobalVariables.wxFooListWithOpenIdArticleReadLike,
                FileReporter.REPORTER_TYPE.R_T_OPENID_ARTICLE_READ_LIKE, FileReporter.REPORTER_FILE_TYPE.EXCEL).write();
            // Save to DB.
            GlobalVariables.jdbcTempate.update(
                "update wxArticle set articleLikeNum=? AND articleReadNum=? where openId=?",
                new PreparedStatementSetter()
                {
                    @Override
                    public void setValues(java.sql.PreparedStatement ps) throws SQLException
                    {
                        LOG.info("likeNum: " + likeNum + ",readNum: " + readNum);
                        ps.setInt(1, likeNum.intValue());
                        ps.setInt(2, readNum.intValue());
                        ps.setString(3, queriedSubscriber.getOpenId());
                    }
                });
        }
    }
}
