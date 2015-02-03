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

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

    private static Logger LOG = LogManager.getLogger(CSVReporter.class);

    private String fileName;

    public String getFileName()
    {
        return fileName;
    }

    private ArrayList<String[]> dataSource = new ArrayList<String[]>();

    public ArrayList<String[]> getDataSource()
    {
        return dataSource;
    }

    public void setDataSource(ArrayList<String[]> dataSource)
    {
        this.dataSource = dataSource;
    }

    public CSVReporter()
    {

    }

    public CSVReporter(String fileName, ArrayList<String[]> dataSource)
    {
        this.fileName = fileName;
        this.setDataSource(dataSource);
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
