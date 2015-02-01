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
package info.smartkit.hairy_batman;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 微信文章页面分析测试
 * 
 * @see: http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFt_Ri_gqjARIY_shVuqjc3Zo&page=1&t=1422689363681
 * @see: 
 *       http://weixin.sogou.com/weixin?type=1&query=xiandaipengao&fr=sgsearch&ie=utf8&_ast=1422689280&_asf=null&w=01029901
 *       &cid=null
 * @author yangboz
 */
public class ApplicationHtmlParse
{

    private static String articleUrl = "http://weixin.sogou.com/gzh?openid=oIWsFt_Ri_gqjARIY_shVuqjc3Zo";

    public static void main(String[] args)
    {
        Document doc;
        try {

            // need http protocol
            doc = Jsoup.connect(articleUrl).get();
            System.out.println("doc:" + doc);
            // get page title
            String title = doc.title();
            System.out.println("title : " + title);

            // get all links
            Elements links = doc.select("a[href]");
            for (Element link : links) {

                // get the value from href attribute
                System.out.println("\nlink : " + link.attr("href"));
                System.out.println("text : " + link.text());

            }
            // get class div
            Elements divs = doc.select(".wx-rb.bg-blue.wx-rb_v1._item");
            System.out.println("divs(count): " + divs.size());
            for (Element div : divs) {

                // get the value from div class
                System.out.println("\ndiv : " + div.toString());
                // System.out.println("text : " + link.text());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // final WebClient webClient = new WebClient();
        // HtmlPage page = null;
        // try {
        // page = webClient.getPage(articleUrl);
        // // System.out.println("page:" + page);
        // } catch (FailingHttpStatusCodeException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (MalformedURLException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // // get list of all divs
        // final List<?> divs = page.getByXPath("//a[@class='news_lst_tab']");
        // // final HtmlDivision divs = page.getHtmlElementById("sogou_vr_11002601_box_0");
        // System.out.println("divs:(size())" + divs.size());
        // for (Object div : divs) {
        // // get the value from href attribute
        // System.out.println("\ndiv : " + div.toString());
        // }
    }
}
