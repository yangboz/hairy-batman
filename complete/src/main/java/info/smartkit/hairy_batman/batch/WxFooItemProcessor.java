package info.smartkit.hairy_batman.batch;

import info.smartkit.hairy_batman.config.GlobalConsts;
import info.smartkit.hairy_batman.config.GlobalVariables;
import info.smartkit.hairy_batman.domain.WxComplexSubscriber;
import info.smartkit.hairy_batman.domain.WxSimpleSubscriber;
import info.smartkit.hairy_batman.plain.WxSogou;
import info.smartkit.hairy_batman.query.SogouSearchQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

public class WxFooItemProcessor implements
		ItemProcessor<WxSimpleSubscriber, WxSimpleSubscriber> {
	private static Logger LOG = LogManager.getLogger(WxFooItemProcessor.class);

	@Override
	public WxSimpleSubscriber process(final WxSimpleSubscriber wxFoo)
			throws Exception {
		LOG.info("WxFooItemProcessor processing..." + wxFoo);
		if (!(wxFoo.getSubscribeId().equalsIgnoreCase("NULL")||wxFoo.getSubscribeId().indexOf("NULL")!=-1)) {

			SogouSearchQuery sogouSearchQuery = new SogouSearchQuery(
					new WxComplexSubscriber(wxFoo.getId(), wxFoo.getCode(),
							wxFoo.getStore(), wxFoo.getAgency(),
							wxFoo.getUnit(), wxFoo.getOnSubscribe(),
							wxFoo.getSubscribeId(), null, null, null, null,
							null, null, null));
			JdbcTemplate jdbcTempate = GlobalVariables.jdbcTempate;
			// TODO:读取excel， 读取db 4s店基础信息表
			// 根据 code，分析OpenId
			// 如果有差异，更新差异，去除不要的，新增新加的 （Thead Sleep 10s）
			// 如果没有差异，继续
			// GlobalVariables.jdbcTempate.execute(sql);
			// LOG.debug("SogouSearchQuery processing..." + wxFoo);
			if (wxFoo != null && wxFoo.getSubscribeId() != "NULL") {
				String dbOpenId;
				try {

					dbOpenId = (String) jdbcTempate.queryForObject(
							"SELECT openId FROM "
									+ GlobalConsts.QUERY_TABLE_NAME_BASIC
									+ " WHERE code= ?",
							new Object[] { wxFoo.getCode() },
							java.lang.String.class);
				} catch (Exception e) {
					LOG.info("get dbOpenId is NULL,goto SogouSearchQuery");
					dbOpenId = null;
				}

				// avoid antispider from weixin.sogou.com
				Thread.sleep(2 * 1000);
				long count = 0;
				if (dbOpenId != null) {
					// clear article info TODO when online
					count = (Long) jdbcTempate.queryForObject(
							"SELECT count(*) FROM "
									+ GlobalConsts.QUERY_TABLE_NAME_ARTICLE
									+ " WHERE openId= ?",
							new Object[] { dbOpenId }, java.lang.Long.class);
					String url = GlobalConsts.SOGOU_SEARCH_URL_JSON
							+ dbOpenId + "&page=1";
					String content = sogouSearchQuery.getJsonContent(url);
					System.out.println("1:" + content);
					long totalArticles = 0;
					if (content != null && content.length() > 0) {
						try {
							ObjectMapper mapper = new ObjectMapper();
							WxSogou wxSogouJson = null;
							wxSogouJson = mapper.readValue(content,
									WxSogou.class);
							totalArticles = wxSogouJson.getTotalItems();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else {
						System.err
								.print("Error get info from weixin.sogou.com. URL is :"
										+ url);
					}

					// recollect article info
					if (count != totalArticles) {
						jdbcTempate.execute("delete from wxarticle where openid='"+dbOpenId+"'");
						sogouSearchQuery.parseSogouJsonSite(dbOpenId);
					}
				} else {
					// collect article info for the first time
					sogouSearchQuery.parseWxOpenId();
				}
			} else {
				LOG.warn("wxFoo.getSubscribeId() is NULL.");
			}
			// new KJsonApiQuery(wxFoo).query();
			// LOG.debug("KJsonApiQuery processing..." + wxFoo);
			// TODO:JobLauncher start here.
			// TaskletStep taskletStep1 = new TaskletStep("step_parseWxOpenId");
			// Tasklet tasklet_parseWxOpenId = new TaskParseWxOpenId();
			// taskletStep1.setTasklet(tasklet_parseWxOpenId);
			// //
			// SimpleJob simpleJob = new SimpleJob("simpleJob");
			// simpleJob.addStep(taskletStep1);
			// SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
			// // JobLauncher jobLauncher =
			// GlobalVariables.appContext.getBean(JobLauncher.class);
			// JobParameters jobParameters = new
			// JobParametersBuilder().addDate("date", new
			// Date()).toJobParameters();
			// // jobLauncher.run(simpleJob, jobParameters);
			// simpleJobLauncher.run(simpleJob, jobParameters);
			//
		}
		return wxFoo;
	}
}