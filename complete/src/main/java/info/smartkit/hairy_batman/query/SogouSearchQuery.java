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

    public void parse()
    {
        Document doc;
        try {

            // need http protocol
            doc = Jsoup.connect(GlobalConsts.SOGOU_SEARCH_URL_BASE + wxFoo.getSubscribeId()).get();

            // get page title
            String title = doc.title();
            LOG.debug("title : " + title);
            // get all "微信号:" value of html <span>
            Elements openIdSpans = doc.select(GlobalConsts.SOGOU_SEARCH_HTML_ELEMENTS);

            for (Element openIdSpan : openIdSpans) {
                if (openIdSpan.hasText()) {
                    if (openIdSpan.text().contains(GlobalConsts.SOGOU_SEARCH_KEYWORDS)) {
                        // get the value from href attribute
                        LOG.debug("openId span text : " + openIdSpan.text());
                        // FIXME:过滤同一个订阅号搜到多条结果，默认选择第一个
                        if (this.wxFoo.getOpenId() == null) {
                            this.wxFoo.setOpenId(openIdSpan.text().split(GlobalConsts.SOGOU_SEARCH_KEYWORDS)[1]);
                            LOG.info("saved wxOpenId value: " + this.wxFoo.getOpenId());
                            GlobalVariables.wxFooListWithOpenId.add(this.wxFoo);
                        }
                        // TODO:parse JSON site,for the next step.
                        // @see: http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFt_Ri_gqjARIY_shVuqjc3Zo
                    }
                }
            }

        } catch (IOException e) {
            // e.printStackTrace();
            LOG.error(e.toString());
        }
    }
}
