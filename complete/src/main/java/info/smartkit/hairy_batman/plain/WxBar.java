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
 * Parse kjson.com API result.
 * 
 * @author yangboz
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WxBar
{
    private String ok;

    public String getOk()
    {
        return ok;
    }

    public void setOk(String ok)
    {
        this.ok = ok;
    }

    private String reason;

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    private ArrayList<WxKJson> data;

    public ArrayList<WxKJson> getData()
    {
        return data;
    }

    public void setData(ArrayList<WxKJson> data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ok: " + ok + ",reason: " + reason + ",data: " + data.toString();
    }

}
