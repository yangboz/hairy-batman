# hairy-batman

Spring-boot batch processing with Nutch technology.

# References:
==============
https://spring.io/guides/gs/batch-processing/

http://www.kjson.com/weixin/bind

https://spring.io/guides/gs/consuming-rest/

# LogStash
=============
https://www.digitalocean.com/community/tutorials/how-to-use-logstash-and-kibana-to-centralize-logs-on-centos-6

# Notes:
==============

1min=60c * 20urls = 1200 

10000 weixin openID * 20 /1200 = 167 min (3 java instance)

API account: 4 

========
1,openid,....
1,openid,[
url1,url2,
]

=======

// step0:读取村长提供的CSV文件,提取微信订阅号相关信息;
// step1:分析weixin.sogou.com搜索订阅号页面的微信OpenID,例如(http://weixin.sogou.com/weixin?type=1&query=gossipmaker);
// step2:分析基于step1的OpenID组成的JSON结果得到对应的文章标题列表对应并保存,例如(http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFt_Ri_gqjARIY_shVuqjc3Zo)
// step3:读取step2保存的一行结果，请求kjson(将来要自己实现的)API得到对应每一篇文章的阅读数和点赞数并保存,例如(OpenId,[{articleUrl1,readNum1,likeNum1},{articleUrl2,readNum2,likeNum2},...)
// step4:基于step3保存表做数据报表/KPI/统计分析;
