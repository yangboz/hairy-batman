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
import info.smartkit.hairy_batman.domain.WxFoo;

import java.io.IOException;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Abstract query class for html(based on Sogou search) reading and parsing then querying.
 * 
 * @author yangboz
 */
public class SogouSearchQuery
{
    private static Logger LOG = LogManager.getLogger(SogouSearchQuery.class);

    protected WxFoo wxFoo;

    MultiValueMap<String, String> parameters;

    public SogouSearchQuery()
    {

    }

    public SogouSearchQuery(WxFoo wxFoo)
    {
        this.wxFoo = wxFoo;
        this.parameters = new LinkedMultiValueMap<String, String>();
        this.parameters.add("urls", wxFoo.getArticleUrl());
        // "http://mp.weixin.qq.com/s?__biz=MjM5ODE4MTUzMg==&mid=202895379&idx=1&sn=a46187dd2e3fc704b72277dbf863f356&3rd=MzA3MDU4NTYzMw==&scene=6#rd");
    }

    public void parseWxOpenId()
    {
        Document doc;
        try {

            // need http protocol
            doc = Jsoup.connect(GlobalConsts.SOGOU_SEARCH_URL_BASE + wxFoo.getSubscribeId()).get();

            // get page title
            String title = doc.title();
            LOG.debug("title : " + title);
            // get all "微信号:" value of html <span>
            Elements openIdLink =
                doc.select(GlobalConsts.SOGOU_SEARCH_WX_OPEN_ID_HTML_ELEMENTS).select(
                    GlobalConsts.SOGOU_SEARCH_WX_OPEN_ID_HTML_ELE_IDENTITY);
            String openIdLinkHref = openIdLink.attr("href");
            LOG.debug("openIdLinkHref:" + openIdLinkHref);
            // FIXME:过滤同一个订阅号搜到多条结果，默认选择第一个
            if (this.wxFoo.getOpenId() == null) {
                this.wxFoo.setOpenId(openIdLinkHref.split(GlobalConsts.SOGOU_SEARCH_WX_OPEN_ID_KEYWORDS)[1]);
                LOG.info("saved wxOpenId value: " + this.wxFoo.getOpenId());
                GlobalVariables.wxFooListWithOpenId.add(this.wxFoo);
                // Then,OpenID JSON site parse
                this.parseSogouJsonSite(this.wxFoo.getOpenId());
            }

        } catch (IOException e) {
            // e.printStackTrace();
            LOG.error(e.toString());
        }
    }

    public void parseWxUserId()
    {
        Document doc;
        try {

            // need http protocol
            doc = Jsoup.connect(GlobalConsts.SOGOU_SEARCH_URL_BASE + wxFoo.getSubscribeId()).get();

            // get page title
            String title = doc.title();
            LOG.debug("title : " + title);
            // get all "微信号:" value of html <span>
            Elements openIdSpans = doc.select(GlobalConsts.SOGOU_SEARCH_WX_USER_ID_HTML_ELEMENTS);
            //
            for (Element openIdSpan : openIdSpans) {
                if (openIdSpan.hasText()) {
                    if (openIdSpan.text().contains(GlobalConsts.SOGOU_SEARCH_WX_USER_ID_KEYWORDS)) {
                        // get the value from href attribute
                        LOG.debug("openId span text : " + openIdSpan.text());
                        // FIXME:过滤同一个订阅号搜到多条结果，默认选择第一个
                        if (this.wxFoo.getUserId() == null) {
                            this.wxFoo
                                .setOpenId(openIdSpan.text().split(GlobalConsts.SOGOU_SEARCH_WX_USER_ID_KEYWORDS)[1]);
                            LOG.info("saved wxUserId value: " + this.wxFoo.getUserId());
                            GlobalVariables.wxFooListWithUserId.add(this.wxFoo);
                        }
                    }
                }
            }

        } catch (IOException e) {
            // e.printStackTrace();
            LOG.error(e.toString());
        }
    }

    // @see: http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFt_Ri_gqjARIY_shVuqjc3Zo
    @SuppressWarnings("deprecation")
    private void parseSogouJsonSite(String openId)
    {
        String html = null;
        DefaultHttpClient httpClient = new DefaultHttpClient();// 创建httpClient对象
        HttpGet httpget = new HttpGet(GlobalConsts.SOGOU_SEARCH_URL_JSON + openId);// 以get方式请求该URL
        try {
            HttpResponse responce = httpClient.execute(httpget);// 得到responce对象
            int resStatu = responce.getStatusLine().getStatusCode();// 返回码
            if (resStatu == HttpStatus.SC_OK) {// 200正常 其他就不对
                // 获得相应实体
                HttpEntity entity = responce.getEntity();
                if (entity != null) {
                    html = EntityUtils.toString(entity);// 获得html源代码
                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
            LOG.error(e.toString());
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        LOG.info("parseSogouJsonSite result:" + html);
        // // Sogou API testing using RestTemplate.
        // RestTemplate restTemplate = new RestTemplate();
        // // restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        // MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // // @see:
        // //
        // http://stackoverflow.com/questions/22329368/spring-android-rest-template-parse-json-data-with-content-type-text-html
        // converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        // restTemplate.getMessageConverters().add(converter);
        // // Spring batch for CSV reading.
        // //
        // WxBar api_query_resutls = restTemplate.getForObject(GlobalConsts.SOGOU_SEARCH_URL_JSON + openId,
        // WxBar.class);
        // // WxBar returns = restTemplate.getForObject(GlobalConsts.KJSON_API_URI, WxBar.class);
        // ArrayList<WxKJson> api_query_resutls_data = api_query_resutls.getData();
        // // System.out.println("ApiQuery result data:  " + api_query_resutls_data);
        // LOG.info("ApiQuery result data:  " + api_query_resutls_data);
    }

}
