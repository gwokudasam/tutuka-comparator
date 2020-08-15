package com.tutuka.transaction.comparator.config;

import com.tutuka.transaction.comparator.dal.model.EntityMarker;
import com.tutuka.transaction.comparator.dal.repository.RepositoryMarker;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Application Configuration
 * */

@ComponentScan
@EntityScan(basePackageClasses = EntityMarker.class)
@EnableJpaRepositories(basePackageClasses = RepositoryMarker.class)
public class AppConfig {
}
