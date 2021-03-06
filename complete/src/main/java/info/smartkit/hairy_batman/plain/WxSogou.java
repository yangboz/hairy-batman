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
package info.smartkit.hairy_batman.plain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Json convertor for Weixin result from Sogou search results.
 * 
 * @see http://weixin.sogou.com/gzhjs?openid=oIWsFt_Ri_gqjARIY_shVuqjc3Zo
 * @author yangboz
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxSogou
{

    private static Logger LOG = LogManager.getLogger(WxSogou.class);

    private long totalItems;

    public long getTotalItems()
    {
        return totalItems;
    }

    public void setTotalItems(long totalItems)
    {
        this.totalItems = totalItems;
    }

    private long totalPages;

    public long getTotalPages()
    {
        return totalPages;
    }

    public void setTotalPages(long totalPages)
    {
        this.totalPages = totalPages;
    }

    private long page;

    public long getPage()
    {
        return page;
    }

    public void setPage(long page)
    {
        this.page = page;
    }

    private ArrayList<Object> items = new ArrayList<Object>();

    public ArrayList<Object> getItems()
    {
        return items;
    }

    public void setItems(ArrayList<Object> items)
    {
        this.items = items;
    }

    @Override
    public String toString()
    {
        return "page:" + this.getPage() + ",totalPages:" + this.getTotalPages() + ",totalItems:" + this.getTotalItems()
            + ",items:" + this.getTitlesUrls().toString();
    }

    //
    private ArrayList<WxSogouSimple> titlesUrls = new ArrayList<WxSogouSimple>();

    // Get items' titles and urls.
    public ArrayList<WxSogouSimple> getTitlesUrls()
    {
        // XML travel to title element,and push it to titles;
    	titlesUrls = new ArrayList<WxSogouSimple>();
        for (Object item : this.items) {
            try {
                Document doc = DocumentHelper.parseText(item.toString());
                Element root = doc.getRootElement();

                @SuppressWarnings("rawtypes")
                List aa = root.selectNodes("//item//display");

                @SuppressWarnings("rawtypes")
                Iterator iters = aa.iterator();
                //
                while (iters.hasNext()) {

                    Element itemEle = (Element) iters.next();

                    String title = itemEle.elementTextTrim("title");
                    // String title1 = itemEle.elementTextTrim("title1");
                    LOG.info("item title:" + title);
                    String date = itemEle.elementTextTrim("date");
                    LOG.info("item date:" + date);
                    String url = itemEle.elementTextTrim("url");
                    // String title1 = itemEle.elementTextTrim("title1");
                    LOG.info("item url:" + url);
                    // Store values.
                    this.titlesUrls.add(new WxSogouSimple(title, date, url));
                    // System.out.println("title1:" + title1);
                }
            } catch (DocumentException e) {
                // e.printStackTrace();
                LOG.error(e.toString());
            }
        }
        return this.titlesUrls;
    }

}
