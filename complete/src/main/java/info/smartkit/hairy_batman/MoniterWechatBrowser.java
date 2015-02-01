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

/**
 * TODO: DOCUMENT ME!
 * 
 * @author yangboz
 */

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * 模拟微信浏览器请求(尝试中)
 */
public class MoniterWechatBrowser
{
    public static void main(String[] args)
    {
        String url = "http://weixin.sogou.com/gzh?openid=oIWsFt_Ri_gqjARIY_shVuqjc3Zo";
        String userAgent =
            "Mozilla/5.0 (Linux; U; Android 4.1.2; zh-cn; GT-I9300 Build/JZO54K) "
                + "AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 MicroMessenger/5.2.380";
        String html = getHttpClientHtml(url, "UTF-8", userAgent);
        System.out.println(html);
    }

    /**
     * 根据URL获得所有的html信息
     */
    @SuppressWarnings("deprecation")
    public static String getHttpClientHtml(String url, String code, String userAgent)
    {
        String html = null;
        @SuppressWarnings("deprecation")
        DefaultHttpClient httpClient = new DefaultHttpClient();// 创建httpClient对象
        HttpGet httpget = new HttpGet(url);// 以get方式请求该URL
        // Pause for 4 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
            System.out.println(e1.toString());
        }
        //
        httpget.setHeader("User-Agent", userAgent);
        try {
            // 得到responce对象
            HttpResponse responce = httpClient.execute(httpget);
            // 返回码
            int returnCode = responce.getStatusLine().getStatusCode();
            // 是200证明正常 其他就不对
            if (returnCode == HttpStatus.SC_OK) {
                // 获得相应实体
                HttpEntity entity = responce.getEntity();
                if (entity != null) {
                    html = new String(EntityUtils.toString(entity));// 获得html源代码
                }
            }
        } catch (Exception e) {
            System.out.println("出现出现异常");
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return html;
    }
}
