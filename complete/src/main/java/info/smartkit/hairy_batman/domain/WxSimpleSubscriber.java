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

import info.smartkit.hairy_batman.config.GlobalConsts;

/**
 * Foo object entity
 * 
 * @author yangboz
 */
public class WxSimpleSubscriber
{

    public WxSimpleSubscriber()
    {

    }

    public WxSimpleSubscriber(Integer id, String code, String store, String subscribeId)
    {
        this.id = id;
        this.code = code;
        this.store = store;
        this.subscribeId = subscribeId;
    }

    protected Integer id;// 序号

    public Integer getId()
    {
        return id + GlobalConsts.LINES_SKIP_FOR_HEADER_EXCEL;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    protected String code;// 代码

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    protected String store;// 店名

    public String getStore()
    {
        return store;
    }

    public void setStore(String store)
    {
        this.store = store;
    }

    protected String subscribeId;// 订阅号名全称,subscribeId VARCHAR(20),

    public String getSubscribeId()
    {
        return subscribeId;
    }

    public void setSubscribeId(String subscribeId)
    {
        this.subscribeId = subscribeId;
    }

    @Override
    public String toString()
    {
        return "id: " + id + ",code: " + code + ", store: " + store + ",subscribeId: " + subscribeId;
    }

    public String[] toStringArray()
    {
        return new String[] {this.getCode(), this.getStore(), this.getSubscribeId()};
    }

}
