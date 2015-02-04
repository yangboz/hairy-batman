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
package info.smartkit.hairy_batman.domain;

/**
 * Foo object entity
 * 
 * @author yangboz
 */
public class WxComplexSubscriber extends WxSimpleSubscriber
{

    public WxComplexSubscriber()
    {

    }

    private WxFooArticle wxFooArticle;

    public WxFooArticle getWxFooArticle()
    {
        return wxFooArticle;
    }

    public void setWxFooArticle(WxFooArticle wxFooArticle)
    {
        this.wxFooArticle = wxFooArticle;
    }

    // public WxComplexSubscriber(Integer id, String code, String store, String manager, String agency, String unit,
    public WxComplexSubscriber(Integer id, String code, String store, String onSubscribe, String subscribeId,
        String moniterTime, WxFooArticle wxFooArticle)
    {
        this.id = id;
        this.code = code;
        this.store = store;
        // this.manager = manager;
        // this.agency = agency;
        // this.unit = unit;
        this.onSubscribe = onSubscribe;
        this.subscribeId = subscribeId;
        this.wxFooArticle = wxFooArticle;
    }

    // public WxComplexSubscriber(Integer id, String code, String store, String manager, String agency, String unit,
    public WxComplexSubscriber(Integer id, String code, String store, String onSubscribe, String subscribeId,
        String articleTime, String articleUrl, String articleTitle, String articleReadNum, String articleLikeNum,
        String articleLikeRate, String moniterTime)
    {
        this.id = id;
        this.code = code;
        this.store = store;
        // this.manager = manager;
        // this.agency = agency;
        // this.unit = unit;
        this.onSubscribe = onSubscribe;
        this.subscribeId = subscribeId;
        this.articleTime = articleTime;
        this.articleUrl = articleUrl;
        this.articleTitle = articleTitle;
        this.articleReadNum = articleReadNum;
        this.articleLikeNum = articleLikeNum;
        this.articleLikeRate = articleLikeRate;
        this.moniterTime = moniterTime;
    }

    private String manager;// 区域经理

    public String getManager()
    {
        return manager;
    }

    public void setManager(String manager)
    {
        this.manager = manager;
    }

    private String agency;// 办事处

    public String getAgency()
    {
        return agency;
    }

    public void setAgency(String agency)
    {
        this.agency = agency;
    }

    private String unit;// 事业部

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    private String onSubscribe;// 是否开通订阅号

    public String getOnSubscribe()
    {
        return onSubscribe;
    }

    public void setOnSubscribe(String onSubscribe)
    {
        this.onSubscribe = onSubscribe;
    }

    private String articleTime;// 文章发布日期，articleTime VARCHAR(10),

    public String getArticleTime()
    {
        return articleTime;
    }

    public void setArticleTime(String articleTime)
    {
        this.articleTime = articleTime;
    }

    private String articleUrl;// 文章地址，articleUrl VARCHAR(255),

    public String getArticleUrl()
    {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl)
    {
        this.articleUrl = articleUrl;
    }

    private String articleTitle;// 文章标题,articleTitle VARCHAR(50),

    public String getArticleTitle()
    {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle)
    {
        this.articleTitle = articleTitle;
    }

    private String articleReadNum;// 文章阅读数,articleReadNum VARCHAR(10),

    public String getArticleReadNum()
    {
        return articleReadNum;
    }

    public void setArticleReadNum(String articleReadNum)
    {
        this.articleReadNum = articleReadNum;
    }

    private String articleLikeNum;// 文章点赞数,articleLikeNum VARCHAR(10),

    public String getArticleLikeNum()
    {
        return articleLikeNum;
    }

    public void setArticleLikeNum(String articleLikeNum)
    {
        this.articleLikeNum = articleLikeNum;
    }

    private String articleLikeRate;// 文章点赞率,articleLikeRate VARCHAR(5),

    public String getArticleLikeRate()
    {
        return articleLikeRate;
    }

    public void setArticleLikeRate(String articleLikeRate)
    {
        this.articleLikeRate = articleLikeRate;
    }

    private String moniterTime;// 检测日期, updateTime VARCHAR(10)

    public String getMoniterTime()
    {
        return moniterTime;
    }

    public void setMoniterTime(String moniterTime)
    {
        this.moniterTime = moniterTime;
    }

    // @Override
    // public String toString()
    // {
    // return "code: " + code + ", store: " + store + ",manager: " + manager + ",agency: " + agency + ",unit: " + unit
    // + ",onSubscribe: " + onSubscribe + ",subscribeId: " + subscribeId + ",articleTime: " + articleTime
    // + ",articleUrl: " + articleUrl + ",articleTitle: " + articleTitle + ",articleReadNum: " + articleReadNum
    // + ",articleLikeNum: " + articleLikeNum + ",articleLikeRate: " + articleLikeRate + ",moniterTime: "
    // + moniterTime;
    // }
    @Override
    public String toString()
    {
        return "code: " + code + ", store: " + store + ",subscribeId: " + subscribeId + ",articleTime: " + articleTime
            + ",articleUrl: " + articleUrl + ",articleTitle: " + articleTitle + ",articleReadNum: " + articleReadNum
            + ",articleLikeNum: " + articleLikeNum + ",articleLikeRate: " + articleLikeRate + ",moniterTime: "
            + moniterTime;
    }

    public String[] toFullStringArray()
    {
        return new String[] {this.getCode(), this.getStore(), this.getManager(), this.getAgency(), this.getUnit(),
        this.getOnSubscribe(), this.getSubscribeId(), this.getArticleTime(), this.getArticleUrl(),
        this.getArticleTitle(), this.getArticleReadNum(), this.getArticleLikeNum(), this.getArticleLikeRate(),
        this.getMoniterTime()};
    }

    public String[] toOpenIdStringArray()
    {
        // return ArrayUtil.addAll(super.toStringArray(), new String[] {this.getOpenId()});
        return new String[] {this.getId().toString(), this.getCode(), this.getStore(), this.getSubscribeId(),
        this.getOpenId()};
    }

    public String[] toOpenIdArticleStringArray()
    {
        return new String[] {this.getId().toString(), this.getCode(), this.getSubscribeId(), this.getOpenId(),
        this.getArticleTitle(), this.getArticleUrl()};
    }

    public String[] toOpenIdArticleReadLikeStringArray()
    {
        return new String[] {this.getCode(), this.getSubscribeId(), this.getOpenId(), this.getArticleTitle(),
        this.getArticleUrl(), this.getArticleReadNum(), this.getArticleLikeNum(), this.getArticleLikeRate()};
    }

    private String openId = null;// 微信openID

    public String getOpenId()
    {
        return openId;
    }

    public void setOpenId(String openId)
    {
        this.openId = openId;
    }

    private String userId = null;// 微信用户ID

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

}
