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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * KJson object entity.
 * {url=http://mp.weixin.qq.com/s?__biz=MjM5ODE4MTUzMg==&mid=202895379&idx=1&sn=a46187dd2e3fc704b72277dbf863f356
 * &3rd=MzA3MDU4NTYzMw==&scene=6#rd, read=2018, like=20}
 * 
 * @author yangboz
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxKJson
{
    private String url;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    private String read;

    public String getRead()
    {
        return read;
    }

    public void setRead(String read)
    {
        this.read = read;
    }

    private String like;

    public String getLike()
    {
        return like;
    }

    public void setLike(String like)
    {
        this.like = like;
    }
}
