package com.zbin.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;

import com.zbin.ds.DataSourceNames;
import com.zbin.ds.DynamicDataSource;
import com.zbin.ds.MultipleDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.*;

/**
 * Created by zhenglibin on 2018/6/26.
 */
@Configuration
public class DatasourceConfig implements BeanDefinitionRegistryPostProcessor,InitializingBean,ApplicationContextAware {

    @Bean
    public MultipleDataSource multipleDataSource() {
        return new MultipleDataSource(Lists.newArrayList(DataSourceNames.DS1, DataSourceNames.DS2,DataSourceNames.MAIN));
}

    private ApplicationContext applicationContext;



    @Bean("dynamicDataSource")
    public AbstractRoutingDataSource dynamicDataSource(MultipleDataSource multipleDataSource) {
        DynamicDataSource dynamicRoutingDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(4);

        String ds;
        DruidDataSource dataSource;
        for(Iterator var6 = multipleDataSource.getDataSources().iterator(); var6.hasNext(); dataSourceMap.put(ds, dataSource)) {
            ds = (String)var6.next();
            dataSource = (DruidDataSource)this.applicationContext.getBean(ds, DruidDataSource.class);
            if(ds.equalsIgnoreCase(multipleDataSource.getDefaultDataSource())) {
                dynamicRoutingDataSource.setDefaultTargetDataSource(dataSource);
            }
            dataSourceMap.put(DataSourceNames.DS1,dataSource);
        }


        // 将 master 和 slave 数据源作为指定的数据源
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        System.out.println("初始化动态数据源"+dataSourceMap);
        return dynamicRoutingDataSource;
    }

    @Bean(
            name = {"sqlSessionFactory"}
    )
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(AbstractRoutingDataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dynamicDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mapping/*.xml"));
        List<Interceptor> interceptors = new ArrayList();
        Properties properties = new Properties();
        properties.put("dialect", "sqlserver");
        sessionFactory.setConfigurationProperties(properties);
        return sessionFactory.getObject();
    }

    @Bean(
            name = {"transactionManager"}
    )
    @ConditionalOnMissingBean
    public PlatformTransactionManager transactionManager(AbstractRoutingDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        MultipleDataSource multipleDataSource = (MultipleDataSource)this.applicationContext.getBean(MultipleDataSource.class);
        Set<String> dataSources = multipleDataSource.getDataSources();
        String defaultDataSource = multipleDataSource.getDefaultDataSource();

        String ds;
        BeanDefinitionBuilder builder;
        for(Iterator var5 = dataSources.iterator(); var5.hasNext(); beanDefinitionRegistry.registerBeanDefinition(ds, builder.getBeanDefinition())) {
            ds = (String)var5.next();
        builder = BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class);
        if(ds.equalsIgnoreCase(defaultDataSource)) {
            builder.getBeanDefinition().setPrimary(true);
        }
    }
        System.out.println("数据源bean邦定");
    }

    @Bean(
            name = {"multipleDataSourceInitializer"}
    )
    public Boolean multipleDataSourceInitializer(MultipleDataSource multipleDataSource) throws Exception {
        Iterator var3 = multipleDataSource.getDataSources().iterator();

        while(var3.hasNext()) {
            String ds = (String)var3.next();
            DruidDataSource dataSource = (DruidDataSource)this.applicationContext.getBean(ds, DruidDataSource.class);
            Environment environment = (Environment)this.applicationContext.getBean(Environment.class);

            String url = environment.getProperty(ds + ".url");
            String username = environment.getProperty(ds + ".username");
            String password = environment.getProperty(ds + ".password");
            String driverClass = environment.getProperty(ds + ".driver-class-name");
            dataSource.setDriverClassName(driverClass);
            dataSource.setPassword(password);
            dataSource.setUrl(url);
            dataSource.setUsername(username);

            this.initDruidDataSource(dataSource);
        }
        System.out.println("数据源初始化");
        return Boolean.TRUE;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    private void initDruidDataSource(DruidDataSource dataSource) {
        try {
            if(dataSource.getMaxActive() == 8 || dataSource.getMaxActive() == 5) {
                dataSource.setMaxActive(100);
            }

            if(dataSource.getInitialSize() == 0 || dataSource.getInitialSize() == 1) {
                dataSource.setInitialSize(10);
            }

            if(dataSource.getMinIdle() == 0) {
                dataSource.setMinIdle(10);
            }

            if(!dataSource.isPoolPreparedStatements()) {
                dataSource.setMaxPoolPreparedStatementPerConnectionSize(5);
            }

            if(dataSource.getMaxWait() < 0L || dataSource.getMaxWait() > 5000L) {
                dataSource.setMaxWait(5000L);
            }

            if(dataSource.getValidationQuery() == null) {
                dataSource.setValidationQuery("SELECT 'x'");
            }
        } catch (Exception var3) {

        }

    }
}
