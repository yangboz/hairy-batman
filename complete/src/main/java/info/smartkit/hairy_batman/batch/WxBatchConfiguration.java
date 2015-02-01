package info.smartkit.hairy_batman.batch;

import info.smartkit.hairy_batman.config.GlobalConsts;
import info.smartkit.hairy_batman.domain.WxFoo;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableBatchProcessing
// step1:分析weixin.sogou.com搜索订阅号页面的微信OpenID,例如(http://weixin.sogou.com/weixin?type=1&query=gossipmaker)
// step2:分析基于step1的OpenID组成的JSON结果得到对应的文章标题列表对应并保存,例如(http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFt_Ri_gqjARIY_shVuqjc3Zo)
// step3:读取step2保存的一行结果，请求kjson
// API得到对应每一篇文章的阅读数和点赞数并保存,例如(OpenId,[{articleUrl1,readNum1,likeNum1},{articleUrl2,readNum2,likeNum2},...)
// step4:基于step3保存表做数据分析;
@PropertySource("classpath:application.properties")
public class WxBatchConfiguration
{
    /*
     * Load the properties
     */
    @Value("${database.driver}")
    private String databaseDriver;

    @Value("${database.url}")
    private String databaseUrl;

    @Value("${database.username}")
    private String databaseUsername;

    @Value("${database.password}")
    private String databasePassword;

    // tag::readerwriterprocessor[]
    @Bean
    public ItemReader<WxFoo> reader()
    {
        FlatFileItemReader<WxFoo> reader = new FlatFileItemReader<WxFoo>();
        reader.setResource(new ClassPathResource(GlobalConsts.CSV_RESOURCE_FILE_INPUT));
        reader.setLineMapper(new DefaultLineMapper<WxFoo>()
        {
            {
                setLineTokenizer(new DelimitedLineTokenizer(GlobalConsts.CSV_DELIMITED_LINE_TOKENIZER)
                {
                    {
                        // System.out.println("GlobalConsts.CSV_COLUMNS_NAME: " + GlobalConsts.CSV_COLUMNS_NAME);
                        setNames(GlobalConsts.CSV_COLUMNS_NAME);
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<WxFoo>()
                {
                    {
                        setTargetType(WxFoo.class);
                    }
                });
            }
        });
        return reader;
    }

    @Bean
    public ItemProcessor<WxFoo, WxFoo> processor()
    {
        return new WxFooItemProcessor();
    }

    @Bean
    public ItemWriter<WxFoo> writer(DataSource dataSource)
    {
        System.out.println("GlobalConsts.QUERY_COLUMNS_LABEL: " + GlobalConsts.QUERY_COLUMNS_LABEL);
        JdbcBatchItemWriter<WxFoo> writer = new JdbcBatchItemWriter<WxFoo>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<WxFoo>());
        writer.setSql("INSERT INTO " + GlobalConsts.QUERY_TABLE_NAME + "(" + GlobalConsts.QUERY_COLUMNS_NAME
            + ") VALUES (:" + GlobalConsts.QUERY_COLUMNS_LABEL + ")");
        writer.setDataSource(dataSource);
        return writer;
    }

    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job importWxFooJob(JobBuilderFactory jobs, Step s1)
    {
        return jobs.get("importWxFooJob").incrementer(new RunIdIncrementer()).flow(s1).end().build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<WxFoo> reader, ItemWriter<WxFoo> writer,
        ItemProcessor<WxFoo, WxFoo> processor)
    {
        return stepBuilderFactory.get("step1").<WxFoo, WxFoo>chunk(10).reader(reader).processor(processor)
            .writer(writer).build();
    }

    // @Bean
    // public Step step2(StepBuilderFactory stepBuilderFactory, ItemReader<WxFoo> reader, ItemWriter<WxFoo> writer,
    // ItemProcessor<WxFoo, WxFoo> processor)
    // {
    // return stepBuilderFactory.get("step2").<WxFoo, WxFoo>chunk(10).reader(reader).processor(processor)
    // .writer(writer).build();
    // }

    // end::jobstep[]

    // @Bean
    // public DataSource dataSource()
    // {
    // DriverManagerDataSource dataSource = new DriverManagerDataSource();
    // dataSource.setDriverClassName(databaseDriver);
    // dataSource.setUrl(databaseUrl);
    // dataSource.setUsername(databaseUsername);
    // dataSource.setPassword(databasePassword);
    // return dataSource;
    // }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource)
    {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }

}
