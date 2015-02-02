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
package info.smartkit.hairy_batman.config;

import info.smartkit.hairy_batman.domain.WxSubscriber;
import info.smartkit.hairy_batman.plain.WxSogou;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global variables storage here.
 * 
 * @author yangboz
 */
public class GlobalVariables
{
    //
    public static List<WxSubscriber> wxFooList = new ArrayList<WxSubscriber>();

    //
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    public static String now()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    // Temporary for storage with WX OpenId info updated.
    public static List<WxSubscriber> wxFooListWithOpenId = new ArrayList<WxSubscriber>();

    // Temporary for storage with WX UserId info updated.
    public static List<WxSubscriber> wxFooListWithUserId = new ArrayList<WxSubscriber>();

    // Temporary for storage with WX OpenId info updated.
    public static Map<String, WxSogou> openIdWithArticleList = new HashMap<String, WxSogou>();

}
