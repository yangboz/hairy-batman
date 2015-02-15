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
package info.smartkit.hairy_batman.query;

import info.smartkit.hairy_batman.config.GlobalConsts;
import info.smartkit.hairy_batman.config.GlobalVariables;
import info.smartkit.hairy_batman.domain.WxComplexSubscriber;
import info.smartkit.hairy_batman.plain.WxSogou;
import info.smartkit.hairy_batman.plain.WxSogouSimple;
import info.smartkit.hairy_batman.reports.FileReporter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.dao.DataAccessException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Abstract query class for html(based on Sogou search) reading and parsing then
 * querying.
 * 
 * @author yangboz
 */
public class SogouSearchQuery {
	private static Logger LOG = LogManager.getLogger(SogouSearchQuery.class);

	protected WxComplexSubscriber wxFoo;

	MultiValueMap<String, String> parameters;

	public SogouSearchQuery() {

	}

	public SogouSearchQuery(WxComplexSubscriber wxFoo) {
		this.wxFoo = wxFoo;
		this.parameters = new LinkedMultiValueMap<String, String>();
	}

	public void parseWxOpenId() {
		Document doc;
		try {

			// need http protocol
			// doc = Jsoup.connect(GlobalConsts.SOGOU_SEARCH_URL_BASE+ wxFoo.getSubscribeId()).get();
			doc = Jsoup.connect("http://weixin.sogou.com/weixin?type=1&query="+wxFoo.getSubscribeId()+"&fr=sgsearch&ie=utf8&_ast=1423915648&_asf=null&w=01019900&cid=null&sut=19381").get();

			// System.out.println("doc : " + doc.html());
			// get page title
			String title = doc.title();
			LOG.debug("title : " + title);
			// get all "微信号:" value of html <span>
			//Elements openIdLink = doc.select(GlobalConsts.SOGOU_SEARCH_WX_OPEN_ID_HTML_ELEMENTS).select(GlobalConsts.SOGOU_SEARCH_WX_OPEN_ID_HTML_ELE_IDENTITY);
		
			Elements openIdLink = doc.getElementsByClass("wx-rb");
			Element a =null;
			String openIdLinkHref ="";
			if (openIdLink!=null && openIdLink.size()>0)
			{
				Iterator<Element> itea = openIdLink.iterator();
				while (itea.hasNext())
				{
					a = itea.next();
					System.out.println(a.html());
					if (a.getElementsByTag("em").html().indexOf(wxFoo.getSubscribeId())!=-1)
					{
						break;
					}
				}
			}
			if (a!=null){
				openIdLinkHref = a.attr("href");
			}
			LOG.debug("openIdLinkHref:" + openIdLinkHref);
			// FIXME:过滤同一个订阅号搜到多条结果，默认选择第一个
			if (this.wxFoo.getOpenId() == null && openIdLinkHref.length() > 0) {

				this.wxFoo
						.setOpenId(openIdLinkHref
								.split(GlobalConsts.SOGOU_SEARCH_WX_OPEN_ID_KEYWORDS)[1]);
				LOG.info("saved wxOpenId value: " + this.wxFoo.getOpenId());
				GlobalVariables.wxFooListWithOpenId.add(this.wxFoo);
				// File reporting
				new FileReporter(GlobalConsts.REPORT_FILE_OUTPUT_OPENID,
						GlobalVariables.wxFooListWithOpenId,
						FileReporter.REPORTER_TYPE.R_T_OPENID,
						FileReporter.REPORTER_FILE_TYPE.EXCEL).write();
				// Then,OpenID JSON site parse
				if (this.wxFoo.getOpenId() != null) {
					// Save openId to DB.
					try {
						GlobalVariables.jdbcTempate
								.update("insert into "
										+ GlobalConsts.QUERY_TABLE_NAME_BASIC
										+ "(id,store,agency,unit,subscribeId,onSubscribe,code,openId) values(?,?,?,?,?,?,?,?)",
										new Object[] { this.wxFoo.getId(),
												this.wxFoo.getStore(),
												this.wxFoo.getAgency(),
												this.wxFoo.getUnit(),
												this.wxFoo.getSubscribeId(),
												this.wxFoo.getOnSubscribe(),
												this.wxFoo.getCode(),
												this.wxFoo.getOpenId() },
										new int[] { java.sql.Types.INTEGER,
												java.sql.Types.VARCHAR,
												java.sql.Types.VARCHAR,
												java.sql.Types.VARCHAR,
												java.sql.Types.VARCHAR,
												java.sql.Types.VARCHAR,
												java.sql.Types.VARCHAR,
												java.sql.Types.VARCHAR });
						LOG.info("INSERTed openId: " + this.wxFoo.getOpenId());
						//
						this.parseSogouJsonSite(this.wxFoo.getOpenId());
					} catch (DataAccessException e) {
						// e.printStackTrace();
						LOG.error(e.toString());
					}
				} else {
					LOG.warn("SogouSearchQuery getOpenId Failure! site info:"
							+ wxFoo.getCode());
					// TODO write those info to File or DB for collect which
					// agency not open weixin service
				}
			}

		} catch (IOException e) {
			// e.printStackTrace();
			LOG.error(e.toString());
		}
	}

	public void parseWxUserId() {
		Document doc;
		try {

			// need http protocol
			doc = Jsoup
					.connect(
							GlobalConsts.SOGOU_SEARCH_URL_BASE
									+ wxFoo.getSubscribeId()).get();

			// get page title
			String title = doc.title();
			LOG.debug("title : " + title);
			// get all "微信号:" value of html <span>
			Elements openIdSpans = doc
					.select(GlobalConsts.SOGOU_SEARCH_WX_USER_ID_HTML_ELEMENTS);
			//
			for (Element openIdSpan : openIdSpans) {
				if (openIdSpan.hasText()) {
					if (openIdSpan.text().contains(
							GlobalConsts.SOGOU_SEARCH_WX_USER_ID_KEYWORDS)) {
						// get the value from href attribute
						LOG.debug("openId span text : " + openIdSpan.text());
						// FIXME:过滤同一个订阅号搜到多条结果，默认选择第一个
						if (this.wxFoo.getUserId() == null) {
							this.wxFoo
									.setOpenId(openIdSpan
											.text()
											.split(GlobalConsts.SOGOU_SEARCH_WX_USER_ID_KEYWORDS)[1]);
							LOG.info("saved wxUserId value: "
									+ this.wxFoo.getUserId());
							GlobalVariables.wxFooListWithUserId.add(this.wxFoo);
						}
					}
				}
			}

		} catch (IOException e) {
			// e.printStackTrace();
			LOG.error(e.toString());
		}
	}

	// @see:
	// http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFt_Ri_gqjARIY_shVuqjc3Zo
	public void parseSogouJsonSite(String openId) {
		LOG.info("GlobalVariables.wxFooListWithOpenId before parseSogouJsonSite:"
				+ GlobalVariables.wxFooListWithOpenId.toString());
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		WxSogou wxSogouJson = null;
		try {
			int i = 2;
			String url = GlobalConsts.SOGOU_SEARCH_URL_JSON + openId
					+ "&page=1";
			String content = this.getJsonContent(url);
			System.out.println("1:" + content);
			if (content != null && content.length() > 0) {
				wxSogouJson = mapper.readValue(this.getJsonContent(url),
						WxSogou.class);
				GlobalVariables.openIdWithArticleList.put(openId, wxSogouJson);// Store	 it.
				this.assembleWxfooListWithAritcle(wxSogouJson,openId);

			} else {
				System.err
						.print("Error get info from weixin.sogou.com. URL is :"
								+ url);
			}
			// Thread.sleep(6000);
			long totalPages = wxSogouJson.getTotalPages();

			System.out.println("totalPages:" + totalPages);
			while (i < totalPages + 1) {
				Thread.sleep(2 * 1000);
				content = this
						.getJsonContent(GlobalConsts.SOGOU_SEARCH_URL_JSON
								+ openId + "&page=" + i);
				wxSogouJson = mapper.readValue(content, WxSogou.class);

				this.assembleWxfooListWithAritcle(wxSogouJson,openId);

				GlobalVariables.openIdWithArticleList.put(openId, wxSogouJson);// Store
																				// it.
				// Thread.sleep(6000);
				i++;
			}
			wxSogouJson = null;
			System.out.println(i + ":" + totalPages + ":" + content);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getJsonContent(String urlStr) {
		try {// 获取HttpURLConnection连接对象
			URL url = new URL(urlStr);
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			// 设置连接属性
			httpConn.setConnectTimeout(3000);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod("GET");
			// 获取相应码
			int respCode = httpConn.getResponseCode();
			if (respCode == 200) {
				String a = ConvertStream2Json(httpConn.getInputStream());
				return a.substring(a.indexOf("(") + 1, a.lastIndexOf(")"));
			}
		} catch (MalformedURLException e) {
			LOG.error(e.toString());
		} catch (IOException e) {
			LOG.error(e.toString());
		}
		return "";
	}

	private String ConvertStream2Json(InputStream inputStream) {
		String jsonStr = "";
		// ByteArrayOutputStream相当于内存输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// 将输入流转移到内存输出流中
		try {
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// 将内存流转换为字符串
			jsonStr = new String(out.toByteArray(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	private void assembleWxfooListWithAritcle(WxSogou wxSogou,String openId) {
		LOG.info("wxSogou json result:" + wxSogou.toString());
		//
		ArrayList<WxSogouSimple> titlesUrls = wxSogou.getTitlesUrls();
		for (WxSogouSimple titleUrl : titlesUrls) {
			WxComplexSubscriber subscriber = new WxComplexSubscriber();
			subscriber.setId(this.wxFoo.getId());
			subscriber.setCode(this.wxFoo.getCode());
			subscriber.setStore(this.wxFoo.getStore());
			subscriber.setAgency(this.wxFoo.getAgency());
			subscriber.setUnit(this.wxFoo.getUnit());
			subscriber.setOnSubscribe(this.wxFoo.getOnSubscribe());
			subscriber.setSubscribeId(this.wxFoo.getSubscribeId());
			subscriber.setOpenId(openId);
			subscriber.setArticleTitle(titleUrl.getTitle());
			subscriber.setArticleUrl(titleUrl.getUrl());
			subscriber.setArticleTime(titleUrl.getDate());
			GlobalVariables.wxFooListWithOpenIdArticle.add(subscriber);
			// Save values to DB(wxArticle).
			GlobalVariables.jdbcTempate.update(
					GlobalConsts.JDBC_QUERY_INSERT_OPENID_ARTICLE,
					titleUrl.getDate(), titleUrl.getTitle(), titleUrl.getUrl(),
					openId);
		}
		/*
		 * LOG.info("GlobalVariables.wxFooListWithOpenIdArticle(size): " +
		 * GlobalVariables.wxFooListWithOpenIdArticle.size() + ", raw: " +
		 * GlobalVariables.wxFooListWithOpenIdArticle.toString()); // File
		 * reporting... new
		 * FileReporter(GlobalConsts.REPORT_FILE_OUTPUT_OPENID_ARITICLE,
		 * GlobalVariables.wxFooListWithOpenIdArticle,
		 * FileReporter.REPORTER_TYPE.R_T_OPENID_ARTICLE,
		 * FileReporter.REPORTER_FILE_TYPE.EXCEL).write(); // KJSON API call. if
		 * (GlobalVariables.wxFooListWithOpenIdArticle.size() >= 1) { new
		 * KJsonApiQuery(GlobalVariables.wxFooListWithOpenIdArticle).query();
		 * LOG.debug("KJsonApiQuery processing..." + wxFoo.toString()); }
		 */
	}
}
