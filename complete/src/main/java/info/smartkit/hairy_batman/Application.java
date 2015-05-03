package info.smartkit.hairy_batman;

import info.smartkit.hairy_batman.config.GlobalVariables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@ComponentScan
@PropertySources({@PropertySource(value = "classpath:application-${spring.profiles.active}.properties")})
@EnableAutoConfiguration
// @SpringBootApplication
public class Application
{
    private static Logger LOG = LogManager.getLogger(Application.class);

    public static void main(String[] args)
    {
        GlobalVariables.appContext = SpringApplication.run(Application.class, args);
        /*
         * LOG.info("Weixin smartkit beginning at :" + new Date().getTime()); // Check the openId storage results:
         * LOG.info("GlobalVariables.wxFooListWithOpenId(size):" + GlobalVariables.wxFooListWithOpenId.size()); //
         * Spring-batch reading CSV testing. List<WxComplexSubscriber> batch_results =
         * GlobalVariables.appContext.getBean(JdbcTemplate.class).query( "SELECT " + GlobalConsts.QUERY_COLUMNS_NAME +
         * " FROM " + GlobalConsts.QUERY_TABLE_NAME_BASIC // + " WHERE openId!='null'", new
         * RowMapper<WxComplexSubscriber>() , new RowMapper<WxComplexSubscriber>() {
         * @Override public WxComplexSubscriber mapRow(ResultSet rs, int row) throws SQLException { WxComplexSubscriber
         * subscriber = new WxComplexSubscriber(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs
         * .getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs .getString(10),
         * rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14)); return subscriber; } });
         * LOG.info("Processed item size:" + batch_results.size());
         */
    }

}
