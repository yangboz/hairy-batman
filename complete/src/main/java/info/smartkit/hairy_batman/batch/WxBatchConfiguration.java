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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableBatchProcessing
public class WxBatchConfiguration
{

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

    // end::jobstep[]

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource)
    {
        return new JdbcTemplate(dataSource);
    }

}
