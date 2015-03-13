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
 * WxFoo object entity special for article
 * 
 * @author yangboz
 */
public class WxFooArticle
{

    public WxFooArticle()
    {

    }

    public WxFooArticle(String articleUrl, String articleTitle, String articleTime, String articleReadNum,
        String articleLikeNum, String articleLikeRate)
    {
        this.articleTime = articleTime;
        this.articleUrl = articleUrl;
        this.articleTitle = articleTitle;
        this.articleReadNum = articleReadNum;
        this.articleLikeNum = articleLikeNum;
        this.articleLikeRate = articleLikeRate;
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

    @Override
    public String toString()
    {
        return "articleTime: " + articleTime + ",articleUrl: " + articleUrl + ",articleTitle: " + articleTitle
            + ",articleReadNum: " + articleReadNum + ",articleLikeNum: " + articleLikeNum + ",articleLikeRate: "
            + articleLikeRate;
    }

    public String[] toStringArray()
    {
        return new String[] {this.getArticleTime(), this.getArticleUrl(), this.getArticleTitle(),
        this.getArticleReadNum(), this.getArticleLikeNum(), this.getArticleLikeRate()};
    }

}
