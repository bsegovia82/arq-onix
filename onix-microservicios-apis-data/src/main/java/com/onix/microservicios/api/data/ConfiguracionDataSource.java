package com.onix.microservicios.api.data;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration  
//@PropertySources({ 
//	@PropertySource("classpath:${app.archivodatasource}") 
//	})
public class ConfiguracionDataSource {
	
	private static final String DB_DRIVER_CLASS = "datasource.db.driver";
	private static final String DB_PASSWORD = "datasource.db.password";
	private static final String DB_URL = "datasource.db.url";
	private static final String DB_USER = "datasource.db.username";
	private static final String POOL_MAX_SIZE = "datasource.pool.maxsize";
	private static final String POOL_CONEXIONTIMEUP = "datasource.pool.conexiontimeup";
	private static final String POOL_IDLETIMEOUT = "datasource.pool.idletimeout";
	private static final String POOL_MAXLIFETIME = "datasource.pool.maxlifetime";
	private static final String POOL_INITIALIZATIONFAILFAST = "datasource.pool.initializationfailfast";
	private static final String POOL_CONNECTIONTESTQUERY = "datasource.pool.connectiontestquery";
	private static final String POOL_MINIMUMIDLE = "datasource.pool.minimumidle";
	private static final String POOL_CACHEPREPSTMTS = "datasource.pool.cachePrepStmts";

	private static final String POOL_PREPSTMTCACHESIZE = "datasource.pool.prepStmtCacheSize";
	private static final String POOL_PREPSTMTCACHESQLLIMIT = "datasource.pool.prepStmtCacheSqlLimit";

	private static final String CACHEPREPSTMTS = "cachePrepStmts";
	private static final String PREPSTMTCACHESIZE = "prepStmtCacheSize";
	private static final String PREPSTMTCACHESQLLIMIT = "prepStmtCacheSqlLimit";
	
	
	@Bean(destroyMethod = "close")
	DataSource dataSource(Environment env) {
		HikariConfig configuracion = new HikariConfig();
		configuracion.setDriverClassName(env.getRequiredProperty(DB_DRIVER_CLASS));
		configuracion.setJdbcUrl(env.getRequiredProperty(DB_URL));
		configuracion.setUsername(env.getRequiredProperty(DB_USER));
		configuracion.setPassword(env.getRequiredProperty(DB_PASSWORD));
		configuracion.setPoolName(env.getRequiredProperty(DB_USER));
		configuracion.setMaximumPoolSize(Integer.parseInt(env.getRequiredProperty(POOL_MAX_SIZE)));
		configuracion.setConnectionTimeout(Long.parseLong(env.getRequiredProperty(POOL_CONEXIONTIMEUP)));
		configuracion.setIdleTimeout(Long.parseLong(env.getRequiredProperty(POOL_IDLETIMEOUT)));
		configuracion.setMaxLifetime(Long.parseLong(env.getRequiredProperty(POOL_MAXLIFETIME)));
		configuracion
				.setInitializationFailFast(Boolean.parseBoolean(env.getRequiredProperty(POOL_INITIALIZATIONFAILFAST)));
		configuracion.setConnectionTestQuery(env.getRequiredProperty(POOL_CONNECTIONTESTQUERY));
		configuracion.setMinimumIdle(Integer.parseInt(env.getRequiredProperty(POOL_MINIMUMIDLE)));//
		configuracion.addDataSourceProperty(CACHEPREPSTMTS,
				Boolean.parseBoolean(env.getRequiredProperty(POOL_CACHEPREPSTMTS)));
		configuracion.addDataSourceProperty(PREPSTMTCACHESIZE, env.getRequiredProperty(POOL_PREPSTMTCACHESIZE));
		configuracion.addDataSourceProperty(PREPSTMTCACHESQLLIMIT, env.getRequiredProperty(POOL_PREPSTMTCACHESQLLIMIT));
		return new HikariDataSource(configuracion);
	}

}
