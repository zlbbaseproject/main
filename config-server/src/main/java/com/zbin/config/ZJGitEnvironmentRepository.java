package com.zbin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.server.environment.JGitEnvironmentProperties;
import org.springframework.cloud.config.server.environment.JGitEnvironmentRepository;
import org.springframework.cloud.config.server.environment.NoSuchRepositoryException;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: if no config secure-group ,  there is no exception.
 * 与MultipleJGitEnvironmentRepository的区别，此类更精简。
 * Author:  suwenlong
 * Date: 2018/5/23 10:06
**/

public class ZJGitEnvironmentRepository extends JGitEnvironmentRepository {

    private static final Logger logger = LoggerFactory.getLogger(ZJGitEnvironmentRepository.class);

    private Map<String, JGitEnvironmentRepository> placeholders = new LinkedHashMap<>();


    public ZJGitEnvironmentRepository(ConfigurableEnvironment environment, JGitEnvironmentProperties props) {
        super(environment,props);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }

    @Override
    public Environment findOne(String application, String profile, String label) {

        try{
            JGitEnvironmentRepository candidate = getRepository(this, application, profile,
                    label);
            if (label == null) {
                label = candidate.getDefaultLabel();
            }
            if (candidate == this) {
                return super.findOne(application, profile, label);
            }
            return candidate.findOne(application, profile, label);
        }catch(Exception e){
            String message = e.getMessage();
            if(e instanceof NoSuchRepositoryException && message != null &&
                    (message.contains("resource-config") || message.contains("-resource"))) {

                logger.warn("Config-Resource-Group-Exception| Resource group not exist . application:{}, e:{}",application, e.toString());
                return new Environment(application, profile);
            } else {
                throw e;
            }
        }
    }

    @Override
    public Locations getLocations(String application, String profile, String label) {
        JGitEnvironmentRepository candidate = getRepository(this, application, profile,
                label);
        if (candidate == this) {
            return super.getLocations(application, profile, label);
        }
        return candidate.getLocations(application, profile, label);
    }

    private List<JGitEnvironmentRepository> getRepositories(
            JGitEnvironmentRepository repository, String application, String profile,
            String label) {
        List<JGitEnvironmentRepository> list = new ArrayList<>();
        String[] profiles = profile == null ? new String[] { null }
                : org.springframework.util.StringUtils.commaDelimitedListToStringArray(profile);
        for (int i = profiles.length; i-- > 0;) {
            list.add(getRepository(repository, application, profiles[i], label));
        }
        return list;
    }

    private JGitEnvironmentRepository getRepository(JGitEnvironmentRepository repository,
                                                    String application, String profile, String label) {
        if (!repository.getUri().contains("{")) {
            return repository;
        }
        String key = repository.getUri();

        if (key.contains("{label}") && label == null) {
            label = repository.getDefaultLabel();
        }

        if (application != null) {
            key = key.replace("{application}", application);
        }
        if (profile != null) {
            key = key.replace("{profile}", profile);
        }
        if (label != null) {
            key = key.replace("{label}", label);
        }
        if(!this.placeholders.containsKey(key)) {
            this.placeholders.put(key, this.getRepository(repository, key));
        }

        return (JGitEnvironmentRepository)this.placeholders.get(key);
    }

    private JGitEnvironmentRepository getRepository(JGitEnvironmentRepository source, String uri) {
        JGitEnvironmentRepository repository = new JGitEnvironmentRepository((ConfigurableEnvironment)null, new JGitEnvironmentProperties());
        File basedir = repository.getBasedir();
        BeanUtils.copyProperties(source, repository);
        repository.setUri(uri);
        repository.setBasedir(new File(source.getBasedir().getParentFile(), basedir.getName()));
        return repository;
    }

    @Override
    public void setOrder(int order) {
        super.setOrder(order);
    }

}
