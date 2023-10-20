package in.eureka.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@Order(200)
public class SecurityConfig {

	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().anyRequest().permitAll();
		http.httpBasic().disable();
//		http.formLogin().disable();

//		http.csrf().disable().authorizeRequests().antMatchers("/*").hasRole("*").and().httpBasic().disable();

	}


}
