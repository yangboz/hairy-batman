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

/**
 * Global constants here.
 * 
 * @author yangboz
 */
public class GlobalConsts
{
    public static final String KJSON_API_URI = "http://www.kjson.com/weixin/api?key=45cfa3defbebeaab767517d2339b57e5";

    public static final Integer DEFINITION_PRECISION = 5;

    // @see:http://www.kjson.com/weixin/bind
    public static final long KJSON_API_QPM = 60;// Query numbers per minutes;

    public static final long KJSON_API_PPQ = 20;// Page numbers per query;

    public static final String QUERY_TABLE_NAME = "wxfoo";

    public static final String QUERY_COLUMNS_NAME =
    // "code,store,manager,agency,unit,onSubscribe,subscribeId,articleTime,articleUrl,articleTitle,articleReadNum,articleLikeNum,articleLikeRate,moniterTime";
        "id,code,store,agency,unit,onSubscribe,subscribeId,articleTime,articleUrl,articleTitle,articleReadNum,articleLikeNum,articleLikeRate,moniterTime";

    public static final String QUERY_COLUMNS_NAME_SIMPLE = "id,store,agency,unit,subscribeId,onSubscribe,code";

    public static final String QUERY_COLUMNS_LABEL = QUERY_COLUMNS_NAME.replace(",", ",:");

    public static final String[] CSV_COLUMNS_NAME = QUERY_COLUMNS_NAME.split(",");

    public static final String[] CSV_COLUMNS_NAME_SIMPLE = QUERY_COLUMNS_NAME_SIMPLE.split(",");

    public static final String RESOURCE_FILE_INPUT_CSV = "wxStatisticInput.csv";

    public static final String RESOURCE_FILE_INPUT_XLS = "wxStatisticInput.xlsx";

    public static final String REPORT_FILE_OUTPUT_FULL = "QueryNumOfReadLike_output_full";

    public static final String REPORT_FILE_OUTPUT_OPENID_ARITICLE = "QueryNumOfReadLike_output_openid_article";

    public static final String REPORT_FILE_OUTPUT_OPENID_ARITICLE_READ_LIKE =
        "QueryNumOfReadLike_output_openid_article_readlike";

    public static final String REPORT_FILE_OUTPUT_OPENID = "QueryNumOfReadLike_output_openid";

    public static final String CSV_DELIMITED_LINE_TOKENIZER = ";";

    public static final String CSV_ENCODING_UTF = "UTF-8";

    public static final String SOGOU_SEARCH_URL_BASE = "http://weixin.sogou.com/weixin?type=1&query=";

    public static final String SOGOU_SEARCH_WX_USER_ID_HTML_ELEMENTS = "span";

    public static final String SOGOU_SEARCH_WX_USER_ID_HTML_ELE_IDENTITY = "";

    public static final String SOGOU_SEARCH_WX_OPEN_ID_HTML_ELEMENTS = "div";

    public static final String SOGOU_SEARCH_WX_OPEN_ID_HTML_ELE_IDENTITY = "[href*=/gzh?]";

    public static final String SOGOU_SEARCH_WX_USER_ID_KEYWORDS = "微信号：";

    public static final String SOGOU_SEARCH_WX_OPEN_ID_KEYWORDS = "openid=";

    public static final String SOGOU_SEARCH_URL_JSON = "http://weixin.sogou.com/gzhjs?openid=";

    public static final String FILE_REPORTER_TYPE_EXCEL = ".xls";

    public static final String FILE_REPORTER_TYPE_CSV = ".csv";

    public static final Integer LINES_SKIP_FOR_HEADER_EXCEL = 1;

    public static final String[] FILE_REPORTER_EXCEL_HEADER_OPENID = new String[] {"序号", "店代码", "店名", "办事处", "事业部",
    "微信号类型", "微信号全称", "OpenId"};

    public static final String[] FILE_REPORTER_EXCEL_HEADER_OPENID_ARTICLE = new String[] {"序号", "店代码", "店名", "办事处",
    "事业部", "微信号类型", "微信号全称", "OpenId", "文章标题", "文章地址"};

    public static final String[] FILE_REPORTER_EXCEL_HEADER_OPENID_ARTICLE_READ_LIKE = new String[] {"序号", "店代码", "店名",
    "办事处", "事业部", "微信号类型", "微信号全称", "OpenId", "文章标题", "发表时间", "文章地址", "阅读数", "点赞数", "点赞率", "监测时间"};

    public static final String[] FILE_REPORTER_EXCEL_HEADER_OPENID_FULL = new String[] {"序号", "店代码", "店名", "订阅号",
    "OpenId", "文章标题", "发表时间", "文章地址", "阅读数", "点赞数"};

}
