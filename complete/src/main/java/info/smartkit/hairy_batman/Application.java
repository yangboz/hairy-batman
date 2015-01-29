package info.smartkit.hairy_batman;

import info.smartkit.hairy_batman.config.GlobalConsts;
import info.smartkit.hairy_batman.domain.WxFoo;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import au.com.bytecode.opencsv.CSVWriter;

@ComponentScan
@EnableAutoConfiguration
public class Application
{
    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        // Spring-batch reading CSV testing.
        List<WxFoo> batch_results =
            ctx.getBean(JdbcTemplate.class).query(
                "SELECT " + GlobalConsts.QUERY_COLUMNS_NAME + " FROM " + GlobalConsts.QUERY_TABLE_NAME,
                new RowMapper<WxFoo>()
                {
                    @Override
                    public WxFoo mapRow(ResultSet rs, int row) throws SQLException
                    {
                        return new WxFoo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs
                            .getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs
                            .getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14));
                    }
                });

        for (WxFoo wxFoo : batch_results) {
            System.out.println("Found <" + wxFoo + "> in the database.");
        }
        // CSVWriter
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(GlobalConsts.CSV_RESOURCE_FILE_OUTPUT, true));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        // for (WxFoo elem : batch_results) {
        // System.out.println("elem.toStringArray():" + elem.toStringArray());
        // writer.writeNext(elem.toStringArray());
        // }

        List<String[]> allElements = new ArrayList<String[]>();
        for (WxFoo elem : batch_results) {
            System.out.println("elem.toStringArray():" + elem.toStringArray());
            allElements.add(elem.toStringArray());
        }
        writer.writeAll(allElements);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }
}
