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
}