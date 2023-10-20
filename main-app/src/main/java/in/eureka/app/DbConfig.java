package in.eureka.app;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = {
		"in.eureka.jpa.repository" })
@SuppressWarnings({ "rawtypes", "unchecked" })
@Configuration
public class DbConfig {
	/** */
	private static final Logger logger = LoggerFactory.getLogger(DbConfig.class);
	/** */
	private String[] packagesToScan = { "in.eureka.jpa.repository", "in.eureka.jpa.entity" };
	/** */
	private static String persistenceUnit = "gogodbunit";

	private boolean genDDl = true;

	private boolean showSql = true;

	private String hibernateDialect = "org.hibernate.dialect.MySQL8Dialect";

	@Value("${spring.driver.class.name}")
	private String driverClassName;

	@Bean(name = "gogoDatasource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource gogoEntityDataSource() {
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		DataSource dataSource = dataSourceBuilder.build();
		org.apache.tomcat.jdbc.pool.DataSource tomcatDatasource = (org.apache.tomcat.jdbc.pool.DataSource) dataSource;

		PoolConfiguration poolConfiguration = tomcatDatasource.getPoolProperties();
		if (logger.isDebugEnabled()) {
			logger.debug("gogodb poolConfiguration:" + poolConfiguration);
		}
		poolConfiguration.setInitialSize(3);
		poolConfiguration.setTestWhileIdle(true);
		poolConfiguration.setMaxIdle(5);
		poolConfiguration.setMinIdle(3);
		poolConfiguration.setValidationQuery("SELECT 1");
		poolConfiguration.setTimeBetweenEvictionRunsMillis(1500000);

		if (!StringUtils.isEmpty(driverClassName)) {
			poolConfiguration.setDriverClassName(driverClassName);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("gogodb poolConfiguration:" + tomcatDatasource.getPoolProperties());
		}
		return dataSource;
	}

	@Bean(name = "gogoJpaVendor")
	public JpaVendorAdapter gogoJpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setGenerateDdl(genDDl);
		jpaVendorAdapter.setShowSql(showSql);
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setDatabasePlatform(hibernateDialect);
		return jpaVendorAdapter;
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource gogoDatasource,
			JpaVendorAdapter gogoJpaVendor) {
		Map jpaProperties = gogoJpaVendor.getJpaPropertyMap();
		jpaProperties.put(Environment.IMPLICIT_NAMING_STRATEGY, "in.eureka.app.CustomImplicitNamingStrategy");
		// jpaProperties.put(Environment.PHYSICAL_NAMING_STRATEGY,
		// "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
		jpaProperties.put(Environment.PHYSICAL_NAMING_STRATEGY, "in.eureka.app.CustomPhysicalNamingStrategy");
		EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(gogoJpaVendor, jpaProperties, null);
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = builder.dataSource(gogoDatasource)
				.packages(packagesToScan).persistenceUnit(persistenceUnit).build();
		return entityManagerFactoryBean;
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager gogoTransactionManager(
			@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory.getObject());
	}

}
