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

    // @see:http://www.kjson.com/weixin/bind
    public static final long KJSON_API_QPM = 60;// Query numbers per minutes;

    public static final long KJSON_API_PPQ = 20;// Page numbers per query;

    public static final String QUERY_TABLE_NAME = "wxfoo";

    public static final String QUERY_COLUMNS_NAME =
        "code,store,manager,agency,unit,onSubscribe,subscribeId,articleTime,articleUrl,articleTitle,articleReadNum,articleLikeNum,articleLikeRate,moniterTime";

    public static final String QUERY_COLUMNS_LABEL = QUERY_COLUMNS_NAME.replace(",", ",:");

    public static final String[] CSV_COLUMNS_NAME = QUERY_COLUMNS_NAME.split(",");

    public static final String CSV_RESOURCE_FILE_INPUT = "QueryNumOfReadLike.csv";

    public static final String CSV_RESOURCE_FILE_OUTPUT = "QueryNumOfReadLike_output.csv";

    public static final String CSV_DELIMITED_LINE_TOKENIZER = ";";

}
