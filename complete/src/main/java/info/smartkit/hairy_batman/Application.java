package info.smartkit.hairy_batman;

import info.smartkit.hairy_batman.domain.Person;

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

        List<Person> results =
            ctx.getBean(JdbcTemplate.class).query("SELECT first_name, last_name FROM people", new RowMapper<Person>()
            {
                @Override
                public Person mapRow(ResultSet rs, int row) throws SQLException
                {
                    return new Person(rs.getString(1), rs.getString(2));
                }
            });

        for (Person person : results) {
            System.out.println("Found <" + person + "> in the database.");
        }

        // List<WxFoo> results2 =
        // ctx.getBean(JdbcTemplate.class)
        // .query(
        // "SELECT code, store,manager,agency,unit,onSubscribe,subscribe,followSubscribe,onService,service,followService FROM wxfoo",
        // new RowMapper<WxFoo>()
        // {
        // @Override
        // public WxFoo mapRow(ResultSet rs, int row) throws SQLException
        // {
        // return new WxFoo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs
        // .getString(5), Boolean.valueOf(rs.getString(6)), rs.getString(7), Long.parseLong(rs
        // .getString(8)), Boolean.valueOf(rs.getString(9)), rs.getString(10), Long.parseLong(rs
        // .getString(11)));
        // }
        // });
        //
        // for (WxFoo wxFoo : results2) {
        // System.out.println("Found <" + wxFoo + "> in the database.");
        // }
    }

}
