package info.smartkit.hairy_batman;

import info.smartkit.hairy_batman.domain.WxFoo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@ComponentScan
@EnableAutoConfiguration
public class Application
{

    private static final String WX_API = "http://www.kjson.com/weixin/api?key=45cfa3defbebeaab767517d2339b57e5";

    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        List<WxFoo> results =
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

        for (WxFoo wxFoo : results) {
            System.out.println("Found <" + wxFoo + "> in the database.");
        }
    }

}
