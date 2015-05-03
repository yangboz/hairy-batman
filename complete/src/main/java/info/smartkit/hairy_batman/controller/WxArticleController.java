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
package info.smartkit.hairy_batman.controller;

import info.smartkit.hairy_batman.dto.JsonObject;
import info.smartkit.hairy_batman.model.WxSubscriberJPAModel;
import info.smartkit.hairy_batman.model.WxSubscriberRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * TODO: DOCUMENT ME!
 * 
 * @author yangboz
 */
@RestController
@RequestMapping("v0/wxArticle")
public class WxArticleController
{

    @Autowired
    WxSubscriberRepository repository;

    // @RequestMapping(method = RequestMethod.GET, value = "v0/wxArticle")
    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(httpMethod = "PUT", value = "增加微信订阅号ID")
    public JsonObject addArticle(
        @RequestParam(value = "subscriberId", required = true, defaultValue = "gh_d8c432a3cc12") String subscriberId)
    {
        WxSubscriberJPAModel model = new WxSubscriberJPAModel();
        model.setSubscribeId(subscriberId);
        // /
        repository.save(model);
        //
        return new JsonObject(repository.findBySubscriberId(subscriberId));

    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ApiOperation(httpMethod = "DELETE", value = "删除微信订阅号ID")
    public JsonObject delArticle(
        @RequestParam(value = "subscriberId", required = true, defaultValue = "gh_d8c432a3cc12") String subscriberId)
    {
        // /
        WxSubscriberJPAModel findOne = repository.findBySubscriberId(subscriberId).get(0);
        repository.delete(findOne.getId());
        //
        return new JsonObject(findOne);

    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "列出微信订阅号IDs")
    public JsonObject getArticles(
        @RequestParam(value = "subscriberId", required = true, defaultValue = "gh_d8c432a3cc12") String subscriberId)
    {
        // /
        List<WxSubscriberJPAModel> findAll = repository.findBySubscriberId(subscriberId);
        //
        return new JsonObject(findAll);

    }

    // @RequestMapping(method = RequestMethod.GET)
    // @ApiOperation(httpMethod = "GET", value = "列出微信订阅号ID")
    // public JsonObject getArticle(
    // @RequestParam(value = "subscriberId", required = true, defaultValue = "gh_d8c432a3cc12") String subscriberId)
    // {
    // // /
    // WxSubscriberJPAModel findOne = repository.findBySubscriberId(subscriberId).get(0);
    // //
    // return new JsonObject(findOne);
    // }

}
