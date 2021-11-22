package id.co.qualitas.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	@Qualifier("db1.datasource")
	private DataSource dataSource;
	
//	private String ldapURL = "ldap://sariroti.com";
//
//	private String ldapDomain = "sariroti.com";
	
	@Autowired
	private ApplicationContext applicationContext;

//	@Bean
//	public SchedulerFactoryBean quartzScheduler() {
//		SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();
//		quartzScheduler.setDataSource(dataSource);
//		quartzScheduler.setConfigLocation(new ClassPathResource("quartz.properties"));
//
//		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
//		jobFactory.setApplicationContext(applicationContext);
//		quartzScheduler.setJobFactory(jobFactory);
//
//		return quartzScheduler;
//	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/ng-table/**", "/webjars/**", "/bootstrap/**", "/modules/**", "/scripts/**",
//				"/index.html", "/assets/**", "/", "/api/v1/routes", "/favicon.ico");
		web.ignoring().antMatchers("/ng-table/**", "/webjars/**", "/bootstrap/**", "/modules/**", "/scripts/**",
				"/index.html", "/assets/**", "/", "/api/v1/routes", "/api/v1/home/**", "/api/v1/signUp/**", "/favicon.ico", 
				"/api/v1/file/getFile");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//		 auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
				.usersByUsernameQuery("SELECT username, password, enabled FROM users where userlogin = ?")
				.authoritiesByUsernameQuery("select username,authority from authority_members where username = ?")
				.groupAuthoritiesByUsername(
						"select g.id, g.group_name, ga.authority from groups g, group_members gm, group_authorities ga where gm.username = ? and g.id = ga.group_id and g.id = gm.group_id");
//		auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider());
	}
	
//	@Bean
//	public ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
//		ActiveDirectoryLdapAuthenticationProvider adProvider = new ActiveDirectoryLdapAuthenticationProvider(ldapDomain, ldapURL);
//		adProvider.setConvertSubErrorCodesToExceptions(true);
//		adProvider.setUseAuthenticationRequestCredentials(true);
//		adProvider.setSearchFilter("(&(objectClass=user)(userPrincipalName={0}))");
//		adProvider.setAuthoritiesMapper(grantedAuthoritiesMapper());
//
//		return adProvider;
//	}

	@Bean
	public ActiveDirectoryGrantedAuthoritiesMapper grantedAuthoritiesMapper() {
		return new ActiveDirectoryGrantedAuthoritiesMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

//	@Bean
//	@Primary
//	@ConfigurationProperties(prefix="spring.datasource")
//	public DataSource dataSource() {
//	    return DataSourceBuilder.create().build();
//	}
	
//	
//	@Bean(name="secondSessionFactory")
//	public HibernateJpaSessionFactoryBean secondSessionFactory() {
//		HibernateJpaSessionFactoryBean jpaSession();
//		return jpaSession;
//	}
}
