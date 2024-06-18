package br.com.postech.techchallenge.microservico.pedido.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class DatabaseConfig {

	@Value("${spring.datasource.admin.url}")
	private String adminUrl;

	@Value("${spring.datasource.admin.username}")
	private String adminUsername;

	@Value("${spring.datasource.admin.password}")
	private String adminPassword;

	@Value("${spring.datasource.url}")
	private String dataSourceUrl;

	@Value("${spring.datasource.username}")
	private String dataSourceUsername;

	@Value("${spring.datasource.password}")
	private String dataSourcePassword;
	
	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	@Bean
	@ConditionalOnMissingBean(name = "adminDataSource")
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public DataSource adminDataSource() {
		return DataSourceBuilder.create().url(adminUrl).username(adminUsername).password(adminPassword)
				.driverClassName(driverClassName).build();
	}

	@Bean
	@DependsOn("dataSourceInitializer") // Garantir que o flyway dependa do DataSourceInitializer
	public Flyway flyway() {
		DataSource dataSource = DataSourceBuilder.create().url(dataSourceUrl).username(dataSourceUsername)
				.password(dataSourcePassword).driverClassName(driverClassName).build();

		Flyway flyway = Flyway.configure().dataSource(dataSource)
				.locations("classpath:db/migration", "classpath:db/devdata").baselineOnMigrate(true).schemas("public")
				.load();

		flyway.migrate();

		return flyway;
	}

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE + 1)
	public DataSourceInitializer dataSourceInitializer(@Qualifier("adminDataSource") DataSource adminDataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(adminDataSource);

		// Verificar se o banco de dados existe
		Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM pg_database WHERE datname = 'pedidos'",
				Integer.class);

		// Se o banco de dados não existir, cria-lo
		if (count != null && count == 0) {
			jdbcTemplate.execute("CREATE DATABASE pedidos");
		}

		// Configurar o DataSourceInitializer corretamente
		DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(adminDataSource);
		return initializer;
	}
}
