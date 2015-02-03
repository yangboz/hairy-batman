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
package info.smartkit.hairy_batman.reports;

import info.smartkit.hairy_batman.domain.WxSubscriber;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * CSVReporter with file generation support.
 * 
 * @author yangboz
 */
public class CSVReporter
{

    public enum REPORTER_TYPE
    {
        R_T_OPENID,
        R_T_OPENID_ARTICLE,
        R_T_OPENID_ARTICLE_READ_LIKE,
        R_T_FULL
    };

    private static Logger LOG = LogManager.getLogger(CSVReporter.class);

    private String fileName;

    public String getFileName()
    {
        return fileName;
    }

    private ArrayList<String[]> dataSource = new ArrayList<String[]>();

    public ArrayList<String[]> getDataSource()
    {
        for (WxSubscriber elem : this.getRawDataSource()) {
            String[] elemStrings = new String[] {};
            // System.out.println("elem.toStringArray():" + elem.toStringArray());
            switch (this.reporterType) {
                case R_T_OPENID:
                    elemStrings = elem.toOpenIdStringArray();
                    break;
                case R_T_OPENID_ARTICLE:
                    elemStrings = elem.toOpenIdArticleStringArray();
                    break;
                case R_T_OPENID_ARTICLE_READ_LIKE:
                    elemStrings = elem.toOpenIdArticleReadLikeStringArray();
                    break;
                case R_T_FULL:
                    elemStrings = elem.toFullStringArray();
                    break;
                default:
                    break;
            }
            LOG.debug("elemStrings:" + elemStrings);
            dataSource.add(elemStrings);
        }
        return dataSource;
    }

    private REPORTER_TYPE reporterType = REPORTER_TYPE.R_T_FULL;

    private List<WxSubscriber> rawDataSource = new ArrayList<WxSubscriber>();

    public List<WxSubscriber> getRawDataSource()
    {
        return rawDataSource;
    }

    public void setRawDataSource(List<WxSubscriber> rawDataSource)
    {
        this.rawDataSource = rawDataSource;
    }

    public CSVReporter()
    {

    }

    public CSVReporter(String fileName, List<WxSubscriber> rawDataSource, REPORTER_TYPE reporterType)
    {
        this.fileName = fileName;
        this.setRawDataSource(rawDataSource);
        this.reporterType = reporterType;
    }

    public void write()
    {
        // CSVWriter
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(this.getFileName(), true));
        } catch (IOException e) {
            // e.printStackTrace();
            LOG.error(e.toString());
        }
        //
        writer.writeAll(this.getDataSource());
        try {
            writer.close();
        } catch (IOException e) {
            // e.printStackTrace();
            LOG.error(e.toString());
        }
    }
}
