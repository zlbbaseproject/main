package com.zbin.ds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhenglibin on 2018/6/27.
 */
public class MultipleDataSource  implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MultipleDataSource.class);
    private Set<String> dataSources;
    private String defaultDataSource;
    private Set<String> startupNeededDataSources;

    public Set<String> getDataSources() {
        return dataSources;
    }

    public void setDataSources(Set<String> dataSources) {
        this.dataSources = dataSources;
    }

    public String getDefaultDataSource() {
        return defaultDataSource;
    }

    public void setDefaultDataSource(String defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    public Set<String> getStartupNeededDataSources() {
        return startupNeededDataSources;
    }

    public void setStartupNeededDataSources(Set<String> startupNeededDataSources) {
        this.startupNeededDataSources = startupNeededDataSources;
    }

    public MultipleDataSource(List<String> dataSources) {
        this.dataSources = new HashSet(dataSources);
        this.defaultDataSource = (String)dataSources.get(0);
    }
    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
