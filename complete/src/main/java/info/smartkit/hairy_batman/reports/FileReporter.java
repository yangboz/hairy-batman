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

import info.smartkit.hairy_batman.config.GlobalConsts;
import info.smartkit.hairy_batman.domain.WxComplexSubscriber;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * CSVReporter with file generation support.
 * 
 * @author yangboz
 */
public class FileReporter
{

    public enum REPORTER_TYPE
    {
        R_T_OPENID,
        R_T_OPENID_ARTICLE,
        R_T_OPENID_ARTICLE_READ_LIKE,
        R_T_FULL
    };

    public enum REPORTER_FILE_TYPE
    {
        CSV,
        EXCEL
    };

    private static Logger LOG = LogManager.getLogger(FileReporter.class);

    private REPORTER_FILE_TYPE fileType;

    private String getFileExt()
    {
        return fileType == REPORTER_FILE_TYPE.CSV ? ".csv" : ".xls";
    }

    private String fileName;

    public String getFullFileName()
    {
        return fileName + this.getFileExt();
    }

    private ArrayList<String[]> dataSource = new ArrayList<String[]>();

    public ArrayList<String[]> getDataSource()
    {
        for (WxComplexSubscriber elem : this.getRawDataSource()) {
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

    private List<WxComplexSubscriber> rawDataSource = new ArrayList<WxComplexSubscriber>();

    public List<WxComplexSubscriber> getRawDataSource()
    {
        return rawDataSource;
    }

    public void setRawDataSource(List<WxComplexSubscriber> rawDataSource)
    {
        this.rawDataSource = rawDataSource;
    }

    public FileReporter()
    {

    }

    public FileReporter(String fileName, List<WxComplexSubscriber> rawDataSource, REPORTER_TYPE reporterType,
        REPORTER_FILE_TYPE fileType)
    {
        this.fileName = fileName;
        this.setRawDataSource(rawDataSource);
        this.reporterType = reporterType;
        this.fileType = fileType;
    }

    public void write()
    {
        if (this.fileType == FileReporter.REPORTER_FILE_TYPE.EXCEL) {
            this.writeExcel();
        } else {
            this.writeCSV();
        }
    }

    public void writeCSV()
    {
        // CSVWriter
        CSVWriter writer = null;
        try {
            writer =
                new CSVWriter(new OutputStreamWriter(new FileOutputStream(this.getFullFileName()),
                    GlobalConsts.CSV_ENCODING_UTF));
            // writer.write("\uFEFF");
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
        //

    }

    private String[] getExcelHeader()
    {
        String[] excelHeader = new String[] {};
        // System.out.println("elem.toStringArray():" + elem.toStringArray());
        switch (this.reporterType) {
            case R_T_OPENID:
                excelHeader = GlobalConsts.FILE_REPORTER_EXCEL_HEADER_OPENID;
                break;
            case R_T_OPENID_ARTICLE:
                excelHeader = GlobalConsts.FILE_REPORTER_EXCEL_HEADER_OPENID_ARTICLE;
                break;
            case R_T_OPENID_ARTICLE_READ_LIKE:
                excelHeader = GlobalConsts.FILE_REPORTER_EXCEL_HEADER_OPENID_ARTICLE_READ_LIKE;
                break;
            case R_T_FULL:
                excelHeader = GlobalConsts.FILE_REPORTER_EXCEL_HEADER_OPENID_FULL;
                break;
            default:
                break;
        }
        return excelHeader;
    }

    private String[] getExcelRowContents(WxComplexSubscriber subscriber)
    {
        String[] getExcelRowContents = new String[] {};
        // System.out.println("elem.toStringArray():" + elem.toStringArray());
        switch (this.reporterType) {
            case R_T_OPENID:
                getExcelRowContents = subscriber.toOpenIdStringArray();
                break;
            case R_T_OPENID_ARTICLE:
                getExcelRowContents = subscriber.toOpenIdArticleStringArray();
                break;
            case R_T_OPENID_ARTICLE_READ_LIKE:
                getExcelRowContents = subscriber.toOpenIdArticleReadLikeStringArray();
                break;
            case R_T_FULL:
                getExcelRowContents = subscriber.toFullStringArray();
                break;
            default:
                break;
        }
        return getExcelRowContents;
    }

    public void writeExcel()
    {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Statistic Sheet");

        Map<String, String[]> data = new HashMap<String, String[]>();
        data.put("0".toString(), this.getExcelHeader());
        for (WxComplexSubscriber subscriber : this.getRawDataSource()) {
            data.put(subscriber.getId().toString(), this.getExcelRowContents(subscriber));
        }

        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof Date)
                    cell.setCellValue((Date) obj);
                else if (obj instanceof Boolean)
                    cell.setCellValue((Boolean) obj);
                else if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Double)
                    cell.setCellValue((Double) obj);
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(new File(this.getFullFileName()));
            workbook.write(out);
            out.close();
            LOG.info("Excel written successfully.." + this.getFullFileName());

        } catch (FileNotFoundException e) {
            // e.printStackTrace();
            LOG.error(e.toString());
        } catch (IOException e) {
            // e.printStackTrace();
            LOG.error(e.toString());
        }
    }
}
