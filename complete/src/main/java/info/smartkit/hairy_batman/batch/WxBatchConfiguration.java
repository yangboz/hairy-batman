package info.smartkit.hairy_batman.batch;

import info.smartkit.hairy_batman.config.GlobalConsts;
import info.smartkit.hairy_batman.domain.WxComplexSubscriber;
import info.smartkit.hairy_batman.domain.WxSimpleSubscriber;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

//@Configuration
//@EnableBatchProcessing
// step0:读取村长提供的CSV文件,提取微信订阅号相关信息;
// step1:分析weixin.sogou.com搜索订阅号页面的微信OpenID,例如(http://weixin.sogou.com/weixin?type=1&query=gossipmaker);
// step2:分析基于step1的OpenID组成的JSON结果得到对应的文章标题列表对应并保存,例如(http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFt_Ri_gqjARIY_shVuqjc3Zo)
// step3:读取step2保存的一行结果，请求kjson(将来要自己实现的)API得到对应每一篇文章的阅读数和点赞数并保存,例如(OpenId,[{articleUrl1,readNum1,likeNum1},{articleUrl2,readNum2,likeNum2},...)
// step4:基于step3保存表做数据报表/KPI/统计分析;
public class WxBatchConfiguration
{
    /**
     * @see http://spring.io/blog/2013/08/06/spring-boot-simplifying-spring-for-everyone/
     */

    private static Logger LOG = LogManager.getLogger(WxBatchConfiguration.class);

    // tag::readerwriterprocessor[]
    @Bean
    public ItemReader<WxSimpleSubscriber> reader()
    {
        FlatFileItemReader<WxSimpleSubscriber> reader = new FlatFileItemReader<WxSimpleSubscriber>();
        // PoiItemReader<WxSubscriber> reader = new PoiItemReader<WxSubscriber>();
        // reader.setResource(new ClassPathResource(GlobalConsts.CSV_RESOURCE_FILE_INPUT_XLS));
        reader.setResource(new ClassPathResource(GlobalConsts.RESOURCE_FILE_INPUT_CSV));
        reader.setEncoding("UTF-16");
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<WxSimpleSubscriber>()
        {
            {
                // setLineTokenizer(new DelimitedLineTokenizer(GlobalConsts.CSV_DELIMITED_LINE_TOKENIZER)
                setLineTokenizer(new DelimitedLineTokenizer(DelimitedLineTokenizer.DELIMITER_TAB)
                {
                    {
                        setNames(GlobalConsts.CSV_COLUMNS_NAME_SIMPLE);
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<WxSimpleSubscriber>()
                {
                    {
                        setTargetType(WxComplexSubscriber.class);
                    }
                });
            }
        });
        //
        return reader;
    }

    @Bean
    public ItemProcessor<WxSimpleSubscriber, WxSimpleSubscriber> processor()
    {
        return new WxFooItemProcessor();
    }

    @Bean
    public ItemWriter<WxSimpleSubscriber> writer(DataSource dataSource)
    {
        LOG.debug("GlobalConsts.QUERY_COLUMNS_LABEL: " + GlobalConsts.QUERY_COLUMNS_LABEL);
        JdbcBatchItemWriter<WxSimpleSubscriber> writer = new JdbcBatchItemWriter<WxSimpleSubscriber>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<WxSimpleSubscriber>());
        writer.setSql("INSERT INTO " + GlobalConsts.QUERY_TABLE_NAME_BASIC + "(" + GlobalConsts.QUERY_COLUMNS_NAME
            + ") VALUES (:" + GlobalConsts.QUERY_COLUMNS_LABEL + ")");
        writer.setDataSource(dataSource);
        return writer;
    }

    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job csvImportWxFooJob(JobBuilderFactory jobs, Step s1)
    {
        return jobs.get("csvImportWxFooJob").incrementer(new RunIdIncrementer()).flow(s1).end().build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<WxSimpleSubscriber> reader,
        ItemWriter<WxSimpleSubscriber> writer, ItemProcessor<WxSimpleSubscriber, WxSimpleSubscriber> processor)
    {
        return stepBuilderFactory.get("step1").<WxSimpleSubscriber, WxSimpleSubscriber>chunk(2).reader(reader)
            .processor(processor).build();
    }
}
