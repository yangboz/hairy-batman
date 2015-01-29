package info.smartkit.hairy_batman;

import info.smartkit.hairy_batman.config.GlobalConsts;
import info.smartkit.hairy_batman.domain.WxFoo;
import info.smartkit.hairy_batman.plain.WxBar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@ComponentScan
@EnableAutoConfiguration
public class Application
{
    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        // KJSON API testing using RestTemplate.
        RestTemplate restTemplate = new RestTemplate();
        // restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // @see:
        // http://stackoverflow.com/questions/22329368/spring-android-rest-template-parse-json-data-with-content-type-text-html
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_HTML));
        restTemplate.getMessageConverters().add(converter);
        // Spring batch for CSV reading.
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add(
            "urls",
            "http://mp.weixin.qq.com/s?__biz=MjM5ODE4MTUzMg==&mid=202895379&idx=1&sn=a46187dd2e3fc704b72277dbf863f356&3rd=MzA3MDU4NTYzMw==&scene=6#rd");
        //
        WxBar api_results = restTemplate.postForObject(GlobalConsts.KJSON_API, map, WxBar.class);
        // WxBar returns = restTemplate.getForObject(GlobalConsts.KJSON_API, WxBar.class);
        System.out.println("Results:  " + api_results.toString());

        // Spring-batch reading CSV testing.
        List<WxFoo> batch_results =
            ctx.getBean(JdbcTemplate.class)
                .query(
                    "SELECT code, store,manager,agency,unit,onSubscribe,subscribe,followSubscribe,onService,service,followService FROM wxfoo",
                    new RowMapper<WxFoo>()
                    {
                        @Override
                        public WxFoo mapRow(ResultSet rs, int row) throws SQLException
                        {
                            return new WxFoo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs
                                .getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs
                                .getString(10), rs.getString(11));
                        }
                    });

        for (WxFoo wxFoo : batch_results) {
            System.out.println("Found <" + wxFoo + "> in the database.");
        }
    }
}
