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
public class WxFoo
{

    public WxFoo()
    {

    }

    public WxFoo(String code, String store, String manager, String agency, String unit, String onSubscribe,
        String subscribe, String followSubscribe, String onService, String service, String followService)
    {
        this.code = code;
        this.store = store;
        this.manager = manager;
        this.agency = agency;
        this.unit = unit;
        this.onSubscribe = onSubscribe;
        this.subscribe = subscribe;
        this.followSubscribe = followSubscribe;
        this.onService = onService;
        this.service = service;
        this.followService = followService;
    }

    private String code;// 代码

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    private String store;// 店名

    public String getStore()
    {
        return store;
    }

    public void setStore(String store)
    {
        this.store = store;
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

    private String subscribe;// 订阅号名全称

    public String getSubscribe()
    {
        return subscribe;
    }

    public void setSubscribe(String subscribe)
    {
        this.subscribe = subscribe;
    }

    private String followSubscribe;// 关注量1

    public String getFollowSubscribe()
    {
        return followSubscribe;
    }

    public void setFollowSubscribe(String followSubscribe)
    {
        this.followSubscribe = followSubscribe;
    }

    private String onService;// 是否开通服务号

    public String getOnService()
    {
        return onService;
    }

    public void setOnService(String onService)
    {
        this.onService = onService;
    }

    private String service;// 服务号全称

    public String getService()
    {
        return service;
    }

    public void setService(String service)
    {
        this.service = service;
    }

    private String followService;// 关注量2

    public String getFollowService()
    {
        return followService;
    }

    public void setFollowService(String followService)
    {
        this.followService = followService;
    }

    @Override
    public String toString()
    {
        return "code: " + code + ", store: " + store + ",manager: " + manager + ",agency: " + agency + ",unit: " + unit
            + ",onSubscribe: " + onSubscribe + ",subscribe: " + subscribe + ",followSubscribe: " + followSubscribe
            + ",onService: " + onService + ",service: " + service + ",followService: " + followService;
    }
}
